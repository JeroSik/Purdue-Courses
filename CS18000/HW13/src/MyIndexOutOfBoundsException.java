/**
 * Custom {@code IndexOutOfBoundsException} implementation.
 *
 * @author Logan Kulinski
 * @version July 13, 2017
 */
public final class MyIndexOutOfBoundsException extends RuntimeException {
  /**
   * Constructs a newly allocated {@code MyIndexOutOfBoundsException} object.
   *
   * @param message the message to be displayed when the exception is thrown
   */
  public MyIndexOutOfBoundsException(String message) {
    super(message);
  } //MyIndexOutOfBoundsException
}