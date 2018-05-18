import java.util.Scanner;

public class Driver {

  public static void main(String[] args) {

    Scanner s = new Scanner(System.in);

    System.out.println("Enter the radius of the circle: ");
    double radius = s.nextDouble();
    Circle c = new Circle(radius);

    System.out.println("Enter the base of the triangle: ");
    double base = s.nextDouble();
    Triangle t = new Triangle(base);

    System.out.println("Enter the length of the rectangle: ");
    double length = s.nextDouble();
    System.out.println("Enter the width of the rectangle: ");
    double width = s.nextDouble();
    Rectangle r = new Rectangle(length, width);

    Vector v1 = new Vector(2,3,4);
    Vector v2 = new Vector(4,2.3,5);
    Vector v3 = new Vector();



    System.out.println("Circle circumference: " + c.getCircumference());
    System.out.println("Circle area: " + c.getArea());
    System.out.println("Triangle perimeter: " + t.getPerimeter());
    System.out.println("Triangle area: " + t.getArea());
    System.out.println("Rectangle perimeter: " + r.getPerimeter());
    System.out.println("Rectangle area: " + r.getArea());

    System.out.printf("v1 + v2 = <%f, %f, %f >\n", v1.add(v2).x, v1.add(v2).y, v1.add(v2).z);
    System.out.printf("v1 - v2 = <%f, %f, %f >\n", v1.sub(v2).x, v1.sub(v2).y, v1.sub(v2).z);
    System.out.printf("v1 * v2 = <%f, %f, %f >\n", v1.mult(v2).x, v1.mult(v2).y, v1.mult(v2).z);
    System.out.printf("v1 . v2 = %f\n", v1.dot(v2));
    System.out.printf("normal of v1: <%f, %f, %f >\n", v1.norm().x, v1.norm().y, v1.norm().z);
    System.out.printf("normal of v2: <%f, %f, %f >\n", v2.norm().x, v2.norm().y, v2.norm().z);
  }

}