import java.util.*;
import java.lang.*;

public class TravelingMonk {
    private static double calcIntersection(Point A, Point B, Point C, Point D, double lower, double upper) { 
		double a1 = B.y - A.y; 
		double b1 = A.x - B.x;
		double c1 = a1*(A.x) + b1*(A.y);
	
		double a2 = D.y - C.y; 
		double b2 = C.x - D.x; 
		double c2 = a2*(C.x)+ b2*(C.y); 
	
		double determinant = a1*b2 - a2*b1; 
	
		if (determinant == 0) { 
            return -1;
		} else { 
            // double x = (b2*c1 - b1*c2)/determinant; 
			// double y = (a1*c2 - a2*c1)/determinant;
            double t = (b2*c1 - b1*c2)/determinant;
			if (t >= lower && t <= upper) {
                return t;
            } else {
                return -1;
            }
		} 
	}

    private static double findIntersection(double[][] asc, double[][] desc, double elevation) {
        int i = 0, j = 0;
        double ta = 0;
        double tb = 0;
        double ha = 0;
        double hb = elevation;
        double lower = 0;
        double upper = 0;

        while(true) {
            Point A = new Point(ta, ha);
            Point B = new Point(asc[i][0], asc[i][1]);
            Point C = new Point(tb, hb);
            Point D = new Point(desc[j][0], desc[j][1]);

            if (asc[i][0] == (desc[j][0])) {
                ta = asc[i][0];
                ha = asc[i][1];
                i++;

                tb = desc[j][0];
                hb = desc[j][1];
                j++;

                upper = ta;
            } else if (asc[i][0] > desc[j][0]) {
                tb = desc[j][0];
                hb = desc[j][1];
                j++;

                upper = tb;
            } else {
                ta = asc[i][0];
                ha = asc[i][1];
                i++;

                upper = ta;
            }
            
            // System.out.println(A.print() + " and " + B.print() + " | " + C.print() + " and " + D.print());
            double intersection = calcIntersection(A, B, C, D, lower, upper);
            if (intersection != -1) {
                return intersection;
            }

            lower = upper;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int a = scan.nextInt();
        int b = scan.nextInt();

        double[][] asc = new double[a][2];
        double[][] desc = new double[b][2];
        for (int i = 0; i < a; i++) {
            asc[i][1] = i == 0 ? scan.nextDouble() : asc[i - 1][1] + scan.nextDouble();
            asc[i][0] = i == 0 ? scan.nextDouble() : asc[i - 1][0] + scan.nextDouble();
        }
        double elevation = asc[a - 1][1];
        for (int i = 0; i < b; i++) {
            desc[i][1] = i == 0 ? elevation - scan.nextDouble() : desc[i - 1][1] - scan.nextDouble();
            desc[i][0] = i == 0 ? scan.nextDouble() : desc[i - 1][0] + scan.nextDouble();
        }

        System.out.println(findIntersection(asc, desc, elevation));
    }

    private static class Point {
        private double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public String print() {
            // System.out.println("(" + x + ", " + y + ")");
            return "(" + x + ", " + y + ")";
        }
    }
}