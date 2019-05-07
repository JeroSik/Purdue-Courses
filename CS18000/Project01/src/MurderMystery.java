/**
 * Description: CS180 Fall 2017 Project 1
 *
 * @author Youngsik Yoon, yoon161@purdue.edu
 * @version 1.0, 10/6/17
 */
import java.util.Random;
import java.util.Scanner;

public class MurderMystery {
  private final static String round1Banner = "========== Round 1 ==========";
  private final static String round2Banner = "========== Round 2 ==========";
  private final static String round3Banner = "========== Round 3 ==========";
  private final static String suspectList = "0 - Doctor\n" + "1 - Janitor\n" + "2 - Journalist\n" + "3 - Rich Person\n" + "4 - Professor";

  public static void main(String[] args) {
    int seed = Integer.parseInt(args[0]);
    if (seed == -1) {
      return;
    }
    Random r = new Random(seed);
    Scanner scan = new Scanner(System.in);
    int play = 1;
    int killer, first, second, third, fourth;
    String clear = "";

    GAME:
    while (play == 1) {
      //Part 0
      killer = r.nextInt(5);
      do {
        first = r.nextInt(5);
      } while (first == killer);
      do {
        second = r.nextInt(5);
      } while ((second == killer) || (second == first));
      do {
        third = r.nextInt(5);
      } while ((third == killer) || (third == first) || (third == second));
      do {
        fourth = r.nextInt(5);
      } while ((fourth == killer) || (fourth == first) || (fourth == second) || (fourth == third));
      //System.out.println(killer + "" + first + "" + second + "" + third + "" + fourth);

      //Part 1
      System.out.println(round1Banner + "\nThings found at crime scene:");
      switch (first) {
        case 0:
          System.out.println("1. Dirty mop");
          System.out.println("2. High-quality pen");
          System.out.println("3. Rolex watch");
          System.out.println("4. Lecture material");
          break;
        case 1:
          System.out.println("1. Stethoscope");
          System.out.println("2. High-quality pen");
          System.out.println("3. Rolex watch");
          System.out.println("4. Lecture material");
          break;
        case 2:
          System.out.println("1. Stethoscope");
          System.out.println("2. Dirty mop");
          System.out.println("3. Rolex watch");
          System.out.println("4. Lecture material");
          break;
        case 3:
          System.out.println("1. Stethoscope");
          System.out.println("2. Dirty mop");
          System.out.println("3. High-quality pen");
          System.out.println("4. Lecture material");
          break;
        case 4:
          System.out.println("1. Stethoscope");
          System.out.println("2. Dirty mop");
          System.out.println("3. High-quality pen");
          System.out.println("4. Rolex watch");
          break;
      }
      System.out.println("Who do you no longer suspect? \n" + suspectList);
      int firstElimination = scan.nextInt();
      if (firstElimination == first) {
        switch (firstElimination) {
          case 0:
            clear += "Doctor";
            break;
          case 1:
            clear += "Janitor";
            break;
          case 2:
            clear += "Journalist";
            break;
          case 3:
            clear += "Rich Person";
            break;
          default:
            clear += "Professor";
            break;
        }
        System.out.println("Cleared: " + clear);
      } else if (firstElimination == -1) {
        return;
      } else {
        System.out.println("The murderer sneaks up on you and kills you when you are alone...\nYou lost!");
        int firstDeath = 1;
        do {
          System.out.println("Would you like to play again? (y/n)");
          String restart = scan.next();
          switch (restart) {
            case "y":
              continue GAME;
            case "n":
              System.out.println("Thank you for playing Murder Mystery!");
              return;
            case "-1":
              return;
            default:
              System.out.println("Please enter y/n!");
              firstDeath = 1;
              break;
          }
        } while (firstDeath == 1);
      }

      //Part 2
      System.out.println(round2Banner);
      System.out.println("All suspects claim that the items found at the crime scene do not belong to any of them...");
      boolean Evidence0Doctor = r.nextBoolean();
      boolean Evidence1Doctor = r.nextBoolean();
      boolean Evidence2Doctor = r.nextBoolean();
      boolean Evidence0Janitor = r.nextBoolean();
      boolean Evidence1Janitor = r.nextBoolean();
      boolean Evidence2Janitor = r.nextBoolean();
      boolean Evidence0Journalist = r.nextBoolean();
      boolean Evidence1Journalist = r.nextBoolean();
      boolean Evidence2Journalist = r.nextBoolean();
      boolean Evidence0RichPerson = r.nextBoolean();
      boolean Evidence1RichPerson = r.nextBoolean();
      boolean Evidence2RichPerson = r.nextBoolean();
      boolean Evidence0Professor = r.nextBoolean();
      boolean Evidence1Professor = r.nextBoolean();
      boolean Evidence2Professor = r.nextBoolean();
      boolean Evidence3Doctor = true;
      boolean Evidence4Doctor = true;
      boolean Evidence3Janitor = true;
      boolean Evidence4Janitor = true;
      boolean Evidence3Journalist = true;
      boolean Evidence4Journalist = true;
      boolean Evidence3RichPerson = true;
      boolean Evidence4RichPerson = true;
      boolean Evidence3Professor = true;
      boolean Evidence4Professor = true;
      if (r.nextBoolean()) {
        switch (killer) {
          case 0:
            Evidence3Doctor = false;
            break;
          case 1:
            Evidence3Janitor = false;
            break;
          case 2:
            Evidence3Journalist = false;
            break;
          case 3:
            Evidence3RichPerson = false;
            break;
          case 4:
            Evidence3Professor = false;
            break;
        }
        switch (fourth) {
          case 0:
            Evidence4Doctor = false;
            break;
          case 1:
            Evidence4Janitor = false;
            break;
          case 2:
            Evidence4Journalist = false;
            break;
          case 3:
            Evidence4RichPerson = false;
            break;
          case 4:
            Evidence4Professor = false;
            break;
        }
      } else {
        switch (killer) {
          case 0:
            Evidence4Doctor = false;
            break;
          case 1:
            Evidence4Janitor = false;
            break;
          case 2:
            Evidence4Journalist = false;
            break;
          case 3:
            Evidence4RichPerson = false;
            break;
          case 4:
            Evidence4Professor = false;
            break;
        }
        switch (fourth) {
          case 0:
            Evidence3Doctor = false;
            break;
          case 1:
            Evidence3Janitor = false;
            break;
          case 2:
            Evidence3Journalist = false;
            break;
          case 3:
            Evidence3RichPerson = false;
            break;
          case 4:
            Evidence3Professor = false;
            break;
        }
      }
      int questionPrompt;
      int partTwo = 0;
      int questionAnswerer;
      do {
        do {
          System.out.println("Who would you like to question?");
          System.out.println(suspectList);
          questionAnswerer = scan.nextInt();
          if (questionAnswerer == firstElimination) {
            System.out.println("This person has already been eliminated! Please choose someone else!");
            questionPrompt = 1;
          } else if (questionAnswerer == -1) {
            return;
          } else {
            questionPrompt = 0;
          }
        } while (questionPrompt == 1);

        /**
         * Asks the interrogated suspect the list of questions
         */
        System.out.println("What would you like to ask?");
        System.out.println("0 - Did you know the victim?\n" + "1 - Did you suspect something sinister was going on?\n" + "2 - Were you awake during the time of the murder?");
        switch (questionAnswerer) {
          case 0:
            System.out.println("3 - Do you own a Stethoscope?\n" + "4 - Can you show us the Stethoscope right now?");
            break;
          case 1:
            System.out.println("3 - Do you own a Dirty mop?\n" + "4 - Can you show us the Dirty mop right now?");
            break;
          case 2:
            System.out.println("3 - Do you own a High-quality pen?\n" + "4 - Can you show us the High-quality pen right now?");
            break;
          case 3:
            System.out.println("3 - Do you own a Rolex watch?\n" + "4 - Can you show us the Rolex watch right now?");
            break;
          case 4:
            System.out.println("3 - Do you own a Lecture material?\n" + "4 - Can you show us the Lecture material right now?");
            break;
        }
        int questionNumber = scan.nextInt();
        if (questionNumber == -1) {
          return;
        }
        System.out.print("Answer: ");
        switch (questionAnswerer) {
          case 0:
            switch (questionNumber) {
              case 0:
                System.out.println(Evidence0Doctor);
                break;
              case 1:
                System.out.println(Evidence1Doctor);
                break;
              case 2:
                System.out.println(Evidence2Doctor);
                break;
              case 3:
                System.out.println(Evidence3Doctor);
                break;
              case 4:
                System.out.println(Evidence4Doctor);
                break;
            }
            break;
          case 1:
            switch (questionNumber) {
              case 0:
                System.out.println(Evidence0Janitor);
                break;
              case 1:
                System.out.println(Evidence1Janitor);
                break;
              case 2:
                System.out.println(Evidence2Janitor);
                break;
              case 3:
                System.out.println(Evidence3Janitor);
                break;
              case 4:
                System.out.println(Evidence4Janitor);
                break;
            }
            break;
          case 2:
            switch (questionNumber) {
              case 0:
                System.out.println(Evidence0Journalist);
                break;
              case 1:
                System.out.println(Evidence1Journalist);
                break;
              case 2:
                System.out.println(Evidence2Journalist);
                break;
              case 3:
                System.out.println(Evidence3Journalist);
                break;
              case 4:
                System.out.println(Evidence4Journalist);
                break;
            }
            break;
          case 3:
            switch (questionNumber) {
              case 0:
                System.out.println(Evidence0RichPerson);
                break;
              case 1:
                System.out.println(Evidence1RichPerson);
                break;
              case 2:
                System.out.println(Evidence2RichPerson);
                break;
              case 3:
                System.out.println(Evidence3RichPerson);
                break;
              case 4:
                System.out.println(Evidence4RichPerson);
                break;
            }
            break;
          case 4:
            switch (questionNumber) {
              case 0:
                System.out.println(Evidence0Professor);
                break;
              case 1:
                System.out.println(Evidence1Professor);
                break;
              case 2:
                System.out.println(Evidence2Professor);
                break;
              case 3:
                System.out.println(Evidence3Professor);
                break;
              case 4:
                System.out.println(Evidence4Professor);
                break;
            }
            break;
        }
        int interrogation;
        do {
          System.out.println("Would you like to keep questioning? (y/n)");
          String moreQuestions = scan.next();
          if (moreQuestions.equals("y")) {
            partTwo = 1;
            interrogation = 0;
          } else if (moreQuestions.equals("n")) {
            partTwo = 0;
            interrogation = 0;
          } else if (moreQuestions.equals("-1")) {
            return;
          } else {
            System.out.println("Please enter y/n!");
            interrogation = 1;
          }
        } while (interrogation == 1);
      } while (partTwo == 1);
      System.out.println("Cleared: " + clear);
      int secondSuspect;
      int secondElimination;
      do {
        System.out.println("Who do you no longer suspect? \n" + suspectList);
        secondElimination = scan.nextInt();
        if ((secondElimination == second) || (secondElimination == third)) {
          switch (secondElimination) {
            case 0:
              clear += " Doctor";
              break;
            case 1:
              clear += " Janitor";
              break;
            case 2:
              clear += " Journalist";
              break;
            case 3:
              clear += " Rich Person";
              break;
            case 4:
              clear += " Professor";
              break;
          }
          System.out.println("Cleared: " + clear);
          secondSuspect = 0;
        } else if (secondElimination == firstElimination) {
          System.out.println("This person has already been eliminated! Please choose someone else!");
          System.out.println("Cleared: " + clear);
          secondSuspect = 1;
        } else if (secondElimination == -1) {
          return;
        } else {
          System.out.println("The murderer sneaks up on you and kills you when you are alone...\nYou lost!");
          DEATH:
          while (true) {
            System.out.println("Would you like to play again? (y/n)");
            String restart = scan.next();
            if (restart.equals("y")) {
              continue GAME;
            } else if (restart.equals("n")) {
              System.out.println("Thank you for playing Murder Mystery!");
              return;
            } else if (restart.equals("-1")) {
              return;
            } else {
              System.out.println("Please enter y/n!");
              continue DEATH;
            }
          }
        }
      } while (secondSuspect == 1);
      int thirdSuspect;
      int thirdElimination;
      do {
        System.out.println("Who do you no longer suspect? \n" + suspectList);
        thirdElimination = scan.nextInt();
        if ((thirdElimination == firstElimination) || (thirdElimination == secondElimination)) {
          System.out.println("This person has already been eliminated! Please choose someone else!");
          System.out.println("Cleared: " + clear);
          thirdSuspect = 1;
        } else if ((thirdElimination == second) || (thirdElimination == third)) {
          switch (thirdElimination) {
            case 0:
              clear += " Doctor";
              break;
            case 1:
              clear += " Janitor";
              break;
            case 2:
              clear += " Journalist";
              break;
            case 3:
              clear += " Rich Person";
              break;
            case 4:
              clear += " Professor";
              break;
          }
          System.out.println("Cleared: " + clear);
          thirdSuspect = 0;
        } else if (secondElimination == -1) {
          return;
        } else {
          System.out.println("The murderer sneaks up on you and kills you when you are alone...\nYou lost!");
          DEATH:
          while (true) {
            System.out.println("Would you like to play again? (y/n)");
            String restart = scan.next();
            if (restart.equals("y")) {
              continue GAME;
            } else if (restart.equals("n")) {
              System.out.println("Thank you for playing Murder Mystery!");
              return;
            } else if (restart.equals("-1")) {
              return;
            } else {
              System.out.println("Please enter y/n!");
              continue DEATH;
            }
          }
        }
      } while (thirdSuspect == 1);

      //Part 3
      System.out.println(round3Banner);
      System.out.println("Showing puzzle...\n" + "The victim wrote a note before the murder, it was found ripped to pieces in a trash bin nearby.\n" + "The pieces read: ");
      String original = "";
      switch (killer) {
        case 0:
          original = "pharmacy";
          break;
        case 1:
          original = "closet";
          break;
        case 2:
          original = "newspaper";
          break;
        case 3:
          original = "dollar";
          break;
        case 4:
          original = "college";
          break;
      }
      String temp;
      String updated = "";
      while (!(original.equals(""))) {
        int letter = r.nextInt(original.length());
        updated += original.charAt(letter);
        temp = original.substring(0, letter) + original.substring(letter + 1, original.length());
        for (int j = 0;j < temp.length(); j++) {
          if (original.charAt(letter) == temp.charAt(j)) {
            updated += temp.charAt(j);
            temp = temp.substring(0, j) + temp.substring(j + 1, temp.length());
          }
        }
        original = temp;
      }
      System.out.println(updated);
      int killerSuspect;
      int killerElimination;
      do {
        System.out.println("Who do you think the killer is? \n" + suspectList);
        killerElimination = scan.nextInt();
        if (killerElimination == killer) {
          System.out.println("Congratulations! The killer is behind the bars!");
          DEATH:
          while (true) {
            System.out.println("Would you like to play again? (y/n)");
            String restart = scan.next();
            if (restart.equals("y")) {
              continue GAME;
            } else if (restart.equals("n")) {
              System.out.println("Thank you for playing Murder Mystery!");
              return;
            } else if (restart.equals("-1")) {
              return;
            } else {
              System.out.println("Please enter y/n!");
              continue DEATH;
            }
          }
        } else if (killerElimination == -1) {
          return;
        } else {
          System.out.println("The murderer sneaks up on you and kills you when you are alone...\nYou lost!");
          DEATH:
          while (true) {
            System.out.println("Would you like to play again? (y/n)");
            String restart = scan.next();
            if (restart.equals("y")) {
              continue GAME;
            } else if (restart.equals("n")) {
              System.out.println("Thank you for playing Murder Mystery!");
              return;
            } else if (restart.equals("-1")) {
              return;
            } else {
              System.out.println("Please enter y/n!");
              continue DEATH;
            }
          }
        }
      } while (killerSuspect == 1);

    }//end of game loop
  }//end of main method
}//end of class