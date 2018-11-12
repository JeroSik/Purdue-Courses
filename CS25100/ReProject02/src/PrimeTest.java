public class PrimeTest {
  public static void main(String[] args) {
    int num = 4;
    for (int a = 0; a < 1000; a++) {
      double temp = Math.sqrt(num);
      boolean found;
      while (true) {
        found = true;
        for (int i = 2; i <= temp; i++) {
          if (num % i == 0) {
            num++;
            found = false;
            break;
          }
        }
        if (found) {
          System.out.println(num);
          num++;
          break;
        }
      }
    }
  }
}
