/**
 *CS180 - Lab 01
 *
 *A GUI program that copies the user's input and output
 *
 *@author Youngsik Yoon, yoon161@purdue.edu, 4
 *
 *@version 1.0 Aug 24, 2017
 *
 */
import javax.swing.*;

public class Copycat {
  public static void main(String[] args) {
    String s = JOptionPane.showInputDialog("Trigger the copycat: ");
    JOptionPane.showMessageDialog(null, s);
  }
}
