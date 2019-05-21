import java.lang.Object;
import java.util.Arrays;

public final class MyArrayList<T> {
  private int size;
  private T[] values;

  public MyArrayList(int initialCapacity) throws java.lang.IllegalArgumentException {
    if (initialCapacity < 0) {
      throw new IllegalArgumentException();
    } else {
      values = (T[])new Object[initialCapacity];
      size = 0;
    }
  }
  public MyArrayList() {
    this(10);
  }

  public void add(int index, T value) throws MyIndexOutOfBoundsException{
    if ((index < 0 || index > size())) {
      throw new MyIndexOutOfBoundsException("Index is out of bounds.");
    } else {
      ensureCapacity();
      for (int i = size() - 1; i >= index; i--) {
        values[i + 1] = values[i];
      }
      values[index] = value;
      size++;
    }
  }
  public void add(T value) {
    ensureCapacity();
    values[size()] = value;
    size++;
  }
  public void addFront(T value) {
    ensureCapacity();
    for (int i = size() - 1; i >= 0; i--) {
      values[i + 1] = values[i];
    }
    values[0] = value;
    size++;
  }
  public void set(int index, T value) throws MyIndexOutOfBoundsException {
    if (index < 0 || index >= size()) {
      throw new MyIndexOutOfBoundsException("Index is out of bounds.");
    } else {
      values[index] = value;
    }
  }

  public void ensureCapacity() {
    if (size() >= values.length) {
      T[] temp = (T[])new Object[values.length * 2];
      for (int i = 0; i < values.length; i++) {
        temp[i] = values[i];
      }
      values = temp;
    }
  }

  public void remove(int index) throws MyIndexOutOfBoundsException {
    if (index < 0 || index >= size()) {
      throw new MyIndexOutOfBoundsException("Index is out of bounds.");
    } else {
      for (int i = index; i < size() - 1; i++) {
        values[i] = values[i + 1];
      }
      values[size() - 1] = null;
      size--;
    }
  }
  public boolean remove(T value) {
    for (int i = 0; i < size(); i++) {
      if (values[i] == value) {
        for (int j = i; j < size() - 1; j++) {
          values[j] = values[j + 1];
        }
        values[size() - 1] = null;
        size--;
        return true;
      }
    }
    return false;

  }
  public void clear() {
    size = 0;
    for (int i = 0; i < values.length; i++) {
      values[i] = null;
    }
  }

  public boolean isEmpty() {
    if (size == 0) {
      return true;
    } else {
      for (int i = 0; i < values.length; i++) {
        if (values[i] != null) {
          return false;
        }
      }
      return true;
    }
  }
  public boolean contains(T value) {
    for (int i = 0; i < size(); i++) {
      if (value.equals(values[i])) {
        return true;
      }
    }
    return false;
  }
  public int indexOf(T value) {
    for (int i = 0; i < values.length; i++) {
      if (values[i] == value) {
        return i;
      }
    }
    return -1;
  }
  public T get(int index) throws MyIndexOutOfBoundsException {
    if (index < 0 || index >= size()) {
      throw new MyIndexOutOfBoundsException("Index is out of bounds.");
    } else {
      return values[index];
    }
  }
  public int size() {
    return size;
  }

  public boolean equals(Object o) {
    if (o == null || !(o instanceof MyArrayList)) {
      return false;
    }
    MyArrayList myArrayList = (MyArrayList) o;
    if (!Arrays.equals(((MyArrayList) o).values, values)) {
      return false;
    }
    return true;
  }
  public String toString() {
    StringBuilder array = new StringBuilder("[");
    for (int i = 0; i < size() - 1; i++) {
      array.append(values[i] + ", ");
    }
    array.append(values[size() - 1] + "]");
    return array.toString();
  }
}
