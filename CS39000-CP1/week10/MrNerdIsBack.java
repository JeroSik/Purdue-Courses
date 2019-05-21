import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        int t = scan.nextInt();
        ArrayList<Integer> repeats = findRepeats();
        int mod = repeats.size();
        scan.nextLine();
        for (int i = 0; i < t; i++) {
            String num = lastDigits(scan.nextLine());
            int index = Integer.parseInt(num) % mod;
            System.out.println(repeats.get(index));
        }
    }

    public static ArrayList<Integer> findRepeats() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        HashSet<Integer> set = new HashSet<Integer>(); 
        list.add(1);
        set.add(1);
        int x = 1;
        while (true) {
            x = (x * 2019) % 10000;
            if (set.contains(x)) {
                break;
            }
            list.add(x);
            set.add(x);
        }
        return list;
    }
    
    public static String lastDigits(String s) {
        if (s.length() > 4) {
            return s.substring(s.length() - 4);
        } else {
            return s;
        }
    }
}
