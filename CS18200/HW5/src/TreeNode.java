/*
 * User: Ian Renfro
 * Date: Feb. 6, 2018
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class TreeNode {
  private int gold;
  private int nodeId;
  private int maxGold;
  private ArrayList<TreeNode> children;

  public TreeNode(int nodeId, int gold) {
    this(nodeId, gold, new ArrayList<>());
  }

  public TreeNode(int nodeId, int gold, ArrayList<TreeNode> children) {
    this.nodeId = nodeId;
    this.gold = gold;
    this.maxGold = -1;
    this.children = new ArrayList<>();
    this.children.addAll(children);
  }

  public static TreeNode readTreeFromFile(String filename) {
    ArrayList<String> lines = new ArrayList<>();
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filename)));
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        lines.add(line);
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
      System.exit(0);
    }
    Queue<TreeNode> nodes = new LinkedList<>();
    String[] r = lines.get(0).split(" ");
    if (r.length == 2) {
      return new TreeNode(Integer.parseInt(r[0]), Integer.parseInt(r[1]));
    }
    TreeNode tempRoot = new TreeNode(Integer.parseInt(r[0]), Integer.parseInt(r[1]));
    nodes.add(tempRoot);
    while (!nodes.isEmpty()) {
      TreeNode n = nodes.remove();
      String line = getLine(lines, n.getNodeId() + "");
      String[] parts = line.split(" ");
      n.setGold(Integer.parseInt(parts[1]));
      for (int i = 2; i < parts.length; i++) {
        TreeNode child = new TreeNode(Integer.parseInt(parts[i]), -1);
        n.addChild(child);
        nodes.add(child);
      }
    }
    return tempRoot;
  }

  private static String getLine(ArrayList<String> lines, String search) {
    for (String line : lines) {
      if (search.equals(line.substring(0, line.indexOf(' ')))) {
        return line;
      }
    }
    return null;
  }

  public void addChild(int name, int data) {
    this.children.add(new TreeNode(name, data));
  }

  public void addChild(TreeNode n) {
    this.children.add(n);
  }

  public ArrayList<TreeNode> getChildren() {
    return children;
  }

  public int getGold() {
    return gold;
  }

  public void setGold(int gold) {
    this.gold = gold;
  }

  public int getNodeId() {
    return nodeId;
  }

  public int getMaxGold() {
    return maxGold;
  }

  public void setMaxGold(int maxGold) {
    this.maxGold = maxGold;
  }

  public String toString() {
    StringBuilder res = new StringBuilder();
    Queue<TreeNode> nodes = new LinkedList<>();
    nodes.add(this);
    while (!nodes.isEmpty()) {
      TreeNode n = nodes.remove();
      res.append(String.format("%d\t%d:", n.getNodeId(), n.getGold()));
      for (TreeNode m : n.getChildren()) {
        res.append(String.format(" %d", m.getNodeId()));
        nodes.add(m);
      }
      if (n.getChildren().size() == 0) {
        res.append(" Leaf Node");
      }
      res.append('\n');
    }
    res.setLength(res.length() - 1);
    return res.toString();
  }
}
