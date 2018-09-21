import java.util.ArrayList;

/**
 * Created by Utkarsh on 8/30/18.
 * Revised by George Adams on 09/02/18.
 * Revised by Shivaram Gopal on 09/02/18.
 */
/**
 * Project 01
 *
 * Implement a generic stack.
 *
 * @author Youngsik Yoon, yoon161@purdue.edu
 * @version Sep. 11, 2018
 *
 */
 
public class Stack<T> {
    private int size;       // Current size of stack
    private T[] values;     // Holds the stack values

    // Initializes the Stack with a capacity
    public Stack(int capacity) throws java.lang.IllegalArgumentException {
        // Checks if legal capacity
        if (capacity < 0) {
            throw new IllegalArgumentException();
        } else {
            values = (T[])new Object[capacity];     // Sets size of values
            size = 0;                               // Sets size to 0
        }
    }

    // Returns whether the stack is empty or not
    public boolean IsEmpty() {
        // Checks if the size is empty
        if (size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    // Push new elements on the stack and returns whether the element was successfully added
    public boolean push(T val) {
        // Checks if size is at max capacity
        if (size() == values.length) {
            return false;
        } else {
            values[size()] = val;   // Inserts new element into stack
            size++;                 // Increments size of stack
            return true;
        }
    }

    // Pops top element of stack and returns the removed element
    public T pop() throws IndexOutOfBoundsException {
        // Checks if the stack is empty and throws an appropriate error
        if (IsEmpty()) {
            throw new IndexOutOfBoundsException("Empty Stack");
        } else {
            T temp = values[size() - 1];
            values[size() - 1] = null;      // Removes top element
            size--;                     // Decrements size
            return temp;                // Returns top element
        }
    }

    // Returns the number of elements in the stack
    public int size() {
        return size;
    }
}
