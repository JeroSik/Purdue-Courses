import java.util.*;
/**
 * Project 03
 * Quicksort Implementation with Five Pivot Choice techniques and Input Data Generators
 *
 * @author Youngsik Yoon, yoon161@purdue.edu
 * @version 10/26/2018
 *
 */
public class QuickSort {
    /*
     * Implementation of five quicksorts
     * Each quicksort function initializes the count for comparisons
     * Each sort has a sort function which is the recursive quicksort.
     * -Checks if sort finishes
     * -Runs insertion sort if array is less than 6
     * -Otherwise partition array based on pivot
     */
    public int count; // Create count variable

    // Insertion sort for when array is less than 6
    public void insertionSort(int[] arr, int low, int high) {
        // Iterate through section of array
        for (int i = low + 1; i <= high; i++) {
            // Iterate right to left
            for (int j = i; j > low; j--) {
                count++; // Increment comparison counter
                // Swap in place
                if (arr[j] < arr[j - 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                } else {
                    break;
                }
            }
        }
    }
    // Insertion sort for finding pivot
    public void insertionSort(int[] arr, int[] index, int low, int high) {
        // Iterate through section of array
        for (int i = low + 1; i <= high; i++) {
            // Iterate right to left
            for (int j = i; j > low; j--) {
                count++; // Increment comparison counter
                // Swap in place
                if (arr[j] < arr[j - 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;

                    temp = index[j];
                    index[j] = index[j - 1];
                    index[j - 1] = temp;
                } else {
                    break;
                }
            }
        }
    }

    // Quicksort with pivot as last element of sequence
    public int QuickSortPivotA(int [] array) {
        count = 0;
        sortA(array, 0, array.length - 1);
        return count;
    }
    public void sortA(int[] arr, int left, int right) {
        if (left < right) {
            if (right - left + 1 < 6) {
                insertionSort(arr, left, right);
            } else {
                int q = partitionA(arr, left, right);
                sortA(arr, left, q - 1);
                sortA(arr, q + 1, right);
            }
        }
    }
    public int partitionA(int[] arr, int left, int right) {
        int pIndex = right;
        int pValue = arr[pIndex];

        int temp = arr[pIndex];
        arr[pIndex] = arr[right];
        arr[right] = temp;

        int pFinal = left;

        for (int i = left; i < right; i++) {
            count++;
            if (arr[i] <= pValue) {
                temp = arr[i];
                arr[i] = arr[pFinal];
                arr[pFinal] = temp;
                pFinal++;
            }
        }
        temp = arr[pFinal];
        arr[pFinal] = arr[right];
        arr[right] = temp;
        return pFinal;
    }

    // Quicksort with pivot as first element of sequence
    public int QuickSortPivotB(int [] array) {
        /*
        Implement the quicksort with pivot as the first element of the sequence.
        The method to sort array in place in ascending order
        the method is to return the number of comparisons required to complete the sorting.
        */
        count = 0;
        sortB(array, 0, array.length - 1);
        return count;
    }
    public void sortB(int[] arr, int left, int right) {
        if (left < right) {
            if (right - left + 1 < 6) {
                insertionSort(arr, left, right);
            } else {
                int q = partitionB(arr, left, right);
                sortB(arr, left, q - 1);
                sortB(arr, q + 1, right);
            }
        }
    }
    public int partitionB(int[] arr, int left, int right) {
        int pIndex = left;
        int pValue = arr[pIndex];

        int temp = arr[pIndex];
        arr[pIndex] = arr[right];
        arr[right] = temp;

        int pFinal = left;

        for (int i = left; i < right; i++) {
            count++;
            if (arr[i] <= pValue) {
                temp = arr[i];
                arr[i] = arr[pFinal];
                arr[pFinal] = temp;
                pFinal++;
            }
        }

        temp = arr[pFinal];
        arr[pFinal] = arr[right];
        arr[right] = temp;

        return pFinal;
    }

    // Quicksort with pivot as middle element of sequence
    public int QuickSortPivotC(int [] array) {
        count = 0;
        sortC(array, 0, array.length - 1);
        return count;
    }
    public void sortC(int[] arr, int left, int right) {
        if (left < right) {
            if (right - left + 1 < 6) {
                insertionSort(arr, left, right);
            } else {
                int q = partitionC(arr, left, right);
                sortC(arr, left, q - 1);
                sortC(arr, q + 1, right);
            }
        }
    }
    public int partitionC(int[] arr, int left, int right) {
        int pIndex = (left + right + 1) / 2;
        int pValue = arr[pIndex];

        int temp = arr[pIndex];
        arr[pIndex] = arr[right];
        arr[right] = temp;

        int pFinal = left;

        for (int i = left; i < right; i++) {
            count++;
            if (arr[i] <= pValue) {
                temp = arr[i];
                arr[i] = arr[pFinal];
                arr[pFinal] = temp;
                pFinal++;
            }
        }

        temp = arr[pFinal];
        arr[pFinal] = arr[right];
        arr[right] = temp;

        return pFinal;
    }

    // Quicksort with pivot as median between first, middle, and last element of sequence
    public int QuickSortPivotD(int [] array) {
        count = 0;
        sortD(array, 0, array.length - 1);
        return count;
    }
    public void sortD(int[] arr, int left, int right) {
        if (left < right) {
            if (right - left + 1 < 6) {
                insertionSort(arr, left, right);
            } else {
                int q = partitionD(arr, left, right);
                sortD(arr, left, q - 1);
                sortD(arr, q + 1, right);
            }
        }
    }
    public int partitionD(int[] arr, int left, int right) {
        int middle = (left + right + 1) / 2;
        int[] median = {arr[left], arr[middle], arr[right]};
        int[] index = {left, middle, right};
        insertionSort(median, index, 0, 2);
        int pIndex = index[1];

        int pValue = arr[pIndex];

        int temp = arr[pIndex];
        arr[pIndex] = arr[right];
        arr[right] = temp;

        int pFinal = left;

        for (int i = left; i < right; i++) {
            count++;
            if (arr[i] <= pValue) {
                temp = arr[i];
                arr[i] = arr[pFinal];
                arr[pFinal] = temp;
                pFinal++;
            }
        }

        temp = arr[pFinal];
        arr[pFinal] = arr[right];
        arr[right] = temp;

        return pFinal;
    }

    // Quicksort with pivot as median between five elements at 10%, 30%, 50%, 70%, 90% of sequence
    public int QuickSortPivotE(int [] array) {
        count = 0;
        sortE(array, 0, array.length - 1);
        return count;
    }
    public void sortE(int[] arr, int left, int right) {
        if (left < right) {
            if (right - left + 1 < 6) {
                insertionSort(arr, left, right);
            } else {
                int q = partitionE(arr, left, right);
                sortE(arr, left, q - 1);
                sortE(arr, q + 1, right);
            }
        }
    }
    public int partitionE(int[] arr, int left, int right) {
//        int first = (left + right + 1) / 10;
//        int second = 3 * (left + right + 1) / 10;
//        int third = (left + right + 1) / 2;
//        int fourth = 7 * (left + right + 1) / 10;
//        int fifth = 9 * (left + right + 1) / 10;
        int first = (right - left + 1) / 10 + left;
        int second = 3 * (right - left + 1) / 10 + left;
        int third = (right - left + 1) / 2 + left;
        int fourth = 7 * (right - left + 1) / 10 + left;
        int fifth = 9 * (right - left + 1) / 10 + left;

        int[] median = {arr[first], arr[second], arr[third], arr[fourth], arr[fifth]};
        int[] index = {first, second, third, fourth, fifth};
        insertionSort(median, index, 0, 4);
        int pIndex = index[2];

        int pValue = arr[pIndex];

        int temp = arr[pIndex];
        arr[pIndex] = arr[right];
        arr[right] = temp;

        int pFinal = left;

        for (int i = left; i < right; i++) {
            count++;
            if (arr[i] <= pValue) {
                temp = arr[i];
                arr[i] = arr[pFinal];
                arr[pFinal] = temp;
                pFinal++;
            }
        }

        temp = arr[pFinal];
        arr[pFinal] = arr[right];
        arr[right] = temp;

        return pFinal;
    }

    /*
     * Implementation of data input generations.
     */
    // Increasing order
    public int[] GenerateInputSequence1(int N) {
        int[] arr = new int[N];
        for (int i = 1; i <= N; i++) {
            arr[i - 1] = i;
        }
        return arr;
    }
    // Decreasing order
    public int[] GenerateInputSequence2(int N) {
        int[] arr = new int[N];
        for (int i = 1; i <= N; i++) {
            arr[i - 1] = N + 1 - i;
        }
        return arr;
    }
    // Odd then evens
    public int[] GenerateInputSequence3(int N) {
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            if (i < N / 2) {
                arr[i] = 1 + 2 * i;
            } else {
                arr[i] = 2 + 2 * (i - N / 2);
            }
        }
        return arr;
    }
    // Odd increasing then evens decreasing
    public int[] GenerateInputSequence4(int N) {
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            if (i < N / 2) {
                arr[i] = 1 + 2 * i;
            } else if (i == N / 2) {
                arr[i] = N;
            } else {
                arr[i] = N - 2 * (i - N / 2);
            }
        }
        return arr;
    }
    // Alternating between start and end
    public int[] GenerateInputSequence5(int N) {
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            if (i % 2 == 0) {
                arr[i] = (i + 2) / 2;
            } else {
                arr[i] = N + 1 - arr[i - 1];
            }
        }
        return arr;
    }
    // Each number is (7 + the previous number) mod N
    public int[] GenerateInputSequence6(int N) {
        int[] arr = new int[N];
        arr[0] = 0;
        for (int i = 1; i < N; i++) {
            arr[i] = (7 + arr[i - 1]) % N;
        }
        return arr;
    }
    // The first N Fibonacci numbers modulo N
    public int[] GenerateInputSequence7(int N) {
        int[] arr = new int[N];
        arr[0] = 0;
        arr[1] = 1;
        for (int i = 2; i < N; i++) {
            arr[i] = (arr[i - 1] + arr[i - 2]) % N;
        }
        return arr;
    }
    // The first N powers of 2 modulo N
    public int[] GenerateInputSequence8(int N) {
        int[] arr = new int[N];
        arr[0] = 1;
        for (int i = 1; i < N; i++) {
            arr[i] = (2 * arr[i - 1]) % N;
        }
        return arr;
    }
    // The first N powers of 3 modulo N
    public int[] GenerateInputSequence9(int N) {
        int[] arr = new int[N];
        arr[0] = 1;
        for (int i = 1; i < N; i++) {
            arr[i] = (3 * arr[i - 1]) % N;
        }
        return arr;
    }
    // Random integers
    public int[] GenerateInputSequence10(int N, int RNG_Seed) {
        Random r = new Random();
        r.setSeed(RNG_Seed);
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = r.nextInt() % 10000;
        }
        return arr;
    }

    /*
     * Main method to test implementation
     */
    public static void main(String[] args) {
    }
}