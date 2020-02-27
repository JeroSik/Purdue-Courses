import java.util.*;

public class King {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);

    int n = scan.nextInt();
    
    ArrayList<Integer> a = new ArrayList<Integer>();
    for(int i = 1; i<= n; i++) {
        double sq = Math.sqrt(i);
        if (((sq - Math.floor(sq)) == 0)) {
            a.add(i);
        }
    }


    for (int i = 0; i < a.size() - 1; i++) {
        System.out.print(a.get(i) + ",");
    }
    System.out.print(a.get(a.size() - 1));
  }
}