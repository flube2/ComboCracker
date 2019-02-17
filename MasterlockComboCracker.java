/** Class to crack a combination from any black version, and possibly other versions, of the Masterlock combo lock.
 *
 * <p>This class was coded to help allow people to crack Masterlock Combination locks.
 * Disclaimer: I did NOT discover the algorithm behind this. If you are curious and want to know more about this
 * I suggest that you navigate to the source:
 * https://null-byte.wonderhowto.com/how-to/behind-hack-discovered-8-try-master-combo-lock-exploit-0161877/
 * and ask the author your questions.
 * 
 * @author Frank Lubek (flube2@uic.edu)
 *
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MasterlockComboCracker {

  public static void main(String[] args) {
    int combo1;
    int combo3;
    int p3_1 = -1;
    int p3_2 = -1;
    int first; // First "valley" of resistance
    int second; // Second "valley" of resistance
    int lock; // The number the lock won't turn past when pressure is applied to the shackle
    ArrayList<Integer> possibleSeconds = new ArrayList<Integer>();



    // Intro
    System.out.println("\n\n\t\t\t\t\t****** MasterlockComboCracker ******");
    System.out.println("\t\t\t\t\tAuthor: Frank Lubek  flubek@gmail.com\n\n");
    System.out.println("Welcome! So it seems you have a Masterlock Combo lock you need opened.");
    // 64,000 in theory as 40^3 = 64,000. BUT as there are no repeats and numbers wont be
    // within +/- 2 of another number in the combo, 40*35*30 = 42,000
    System.out.println("Due to the physical characteristics of a Masterlock brand combo lock"); 
    System.out.println("there are approximately 42,000 possible combinations");
    System.out.println("Let's narrow that down to 8, shall we?\n\n");




    // Get input from user
    Scanner input = new Scanner(System.in);
    System.out.println("Please apply pressure to the shakle.");
    System.out.println("Not so much that it won't turn at all,\nbut enough so that you notice great resistance at only one point while turning a complete revolution.");
    System.out.println("There should be great resistance here EVERY revolution.");
    System.out.println("Please enter this number now.");
    lock = input.nextInt();

    while (lock < 0 || lock > 39) {
      System.out.println("Number must be between 0 and 39, try again...");
      lock = input.nextInt();
    }
   
    combo1 = lock + 5;
    int modFactor = combo1 % 4;
    
    System.out.println("Now set the dial to 0 and apply great pressure to the shackle and attempt to turn the dial");
    System.out.println("The dial will get stuck in between 2 numbers (a valley).");
    System.out.println("If the midpoint of the valley is NOT an integer(both ends valley are between integer values), try and find the next valley.");
    System.out.println("Note that this number should be between 0 and 11. When found, enter it");
    first = input.nextInt();

    while (first < 0 || first > 11) {
      System.out.println("Number must be between 0 and 11, try again...");
      first = input.nextInt();
    }
    
    System.out.println("Now find the midpoint of the next valley and enter it. Note that it also should never be above 11");
    second = input.nextInt();

    while (second < 0 || second > 11) {
      System.out.println("Number must be between 0 and 11, try again...");
      second = input.nextInt();
    }
    

    // get possible third combo numbers
    int[] possible = new int[10];
    int f = 0;
    int s = 1;
    while (second < 40) {
      possible[f] = first;
      possible[s] = second;
      first = first + 10;
      second = second + 10;
      f = f+2;
      s = s+2;
    }
    

    // narrow possibilities for third number down to 2 numbers
    int j = 0;
    int i = 0;
    for (i = 0; i < 10; ++i) {
      if ((possible[i] % 4) == modFactor) {
        if (p3_1 == -1) {
	  p3_1 = possible[i];
	}
	else {
	  p3_2 = possible[i];
	}
      }
    }

    System.out.println("Your first number has been determined and the number of possibilities for your third number has been narrowed to 2. So those valleys you were just discovering the numbers for, ...similar concept except you need to figure out which of the following 2 numbers has a wider valley (i.e. more \"wiggle room\").");
    System.out.println("\nWhich has more wiggle room, " + p3_1 + " or " + p3_2 + "?");
    combo3 = input.nextInt();

    int val = 0;
    // create helper 8x5 matrix to figure out 
    int[][] comboMatrix = new int[8][5];
    for (j = 0; j < 5; ++j) {
      for (i = 0; i < 8; ++i, ++val) {
        comboMatrix[i][j] = val;
      }
    }


    int roc = (combo3 % 4) + 2;
    int roc2 = roc - 4;
    if (roc2<0) roc2 = 7 + roc2;
    System.out.println("");

    int[] tempArr = new int[10];
    int[] arr = new int[8];
    int k;
    int index;
    for (k = 0, index = 0; k < 5; ++k, index = index + 2) {
     tempArr[index] = comboMatrix[roc][k];
    }
    System.out.println("");

    for(k = 0, index = 1; k < 5; ++k, index = index + 2){
      tempArr[index] = comboMatrix[roc2][k];
    }

    for (k = 0, index = 0; k < 10 && index < 8; ++k) {
      if (tempArr[k] >= (combo3 - 2) && tempArr[k] <= (combo3 + 2)) {
      } else {
        if (index == 7) {
          possibleSeconds.add(tempArr[k]+2);
	} else {
        possibleSeconds.add(tempArr[k]);
        ++index;
	}
      }
    }

    

    Collections.sort(possibleSeconds);
    System.out.println("First number of combination: " + combo1);
    System.out.println("Second number of combination: " + possibleSeconds);
    System.out.println("Third number of combination: " + combo3);

    return;

  }

}
