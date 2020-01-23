import java.util.*;

public class TriTiling {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        List<Integer> ans = new ArrayList<Integer>();

        while (true) {
            int n = scan.nextInt();
            if (n == -1) {
                break;
            }
            
            if (n % 2 == 1) {
                ans.add(0);
            } else {
                List<Integer> case1 = new ArrayList<Integer>();
                List<Integer> case2 = new ArrayList<Integer>();
                case1.add(1);
                case1.add(0);
                case2.add(0);
                case2.add(1);

                while (case1.size() <= n) {
                    int curr = case1.size();
                    case1.add(case1.get(curr - 2) + 2 * case2.get(curr - 1));
                    case2.add(case1.get(curr - 1) + case2.get(curr - 2));
                }
                ans.add(case1.get(n));
            }
        }

        for (int num : ans) {
            System.out.println(num);
        }      
    }
}