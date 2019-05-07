import javax.swing.JOptionPane;

public class DataAnalyzer {
  public static void main(String[] args) {
    boolean restart = true;
    boolean again = true;
    do {
      //Asks user for observation data
      int num = Integer.parseInt(JOptionPane.showInputDialog(null, "How many observations do the data contain?"));
      while (num <= 0) {
        JOptionPane.showMessageDialog(null, "Invalid Input", "Data Analyzer", JOptionPane.ERROR_MESSAGE);
        num = Integer.parseInt(JOptionPane.showInputDialog(null, "How many observations do the data contain?"));
      }
      double[] observations = new double[num];
      for (int i = 1; i <= num; i++) {
        observations[i - 1] = Double.parseDouble(JOptionPane.showInputDialog(null, "please enter observation " + i + ":"));
      }

      //Sorts observation data
      double temp;
      for (int i = 0; i <= observations.length; i++) {
        for (int j = i + 1; j <= observations.length - 1; j++) {
          if (observations[i] > observations[j]) {
            temp = observations[i];
            observations[i] = observations[j];
            observations[j] = temp;
          }
        }
      }

      do {
        //Outputs options
        int option = JOptionPane.showOptionDialog(null, "Would you like to take the quiz?", "Quiz", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"Min", "Max", "Median", "Mean", "Sum"}, null);
        double min = 0, max = 0, median = 0, sum = 0, mean = 0;
        switch (option) {
          case 0:
            min = observations[0];
            JOptionPane.showMessageDialog(null, "the min is " + min);
            break;
          case 1:
            max = observations[observations.length - 1];
            JOptionPane.showMessageDialog(null, "the max is " + max);
            break;
          case 2:
            if (observations.length % 2 == 1) {
              median = observations[((observations.length + 1) / 2) - 1];
            } else {
              median = (observations[(observations.length / 2) - 1] + observations[observations.length / 2]) / 2;
            }
            JOptionPane.showMessageDialog(null, "the median is " + median);
            break;
          case 3:
            for (int i = 0; i < observations.length; i++) {
              mean += observations[i];
            }
            mean = mean / observations.length;
            JOptionPane.showMessageDialog(null, "the mean is " + mean);
            break;
          case 4:
            for (int i = 0; i < observations.length; i++) {
              sum += observations[i];
            }
            JOptionPane.showMessageDialog(null, "the sum is " + sum);
            break;
        }

        //asks user if they want to run again
        int sameSet = JOptionPane.showOptionDialog(null, "Do you want to analyze this data set again?", "Data Analyzer",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (sameSet == JOptionPane.NO_OPTION) {
          again = false;
          int anotherSet = JOptionPane.showOptionDialog(null, "Do you want to analyze another data set?", "Data Analyzer",
                  JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
          if (anotherSet == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(null, "Thank you for using Data Analyzer!");
            restart = false;
          } else if (anotherSet == JOptionPane.YES_OPTION) {
            restart = true;
          }
        } else if (sameSet == JOptionPane.YES_OPTION) {
          again = true;
        }
      } while (again);
    } while (restart);
  }
}
