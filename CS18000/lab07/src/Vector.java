import java.lang.Math;

public class Vector {
  public double x, y, z;

  Vector () {
    this.x = this.y = this.z = 0.0;
  }

  Vector (double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Vector add (final Vector v) {
    Vector add = new Vector(this.x + v.x, this.y + v.y, this.z + v.z);
    return add;
  }

  public Vector sub (final Vector v) {
    Vector sub = new Vector(this.x - v.x, this.y - v.y, this.z - v.z);
    return sub;
  }

  public Vector multC (final double d) {
    Vector multC = new Vector(this.x * d, this.y * d, this.z * d);
    return multC;
  }

  public Vector mult (final Vector v) {
    Vector mult = new Vector(this.x * v.x, this.y * v.y, this.z * v.z);
    return mult;
  }


  public Vector norm () {
    double magnitude = Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
    Vector norm = new Vector(this.x / magnitude, this.y / magnitude, this.z / magnitude);
    return norm;
  }

  public double dot (final Vector v) {
    return this.x * v.x + this.y * v.y + this.z * v.z;
  }
}
