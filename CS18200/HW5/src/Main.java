/*
 * User: Ian Renfro
 * Date: Feb. 6, 2018
 */

public class Main {
  public static void main(String[] args) {
    // The path to your filename will change based on your structure
    // If you are using IntelliJ then it runs the Java files from the project directory so you will need src/filename
    // If you are using a shell then just make sure to include the local path to it
    // If you are looking to make your own file for testing please follow the format below:
    //      Name Gold Child1 Child2 ... ChildN
    // The format is lines of space separated integers where the first integer on a line is the name of the node
    //      the second number is the amount of Gold on the island and then comes a set of space separated integers
    //      that are the direct children of that node.  The root of the tree MUST be the first line in the file.
    //      If you are not sure please ask a TA, come to office hours, look at the given test cases.
    TreeNode t = TreeNode.readTreeFromFile("Trees/tree6.txt");
    System.out.println(t);
    System.out.println("Max Gold: " + Treasure.findMaxGold(t));
    Treasure.findMaxGoldPath(t);
    System.out.print("Path: ");
    for (int i = 0; i < Treasure.findMaxGoldPath(t).size(); i++) {
      System.out.print(Treasure.findMaxGoldPath(t).get(i).getNodeId() + " ");
    }
  }
}
