import java.util.Random;
import java.util.Scanner;

public class DnDLite {
    public static void main (String[] args) {
        String beginEncounter = "A green-skinned humanoid with very large fangs " +
                "puts down the bow it shot the arrow from, picks up a club and a lit torch to attack you!";

        String dead = "You are dead!\nDo you wish to continue?(y/n)";

        String failed = "You failed to kill the boss...";

        String success = "The boss lets out a final shriek and falls to the ground as it succumbed to its many wounds!" +
                "Congratulations! You successfully killed the boss!";

        String attack = "Do you attack? (y/n)";

        String attackSuccess = "You nimbly land a vicious blow against the boss!";

        String attackFail = "You swing hard at the boss but barely missed!";

        String bossSuccess = "The boss charges at you and knocks you back with his giant club!";

        String bossFailed = "You quickly dodge to the side as the boss charges at you!";

        String transition = "The boss is furious at the wounds you have inflicted on it and lets out a very loud shriek while flailing around!" +
                "In its rage, the boss throws the torch towards your direction and you barely managed to get out of the way!\n" +
                "However, dead trees and branches around you caught fire quickly and you are surrounded by flames before you know it!\n" +
                "All damage is now doubled and being hurt by the fire costs you 5 HP!";

        String attrition = "The flames nip at your heels as the battle rages on.\n" +
                "In the heat of battle, you landed in the wrong spot and was singed by the fire!";

        Scanner scan = new Scanner(System.in);
        Random r = new Random();
        int str = 20;
        int con = 20;
        int dex = 10;
        int strBoss = 10;
        int conBoss = 10;
        int intBoss = 3;
        int dexBoss = 10;

        int dexCheck = dex - dexBoss;
        int dexCheckBoss = dexBoss - dex;

        int lives = 3;
        int hp = con * 10;
        int hpBoss = conBoss * 10;
        int hpSaved = hp;
        int hpBossSaved = hpBoss;

        System.out.println(beginEncounter);
        while (lives > 0 && hpBoss > 0) {
            while ((hpBoss >= 50) && (hp > 0)) {
                hpBoss = hpBossSaved;
                hp = hpSaved;
                System.out.println("Your HP: " + hp);
                System.out.println("Boss HP: " + hpBoss);
                System.out.println(attack);
                char choiceAttack = scan.next().charAt(0);
                if (choiceAttack == 'y') {
                    int roll = r.nextInt(100) + 1;
                    if (dexCheck >= 10) {
                        System.out.println(attackFail);
                    }
                    else if (dexCheck >= 5) {
                        if (roll >= 80) {
                            System.out.println(attackSuccess);
                            hpBoss -= str;
                        }
                        else {
                            System.out.println(attackFail);
                        }
                    }
                    else if (dexCheck >= 0) {
                        if (roll >= 50) {
                            System.out.println(attackSuccess);
                            hpBoss -= str;
                        }
                        else {
                            System.out.println(attackFail);
                        }
                    }
                    else {
                        if (roll >= 25) {
                            System.out.println(attackSuccess);
                            hpBoss -= str;
                        }
                        else {
                            System.out.println(attackFail);
                        }
                    }
                }
                int rollBoss = r.nextInt(100) + 1;
                if (dexCheckBoss >= 10) {
                    System.out.println(bossFailed);
                }
                else if (dexCheckBoss >= 5) {
                    if (rollBoss >= 80) {
                        System.out.println(bossSuccess);
                        hp -= strBoss;
                    }
                    else {
                        System.out.println(bossFailed);
                    }
                }
                else if (dexCheckBoss >= 0) {
                    if (rollBoss >= 50) {
                        System.out.println(bossSuccess);
                        hp -= strBoss;
                    }
                    else {
                        System.out.println(bossFailed);
                    }
                }
                else {
                    if (rollBoss >= 25) {
                        System.out.println(bossSuccess);
                        hp -= strBoss;
                    }
                    else {
                        System.out.println(bossFailed);
                    }
                }
                if (hp <= 0) {
                    System.out.println(dead);
                    char resume = scan.next().charAt(0);
                    if (resume == 'y') {
                        lives--;
                        hp = con * 10;
                        hpBoss = conBoss * 10;
                        if (lives <= 0) {
                            System.out.println(failed);
                            System.exit(0);
                        }
                    }
                }
                else {
                    hpBossSaved = hpBoss;
                    hpSaved = hp;
                }
            }
            while ((hp > 0) && (hpBoss < 50) && (hpBoss > 0)) {
                System.out.println(transition);
                System.out.println("Your HP: " + hp);
                System.out.println("Boss HP: " + hpBoss);
                System.out.println(attack);
                char choiceAttack = scan.next().charAt(0);
                if (choiceAttack == 'y') {
                    int roll = r.nextInt(100) + 1;
                    if (dexCheck >= 10) {
                        System.out.println(attackFail);
                        System.out.println(attrition);
                        hp -= 5;
                    }
                    else if (dexCheck >= 5) {
                        if (roll >= 80) {
                            System.out.println(attackSuccess);
                            hpBoss -= str * 2;
                        }
                        else {
                            System.out.println(attackFail);
                            System.out.println(attrition);
                            hp -= 5;
                        }
                    }
                    else if (dexCheck >= 0) {
                        if (roll >= 50) {
                            System.out.println(attackSuccess);
                            hpBoss -= str * 2;
                        }
                        else {
                            System.out.println(attackFail);
                            System.out.println(attrition);
                            hp -= 5;
                        }
                    }
                    else {
                        if (roll >= 25) {
                            System.out.println(attackSuccess);
                            hpBoss -= str * 2;
                        }
                        else {
                            System.out.println(attackFail);
                            System.out.println(attrition);
                            hp -= 5;
                        }
                    }
                }
                int rollBoss = r.nextInt(100) + 1;
                if (dexCheckBoss >= 10) {
                    System.out.println(bossFailed);
                }
                else if (dexCheckBoss >= 5) {
                    if (rollBoss >= 80) {
                        System.out.println(bossSuccess);
                        hp -= strBoss * 2;
                    }
                    else {
                        System.out.println(bossFailed);
                    }
                }
                else if (dexCheckBoss >= 0) {
                    if (rollBoss >= 50) {
                        System.out.println(bossSuccess);
                        hp -= strBoss * 2;
                    }
                    else {
                        System.out.println(bossFailed);
                    }
                }
                else {
                    if (rollBoss >= 25) {
                        System.out.println(bossSuccess);
                        hp -= strBoss * 2;
                    }
                    else {
                        System.out.println(bossFailed);
                    }
                }
                if (hp <= 0) {
                    System.out.println(dead);
                    char resume = scan.next().charAt(0);
                    if (resume == 'y') {
                        lives--;
                        hpBoss = hpBossSaved;
                        hp = hpBossSaved;
                        if (lives <= 0) {
                            System.out.println(failed);
                            System.exit(0);
                        }
                    }
                }
                else if (hp > 0) {
                    hpBossSaved = hpBoss;
                    hpSaved = hp;
                }
            }
            if (hpBoss <= 0) {
                System.out.println(success);
            }
        }
    }
}
