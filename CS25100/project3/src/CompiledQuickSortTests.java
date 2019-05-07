import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Jay P.
 * Last Edited: 10/24/18 2:08 P.M.
 */
@RunWith(Enclosed.class)
public class CompiledQuickSortTests {

    public static class sequenceTests {
        @Test
        public void Sequence1() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            int[] array2 = qs.GenerateInputSequence1(10);
            assertArrayEquals("Input Sequence 1: Not Correct", array, array2);
        }

        @Test
        public void Sequence2() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = {16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
            int[] array2 = qs.GenerateInputSequence2(16);
            assertArrayEquals("Input Sequence 2: Not Correct", array, array2);
        }

        @Test
        public void Sequence3() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = {1, 3, 5, 7, 9, 2, 4, 6, 8, 10};
            int[] array2 = qs.GenerateInputSequence3(10);
            assertArrayEquals("Input Sequence 3: Not Correct", array, array2);
        }

        @Test
        public void Sequence4() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = {1, 3, 5, 7, 9, 10, 8, 6, 4, 2};
            int[] array2 = qs.GenerateInputSequence4(10);
            assertArrayEquals("Input Sequence 4: Not Correct", array, array2);
        }

        @Test
        public void Sequence5() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = {1, 12, 2, 11, 3, 10, 4, 9, 5, 8, 6, 7};
            int[] array2 = qs.GenerateInputSequence5(12);
            assertArrayEquals("Input Sequence 5: Not Correct", array, array2);
        }

        @Test
        public void Sequence6() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = {0, 7, 4, 1, 8, 5, 2, 9, 6, 3};
            int[] array2 = qs.GenerateInputSequence6(10);
            assertArrayEquals("Input Sequence 6: Not Correct", array, array2);
        }

        @Test
        public void Sequence7() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = {0, 1, 1, 2, 3, 5, 8, 3, 1, 4};
            int[] array2 = qs.GenerateInputSequence7(10);
            assertArrayEquals("Input Sequence 7: Not Correct", array, array2);
        }

        @Test
        public void Sequence8() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = {1, 2, 4, 8, 6, 2, 4, 8, 6, 2};
            int[] array2 = qs.GenerateInputSequence8(10);
            assertArrayEquals("Input Sequence 8: Not Correct", array, array2);

        }

        @Test
        public void Sequence9() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = {1, 3, 9, 7, 1, 3, 9, 7, 1, 3};
            int[] array2 = qs.GenerateInputSequence9(10);
            assertArrayEquals("Input Sequence 9: Not Correct", array, array2);
        }

        @Test
        public void Sequence10() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = {-5365, -8095, 2967, 6688, 2580, 5108, -6230, 5615, -701, 5775, 6882, 5923};
            int[] array2 = qs.GenerateInputSequence10(12, 123456789);
            assertArrayEquals("Input Sequence 1: Not Correct", array, array2);
        }

    }

    public static class pivotATests {
        @Test
        public void testQuickSortPivotA() throws Exception {
            QuickSort qs = new QuickSort();
            // Set N: Size of input sequence
            int n = 1002;
            int[] array = qs.GenerateInputSequence2(n);
            int[] array2 = qs.GenerateInputSequence2(n);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotA(array2);
            System.out.println("Pivot-A: compareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 501498, compareCount);
        }

        @Test
        public void customTestPivotA_n7() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = {1, 2, 3, 4, 1, 2, 4};
            int[] array2 = {1, 2, 3, 4, 1, 2, 4};
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotA(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 15, compareCount);
        }

        @Test
        public void customTestPivotA_n7_v2() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = {1, 2, 3, 4, 1, 2, 1};
            int[] array2 = {1, 2, 3, 4, 1, 2, 1};
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotA(array2);
            System.out.println("Pivot-A: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 12, compareCount);
        }

        @Test
        public void customTestPivotA_Sequence1_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence1(40);
            int[] array2 = qs.GenerateInputSequence1(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotA(array2);
            System.out.println("Pivot-A: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 774, compareCount);
        }

        @Test
        public void customTestPivotA_Sequence2_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence2(40);
            int[] array2 = qs.GenerateInputSequence2(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotA(array2);
            System.out.println("Pivot-A: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 777, compareCount);
        }

        @Test
        public void customTestPivotA_Sequence3_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence3(40);
            int[] array2 = qs.GenerateInputSequence3(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotA(array2);
            System.out.println("Pivot-A: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 420, compareCount);
        }

        @Test
        public void customTestPivotA_Sequence4_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence4(40);
            int[] array2 = qs.GenerateInputSequence4(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotA(array2);
            System.out.println("Pivot-A: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 285, compareCount);
        }

        @Test
        public void customTestPivotA_Sequence5_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence5(40);
            int[] array2 = qs.GenerateInputSequence5(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotA(array2);
            System.out.println("Pivot-A: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 302, compareCount);
        }

        @Test
        public void customTestPivotA_Sequence6_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence6(40);
            int[] array2 = qs.GenerateInputSequence6(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotA(array2);
            System.out.println("Pivot-A: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 189, compareCount);
        }

        @Test
        public void customTestPivotA_Sequence7_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence7(40);
            int[] array2 = qs.GenerateInputSequence7(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotA(array2);
            System.out.println("Pivot-A: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 165, compareCount);
        }

        @Test
        public void customTestPivotA_Sequence8_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence8(40);
            int[] array2 = qs.GenerateInputSequence8(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotA(array2);
            System.out.println("Pivot-A: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 224, compareCount);
        }

        @Test
        public void customTestPivotA_Sequence9_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence9(40);
            int[] array2 = qs.GenerateInputSequence9(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotA(array2);
            System.out.println("Pivot-A: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 243, compareCount);
        }

        @Test
        public void customTestPivotA_Sequence10_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence10(40, 123456789);
            int[] array2 = qs.GenerateInputSequence10(40, 123456789);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotA(array2);
            System.out.println("Pivot-A: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 196, compareCount);
        }

        @Test
        public void customTestPivotA_Sequence1_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence1(1000);
            int[] array2 = qs.GenerateInputSequence1(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotA(array2);
            System.out.println("Pivot-A: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 499494, compareCount);
        }

        @Test
        public void customTestPivotA_Sequence2_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence2(1000);
            int[] array2 = qs.GenerateInputSequence2(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotA(array2);
            System.out.println("Pivot-A: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 499497, compareCount);
        }

        @Test
        public void customTestPivotA_Sequence3_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence3(1000);
            int[] array2 = qs.GenerateInputSequence3(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotA(array2);
            System.out.println("Pivot-A: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 250500, compareCount);
        }

        @Test
        public void customTestPivotA_Sequence4_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence4(1000);
            int[] array2 = qs.GenerateInputSequence4(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotA(array2);
            System.out.println("Pivot-A: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 85274, compareCount);
        }

        @Test
        public void customTestPivotA_Sequence5_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence5(1000);
            int[] array2 = qs.GenerateInputSequence5(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotA(array2);
            System.out.println("Pivot-A: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 160410, compareCount);
        }

        @Test
        public void customTestPivotA_Sequence6_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence6(1000);
            int[] array2 = qs.GenerateInputSequence6(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotA(array2);
            System.out.println("Pivot-A: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 73277, compareCount);
        }

        @Test
        public void customTestPivotA_Sequence7_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence7(1000);
            int[] array2 = qs.GenerateInputSequence7(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotA(array2);
            System.out.println("Pivot-A: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 10459, compareCount);
        }

        @Test
        public void customTestPivotA_Sequence8_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence8(1000);
            int[] array2 = qs.GenerateInputSequence8(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotA(array2);
            System.out.println("Pivot-A: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 11559, compareCount);
        }

        @Test
        public void customTestPivotA_Sequence9_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence9(1000);
            int[] array2 = qs.GenerateInputSequence9(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotA(array2);
            System.out.println("Pivot-A: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 10826, compareCount);
        }

        @Test
        public void customTestPivotA_Sequence10_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence10(1000, 123456789);
            int[] array2 = qs.GenerateInputSequence10(1000, 123456789);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotA(array2);
            System.out.println("Pivot-A: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 11450, compareCount);
        }
    }

    public static class pivotBTests {
        @Test
        public void testQuickSortPivotB() throws Exception {
            QuickSort qs = new QuickSort();
            // Set N: Size of input sequence
            int n = 568;
            int[] array = qs.GenerateInputSequence4(n);
            int[] array2 = qs.GenerateInputSequence4(n);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotB(array2);
            System.out.println("Pivot-B: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 27032, compareCount);
        }

        @Test
        public void customTestPivotB_n7() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = {1, 2, 3, 4, 1, 2, 4};
            int[] array2 = {1, 2, 3, 4, 1, 2, 4};
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotB(array2);
            System.out.println("Pivot-B: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 15, compareCount);
        }

        @Test
        public void customTestPivotB_n7_v2() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = {1, 2, 3, 4, 1, 2, 1};
            int[] array2 = {1, 2, 3, 4, 1, 2, 1};
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotB(array2);
            System.out.println("Pivot-B: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 12, compareCount);
        }

        @Test
        public void customTestPivotB_Sequence1_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence1(40);
            int[] array2 = qs.GenerateInputSequence1(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotB(array2);
            System.out.println("Pivot-B: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 774, compareCount);
        }

        @Test
        public void customTestPivotB_Sequence2_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence2(40);
            int[] array2 = qs.GenerateInputSequence2(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotB(array2);
            System.out.println("Pivot-B: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 780, compareCount);
        }

        @Test
        public void customTestPivotB_Sequence3_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence3(40);
            int[] array2 = qs.GenerateInputSequence3(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotB(array2);
            System.out.println("Pivot-B: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 260, compareCount);
        }

        @Test
        public void customTestPivotB_Sequence4_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence4(40);
            int[] array2 = qs.GenerateInputSequence4(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotB(array2);
            System.out.println("Pivot-B: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 265, compareCount);
        }

        @Test
        public void customTestPivotB_Sequence5_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence5(40);
            int[] array2 = qs.GenerateInputSequence5(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotB(array2);
            System.out.println("Pivot-B: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 346, compareCount);
        }

        @Test
        public void customTestPivotB_Sequence6_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence6(40);
            int[] array2 = qs.GenerateInputSequence6(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotB(array2);
            System.out.println("Pivot-B: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 230, compareCount);
        }

        @Test
        public void customTestPivotB_Sequence7_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence7(40);
            int[] array2 = qs.GenerateInputSequence7(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotB(array2);
            System.out.println("Pivot-B: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 280, compareCount);
        }

        @Test
        public void customTestPivotB_Sequence8_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence8(40);
            int[] array2 = qs.GenerateInputSequence8(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotB(array2);
            System.out.println("Pivot-B: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 313, compareCount);
        }

        @Test
        public void customTestPivotB_Sequence9_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence9(40);
            int[] array2 = qs.GenerateInputSequence9(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotB(array2);
            System.out.println("Pivot-B: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 215, compareCount);
        }

        @Test
        public void customTestPivotB_Sequence10_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence10(40, 123456789);
            int[] array2 = qs.GenerateInputSequence10(40, 123456789);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotB(array2);
            System.out.println("Pivot-B: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 205, compareCount);
        }

        @Test
        public void customTestPivotB_Sequence1_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence1(1000);
            int[] array2 = qs.GenerateInputSequence1(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotB(array2);
            System.out.println("Pivot-B: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 499494, compareCount);
        }

        @Test
        public void customTestPivotB_Sequence2_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence2(1000);
            int[] array2 = qs.GenerateInputSequence2(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotB(array2);
            System.out.println("Pivot-B: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 499500, compareCount);
        }

        @Test
        public void customTestPivotB_Sequence3_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence3(1000);
            int[] array2 = qs.GenerateInputSequence3(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotB(array2);
            System.out.println("Pivot-B: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 25628, compareCount);
        }

        @Test
        public void customTestPivotB_Sequence4_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence4(1000);
            int[] array2 = qs.GenerateInputSequence4(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotB(array2);
            System.out.println("Pivot-B: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 84989, compareCount);
        }

        @Test
        public void customTestPivotB_Sequence5_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence5(1000);
            int[] array2 = qs.GenerateInputSequence5(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotB(array2);
            System.out.println("Pivot-B: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 161793, compareCount);
        }

        @Test
        public void customTestPivotB_Sequence6_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence6(1000);
            int[] array2 = qs.GenerateInputSequence6(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotB(array2);
            System.out.println("Pivot-B: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 16144, compareCount);
        }

        @Test
        public void customTestPivotB_Sequence7_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence7(1000);
            int[] array2 = qs.GenerateInputSequence7(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotB(array2);
            System.out.println("Pivot-B: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 15456, compareCount);
        }

        @Test
        public void customTestPivotB_Sequence8_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence8(1000);
            int[] array2 = qs.GenerateInputSequence8(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotB(array2);
            System.out.println("Pivot-B: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 15095, compareCount);
        }

        @Test
        public void customTestPivotB_Sequence9_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence9(1000);
            int[] array2 = qs.GenerateInputSequence9(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotB(array2);
            System.out.println("Pivot-B: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 13316, compareCount);
        }

        @Test
        public void customTestPivotB_Sequence10_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence10(1000, 123456789);
            int[] array2 = qs.GenerateInputSequence10(1000, 123456789);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotB(array2);
            System.out.println("Pivot-B: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 10398, compareCount);
        }
    }

    public static class pivotCTests {
        @Test
        public void testQuickSortPivotC() throws Exception {
            QuickSort qs = new QuickSort();
            // Set N: Size of input sequence
            int n = 568;
            int[] array = qs.GenerateInputSequence5(n);
            int[] array2 = qs.GenerateInputSequence5(n);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotC(array2);
            System.out.println("Pivot-C: compareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 4764, compareCount);

        }

        @Test
        public void customTestPivotC_n7() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = {1, 2, 3, 4, 1, 2, 4};
            int[] array2 = {1, 2, 3, 4, 1, 2, 4};
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotC(array2);
            System.out.println("Pivot-C: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 19, compareCount);
        }

        @Test
        public void customTestPivotC_n7_v2() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = {1, 2, 3, 4, 1, 2, 1};
            int[] array2 = {1, 2, 3, 4, 1, 2, 1};
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotC(array2);
            System.out.println("Pivot-C: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 14, compareCount);
        }

        @Test
        public void customTestPivotC_Sequence1_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence1(40);
            int[] array2 = qs.GenerateInputSequence1(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotC(array2);
            System.out.println("Pivot-C: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 134, compareCount);
        }

        @Test
        public void customTestPivotC_Sequence2_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence2(40);
            int[] array2 = qs.GenerateInputSequence2(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotC(array2);
            System.out.println("Pivot-C: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 155, compareCount);
        }

        @Test
        public void customTestPivotC_Sequence3_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence3(40);
            int[] array2 = qs.GenerateInputSequence3(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotC(array2);
            System.out.println("Pivot-C: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 401, compareCount);
        }

        @Test
        public void customTestPivotC_Sequence4_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence4(40);
            int[] array2 = qs.GenerateInputSequence4(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotC(array2);
            System.out.println("Pivot-C: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 366, compareCount);
        }

        @Test
        public void customTestPivotC_Sequence5_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence5(40);
            int[] array2 = qs.GenerateInputSequence5(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotC(array2);
            System.out.println("Pivot-C: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 160, compareCount);
        }

        @Test
        public void customTestPivotC_Sequence6_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence6(40);
            int[] array2 = qs.GenerateInputSequence6(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotC(array2);
            System.out.println("Pivot-C: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 157, compareCount);
        }

        @Test
        public void customTestPivotC_Sequence7_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence7(40);
            int[] array2 = qs.GenerateInputSequence7(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotC(array2);
            System.out.println("Pivot-C: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 199, compareCount);
        }

        @Test
        public void customTestPivotC_Sequence8_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence8(40);
            int[] array2 = qs.GenerateInputSequence8(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotC(array2);
            System.out.println("Pivot-C: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 242, compareCount);
        }

        @Test
        public void customTestPivotC_Sequence9_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence9(40);
            int[] array2 = qs.GenerateInputSequence9(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotC(array2);
            System.out.println("Pivot-C: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 216, compareCount);
        }

        @Test
        public void customTestPivotC_Sequence10_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence10(40, 123456789);
            int[] array2 = qs.GenerateInputSequence10(40, 123456789);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotC(array2);
            System.out.println("Pivot-C: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 199, compareCount);
        }

        @Test
        public void customTestPivotC_Sequence1_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence1(1000);
            int[] array2 = qs.GenerateInputSequence1(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotC(array2);
            System.out.println("Pivot-C: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 7987, compareCount);
        }

        @Test
        public void customTestPivotC_Sequence2_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence2(1000);
            int[] array2 = qs.GenerateInputSequence2(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotC(array2);
            System.out.println("Pivot-C: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 8393, compareCount);
        }

        @Test
        public void customTestPivotC_Sequence3_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence3(1000);
            int[] array2 = qs.GenerateInputSequence3(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotC(array2);
            System.out.println("Pivot-C: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 250001, compareCount);
        }

        @Test
        public void customTestPivotC_Sequence4_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence4(1000);
            int[] array2 = qs.GenerateInputSequence4(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotC(array2);
            System.out.println("Pivot-C: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 46767, compareCount);
        }

        @Test
        public void customTestPivotC_Sequence5_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence5(1000);
            int[] array2 = qs.GenerateInputSequence5(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotC(array2);
            System.out.println("Pivot-C: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 9847, compareCount);
        }

        @Test
        public void customTestPivotC_Sequence6_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence6(1000);
            int[] array2 = qs.GenerateInputSequence6(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotC(array2);
            System.out.println("Pivot-C: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 9719, compareCount);
        }

        @Test
        public void customTestPivotC_Sequence7_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence7(1000);
            int[] array2 = qs.GenerateInputSequence7(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotC(array2);
            System.out.println("Pivot-C: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 11122, compareCount);
        }

        @Test
        public void customTestPivotC_Sequence8_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence8(1000);
            int[] array2 = qs.GenerateInputSequence8(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotC(array2);
            System.out.println("Pivot-C: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 11730, compareCount);
        }

        @Test
        public void customTestPivotC_Sequence9_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence9(1000);
            int[] array2 = qs.GenerateInputSequence9(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotC(array2);
            System.out.println("Pivot-C: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 12400, compareCount);
        }

        @Test
        public void customTestPivotC_Sequence10_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence10(1000, 123456789);
            int[] array2 = qs.GenerateInputSequence10(1000, 123456789);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotC(array2);
            System.out.println("Pivot-C: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 10961, compareCount);
        }

    }

    public static class pivotDTests {
        @Test
        public void testQuickSortPivotD() throws Exception {
            QuickSort qs = new QuickSort();
            // Set N: Size of input sequence
            int n = 1000;
            int[] array = qs.GenerateInputSequence7(n);
            int[] array2 = qs.GenerateInputSequence7(n);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotD(array2);
            System.out.println("Pivot-D: compareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 10245, compareCount);
        }

        @Test
        public void customTestPivotD_n7() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = {1, 2, 3, 4, 1, 2, 4};
            int[] array2 = {1, 2, 3, 4, 1, 2, 4};
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotD(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 20, compareCount);
        }

        @Test
        public void customTestPivotD_n7_v2() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = {1, 2, 3, 4, 1, 2, 1};
            int[] array2 = {1, 2, 3, 4, 1, 2, 1};
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotD(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 15, compareCount);
        }

        @Test
        public void customTestPivotD_Sequence1_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence1(40);
            int[] array2 = qs.GenerateInputSequence1(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotD(array2);
            System.out.println("Pivot-D: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 148, compareCount);
        }

        @Test
        public void customTestPivotD_Sequence2_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence2(40);
            int[] array2 = qs.GenerateInputSequence2(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotD(array2);
            System.out.println("Pivot-D: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 211, compareCount);
        }

        @Test
        public void customTestPivotD_Sequence3_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence3(40);
            int[] array2 = qs.GenerateInputSequence3(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotD(array2);
            System.out.println("Pivot-D: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 279, compareCount);
        }

        @Test
        public void customTestPivotD_Sequence4_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence4(40);
            int[] array2 = qs.GenerateInputSequence4(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotD(array2);
            System.out.println("Pivot-D: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 256, compareCount);
        }

        @Test
        public void customTestPivotD_Sequence5_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence5(40);
            int[] array2 = qs.GenerateInputSequence5(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotD(array2);
            System.out.println("Pivot-D: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 176, compareCount);
        }

        @Test
        public void customTestPivotD_Sequence6_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence6(40);
            int[] array2 = qs.GenerateInputSequence6(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotD(array2);
            System.out.println("Pivot-D: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 175, compareCount);
        }

        @Test
        public void customTestPivotD_Sequence7_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence7(40);
            int[] array2 = qs.GenerateInputSequence7(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotD(array2);
            System.out.println("Pivot-D: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 190, compareCount);
        }

        @Test
        public void customTestPivotD_Sequence8_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence8(40);
            int[] array2 = qs.GenerateInputSequence8(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotD(array2);
            System.out.println("Pivot-D: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 266, compareCount);
        }

        @Test
        public void customTestPivotD_Sequence9_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence9(40);
            int[] array2 = qs.GenerateInputSequence9(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotD(array2);
            System.out.println("Pivot-D: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 277, compareCount);
        }

        @Test
        public void customTestPivotD_Sequence10_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence10(40, 123456789);
            int[] array2 = qs.GenerateInputSequence10(40, 123456789);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotD(array2);
            System.out.println("Pivot-D: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 186, compareCount);
        }

        @Test
        public void customTestPivotD_Sequence1_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence1(1000);
            int[] array2 = qs.GenerateInputSequence1(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotD(array2);
            System.out.println("Pivot-D: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 8497, compareCount);
        }

        @Test
        public void customTestPivotD_Sequence2_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence2(1000);
            int[] array2 = qs.GenerateInputSequence2(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotD(array2);
            System.out.println("Pivot-D: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 14326, compareCount);
        }

        @Test
        public void customTestPivotD_Sequence3_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence3(1000);
            int[] array2 = qs.GenerateInputSequence3(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotD(array2);
            System.out.println("Pivot-D: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 23235, compareCount);
        }

        @Test
        public void customTestPivotD_Sequence4_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence4(1000);
            int[] array2 = qs.GenerateInputSequence4(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotD(array2);
            System.out.println("Pivot-D: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 65880, compareCount);
        }

        @Test
        public void customTestPivotD_Sequence5_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence5(1000);
            int[] array2 = qs.GenerateInputSequence5(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotD(array2);
            System.out.println("Pivot-D: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 10030, compareCount);
        }

        @Test
        public void customTestPivotD_Sequence6_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence6(1000);
            int[] array2 = qs.GenerateInputSequence6(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotD(array2);
            System.out.println("Pivot-D: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 9763, compareCount);
        }

        @Test
        public void customTestPivotD_Sequence7_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence7(1000);
            int[] array2 = qs.GenerateInputSequence7(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotD(array2);
            System.out.println("Pivot-D: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 10245, compareCount);
        }

        @Test
        public void customTestPivotD_Sequence8_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence8(1000);
            int[] array2 = qs.GenerateInputSequence8(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotD(array2);
            System.out.println("Pivot-D: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 11924, compareCount);
        }

        @Test
        public void customTestPivotD_Sequence9_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence9(1000);
            int[] array2 = qs.GenerateInputSequence9(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotD(array2);
            System.out.println("Pivot-D: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 12823, compareCount);
        }

        @Test
        public void customTestPivotD_Sequence10_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence10(1000, 123456789);
            int[] array2 = qs.GenerateInputSequence10(1000, 123456789);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotD(array2);
            System.out.println("Pivot-D: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 10161, compareCount);
        }
    }

    public static class pivotETests {
        @Test
        public void testQuickSortPivotE() throws Exception {
            QuickSort qs = new QuickSort();
            // Set N: Size of input sequence
            int n = 680;
            int[] array = qs.GenerateInputSequence10(n, 123456789);
            int[] array2 = qs.GenerateInputSequence10(n, 123456789);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotE(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 7145, compareCount);
        }

        @Test
        public void customTestPivotE_n7() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = {1, 2, 3, 4, 1, 2, 4};
            int[] array2 = {1, 2, 3, 4, 1, 2, 4};
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotE(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 17, compareCount);
        }

        @Test
        public void customTestPivotE_n7_v2() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = {1, 2, 3, 4, 1, 2, 1};
            int[] array2 = {1, 2, 3, 4, 1, 2, 1};
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotE(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 20, compareCount);
        }

        @Test
        public void customTestPivotE_Sequence1_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence1(40);
            int[] array2 = qs.GenerateInputSequence1(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotE(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 162, compareCount);
        }

        @Test
        public void customTestPivotE_Sequence2_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence2(40);
            int[] array2 = qs.GenerateInputSequence2(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotE(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 219, compareCount);
        }

        @Test
        public void customTestPivotE_Sequence3_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence3(40);
            int[] array2 = qs.GenerateInputSequence3(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotE(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 220, compareCount);
        }

        @Test
        public void customTestPivotE_Sequence4_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence4(40);
            int[] array2 = qs.GenerateInputSequence4(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotE(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 218, compareCount);
        }

        @Test
        public void customTestPivotE_Sequence5_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence5(40);
            int[] array2 = qs.GenerateInputSequence5(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotE(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 198, compareCount);
        }

        @Test
        public void customTestPivotE_Sequence6_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence6(40);
            int[] array2 = qs.GenerateInputSequence6(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotE(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 225, compareCount);
        }

        @Test
        public void customTestPivotE_Sequence7_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence7(40);
            int[] array2 = qs.GenerateInputSequence7(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotE(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 237, compareCount);
        }

        @Test
        public void customTestPivotE_Sequence8_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence8(40);
            int[] array2 = qs.GenerateInputSequence8(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotE(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 339, compareCount);
        }

        @Test
        public void customTestPivotE_Sequence9_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence9(40);
            int[] array2 = qs.GenerateInputSequence9(40);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotE(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 299, compareCount);
        }

        @Test
        public void customTestPivotE_Sequence10_n40() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence10(40, 123456789);
            int[] array2 = qs.GenerateInputSequence10(40, 123456789);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotE(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 213, compareCount);
        }

        @Test
        public void customTestPivotE_Sequence1_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence1(1000);
            int[] array2 = qs.GenerateInputSequence1(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotE(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 9007, compareCount);
        }

        @Test
        public void customTestPivotE_Sequence2_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence2(1000);
            int[] array2 = qs.GenerateInputSequence2(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotE(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 10316, compareCount);
        }

        @Test
        public void customTestPivotE_Sequence3_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence3(1000);
            int[] array2 = qs.GenerateInputSequence3(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotE(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 9994, compareCount);
        }

        @Test
        public void customTestPivotE_Sequence4_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence4(1000);
            int[] array2 = qs.GenerateInputSequence4(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotE(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 10120, compareCount);
        }

        @Test
        public void customTestPivotE_Sequence5_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence5(1000);
            int[] array2 = qs.GenerateInputSequence5(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotE(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 9899, compareCount);
        }

        @Test
        public void customTestPivotE_Sequence6_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence6(1000);
            int[] array2 = qs.GenerateInputSequence6(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotE(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 10480, compareCount);
        }

        @Test
        public void customTestPivotE_Sequence7_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence7(1000);
            int[] array2 = qs.GenerateInputSequence7(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotE(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 10488, compareCount);
        }

        @Test
        public void customTestPivotE_Sequence8_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence8(1000);
            int[] array2 = qs.GenerateInputSequence8(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotE(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 13059, compareCount);
        }

        @Test
        public void customTestPivotE_Sequence9_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence9(1000);
            int[] array2 = qs.GenerateInputSequence9(1000);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotE(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 14032, compareCount);
        }

        @Test
        public void customTestPivotE_Sequence10_n1000() throws Exception {
            QuickSort qs = new QuickSort();
            int[] array = qs.GenerateInputSequence10(1000, 123456789);
            int[] array2 = qs.GenerateInputSequence10(1000, 123456789);
            Arrays.sort(array);
            int compareCount = qs.QuickSortPivotE(array2);
            System.out.println("Pivot-E: CompareCount:" + compareCount);
            assertArrayEquals("Sorting Not Correct", array, array2);
            assertEquals("Compare Count Not Correct", 11584, compareCount);
        }

    }
}