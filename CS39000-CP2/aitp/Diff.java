import java.util.*;
import java.lang.*;

public class Diff {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int n = scan.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
          nums[i] = scan.nextInt();
        }
        
        int m = scan.nextInt();
        for (int t = 0; t < m; t++) {
            int k = scan.nextInt();
            boolean exists = false;

            HashSet<Integer> s = new HashSet<Integer>(); 
            for (int i = 0; i < n; ++i) { 
                int temp = k + nums[i]; 
    
                if (s.contains(temp)) { 
                    exists = true;
                    break;
                }
                s.add(nums[i]);
            }
            
            s = new HashSet<Integer>(); 
            for (int i = 0; i < n; ++i) { 
                int temp = nums[i] - k; 
    
                if (s.contains(temp)) { 
                    exists = true;
                    break;
                }
                s.add(nums[i]);
            }

            if (exists) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }
}