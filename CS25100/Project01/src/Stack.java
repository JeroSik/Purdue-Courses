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
 * @version date of completition
 *
 */


public class Stack<T> {
    private int size;
    private T[] values;

    public Stack(int capacity) throws java.lang.IllegalArgumentException {
        // Initialize your stack here
        if (capacity < 0) {
            throw new IllegalArgumentException();
        } else {
            values = (T[])new Object[capacity];
            size = 0;
        }
    }

    public boolean IsEmpty() {
        // Return whether the stack is empty or not
        if (size == 0) {
            return true;
        }
        return false;
    }

    public boolean push(T val){
        // Write your code here
        // Push the new element on the stack
        // If the element was added successfully, return true
        // If the element was not added, return false
        if (size()) {

        }
        values[size()] = val;
        size++;
        return true;             //remove this line and return the appropriate answer
    }

    public T pop() throws IndexOutOfBoundsException{
        //Write your code here
        // Method should return the top element of the stack
        // This removes the element from the stack
        // Incase the stack is empty, it should throw an error,
        // with the message "Empty Stack"
        if (IsEmpty()) {
            return false;           //remove this line and return the appropriate answer
        } else {
            values[size()] = null;
            size--;
            return true;
        }
        return null;    // add your own return statement
    }

    public void resize(boolean insert) {
        if (insert) {
            if (size() == values.length) {
                T[] temp = (T[]) new Object[values.length * 2];
                for (int i = 0; i < values.length; i++) {
                    temp[i] = values[i];
                }
                values = temp;
            }
        } else {
            if (size() > 0 || size() == values.length / 4) {
                T[] temp = (T[]) new Object[values.length / 2];
                for (int i = 0; i < values.length; i++) {
                    temp[i] = values[i];
                }
                values = temp;
            }
        }
    }

    public int size()
    {
        //Write your code here
        //number of elements currently in the stack

        return size;               //remove this line and return the appropriate answer
    }
}
