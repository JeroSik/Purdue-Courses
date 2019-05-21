/**
 *
 *CS180 - Project02
 *
 *Connect 4 through the terminal.
 *
 *@author Youngsik Yoon, yoon161@purdue.edu
 *
 *@version 1.0
 *
 */

//TODO: Add a color scheme

import java.util.Scanner;

public class Connect4 {
  //public static final String ANSI_RED = "\u001B[31m";
  //public static final String ANSI_YELLOW = "\u001B[33m";
  //public static final String ANSI_RESET = "\u001B[0m";
  private char[][] board;
  private char red;
  private char yellow;
  private char emptySpace;

  public Connect4() {
    this.red = 'O';
    this.yellow = 'X';
    this.emptySpace = ' ';
    this.board = new char[6][7];
    for (int i = 0; i < this.board.length; i++) {
      for (int j = 0; j < this.board[i].length; j++) {
        board[i][j] = emptySpace;
      }
    }
  }

  public char[][] getBoard() {
    char[][] newBoard = new char[6][7];
    for (int i = 0; i < this.board.length; i++) {
      for (int j = 0; j < this.board[i].length; j++) {
        newBoard[i][j] = this.board[i][j];
      }
    }
    return newBoard;
  }

  public int putPiece(int column, char color) {
    int row = -1;
    for (int i = 5; i >= 0; i--) {
      if (this.board[i][column] == ' ') {
        this.board[i][column] = color;
        row = i;
        break;
      }
    }
    return row;
  }

  public char checkAlignment (int row, int column) {
    int vertical = 0;
    for (int i = row; i <= 5; i++) {
      if (this.board[row][column] == this.board[i][column]) {
        vertical++;
      } else {
        vertical = 0;
      }
      if (vertical == 4) {
        return this.board[row][column];
      }
    }//check vertical

    int horizontal = 0;
    for (int i = 0; i <= 6; i++) {
      if (this.board[row][column] == this.board[row][i]) {
        horizontal++;
      } else {
        horizontal = 0;
      }
      if (horizontal == 4) {
        return this.board[row][column];
      }
    }//check horizontal

    int diagonal1 = 0;
    for (int i = row, j = column; i <= 5; i++, j++) {
      if (j <= 6) {
        if (this.board[row][column] == this.board[i][j]) {
          diagonal1++;
        } else {
          break;
        }
        if (diagonal1 == 4) {
          return this.board[row][column];
        }
      }
    }//check y=-x
    if (diagonal1 < 4) {
      for (int i = row, j = column; i >= 0; i--, j--) {
        if (j >= 0) {
          if (this.board[row][column] == this.board[i][j]) {
            diagonal1++;
          } else {
            diagonal1 = 0;
          }
          if (diagonal1 == 5) {
            return this.board[row][column];
          }
        }
      }
    }//check y=-x other way

    int diagonal2 = 0;
    for (int i = row, j = column; i <= 5; i++, j--) {
      if (j >= 0) {
        if (this.board[row][column] == this.board[i][j]) {
          diagonal2++;
        } else {
          break;
        }
        if (diagonal2 == 4) {
          return this.board[row][column];
        }
      }
    }//check diagonal one way
    if (diagonal2 < 4) {
      for (int i = row, j = column; i >= 0; i--, j++) {
        if (j <= 6) {
          if (this.board[row][column] == this.board[i][j]) {
            diagonal2++;
          } else {
            diagonal2 = 0;
          }
          if (diagonal2 == 5) {
            return this.board[row][column];
          }
        }
      }
    }//check diagonal other way
    return ' ';
  }

  public void printScreen() {
    char[] alphabet = new char[]{'A', 'B', 'C', 'D', 'E', 'F'};
    System.out.println("    0   1   2   3   4   5   6");
    System.out.println("  -----------------------------");
    for (int i = 0; i < this.board.length; i++) {
      System.out.print(alphabet[i] + " | ");
      for (int j = 0; j < this.board[i].length; j++) {
        System.out.print(this.board[i][j] + " | ");
      }
      System.out.println("\n  -----------------------------");
    }
  }

  public void play() {
    Scanner scan = new Scanner(System.in);
    int turn = 0;
    int column;
    char player;
    int row;
    GAME:
    while (true) {
      if (turn % 2 == 0) {
        player = red;
      } else {
        player = yellow;
      }
      printScreen();
      System.out.println("Current player: '" + player + "'");
      System.out.print("Choose a column: ");
      if (scan.hasNextInt()) {
        column = scan.nextInt();
      } else {
        System.out.println("Please input an integer.");
        scan.next();
        continue GAME;
      }//checks for integer input

      if (column > 6 || column < 0) {
        System.out.println("Column does not exist. Choose a different column.");
        continue GAME;
      }//checks if column exists

      row = putPiece(column, player);
      if (row == -1) {
        System.out.println("Column is full. Choose a different column.");
        continue GAME;
      } else {
        turn++;
        if ((checkAlignment(row, column) == player)) {
          printScreen();
          System.out.println("!!! Winner is Player '" + player + "'");
          return;
        }
      }//check for full column && winner

      boolean tie = true;
      for (int i = 0; i < this.board.length; i++) {
        for (int j = 0; j < this.board[i].length; j++) {
          if (this.board[i][j] == ' ') {
            tie = false;
          }
        }
      }
      if (tie) {
        printScreen();
        System.out.println("!!! The game is a tie");
        return;
      }
    }
  }
}
