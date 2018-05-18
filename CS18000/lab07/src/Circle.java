import java.lang.Math;

public class Circle {
  private double radius;

  public Circle (double radius) {
    this.radius = radius;
  }

  public double getCircumference () {
    return Math.PI * 2 * this.radius;
  }

  public double getArea () {
    return Math.PI * Math.pow(this.radius, 2);
  }
}
