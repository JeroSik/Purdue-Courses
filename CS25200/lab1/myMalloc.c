#include <errno.h>
#include <pthread.h>
#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include "myMalloc.h"
#include "printing.h"
 
/* Due to the way assert() prints error messges we use out own assert function
 * for deteminism when testing assertions
 */
#ifdef TEST_ASSERT
  inline static void assert(int e) {
    if (!e) {
      const char * msg = "Assertion Failed!\n";
      write(2, msg, strlen(msg));
      exit(1);
    }
  }
#else
  #include <assert.h>
#endif

/*
 * Mutex to ensure thread safety for the freelist
 */
static pthread_mutex_t mutex;

/*
 * Array of sentinel nodes for the freelists
 */
header freelistSentinels[N_LISTS];

/*
 * Pointer to the second fencepost in the most recently allocated chunk from
 * the OS. Used for coalescing chunks
 */
header * lastFencePost;

/*
 * Pointer to maintian the base of the heap to allow printing based on the
 * distance from the base of the heap
 */ 
void * base;

/*
 * List of chunks allocated by  the OS for printing boundary tags
 */
header * osChunkList [MAX_OS_CHUNKS];
size_t numOsChunks = 0;

/*
 * direct the compiler to run the init function before running main
 * this allows initialization of required globals
 */
static void init (void) __attribute__ ((constructor));

// Helper functions for manipulating pointers to headers
static inline header * get_header_from_offset(void * ptr, ptrdiff_t off);
static inline header * get_left_header(header * h);
static inline header * ptr_to_header(void * p);

// Helper functions for allocating more memory from the OS
static inline void initialize_fencepost(header * fp, size_t left_size);
static inline void insert_os_chunk(header * hdr);
static inline void insert_fenceposts(void * raw_mem, size_t size);
static header * allocate_chunk(size_t size);

// Helper functions for freeing a block
static inline void deallocate_object(void * p);

// Helper functions for allocating a block
static inline header * allocate_object(size_t raw_size);

// Helper functions for verifying that the data structures are structurally 
// valid
static inline header * detect_cycles();
static inline header * verify_pointers();
static inline bool verify_freelist();
static inline header * verify_chunk(header * chunk);
static inline bool verify_tags();

static void init();

static bool isMallocInitialized;

// My helper functions for finding variables
static int findIndex(size_t size);
static size_t findSize(size_t raw_size);

// My helper functions for creating chunks
static header * createChunk();

// My helper functions for coalescing blocks
static void coalesceRight(header * free_block, header * right_block);
static void coalesceLeft(header * free_block, header * left_block, header * right_block);

// My helper functions for inserting into the freelist
static void relocate(header * block);

/**
 * @brief Helper function to retrieve a header pointer from a pointer and an 
 *        offset
 *
 * @param ptr base pointer
 * @param off number of bytes from base pointer where header is located
 *
 * @return a pointer to a header offset bytes from pointer
 */
static inline header * get_header_from_offset(void * ptr, ptrdiff_t off) {
	return (header *)((char *) ptr + off);
}

/**
 * @brief Helper function to get the header to the right of a given header
 *
 * @param h original header
 *
 * @return header to the right of h
 */
header * get_right_header(header * h) {
	return get_header_from_offset(h, get_size(h));
}

/**
 * @brief Helper function to get the header to the left of a given header
 *
 * @param h original header
 *
 * @return header to the right of h
 */
inline static header * get_left_header(header * h) {
  return get_header_from_offset(h, -h->left_size);
}

/**
 * @brief Fenceposts are marked as always allocated and may need to have
 * a left object size to ensure coalescing happens properly
 *
 * @param fp a pointer to the header being used as a fencepost
 * @param left_size the size of the object to the left of the fencepost
 */
inline static void initialize_fencepost(header * fp, size_t left_size) {
	set_state(fp,FENCEPOST);
	set_size(fp, ALLOC_HEADER_SIZE);
	fp->left_size = left_size;
}

/**
 * @brief Helper function to maintain list of chunks from the OS for debugging
 *
 * @param hdr the first fencepost in the chunk allocated by the OS
 */
inline static void insert_os_chunk(header * hdr) {
  if (numOsChunks < MAX_OS_CHUNKS) {
    osChunkList[numOsChunks++] = hdr;
  }
}

