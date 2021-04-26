package simpledb.tx;

import simpledb.buffer.BufferMgr;
import simpledb.file.*;
import simpledb.log.LogMgr;
import simpledb.server.SimpleDB;
import simpledb.tx.concurrency.LockAbortException;

public class DeadlockTest {
	private static FileMgr fm;
	private static LogMgr lm;
	private static BufferMgr bm;

	public static void main(String[] args) {
		//initialize the database system
		SimpleDB db = new SimpleDB("deadlocktest", 400, 8); 
		fm = db.fileMgr();
		lm = db.logMgr();
		bm = db.bufferMgr();

		A a = new A(); new Thread(a).start();
		B b = new B(); new Thread(b).start();
	}

	static class A implements Runnable { 
		public void run() {
			Transaction txA = null;
			try {
				txA = new Transaction(fm, lm, bm);
				System.out.println("Transaction A starts");
				BlockId blk1 = new BlockId("testfile", 1);
				BlockId blk2 = new BlockId("testfile", 2);
				txA.pin(blk1);
				txA.pin(blk2);

				System.out.println("Tx A: request xlock 1");
				txA.setInt(blk1, 0, 0, false);
				System.out.println("Tx A: receive xlock 1");
				Thread.sleep(1000);
				System.out.println("Tx A: request xlock 2");
				txA.setInt(blk2, 0, 0, false);
				System.out.println("Tx A: receive xlock 2");

				txA.commit();
				System.out.println("Tx A: commit");
			}
			catch(InterruptedException e) {
				System.out.println("Transaction A interrupted");
				txA.rollback();
			} catch(LockAbortException e) {
				System.out.println("Transaction A aborts");
				txA.rollback();
			}
			;
		}
	}

	static class B implements Runnable {
		public void run() {
			Transaction txB = null;
			try {
				txB = new Transaction(fm, lm, bm);
				System.out.println("Transaction B starts");
				BlockId blk1 = new BlockId("testfile", 1);
				BlockId blk2 = new BlockId("testfile", 2);
				txB.pin(blk1);
				txB.pin(blk2);

				System.out.println("Tx B: request xlock 2");
				txB.setInt(blk2, 0, 0, false);
				System.out.println("Tx B: receive xlock 2");
				Thread.sleep(1000);
				System.out.println("Tx B: request xlock 1");
				txB.setInt(blk1, 0, 0, false);
				System.out.println("Tx B: receive xlock 1");

				txB.commit();
				System.out.println("Tx B: commit");
			}
			catch(InterruptedException e) {
				System.out.println("Transaction B interrupted");
				txB.rollback();
			} catch(LockAbortException e) {
				System.out.println("Transaction B aborts");
				txB.rollback();
			}
		}
	}
}
