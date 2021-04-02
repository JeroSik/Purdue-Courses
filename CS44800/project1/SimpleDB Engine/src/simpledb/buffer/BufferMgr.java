package simpledb.buffer;

import java.util.*;
import simpledb.file.*;
import simpledb.log.LogMgr;

/**
 * Manages the pinning and unpinning of buffers to blocks.
 * 
 * @author Edward Sciore
 *
 */
public class BufferMgr {
	private Buffer[] bufferpool;
	private Hashtable<BlockId, Integer> buffertable; 								// Declare buffertable
	private int numAvailable;
	private static final long MAX_TIME = 10000; // 10 seconds

	/**
	 * Creates a buffer manager having the specified number of buffer slots. This
	 * constructor depends on a {@link FileMgr} and {@link simpledb.log.LogMgr
	 * LogMgr} object.
	 * 
	 * @param numbuffs the number of buffer slots to allocate
	 */
	public BufferMgr(FileMgr fm, LogMgr lm, int numbuffs) {
		buffertable = new Hashtable<BlockId, Integer>(); 							// Initialize buffertable
		bufferpool = new Buffer[numbuffs];
		numAvailable = numbuffs;
		for (int i = 0; i < numbuffs; i++)
			bufferpool[i] = new Buffer(fm, lm);
	}

	private class Tuple2<K, V> {													// Create tuple Data structure to return multiple types
		private K first;
		private V second;

		public Tuple2(K first, V second) {
			this.first = first;
			this.second = second;
		}

		public K getFirst() {
			return this.first;
		}

		public V getSecond() {
			return this.second;
		}
	}

	/**
	 * Returns the number of available (i.e. unpinned) buffers.
	 * 
	 * @return the number of available buffers
	 */
	public synchronized int available() {
		return numAvailable;
	}

	/**
	 * Flushes the dirty buffers modified by the specified transaction.
	 * 
	 * @param txnum the transaction's id number
	 */
	public synchronized void flushAll(int txnum) {
		for (Buffer buff : bufferpool)
			if (buff.modifyingTx() == txnum)
				buff.flush();
	}

	/**
	 * Unpins the specified data buffer. If its pin count goes to zero, then notify
	 * any waiting threads.
	 * 
	 * @param buff the buffer to be unpinned
	 */
	public synchronized void unpin(Buffer buff) {
		buff.unpin();
		if (!buff.isPinned()) {
			buffertable.remove(buff.block());										// Remove blockID, index pair from buffertable
			numAvailable++;
			notifyAll();
		}
	}

	/**
	 * Pins a buffer to the specified block, potentially waiting until a buffer
	 * becomes available. If no buffer becomes available within a fixed time period,
	 * then a {@link BufferAbortException} is thrown.
	 * 
	 * @param blk a reference to a disk block
	 * @return the buffer pinned to that block
	 */
	public synchronized Buffer pin(BlockId blk) {
		try {
			long timestamp = System.currentTimeMillis();
			Buffer buff = tryToPin(blk);
			while (buff == null && !waitingTooLong(timestamp)) {
				wait(MAX_TIME);
				buff = tryToPin(blk);
			}
			if (buff == null)
				throw new BufferAbortException();
			return buff;
		} catch (InterruptedException e) {
			throw new BufferAbortException();
		}
	}

	private boolean waitingTooLong(long starttime) {
		return System.currentTimeMillis() - starttime > MAX_TIME;
	}

	/**
	 * Tries to pin a buffer to the specified block. If there is already a buffer
	 * assigned to that block then that buffer is used; otherwise, an unpinned
	 * buffer from the pool is chosen. Returns a null value if there are no
	 * available buffers.
	 * 
	 * @param blk a reference to a disk block
	 * @return the pinned buffer
	 */
	private Buffer tryToPin(BlockId blk) {
		Buffer buff = findExistingBuffer(blk);
		if (buff == null) {
			Tuple2<Buffer, Integer> buff_index_pair = chooseUnpinnedBuffer();       // Get next unpinned buffer and index pair
			if (buff_index_pair == null) {
				return null;
			}

			buff = buff_index_pair.getFirst();										// Get unpinned buffer
			int buff_index = buff_index_pair.getSecond();							// Get index of unpinned buffer

			buff.assignToBlock(blk);
			buffertable.put(blk, buff_index);                                       // Insert block ID and index pair into table
		}
		if (!buff.isPinned())
			numAvailable--;
		buff.pin();
		return buff;
	}

	private Buffer findExistingBuffer(BlockId blk) {
		if (buffertable.containsKey(blk)) {                                         // Check if block ID exists
			int buff_index = buffertable.get(blk);                                  // Get index from block ID
			return bufferpool[buff_index];                                          // Return corresponding buffer
		}

		return null;                                                                // Return null if does not exist
	}

	private Tuple2<Buffer, Integer> chooseUnpinnedBuffer() {
		for (int i = 0; i < bufferpool.length; i++) {
			Buffer buff = bufferpool[i];
			if (!buff.isPinned()) {
                return new Tuple2<>(buff, i);                                       // Return the bufferpool index with buffer object
			}
		}

		return null;
	}
}