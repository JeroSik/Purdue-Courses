/**
 * CS180 - Lab 09
 *
 * Representation of an unsigned integer.
 *
 * @author Youngsik Yoon, yoon161@purdue.edu, 4
 *
 * @version 1.0 Oct 19, 2017
 *
 */
import java.lang.Number;

public class UnsignedInteger extends Number{
  private int value;

  @Override
  public int intValue() {
    return this.value;
  }
  @Override
  public long longValue() {
    return (long)this.value;
  }
  @Override
  public float floatValue() {
    return (float)this.value;
  }
  @Override
  public double doubleValue() {
    return (double)this.value;
  }

  public UnsignedInteger() {
    this.value = 0;
  }
  public UnsignedInteger (int value) {
    if (value < 0) {
      this.value = Integer.MAX_VALUE - Math.abs(value + 1);
    } else {
      this.value = value;
    }
  }

  public UnsignedInteger add (UnsignedInteger unsignedInteger) {
    int result = value + unsignedInteger.value;
    if (result < 0) {
      result = Integer.MAX_VALUE - Math.abs(result + 1);
    }
    return new UnsignedInteger(result);
  }
  public UnsignedInteger subtract (UnsignedInteger unsignedInteger) {
    int result = value - unsignedInteger.value;
    if (result < 0) {
      result = Integer.MAX_VALUE - Math.abs(result + 1);
    }
    return new UnsignedInteger(result);
  }
  public UnsignedInteger multiply (UnsignedInteger unsignedInteger) {
    int result = value * unsignedInteger.value;
    if (result < 0) {
      result = Integer.MAX_VALUE - Math.abs(result + 1);
    }
    return new UnsignedInteger(result);
  }
  public UnsignedInteger divide (UnsignedInteger unsignedInteger) {
    if (unsignedInteger.value == 0) {
      throw new IllegalArgumentException();
    }
    int result = value / unsignedInteger.value;
    if (result < 0) {
      result = Integer.MAX_VALUE - Math.abs(result + 1);
    }
    return new UnsignedInteger(result);
  }

  @Override
  public int hashCode() {
    int result = 23;

    result = 19 * result + this.value;

    return result;
  } //hashCode

  @Override
  public boolean equals(Object o) {
    return o instanceof UnsignedInteger && (this.value == ((UnsignedInteger) o).value);
  } //equals

  @Override
  public String toString() {
    return String.format("UnsignedInteger[%d]", this.value);
  } //toString
}