/**
 * @brief given a chunk of memory insert fenceposts at the left and 
 * right boundaries of the block to prevent coalescing outside of the
 * block
 *
 * @param raw_mem a void pointer to the memory chunk to initialize
 * @param size the size of the allocated chunk
 */
inline static void insert_fenceposts(void * raw_mem, size_t size) {
  // Convert to char * before performing operations
  char * mem = (char *) raw_mem;

  // Insert a fencepost at the left edge of the block
  header * leftFencePost = (header *) mem;
  initialize_fencepost(leftFencePost, ALLOC_HEADER_SIZE);

  // Insert a fencepost at the right edge of the block
  header * rightFencePost = get_header_from_offset(mem, size - ALLOC_HEADER_SIZE);
  initialize_fencepost(rightFencePost, size - 2 * ALLOC_HEADER_SIZE);
}

/**
 * @brief Allocate another chunk from the OS and prepare to insert it
 * into the free list
 *
 * @param size The size to allocate from the OS
 *
 * @return A pointer to the allocable block in the chunk (just after the 
 * first fencpost)
 */
static header * allocate_chunk(size_t size) {
  void * mem = sbrk(size);
  
  insert_fenceposts(mem, size);
  header * hdr = (header *) ((char *)mem + ALLOC_HEADER_SIZE);
  set_state(hdr, UNALLOCATED);
  set_size(hdr, size - 2 * ALLOC_HEADER_SIZE);
  hdr->left_size = ALLOC_HEADER_SIZE;
  return hdr;
}

/**
 * @brief Find the appropriate index given the requested size
 *
 * @param size The size requested
 *
 * @return An integer that marks the index in the freelist sentinels
 */
static int findIndex(size_t size) {
  int index = size / 8 - 3;
  if (index >= N_LISTS) {
    return N_LISTS - 1;
  } else {
    return index;
  }
}

/**
 * @brief Helper allocate an object given a raw request size from the user
 *
 * @param raw_size number of bytes the user needs
 *
 * @return A block satisfying the user's request
 */
static inline header * allocate_object(size_t raw_size) {
  // Check if zero
  if (raw_size == 0) { return NULL; }

  // Get required block size
  size_t actual_size = findSize(raw_size);
  
  // Find appropriate free list to look for a block to allocate
  int index = findIndex(actual_size);

  // Check the next freelist until there is a block and if there is no free list, just go to N_LISTS - 1
  header * freelist = &freelistSentinels[index];
  while (freelist->next == freelist && index < N_LISTS - 1) {
    index++;
    freelist = &freelistSentinels[index];
  }

  // Get the first free UNALLOCATED block and get the size
  header * free_block = freelist->next;
  size_t block_size = get_size(free_block);
  // If searching through the final list, then find the search through the blocks
  if (index == N_LISTS - 1) {
    while (actual_size > block_size) {
      free_block = free_block->next;
      block_size = get_size(free_block);
      // If no blocks are free, then add additional OS chunks
      if (free_block == freelist) {
        // Add enough OS chunks to handle memory
        while (actual_size > block_size) {
          free_block = createChunk();
          block_size = get_size(free_block);
        }
        break;
      }
    }
  }
 
  // Check to see whether to split block or not 
  if (block_size - actual_size >= sizeof(header)) {
    // Get adjacent blocks
    header * split_block = get_header_from_offset(free_block, block_size - actual_size);
    header * right_block = get_right_header(free_block);

    // Set sizes appropriately
    set_size(free_block, block_size - actual_size);
    set_size(split_block, actual_size);
    split_block->left_size = get_size(free_block);
    right_block->left_size = get_size(split_block);

    // Set state to ALLOCATED
    set_state(split_block, ALLOCATED);

    // Check if remaining block size is no longer appropriate for the current list
    int new_index = findIndex(get_size(free_block));
    // If block needs to be moved
    if (new_index != index) {
      header * new_freelist = &freelistSentinels[new_index];
      // Remove from freelist
      free_block->next->prev = free_block->prev;
      free_block->prev->next = free_block->next; 
      // Relocate the block to the appropriate freelist
      relocate(free_block);
    }

    return split_block;
  // If you don't split, make up entire block
  } else {
    // Remove from freelist
    free_block->prev->next = free_block->next;
    free_block->next->prev = free_block->prev;
    // Set to ALLOCATED
    set_state(free_block, ALLOCATED);
    return free_block;
  }
}

