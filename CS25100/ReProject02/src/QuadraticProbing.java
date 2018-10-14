/**
 * Project 02
 *
 * An infix arithmetic expression evaluator
 *
 * @author Youngsik Yoon, yoon161@purdue.edu
 * @version 9/27/2018
 *
 */

/* Quadratic Probing */
public class QuadraticProbing<AnyType> {
	private static final int DEFAULT_TABLE_SIZE = 13;
	private HashEntry<AnyType> [ ] array; // The array of elements
	private int size;

	public static class HashEntry<AnyType> {
		/* Initialize the entries here. You can write a constructor for the same */
		public AnyType  element;
		public boolean isActive;  // For Lazy deletion

		// Initialize a HashEntry variable
		public HashEntry(AnyType element, boolean isActive) {
			this.element = element;
			this.isActive = isActive;
		}

		public String toString() {
			if(this.element!=null)
				return (String) element;
			else
				return "NULL";
		}
	}

	/* Construct the hash table */
	public QuadraticProbing( ) {
		this( DEFAULT_TABLE_SIZE );
	}

	/* Construct the hash table */
	@SuppressWarnings("unchecked")
	public QuadraticProbing (int size) throws IllegalArgumentException {
		/* allocate memory to hash table */
		// Check if given size is allowed
		if (size < 0) {
			throw new IllegalArgumentException();
		} else {
			array = new HashEntry[size];
			this.size = 0;
		}
	}


	/* Return true if currentPos exists and is active - Lazy Deletion*/
	public boolean isActive(int position) {
		return array[position].isActive;
	}

	/* Find an item in the hash table. */
	public boolean contains( AnyType x ) {
		/* Should return the active status of key in hash table */
		int index = hash(x.toString(), array.length);
		int probe = 0;
		int first = 0;
		boolean found = false;
		while (array[index] != null) {
			if (!found && !array[index].isActive) {
				first = index;
				found = true;
			}
			if (array[index].element.equals(x) && array[index].isActive) {
				array[first].element = x;
				array[first].isActive = true;
				array[index].isActive = false;
				return true;
			}
			probe++;
			index = (hash(x.toString(), array.length) + probe * probe) % array.length;
		}
		return false;
	}


	/* Insert into the Hash Table */
	/* Insert an element */
	public void insert( AnyType x ) {
		// Find chain containing key
		int index = hash(x.toString(), array.length);
		int probe = 0;
		while (array[index] != null && array[index].isActive) {
			probe++;
			index = (hash(x.toString(), array.length) + probe * probe) % array.length;
		}
		array[index] = new HashEntry<>(x, true);
		size++;
		if (size >= 0.4 * array.length) {
			rehash();
		}
	}


	/* Remove from the hash table. */
	public void remove( AnyType x ) {
		/* Lazy Deletion*/
		int index = hash(x.toString(), array.length);
		int probe = 0;
		while (array[index] != null) {
			if (array[index].element.equals(x)) {
				size--;
				array[index].isActive = false;
				break;
			}
			probe++;
			index = (hash(x.toString(), array.length) + probe * probe) % array.length;
		}
	}


	/* Rehashing for quadratic probing hash table */
	@SuppressWarnings("unchecked")
	private void rehash( ) {
		// Resize the hashtable such that the new size is the first prime number greater than two times the current size.
		// For example, if current size is 5, then the new size would be 11.
		HashEntry<AnyType>[] newArray = array.clone();
		int oldSize = this.size;
		int newCap = nextPrime(array.length * 2);
		array = new HashEntry[newCap];

		for (int i = 0; i < newArray.length; i++) {
			if (newArray[i] != null && newArray[i].isActive) {
				insert(newArray[i].element);
			}
		}
		this.size = oldSize;

		// HashEntry<AnyType> [] newArray = new HashEntry[nextPrime(array.length * 2)];

	}

	public int nextPrime(int num) {
		double temp = Math.sqrt(num);
		boolean found;
		while (true) {
			found = true;
			for (int i = 2; i < num; i++) {
				if (num % i == 0) {
					num++;
					found = false;
					break;
				}
			}
			if (found) {
				return num;
			}
		}
	}

	/* Hash Function */
	public int hash( String key, int tableSize ) {
		/**  Make sure to type cast "AnyType"  to string 
		 before calling this method - ex: if "x" is of "AnyType",
		 you should invoke this function as hash((x.toString()), tableSize) */

		// Compute the hash code
		int hashVal = 0;
		// Use Horner's rule to compute the hashval and return it.
		for (int i = 0; i < key.length(); i++) {
			hashVal = (37 * hashVal + (int) key.charAt(i)) % tableSize;
		}
		return hashVal;
	}

	/* Return the number of probes encountered for a key */
	public int probe(AnyType x) {
		int index = hash(x.toString(), array.length);
		int probe = 0;
		while (!array[index].element.equals(x)) {
			probe++;
			index = (hash(x.toString(), array.length) + probe * probe) % array.length;
		}
		return probe;
	}
}

