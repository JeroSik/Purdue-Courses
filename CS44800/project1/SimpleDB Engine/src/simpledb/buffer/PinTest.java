package simpledb.buffer;

import simpledb.server.SimpleDB;
import simpledb.file.*;
import java.util.*;

public class PinTest {
	private static Map<BlockId,Buffer> buffs = new HashMap<>();
	private static BufferMgr bm;

	public static void main(String args[]) throws Exception {		
		for (int i = 1000; i <= 10000; i += 1000) {
			test_pinning(100, i);
		}
	}
	
	private static void test_pinning(int block_size, int buffer_size) {
		SimpleDB db = new SimpleDB("pin_test", block_size, buffer_size);
		bm = db.bufferMgr();
		
		final long start = System.currentTimeMillis();
		pin_and_unpin(buffer_size);
		final long end = System.currentTimeMillis();

//		System.out.printf("Buffer size: %d\tTime (ms): %d\n", buffer_size, end - start);
	}
	
	private static void pin_and_unpin(int n) {
		for (int i = 0; i < n; i++) {
			pinBuffer(i);
		}

		for (int i = 0; i < n; i++) {
			unpinBuffer(i);
		}
	}

	private static void pinBuffer(int i) {
		BlockId blk = new BlockId("test", i);
		Buffer buff = bm.pin(blk);
		buffs.put(blk, buff);
	}

	private static void unpinBuffer(int i) {
		BlockId blk = new BlockId("test", i);
		Buffer buff = buffs.remove(blk);
		bm.unpin(buff);
	}
}