/**
 * @breif Creates a new OS chunk and gets the new free block
 *
 * @return Header pointer to the new free block created from the OS chunk
 */
static header * createChunk() {
  // Allocate an additional chunk
  header * chunk = allocate_chunk(ARENA_SIZE);
  
  // Get neighboring fenceposts
  header * newLeftFencePost = get_left_header(chunk);
  header * newRightFencePost = get_right_header(chunk);
  header * h = lastFencePost;
  header * checkContinuous = get_header_from_offset(h, ALLOC_HEADER_SIZE);

  // Check if continuous
  if (newLeftFencePost == checkContinuous) {
    // Remove fenceposts
    set_size_and_state(h, ALLOC_HEADER_SIZE * 2 + get_size(chunk), UNALLOCATED);

    // Check if left coalesce is needed
    header * left_h = get_left_header(h);
    // If you need to coalesce
    if (get_state(left_h) == UNALLOCATED) {
      // Calculate old index
      int old_index = findIndex(get_size(left_h));
      // Coalesce Left
      coalesceLeft(h, left_h, get_right_header(h));
      // Compare new index and old index to see if block needs to be relocated
      int new_index = findIndex(get_size(left_h));
      if (old_index != new_index) {
        // Remove from freelist
        left_h->next->prev = left_h->prev;
        left_h->prev->next = left_h->next;
        // Relocate to new freelist
        relocate(left_h); 
      }
      // Update lastFencePost
      lastFencePost = newRightFencePost;
      return left_h;
    // If you don't need to coalesce
    } else {
      // Update sizes
      h->left_size = get_size(left_h);
      get_right_header(h)->left_size = get_size(h);
      // Relocate to freelist
      relocate(h);
      // Update lastFencePost
      lastFencePost = newRightFencePost;
      return h;
    }
  // If not consecutive
  } else {
    // Insert into the new OS chunk list
    insert_os_chunk(newLeftFencePost);
    // Update lastFencePost
    lastFencePost = newRightFencePost;
    // Relocate to new freelist
    relocate(chunk);
  }
  return chunk;
}

/**
 * @brief Gets the actual size requested from the raw size
 *
 * @param raw_size The raw size of the block requested
 *
 * @return A size_t of the actual_size of the block requested
 */
static size_t findSize(size_t raw_size) {
  // Calculate the actual request size by adding size of the required metadata
  size_t actual_size = raw_size + ALLOC_HEADER_SIZE;
  
  // Round the allocation size up to the next 8-byte modulo
  if (actual_size % 8 != 0) {
    actual_size += 8 - (actual_size % 8);
  }

  // Actual request size is larger of either the size calculated or the size of the header struct
  if (actual_size < 2 * ALLOC_HEADER_SIZE) {
    return 2 * ALLOC_HEADER_SIZE;
  } else {
    return actual_size;
  }
}

/**
 * @brief Helper to get the header from a pointer allocated with malloc
 *
 * @param p pointer to the data region of the block
 *
 * @return A pointer to the header of the block
 */
static inline header * ptr_to_header(void * p) {
  return (header *)((char *) p - ALLOC_HEADER_SIZE); //sizeof(header));
}

/**
 * @brief Helper to manage deallocation of a pointer returned by the user
 *
 * @param p The pointer returned to the user by a call to malloc
 */
