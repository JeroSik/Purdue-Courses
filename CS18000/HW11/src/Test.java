public class Test {
  public static void main(String[] args) {
    int[] observations = new int[]{5, 4, 3, 1, 2};
    int temp;
    for (int i = 0; i <= observations.length; i++) {
      for (int j = i + 1; j <= observations.length - 1; j++) {
        if (observations[i] > observations[j]) {
          temp = observations[i];
          observations[i] = observations[j];
          observations[j] = temp;
        }
      }
    }

    for (int i = 0; i < observations.length; i++) {
      System.out.print(observations[i]);
    }
  }
}
