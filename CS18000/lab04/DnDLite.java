/**
 *CS180 - Lab01
 *
 *A lite version of Dungeons & Dragons game that allows character creation and game play.
 *
 *@author Youngsik Yoon, yoon161@purdue.edu, 4
 *
 *@version 1.0 Sep 7, 2017
 *
 */
import java.util.Scanner;
import java.util.Random;

public class DnDLite {
  final static String welcome = "Welcome to the world of Dungeons and Dragons - Lite!\n"
  + "Where your choices and the die determines everything!\n"
  + "First off let us create your character.\nWhat is your name?";

  final static String stat = "For each stat, you have the option to roll the die (1-20) or choose 10.";

  final static String choices1 = "STR:\n1 - Roll the die\n2 - Use 10";
  final static String choices2 = "CON:\n1 - Roll the die\n2 - Use 10";
  final static String choices3 = "INT:\n1 - Roll the die\n2 - Use 10";
  final static String choices4 = "DEX:\n1 - Roll the die\n2 - Use 10";

  final static String despair = "You are doomed to suffer through your pathetic existence in\n" +
  "the world of Dungeons and Dragons while the gods laugh at your pitiful attempts to survive..." +
  "\n(You are born with significantly lower attributes)";

  final static String unlucky = "The gods enjoy the thought of witnessing your struggle as you were given some strength..." +
  " But not enough to thrive on!\n" +
  "Your journey through the world of Dungeons and Dragons will be a rough one..." +
  "\n(You are      born with slightly lower attributes)";

  final static String mediocre = "The gods took no notice during your creation.\nYour journey through the world of" +
  "Dungeons and Dragons will neither be easy nor difficult...\nPerhaps you will prove them wrong" +
  " and show them your worth?" + "\n(You are born with average attributes)";

  final static String better = "One of the gods decided to bless you with his/her presence during your creation!\n" +
  " You will notice things tend to go your way more often than not." +
  "\n(You are born with slightly higher attributes)";

  final static String awesome = "The gods blessed you during your creation!\nThings come naturally to you and" +
  "foes will fall before your superior might!\nMay the gods be merciful to your foes!" +
  "\n(You are born with significantly higher attributes)";

  final static String pity = "One of the gods takes pity on your and grants you a boon...\n"
  + "Your lowest stat is changed to 20!";

  final static String invalid = "You could not even type in a correct stat!\nThe benevolent god curses you as you squander his gift!";

  final static String hell = "Even the most merciful gods detest you. You lead a cursed life indeed...";

  final static String twist = "However, one of the gods is jealous of you! He curses you to impede your journey!";

  final static String hacks = "The gods smite you as you try to change the rules of creation!\n"
  + "You went outside the possible range... You are now dead.";

  final static String exit = "Thanks for playing Dungeons and Dragons - Lite!\n" + "To be continued...";

  final static String start = "Hit enter to begin your journey...";

  final static String uncursed = "You wake up on a bed in a small cabin wearing a set of leather armor and a sharp sword in your hand.";

  final static String cursed = "You wake up on the floor of a small cabin with nothing but rags on your body, " +
  "you feel stiff from sleeping on the floor.";

  final static String startOptions = "You see a closed door. What do you do?\n1 - Look around the cabin\n2 - Open the door";

  final static String inspect = "You notice a sturdy metal helmet hidden in a corner of the cabin.\n"
  + "1 - Put on the helmet and open the door\n2 - Ignore the helmet and open the door";

  final static String dodge = "You notice an arrow flying out of nowhere and you quickly duck and avoided certain death!\n"
  + "So begins your journey to the great world of Dungeons and Dragons!";

  final static String dead = "An arrow flies out of nowhere and pierces your skull.\nYou are now dead...";

  final static String helmet = "An arrow flies out of nowhere and clips the helmet you just put on.\n"
  + "So begins your journey to the great world of Dungeons and Dragons!";

  final static String rate = "\nPlease rate the game with a score from 1 stars to 5 stars:";
    
  final static String one = "So you didn't like it, but you don't have to be mean.";
    
  final static String two = "I'm sorry you didn't like the game, but it didn't really like you either.";
    
  final static String three = "That's a respectable score, though it could be better.";
    
  final static String four = "Yay! You liked the game.";
    
