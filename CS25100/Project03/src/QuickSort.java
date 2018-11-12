import java.util.*;

/*
*  Quicksort Implementation with Five Pivot Choice techniques
*  and
*  Input Sequence Data Generator
* */

/**
 * Project 03
 *
 * An infix arithmetic expression evaluator
 *
 * @author Youngsik Yoon, yoon161@purdue.edu
 * @version 23 October, 2018
 *
 */

public class QuickSort
{
    public int QuickSortPivotA(int [] array)
    {
        /*
        Implement the quicksort with pivot as the last element of the sequence.
        The method to sort array in place in ascending order
        the method is to return the number of comparisons required to complete the sorting.
        */

        return 0;
    }

    public int QuickSortPivotB(int [] array)
    {
        /*
        Implement the quicksort with pivot as the first element of the sequence.
        The method to sort array in place in ascending order
        the method is to return the number of comparisons required to complete the sorting.
        */

        return 0;
    }

    public int QuickSortPivotC(int [] array)
    {
        /*
        Implement the quicksort with pivot as the middle element of the sequence.
        The method to sort array in place in ascending order
        the method is to return the number of comparisons required to complete the sorting.
        */

        return 0;
    }

    public int QuickSortPivotD(int [] array)
    {
        /*
        Implement the quicksort with pivot as the median of the first, middle and last elements of the sequence.
        The method to sort array in place in ascending order
        the method is to return the number of comparisons required to complete the sorting.
        */
        return 0;
    }

    public int QuickSortPivotE(int [] array)
    {
        /*
        Implement the quicksort with pivot as the median of five elements of the sequence, chosen to be roughly 10%,
         30%, 50%, 70% and 90% of the way through the sequence.
        The method to sort array in place in ascending order
        the method is to return the number of comparisons required to complete the sorting.
        */

        return 0;
    }


    /*
    *
    * Implement the rest of the functions required to do the quicksort for every variation.
    * */


    public int[] GenerateInputSequence1(int N)
    {
        /*
        * return an array with the sequence 1, 2, 3, ..., N (in increasing order).
        * For example, if N = 1000, then the sequence would be: 1, 2, 3, 4, 5, ..., 1000
        * */
        return null;

    }

    public int[] GenerateInputSequence2(int N)
    {
        /*
         * return an array with The sequence N, N-1, ..., 2, 1 (in decreasing order).
         * For example, if N = 1000, then the sequence would be: 1000, 999, ..., 2, 1
         * */

        return null;
    }

    public int[] GenerateInputSequence3(int N)
    {
        /*
         * return an array with The sequence 1, 3, 5, ..., N-1, 2, 4, 6, ..., N.
         * For example,  if N = 1000, then the sequence would be: 1, 3, 5, ..., 999, 2, 4, 6, ..., 1000
         * */

        return null;
    }

    public int[] GenerateInputSequence4(int N)
    {
        /*
         * return an array with the sequence 1, 3, 5, ..., N-3, N-1, N, N-2, N-4, ..., 4, 2.
          * For example,  if N = 1000, then the sequence would be: 1,3,5 ...,997,999,1000,998,996, ..., 4,2
         * */

        return null;
    }

    public int[] GenerateInputSequence5(int N)
    {
        /*
         * return an array with sequence 1, N, 2, N-1, 3, N-2, 4, N-3, ..., N/2, (N/2)+1.
         * For example,  if N = 1000, then the sequence would be: 1, 1000, 2, 999, 3, 998, 4, 997, ..., 500, 501
         * */

        return null;
    }


    public int[] GenerateInputSequence6(int N)
    {
        /*
         * return an array with the sequence: Each number is (7 + the previous number) mod N.
         * That is, a(i) = (7 + a(i-1)) mod N, a(0)=0
         * For example,  if N = 1000, then the sequence would be: 0, 7, 14, ..., 994, 1, 8, ..., 993
         */

        return null;

    }


    public int[] GenerateInputSequence7(int N)
    {
        /*
         * return an array with The sequence: The first N Fibonacci numbers modulo N:
         * a(0) = 0; a(1) = 1; a(i) = (a(i-1) + a(i-2)) mod N for 1 < i < N.         *
         * */



        return null;
    }


    public int[] GenerateInputSequence8(int N)
    {
        /*
         * return an array with The sequence The first N powers of 2 modulo N:
         * a(0) = 1; a(i) = (2*a(i-1)) mod N for 0 < i < N.
         * */
        return null;
    }


    public int[] GenerateInputSequence9(int N)
    {
        /*
         * return an array with The sequence: The first N powers of 3 modulo N:
         * a(0) = 1; a(i) = (3*a(i-1)) mod N for 0 < i < N.
         * */
        return null;
    }


    public int[] GenerateInputSequence10(int N, int RNG_Seed)
    {
        /*
         * return an array with The sequence N, N-1, ..., 2, 1 (in decreasing order).
         * A random sequence using the methods in java.util.Random: Use setSeed(long seed) to set the seed using
         * a nine-digit which will be an input to your method. Use nextInt()%10000 N times to get N random integers
          * between -9999 and 9999.  Use these in the order generated as the sequence.
          * Example:Random generator = new Random();
          * generator.setSeed(123456789);   // 123456789 is example, seed will be input
          * int num = generator.nextInt()%10000; // will be called N times to complete the sequence
          * */
        return null;
    }


    public static void main(String[] args)
    {
        /*
        You can test you implementation of the quicksort here.
        Please do not change the function names as we will use them to
        grade your work.
        */
    }
}