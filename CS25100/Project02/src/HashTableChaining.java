import java.util.ArrayList; 
import java.util.LinkedList;

/**
 * Project 02
 *
 * Implementations of a hash table
 *
 * @author Youngsik Yoon, yoon161@purdue.edu
 * @version date of completition
 *
 */

class Pair<K,V> {
    // The Pair class is intended to store key, value pairs.
    // It'll be helpful for part 1.2 of the assignment.
    public K key;
    public V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

/**************PART 1.2.1*******************/

public class HashTableChaining<K,V> {
    // Write your code for the hashtable with chaining here.
    // You are allowed to use arraylists and linked lists.
    private ArrayList<LinkedList<Pair<String, Integer>>> hashTable = new ArrayList<>();
    private int capacity;
    private int size;

    public HashTableChaining(int capacity) throws IllegalArgumentException {
        // Initialize your hashtable with capacity equal to the input capacity.
        if (capacity < 0) {
            throw new IllegalArgumentException();
        } else {
            hashTable = new ArrayList<>(capacity);
            size = 0;
            this.capacity = capacity;
            for (int i = 0; i < capacity; i++) {
                hashTable.add(new LinkedList<Pair<String, Integer>>());
            }
        }
    }

    public void insert(String key, int val) {
        // Insert the key into the hashtable if it is not already in the hashtable.
        int hashCode = hash(key);
        hashTable.get(hashCode).add(new Pair(key, val));
        size++;
        if (size() > 0.75 * capacity) {
            rehash();
        }
    }

    public void remove(String key) {
        // Remove key from the hashtable if it is present in the hashtable.
        int hashCode = hash(key);
        for (int i = 0; i < hashTable.get(hashCode).size(); i++) {
            if (hashTable.get(hashCode).get(i).key.equals(key)) {
                hashTable.get(hashCode).remove(i);
                size--;
            }
        }
    }
    public boolean contains(String key) {
        // Search the hashtable for key, if found return true else return false
        int hashCode = hash(key);
        for (int i = 0; i < hashTable.get(hashCode).size(); i++) {
            if (hashTable.get(hashCode).get(i).key.equals(key)) {
                return true;
            }
        }
        return false;
    }
    
    public int size() {
        // return the total number of keys in the hashtable.
        return size;
    }
    
    public int hash(String key) {
        // Use Horner's rule to compute the hashval and return it.
        int hashCode = 0;
        for (int i = 0; i < key.length(); i++) {
            hashCode = (37 * hashCode + (int) key.charAt(i)) % capacity;
        }
        return hashCode;
    }

    public int getVal(String key) {
        // return the value corresponding to the key in the hashtable.
        int hashCode = hash(key);
        for (int i = 0; i < hashTable.get(hashCode).size(); i++) {
            if (hashTable.get(hashCode).get(i).key.equals(key)) {
                return hashTable.get(hashCode).get(i).value;
            }
        }
        return -1;
    }

    
    public void rehash() {
        // Resize the hashtable such that the new size is the first prime number greater than two times the current size.
        // For example, if current size is 5, then the new size would be 11.
        this.capacity = nextPrime(this.capacity * 2);
        ArrayList<LinkedList<Pair<String, Integer>>> newHashTable = new ArrayList<>();
        for (int i = 0; i < this.capacity; i++) {
            newHashTable.add(new LinkedList<Pair<String, Integer>>());
        }

        int hashCode;
        for (int i = 0; i < hashTable.size(); i++) {
            for (int j = 0; j < hashTable.get(i).size(); j++) {
                hashCode = hash(hashTable.get(i).get(j).key);
                newHashTable.get(hashCode).add(new Pair(hashTable.get(i).get(j).key, hashTable.get(i).get(j).value));
            }
        }

        hashTable = newHashTable;
    }

    public int nextPrime(int num) {
        double temp = Math.sqrt(num);
        boolean found;
        while (true) {
            found = true;
            for (int i = 2; i <= temp; i++) {
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
    
    /**************PART 1.2*******************/

    public String[] mostFrequentStrings(String[] in) {
        // Given an array of strings, print the five most frequent strings.
        // You must use your implementation for hashtable with seperate chaining for this.
        String[] mostFrequent = new String[5];
        HashTableChaining table = new HashTableChaining(nextPrime(in.length));
        for (String s : in) {
            table.insert(s, 1);
        }


        return mostFrequent;
    }
    
}
