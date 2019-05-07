public class ThreadedArray {
  private String[] array;
  private final Object lock = new Object();

  public ThreadedArray(int length) {
    array = new String[length];
  }

  public String[] getArray() {
    return array;
  }

  public boolean insert(String string) {
    synchronized (lock) {
      for (int i = 0; i < array.length; i++) {
        if (array[i] == null) {
          array[i] = string;
          return true;
        }
      }
    }
    return false;
  }

  public boolean setIndex(String string, int index) {
    if (index >= array.length || index < 0) {
      return false;
    }
    synchronized (lock) {
      array[index] = string;
    }
    return true;
  }

  public String remove(int index) {
    if (index >= array.length || index < 0) {
      return null;
    }
    synchronized (lock) {
      String temp = array[index];
      array[index] = null;
      return temp;
    }
  }

  public String getIndex(int index) {
    if (index >= array.length) {
      return null;
    } else if (array[index] != null) {
      synchronized (lock) {
        return array[index];
      }
    }
    return null;
  }
}