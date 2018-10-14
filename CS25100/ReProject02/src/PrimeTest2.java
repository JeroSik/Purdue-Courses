public class PrimeTest2 {
  public static void main(String[] args) {
    int num = 8;
    //for (int a = 0; a < 1000; a++) {
      double temp = Math.sqrt(num);
      boolean found;
      while (true) {
        found = true;
        for (int i = 2; i < num; i++) {
          if (num % i == 0) {
            num++;
            found = false;
            break;
          }
        }
        if (found) {
          System.out.println(num);
          return;
        }
      }
    //}
  }
}
