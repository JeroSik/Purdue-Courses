/*
 * User: Ian Renfro
 * Date: Feb. 6, 2018
 */

import java.util.ArrayList;

public class Treasure {
  public static int findMaxGold(TreeNode root) {
    // Base case
    if (root.getChildren().size() == 0) {
      return root.getGold();
    } else {
      // Recursive case
      int maxGold = root.getGold();
      int gold = 0;
      int temp;

      // Get through each branch
      for(int i = 0; i < root.getChildren().size(); i++) {
        temp = findMaxGold(root.getChildren().get(i));
        if (temp > gold) {
          gold = temp;
        }
      }

      // Return new gold amount
      return maxGold + gold;
    }
  }

  public static ArrayList<TreeNode> findMaxGoldPath(TreeNode root) {
    // Start with the root
    ArrayList<TreeNode> maxGoldPath = new ArrayList<>();
    maxGoldPath.add(root);

    int child = -1;
    int temp;

    // Loops through until leaf node is reached
    while (root.getChildren().size() != 0) {
      int maxGold = 0;
      // Look at the current children and find the one with the most gold in it's subtree
      for (int i = 0; i < root.getChildren().size(); i++) {
        temp = findMaxGold(root.getChildren().get(i));
        if (temp > maxGold) {
          maxGold = temp;
          child = i;
        }
      }

      // Then add it to the arraylist
      maxGoldPath.add(root.getChildren().get(child));

      // Update the node to be that child and look at it's children
      root = root.getChildren().get(child);
    }

    // Return the path
    return maxGoldPath;
  }
}