static inline void deallocate_object(void * p) {
  // Check if pointer is null
  if (p == NULL) { return; } 

  // Calculate the location of the header
  header * free_block = ptr_to_header(p);
  // Check if free pointer is already UNALLOCATED
  if (get_state(free_block) == UNALLOCATED) {
    printf("Double Free Detected\nAssertion Failed!\n");
    exit(1);
  }

  // Get adjacent blocks
  header * right_block = get_right_header(free_block);
  header * left_block = get_left_header(free_block);

  // Check whether right and left block are free
  int right_free = get_state(right_block) == UNALLOCATED ? 1 : 0;
  int left_free = get_state(left_block) == UNALLOCATED ? 1 : 0;
  
  // Change free_block to UNALLOCATED
  set_state(free_block, UNALLOCATED);

  // Neither are free
  if (!right_free && !left_free) {
    // Relocate to freelist
    relocate(free_block);
  // Only right is free
  } else if (right_free && !left_free) {
    // Get old index
    int old_index = findIndex(get_size(free_block));
    // Coalesce Right
    coalesceRight(free_block, right_block);
    // Compare new and old index
    int new_index = findIndex(get_size(free_block));
    if (old_index != new_index) {
      // Remove from current freelist
      free_block->next->prev = free_block->prev;
      free_block->prev->next = free_block->next;
      // Relocate to freelist
      relocate(free_block);
    }
  // Only left is free
  } else if (!right_free && left_free) {
    // Get old index
    int old_index = findIndex(get_size(left_block));
    // Coalesce Left
    coalesceLeft(free_block, left_block, right_block);
    // COmpare new and old index
    int new_index = findIndex(get_size(left_block));
    if (old_index != new_index) {
      // Remove from current freelist
      left_block->next->prev = left_block->prev;
      left_block->prev->next = left_block->next;
      // Relocate to freelist
      relocate(left_block);
    }
  // Both are free
  } else if (right_free && left_free) {
    // Get old index
    int old_index = findIndex(get_size(left_block));
    // Coalesce Right
    coalesceRight(free_block, right_block);
    // Remove from freelist
    free_block->next->prev = free_block->prev;
    free_block->prev->next = free_block->next; 
    // Coalesce Left
    coalesceLeft(free_block, left_block, get_right_header(free_block));

    // Compare new and old index
    int new_index = findIndex(get_size(left_block));
    if (old_index != new_index) {
      // Remove from freelist
      left_block->next->prev = left_block->prev;
      left_block->prev->next = left_block->next;
      //Relocate to freelist
      relocate(left_block);
    }
  }
}

/**
 * @brief Coalesce the right block from the current block
 *
 * @param free_block Header pointer to current block
 *        right_block Header pointer to the right of the current block
 */
static void coalesceRight(header * free_block, header * right_block) {
  // Get far right block
  header * temp = get_right_header(right_block);
  // Set sizes appropriately
  set_size(free_block, get_size(free_block) + get_size(right_block));
  temp->left_size = get_size(free_block);
  // Move freelist pointers and replace right block with current block
  free_block->next = right_block->next;
  free_block->prev = right_block->prev;
  right_block->next->prev = free_block;
  right_block->prev->next = free_block;
}

/**
 * @brief Coalesce the left block from the current block
 *
 * @param free_block Header pointer to current block
 *        left_block Header pointer to the left of the current block
 *        right_block Header pointer to the right of the current block
 */
static void coalesceLeft(header * free_block, header * left_block, header * right_block) {
  // Set size appropriately
  set_size(left_block, get_size(left_block) + get_size(free_block));
  right_block->left_size =  get_size(left_block);
}

/**
 * @brief Relocate the block to its new freelist
 *
 * @param block Header pointer to current block
 */
static void relocate(header * block) {
  // Get new index
  int index = findIndex(get_size(block));
  // Get new freelist
  header * new_freelist = &freelistSentinels[index];
  // Insert block into the front of freelist
  block->next = new_freelist->next;
  block->prev = new_freelist;
  block->next->prev = block;
  new_freelist->next = block;
}

/**
 * @brief Helper to detect cycles in the free list
 * https://en.wikipedia.org/wiki/Cycle_detection#Floyd's_Tortoise_and_Hare
 *
 * @return One of the nodes in the cycle or NULL if no cycle is present
 */
static inline header * detect_cycles() {
  for (int i = 0; i < N_LISTS; i++) {
    header * freelist = &freelistSentinels[i];
    for (header * slow = freelist->next, * fast = freelist->next->next; 
         fast != freelist; 
         slow = slow->next, fast = fast->next->next) {
      if (slow == fast) {
        return slow;
      }
    }
  }
  return NULL;
}

/**
 * @brief Helper to verify that there are no unlinked previous or next pointers
 *        in the free list
 *
 * @return A node whose previous and next pointers are incorrect or NULL if no
 *         such node exists
 */
