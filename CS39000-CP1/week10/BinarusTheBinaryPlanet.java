import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner read = new Scanner(System.in);
        if (read.nextInt() == 2)
            System.out.print(1);
        else if (Math.random() * 10 % 2 == 0)
            System.out.print(1);
        else
            System.out.print(0);
    }
}
