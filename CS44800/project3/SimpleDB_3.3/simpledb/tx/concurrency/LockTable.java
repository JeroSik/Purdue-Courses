package simpledb.tx.concurrency;

import java.util.*;
import simpledb.file.BlockId;

/**
 * The lock table, which provides methods to lock and unlock blocks.
 * If a transaction requests a lock that causes a conflict with an
 * existing lock, then that transaction is placed on a wait list.
 * There is only one wait list for all blocks.
 * When the last lock on a block is unlocked, then all transactions
 * are removed from the wait list and rescheduled.
 * If one of those transactions discovers that the lock it is waiting for
 * is still locked, it will place itself back on the wait list.
 * @author Edward Sciore
 */
class LockTable {
	private static final long MAX_TIME = 10000; // 10 seconds

	private Map<BlockId,HashSet<Integer>> locks = new HashMap<BlockId,HashSet<Integer>>();
	private HashMap<Long,Integer> txMap = new HashMap<Long,Integer>();

	/**
	 * Grant an SLock on the specified block.
	 * If an XLock exists when the method is called,
	 * then the calling thread will be placed on a wait list
	 * until the lock is released.
	 * If the thread remains on the wait list for a certain 
	 * amount of time (currently 10 seconds),
	 * then an exception is thrown.
	 * @param blk a reference to the disk block
	 */
	public synchronized void sLock(BlockId blk, int txnum) {
		try {
			txMap.put(Thread.currentThread().getId(), txnum);
			long timestamp = System.currentTimeMillis();
			while (hasXlock(blk, txnum) && !waitingTooLong(timestamp)) {
				if (isOldest(blk, txnum)) {
					// TODO: since oldest, wound the younger
					HashSet<Integer> youngertx = getYoungerTransactions(blk, txnum);
					Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
					for (Thread t : threadSet) {
						if (youngertx.contains(txMap.get(t.getId()))) {
							t.sleep(100);
							t.interrupt();
						}
					}
					addToLockList(blk, txnum);
					return;
//					throw new LockAbortException();
				} else {
					wait(MAX_TIME);
				}
			}
			if (hasXlock(blk, txnum)) {
				throw new LockAbortException();
			}
			addToLockList(blk, txnum);
		}
		catch(InterruptedException e) {
			throw new LockAbortException();
		}
	}

	/**
	 * Grant an XLock on the specified block.
	 * If a lock of any type exists when the method is called,
	 * then the calling thread will be placed on a wait list
	 * until the locks are released.
	 * If the thread remains on the wait list for a certain 
	 * amount of time (currently 10 seconds),
	 * then an exception is thrown.
	 * @param blk a reference to the disk block
	 */
	synchronized void xLock(BlockId blk, int txnum) {
		try {
			txMap.put(Thread.currentThread().getId(), txnum);
			long timestamp = System.currentTimeMillis();
			while ((hasOtherSLocks(blk, txnum) || hasXlock(blk, txnum)) && !waitingTooLong(timestamp)) {
				if (isOldest(blk, txnum)) {
					// TODO: since oldest, wound the younger
					HashSet<Integer> youngertx = getYoungerTransactions(blk, txnum);
					Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
					for (Thread t : threadSet) {
						if (youngertx.contains(txMap.get(t.getId()))) {
							t.sleep(100);
							t.interrupt();
						}
					}
					addToLockList(blk, txnum);
					return;
//					throw new LockAbortException();
				} else {
					wait(MAX_TIME);
				}
			}
			if (hasOtherSLocks(blk, txnum) || hasXlock(blk, txnum)) {
				throw new LockAbortException();
			}
			addToLockList(blk, -1 * txnum);
		}
		catch(InterruptedException e) {
			throw new LockAbortException();
		}
	}

	/**
	 * Release a lock on the specified block.
	 * If this lock is the last lock on that block,
	 * then the waiting transactions are notified.
	 * @param blk a reference to the disk block
	 */
	synchronized void unlock(BlockId blk, int txnum) {
		HashSet<Integer> transactionList = getTransactions(blk);
		transactionList.remove(txnum);
		transactionList.remove(-1 * txnum);

		if (transactionList.isEmpty()) {
			locks.remove(blk);
			notifyAll();
		}
	}

	private boolean isOldest(BlockId blk, int txnum) {
		HashSet<Integer> transactionList = getTransactions(blk);

		for (int tid : transactionList) {
			if (Math.abs(tid) < txnum) {
				return false;
			}
		}
		return true;
	}
	
	private HashSet<Integer> getYoungerTransactions(BlockId blk, int txnum) {
		HashSet<Integer> transactionList = getTransactions(blk);
		HashSet<Integer> ans = new HashSet<Integer>();

		for (int tid : transactionList) {
			if (Math.abs(tid) > txnum) {
				ans.add(Math.abs(tid));
			}
		}
				
		return ans;
	}

	private void addToLockList(BlockId blk, int txnum) {		
		if (!locks.containsKey(blk)) {
			locks.put(blk, new HashSet<Integer>());  
		}

		locks.get(blk).add(txnum);
	}

	private boolean hasXlock(BlockId blk, int txnum) {
		HashSet<Integer> transactionList = getTransactions(blk);

		for (int tid : transactionList) {
			if (tid < 0 && Math.abs(tid) != txnum) {
				return true;
			}
		}
		return false;
	}

	private boolean hasOtherSLocks(BlockId blk, int txnum) {
		HashSet<Integer> transactionList = getTransactions(blk);

		for (int tid : transactionList) {
			if (tid > 0 && Math.abs(tid) != txnum) {
				return true;
			}
		}
		return false;
	}

	private boolean waitingTooLong(long starttime) {
		return System.currentTimeMillis() - starttime > MAX_TIME;
	}

	private HashSet<Integer> getTransactions(BlockId blk) {
		HashSet<Integer> transactionList = locks.get(blk);
		return (transactionList == null) ? new HashSet<Integer>() : transactionList;
	}
}
