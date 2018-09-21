import java.util.LinkedList;
import java.util.Queue;

public class Test {
  public static void main(String[] args) {
    int[] arr = {10, 7, 3, 5, 8, 2, 9};
    Queue<Integer> queue = new LinkedList<>();
    int temp = arr[0];
    queue.add(temp);

    for (int i = 0; i < arr.length; i++) {
      if (arr[i] > temp) {
        queue.add(arr[i]);
      } else {

      }
    }
  }
}