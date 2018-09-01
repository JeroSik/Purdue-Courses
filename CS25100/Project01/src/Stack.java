import java.util.ArrayList;

/**
 * Created by Utkarsh on 8/30/18.
 */
/**
 * Program Name
 *
 * brief description of the program
 *
 * @author Youngsik Yoon, yoon161@purdue.edu
 * @version date of completition
 *
 */

public class Stack<T> {
    private int size;
    private T[] values;

    public Stack(int capacity) throws java.lang.IllegalArgumentException {
        //Implement your stack here
        if (capacity < 0) {
            throw new IllegalArgumentException();
        } else {
            values = (T[])new Object[capacity];
            size = 0;
        }
    }

    public T top() throws IndexOutOfBoundsException {
        // Similar to peek

        // Method should return the top element of the stack
        // This does not remove the element from the stack
        // Incase the stack is empty, it should throw an error, 

        // add your own return statement
        return null;
    }

    public boolean push(T val){
        //Write your code here
        //Push the new element on the stack 
        //If the element was added successfully, return true
        //If the element was not added, return false
        values[size()] = val;
        size++;
        return true;             //remove this line and return the appropriate answer
    }

    public boolean pop(){
        //Write your code here
        //Return true if the top element was popped successfully 
        //Return false if the element was not popped
        if (IsEmpty()) {
            return false;           //remove this line and return the appropriate answer
        } else {
            values[size()] = null;
            size--;
            return true;
        }
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

    public boolean IsEmpty(){
        //Write your code here
        if (size == 0) {
            return true;
        }

        //Return the status of the stack
        return false;             //remove this line and return the appropriate answer
    }

    public int size()
    {
        //Write your code here
        //Return the remaining size of your stack, i.e. the number of elements that can be added on.
        return size;               //remove this line and return the appropriate answer
    }
}