  final static String five = "Wow, you really liked the game. But is anything really perfect?";
    
  final static String invalidRating = "That score is not acceptable. Your score will be discarded";

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    Random r = new Random();
    int str, con, intel, dex;
    boolean c = false; //curse

    //task 1.3
    System.out.println(welcome);
    String name = scan.nextLine();

    //task 1.4-1.7
    System.out.println(stat);
    System.out.println(choices1);
    int choice1 = scan.nextInt();
    if(choice1 == 1) {
      str = r.nextInt(20) + 1;
    }
    else {
      str = 10;
    }
    System.out.println(choices2);
    int choice2 = scan.nextInt();
    if(choice2 == 1) {
      con = r.nextInt(20) + 1;
    }
    else {
      con = 10;
    }
    System.out.println(choices3);
    int choice3 = scan.nextInt();
    if(choice3 == 1) {
      intel = r.nextInt(20) + 1;
    }
    else {
      intel = 10;
    }
    System.out.println(choices4);
    int choice4 = scan.nextInt();
    if(choice4 == 1) {
      dex = r.nextInt(20) + 1;
    }
    else {
      dex = 10;
    }	
    System.out.println("Name: " + name + "\nSTR: " + str + "\nCON: " + con + "\nINT: " + intel +  "\nDEX: " + dex);
    int total = str + con + intel + dex;
    System.out.println("Stat total: " + total);

    //1.8
    if(total < 20) {
      System.out.println(despair);
      int salvage = r.nextInt(20) + 1;
      if(salvage > 2) {
        System.out.println(pity);
        if(str <= con) {
          if(str <= intel) {
	    if(str <= dex) {
              str = 20;
	    }
	    else {
 	      dex = 20;
            }
	  }//if str <intel
	  else {
            intel = 20;
	  }//if str > intel
	}//if str < con
	else {
          if(con <= intel) {
            if(con <= dex) {
              con  = 20;
            }
            else {
              dex = 20;
            }
          }
          else {
            intel = 20;
          }
	}//str > con
      }//salvage > 2
      else{
        System.out.println(hell);
        c = true;
      } //salavage <= 2
    }//task 1.9
    else if(total < 40) {
      System.out.println(unlucky);
    }
    else if(total < 60) {
      System.out.println(mediocre);
    }
    else if(total < 70) {
      System.out.println(better);
    }
    else if(total < 80) {
      System.out.println(awesome);
      int spook = r.nextInt(20) + 1;
      if(spook < 5) {
        System.out.println(twist);
        c = true;
      }
    }
    else {
      System.out.println(hacks);
      System.out.println(exit);
      System.exit(0);
    }

    //task 2.1
    System.out.println(start);
    scan.nextLine();
    String wait = scan.nextLine();

    //2.2-2.3
    if(c) {
      System.out.println(cursed);
    }
    else {
      System.out.println(uncursed);
    }
    System.out.println(startOptions);
    int check = scan.nextInt();

    //2.4-2.6
    if(check == 1) {
      System.out.println(inspect);
      int looking = scan.nextInt();
      if(looking ==1) {
        System.out.println(helmet);
      }
      else {
        if(dex < 7) {
	  System.out.println(dead);
	}
        else if(dex > 15) {
          System.out.println(dodge);
	}
	else {
	  int more = r.nextInt(20) + 1;
          if(more < 11) {
	    System.out.println(dead);  
	  }
	  else {
	    System.out.println(dodge);
	  }
        }
      }
    }
    else {
      if(c) {
	System.out.println(dead);
      }
      else {
        if(dex < 7) {
          System.out.println(dead);
        }
        else if(dex > 15) {
          System.out.println(dodge);
        }
        else {
          int moreAgain = r.nextInt(20) + 1;
          if(moreAgain < 11) {
            System.out.println(dead);
          }
          else {
            System.out.println(dodge);
          }
        }
      }
    }
    System.out.println(exit);

    //task 3.1-3.3
    System.out.println(rate);
    int score = scan.nextInt();
    switch(score) {
      case 1: System.out.println(one);
        break;
      case 2: System.out.println(two);
        break;
      case 3: System.out.println(three);
        break;
      case 4: System.out.println(four);
        break;
      case 5: System.out.println(five);
        break;
      default: System.out.println(invalidRating);
        break;
    }
  }
}
