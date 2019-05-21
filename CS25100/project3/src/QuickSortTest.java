import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.Assert.*;


public class QuickSortTest {

    @Test
    public void test1() throws Exception {
        QuickSort qs = new QuickSort();
        int[] array = {1,2,3,4,5,6,7,8,9,10};


        int [] array2 = qs.GenerateInputSequence1(10);

        assertArrayEquals("Input Sequence 1: Not Correct",array,array2);
    }

    @Test
    public void test2() throws Exception {
        QuickSort qs = new QuickSort();
        int[] array = {16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1};


        int [] array2 = qs.GenerateInputSequence2(16);

        assertArrayEquals("Input Sequence 2: Not Correct",array,array2);
    }

    @Test
    public void test3() throws Exception {
        QuickSort qs = new QuickSort();
        int[] array = {1,3,5,7,9,2,4,6,8,10};


        int [] array2 = qs.GenerateInputSequence3(10);

        assertArrayEquals("Input Sequence 3: Not Correct",array,array2);
    }

    @Test
    public void test4() throws Exception {
        QuickSort qs = new QuickSort();
        int[] array = {1,3,5,7,9,10,8,6,4,2};

        int [] array2 = qs.GenerateInputSequence4(10);

        assertArrayEquals("Input Sequence 4: Not Correct",array,array2);
    }

    @Test
    public void test5() throws Exception {
        QuickSort qs = new QuickSort();
        int[] array = {1,12,2,11,3,10,4,9,5,8,6,7};

        int [] array2 = qs.GenerateInputSequence5(12);

        assertArrayEquals("Input Sequence 5: Not Correct",array,array2);
    }

    @Test
    public void test6() throws Exception {
        QuickSort qs = new QuickSort();
        int[] array = {0,7,4,1,8,5,2,9,6,3};

        int [] array2 = qs.GenerateInputSequence6(10);

        assertArrayEquals("Input Sequence 6: Not Correct",array,array2);
    }

    @Test
    public void test7() throws Exception {
        QuickSort qs = new QuickSort();
        int[] array = {0,1,1,2,3,5,8,3,1,4};

        int [] array2 = qs.GenerateInputSequence7(10);

        assertArrayEquals("Input Sequence 7: Not Correct",array,array2);
    }

    @Test
    public void test8() throws Exception {
        QuickSort qs = new QuickSort();
        int[] array = {1, 2,4,8,6,2,4,8,6,2};

        int [] array2 = qs.GenerateInputSequence8(10);

        assertArrayEquals("Input Sequence 8: Not Correct",array,array2);

    }

    @Test
    public void test9() throws Exception {
        QuickSort qs = new QuickSort();
        int[] array = {1,3,9,7,1,3,9,7,1,3};

        int [] array2 = qs.GenerateInputSequence9(10);

        assertArrayEquals("Input Sequence 9: Not Correct",array,array2);
    }

    @Test
    public void test10() throws Exception {
        QuickSort qs = new QuickSort();
        int[] array = {-5365,-8095,2967,6688,2580,5108,-6230,5615,-701,5775,6882,5923};

        int [] array2 = qs.GenerateInputSequence10(12,123456789);

        assertArrayEquals("Input Sequence 1: Not Correct",array,array2);
    }

    @Test
    public void testQuickSortPivotA() throws Exception {

        QuickSort qs = new QuickSort();
        // Set N: Size of input sequence
        int n=1002;

        int[] array = qs.GenerateInputSequence2(n);
        int[] array2 = qs.GenerateInputSequence2(n);

        Arrays.sort(array);

        int compareCount = qs.QuickSortPivotA(array2);

        System.out.println("Pivot-A: compareCount:" + compareCount);

        assertArrayEquals("Sorting Not Correct",array,array2);

        assertEquals("Compare Count Not Correct",501498,compareCount,0);
    }

    @Test
    public void testQuickSortPivotB() throws Exception {

        QuickSort qs = new QuickSort();
        // Set N: Size of input sequence
        int n=568;

        int[] array  = qs.GenerateInputSequence4(n);
        int[] array2 = qs.GenerateInputSequence4(n);

        Arrays.sort(array);

        int compareCount = qs.QuickSortPivotB(array2);

        System.out.println("Pivot-B: CompareCount:" + compareCount);

        assertArrayEquals("Sorting Not Correct",array,array2);

        assertEquals("Compare Count Not Correct",27032,compareCount,0);
    }

    @Test
    public void testQuickSortPivotC() throws Exception {

        QuickSort qs = new QuickSort();
        // Set N: Size of input sequence
        int n=568;

        int[] array  = qs.GenerateInputSequence5(n);
        int[] array2 = qs.GenerateInputSequence5(n);

        Arrays.sort(array);

        int compareCount = qs.QuickSortPivotC(array2);

        System.out.println("Pivot-C: compareCount:" + compareCount);

        assertArrayEquals("Sorting Not Correct",array,array2);

        assertEquals("Compare Count Not Correct",4764,compareCount,0);

    }

    @Test
    public void testQuickSortPivotD() throws Exception {

        QuickSort qs = new QuickSort();
        // Set N: Size of input sequence
        int n=1000;

        int[] array  = qs.GenerateInputSequence7(n);
        int[] array2 = qs.GenerateInputSequence7(n);

        Arrays.sort(array);

        int compareCount = qs.QuickSortPivotD(array2);

        System.out.println("Pivot-D: compareCount:" + compareCount);

        assertArrayEquals("Sorting Not Correct",array,array2);

        assertEquals("Compare Count Not Correct",10245,compareCount,0);
    }

    @Test
    public void testQuickSortPivotE() throws Exception {

        QuickSort qs = new QuickSort();
        // Set N: Size of input sequence
        int n=680;

        int[] array  = qs.GenerateInputSequence10(n,123456789);
        int[] array2 = qs.GenerateInputSequence10(n,123456789);

        Arrays.sort(array);

        int compareCount = qs.QuickSortPivotE(array2);

        System.out.println("Pivot-E: CompareCount:" + compareCount);

        assertArrayEquals("Sorting Not Correct",array,array2);

        assertEquals("Compare Count Not Correct",7145,compareCount,0);

    }
}