static inline header * verify_pointers() {
  for (int i = 0; i < N_LISTS; i++) {
    header * freelist = &freelistSentinels[i];
    for (header * cur = freelist->next; cur != freelist; cur = cur->next) {
      if (cur->next->prev != cur || cur->prev->next != cur) {
        return cur;
      }
    }
  }
  return NULL;
}

/**
 * @brief Verify the structure of the free list is correct by checkin for 
 *        cycles and misdirected pointers
 *
 * @return true if the list is valid
 */
static inline bool verify_freelist() {
  header * cycle = detect_cycles();
  if (cycle != NULL) {
    fprintf(stderr, "Cycle Detected\n");
    print_sublist(print_object, cycle->next, cycle);
    return false;
  }

  header * invalid = verify_pointers();
  if (invalid != NULL) {
    fprintf(stderr, "Invalid pointers\n");
    print_object(invalid);
    return false;
  }

  return true;
}

/**
 * @brief Helper to verify that the sizes in a chunk from the OS are correct
 *        and that allocated node's canary values are correct
 *
 * @param chunk AREA_SIZE chunk allocated from the OS
 *
 * @return a pointer to an invalid header or NULL if all header's are valid
 */
static inline header * verify_chunk(header * chunk) {
	if (get_state(chunk) != FENCEPOST) {
		fprintf(stderr, "Invalid fencepost\n");
		print_object(chunk);
		return chunk;
	}
	
	for (; get_state(chunk) != FENCEPOST; chunk = get_right_header(chunk)) {
		if (get_size(chunk)  != get_right_header(chunk)->left_size) {
			fprintf(stderr, "Invalid sizes\n");
			print_object(chunk);
			return chunk;
		}
	}
	
	return NULL;
}

/**
 * @brief For each chunk allocated by the OS verify that the boundary tags
 *        are consistent
 *
 * @return true if the boundary tags are valid
 */
static inline bool verify_tags() {
  for (size_t i = 0; i < numOsChunks; i++) {
    header * invalid = verify_chunk(osChunkList[i]);
    if (invalid != NULL) {
      return invalid;
    }
  }

  return NULL;
}

/**
 * @brief Initialize mutex lock and prepare an initial chunk of memory for allocation
 */
static void init() {
  // Initialize mutex for thread safety
  pthread_mutex_init(&mutex, NULL);

#ifdef DEBUG
  // Manually set printf buffer so it won't call malloc when debugging the allocator
  setvbuf(stdout, NULL, _IONBF, 0);
#endif // DEBUG

  // Allocate the first chunk from the OS
  header * block = allocate_chunk(ARENA_SIZE);

  header * prevFencePost = get_header_from_offset(block, -ALLOC_HEADER_SIZE);
  insert_os_chunk(prevFencePost);

  lastFencePost = get_header_from_offset(block, get_size(block));

  // Set the base pointer to the beginning of the first fencepost in the first
  // chunk from the OS
  base = ((char *) block) - ALLOC_HEADER_SIZE; //sizeof(header);

  // Initialize freelist sentinels
  for (int i = 0; i < N_LISTS; i++) {
    header * freelist = &freelistSentinels[i];
    freelist->next = freelist;
    freelist->prev = freelist;
  }

  // Insert first chunk into the free list
  header * freelist = &freelistSentinels[N_LISTS - 1];
  freelist->next = block;
  freelist->prev = block;
  block->next = freelist;
  block->prev = freelist;
}

/* 
 * External interface
 */
void * my_malloc(size_t size) {
  pthread_mutex_lock(&mutex);
  header * hdr = allocate_object(size); 
  pthread_mutex_unlock(&mutex);
  // Changed return to hdr to hdr -> data
  // Check if zero
  if (hdr == NULL) {
    return NULL;
  }
  return hdr -> data;
}

void * my_calloc(size_t nmemb, size_t size) {
  return memset(my_malloc(size * nmemb), 0, size * nmemb);
}

void * my_realloc(void * ptr, size_t size) {
  void * mem = my_malloc(size);
  memcpy(mem, ptr, size);
  my_free(ptr);
  return mem; 
}

void my_free(void * p) {
  pthread_mutex_lock(&mutex);
  deallocate_object(p);
  pthread_mutex_unlock(&mutex);
}

bool verify() {
  return verify_freelist() && verify_tags();
}
