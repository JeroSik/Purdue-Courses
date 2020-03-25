import java.util.*;
import java.lang.*;

public class Pairs {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int T = scan.nextInt();
        for (int t = 0; t < T; t++) {
            double AB = scan.nextDouble();
            double BC = scan.nextDouble();
            double CA = scan.nextDouble();

            int A = (int) Math.sqrt((AB*CA)/(BC));
            int B = (int) Math.sqrt((BC*AB)/(CA));
            int C = (int) Math.sqrt((BC*CA)/(AB));

            System.out.println(A + " " + B + " " + C);
        }
    }
}