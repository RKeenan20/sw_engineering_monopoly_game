/**
 * Class to implement to simulation of a dice throw for two die, and to implement the required monopoly rules around multiple duplicate throws
 * i.e. 3 Duplicate rolls results in the player being sent directly to jail
*/
package ie.ucd.game;
import java.util.Random;

public class Dice {

	private static int dice1;
	private static int dice2;
	private static int duplicateRollCounter=0;
	
	public static boolean rollDice(){
		Random rollGenerator = new Random();
			
		// .nextInt generates between 0 and specified range (exclusively), so its necessary to add 1 to ensure the dice cannot return 0
		dice1 = rollGenerator.nextInt(6)+1; 
		dice2 = rollGenerator.nextInt(6)+1;
		System.out.println("\n-----------------------\n\tDICE ROLL\n-----------------------");
		System.out.println("\tFirst Dice: "+dice1+"\n\tSecond Dice: "+dice2+"\n");
		return dice1 == dice2;
	}
	
	public static int getDieVals() {
		//return object containing both die values
		return (dice1 + dice2);
	}

	public static void handlePlayerRoll(Player player){

	}

	public static int getDuplicateRollCounter() {
		return duplicateRollCounter;
	}
	public static void resetDuplicateRollCounter() {
		duplicateRollCounter = 0;
	}
	public static void incrementDuplicateRollCounter(){
		duplicateRollCounter++;	
	}
	
}
