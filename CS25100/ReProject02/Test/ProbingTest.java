import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.experimental.runners.Enclosed;

import static org.junit.Assert.*;

/**
 * ProbingTest -- Project 2 -- CS251 Fall 2018
 *
 * Test case suite for QuadraticProbing.
 *
 * Written by Andrew Davis, drew@cs.purdue.edu
 * Last updated: 9/25/2018
 * All rights reserved.
 */
@RunWith(Enclosed.class)
public class ProbingTest {
  private static QuadraticProbing<String> hashTable;

  public static class hashTests {
    private static int capacity = 11;

    @Before
    public void setUp() {
      hashTable = new QuadraticProbing<>(capacity);
    }

    /**
     * Tests correctness of hash() implementation.
     */
    @Test(timeout = 100)
    public void test1() {
      String key = "GeorgeAdams";
      int expected = hashSolution(key, capacity);
      int actual = hashTable.hash(key, capacity);
      assertEquals("Make sure you have correctly implemented your hash() method.", expected, actual);
    }

    /**
     * Tests correctness of hash() implementation.
     */
    @Test(timeout = 100)
    public void test2() {
      String key = "DrGeorgeAdams";
      int expected = hashSolution(key, capacity);
      int actual = hashTable.hash(key, capacity);
      assertEquals("Make sure you have correctly implemented your hash() method.", expected, actual);
    }

  }

  public static class insertTests {

    @Before
    public void setUp() {
      hashTable = new QuadraticProbing<>();
    }

    /**
     * Tests a basic insertion case.
     */
    @Test(timeout = 100)
    public void test1() {
      String key = "GeorgeAdams";
      hashTable.insert(key);

      assertTrue("Make sure you have implemented your insert() and contains() methods.", hashTable.contains(key));

      int expectedNumOfProbes = 0;
      int actualNumOfProbes = hashTable.probe(key);
      assertEquals("Make sure you have implemented your probe() method.", expectedNumOfProbes, actualNumOfProbes);
    }

    /**
     * Tests if isActive() is implemented.
     */
    @Test(timeout = 100)
    public void test2() {
      String key = "SamuelWagstaff";
      hashTable.insert(key);

      assertTrue("Make sure you have implemented your isActive() method.", hashTable.isActive(hashTable.hash(key, 13)));
    }

    /**
     * Tests for two elements that hash to the same value.
     */
    @Test(timeout = 100)
    public void test3() {
      hashTable.insert("ee");
      hashTable.insert("rr");

      int expectedNumOfProbes = 1;
      int actualNumOfProbes = hashTable.probe("rr");

      assertEquals("Make sure you are implementing quadratic probing in your insert() method.", expectedNumOfProbes, actualNumOfProbes);
    }

  }

  public static class removeTests {

    @Before
    public void setUp() {
      hashTable = new QuadraticProbing<>();
    }

    /**
     * Tests a basic removal case.
     */
    @Test(timeout = 100)
    public void test1() {
      String key = "GeorgeAdams";
      hashTable.insert(key);
      int position = hashTable.hash("GeorgeAdams", 13);
      hashTable.remove(key);

      assertFalse("Make sure you are setting the activity of an element to false when it is removed (lazy deletion).", hashTable.isActive(position));

      assertFalse("Make sure you are not including inactive elements when searching in contains().", hashTable.contains(key));
    }

    /**
     * Tests re-insertion of a previously deleted element.
     */
    @Test(timeout = 100)
    public void test2() {
      String key = "GeorgeAdams";
      hashTable.insert(key);
      int hashValue = hashSolution("GeorgeAdams", 13);
      hashTable.remove(key);
      hashTable.insert(key);

      assertTrue("Make sure you are overwriting an inactive element in insert() if a new element's hash value is equal to that position.", hashTable.isActive(hashValue));
    }

    /**
     * Tests re-insertion of a previously deleted element when another element is active in its hashed position.
     */
    @Test(timeout = 100)
    public void test3() {
      hashTable.insert("ee");
      hashTable.insert("rr");
      int hashValue = hashSolution("rr", 13);
      int position = hashValue + 1;
      hashTable.remove("rr");
      hashTable.insert("rr");

      assertTrue("Make sure you are overwriting an inactive element in insert() if a new element probes to that position.", hashTable.isActive(position));
    }

    /**
     * Tests swapping of inactive elements with searched keys in contains().
     */
    @Test(timeout = 100)
    public void test4() {
      hashTable.insert("ee");
      hashTable.insert("rr");
      hashTable.remove("ee");
      hashTable.contains("rr");

      assertTrue("Make sure you are swapping a key with the first inactive position probed in contains() if possible.", hashTable.isActive(hashSolution("rr", 13)));

    }
  }

  public static class rehashTests {

    @Before
    public void setUp() {
      hashTable = new QuadraticProbing<>(3);
    }

    @Test(timeout = 1000)
    public void test1() {
      hashTable.insert("Adams");

      int oldGeorgeHashValue = hashTable.hash("Adams", 3);

      hashTable.insert("Wagstaff");

      int expectedNewAdamsHashValue = hashSolution("Adams", 7);
      int actualNewAdamsHashValue = hashTable.hash("Adams", 7);

      assertEquals("Make sure you have implemented your rehash() method.", expectedNewAdamsHashValue, actualNewAdamsHashValue);

      assertNotEquals("Make sure you are re-hashing the keys when calling rehash().", oldGeorgeHashValue, actualNewAdamsHashValue);
    }
  }

  /**
   * Hashing solution given in the handout.
   * @param key the key to be hashed
   * @return the hash value of the key
   */
  private static int hashSolution(String key, int capacity) {
    int hashcode = 0;

    for (int i = 0; i < key.length(); i++) {
      hashcode = (37 * hashcode + key.charAt(i)) % capacity;
    }

    return hashcode % capacity;
  }
}
