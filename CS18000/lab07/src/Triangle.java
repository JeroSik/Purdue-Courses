import java.lang.Math;

public class Triangle {
  private double base;

  public Triangle (double base) {
    this.base = base;
  }

  public double getPerimeter() {
    return 3 * this.base;
  }

  public double getArea() {
    return (Math.sqrt(3) / 4) * Math.pow(this.base, 2);
  }
}
