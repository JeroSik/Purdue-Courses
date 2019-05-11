import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scan = new Scanner(System.in);
        int l = scan.nextInt();
        scan.nextLine();
        String meme = scan.nextLine();
        String message = scan.nextLine();
        for (int i = 0; i < l - meme.length(); i++) {
            if (meme.charAt(0) == message.charAt(i)) {
                if (meme.equals(message.substring(i, i + meme.length()))) {
                    System.out.print(i + " ");
                }
            }
        }
    }
}
