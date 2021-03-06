package game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import operations.Checks;
import operations.InputOutput;
//This will be the file that reads the configuration of the board and asks for user input
//It is a main file
public class Game {
	private static int remainingHouses=32;
	private static int remainingHotels=12;
    public static ArrayList<Player> playerList = new ArrayList<>();
	public static int numPlayersBankrupt=0;
	public static int getRemainingHouses() {
		return remainingHouses;
	}
	public static int getRemainingHotels() {
		return remainingHotels;
	}
	public static void setRemainingHouses(int newHouseCount) {
		remainingHouses = newHouseCount;
	}
	public static void setRemainingHotels(int newHotelCount) {
		remainingHotels = newHotelCount;
	}

	public static void main(String[] args){
		BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
		//Create player list
		playerList = InputOutput.createListPlayers(userInput);
		//initialise board
		Board.initialiseBoard();
		Dice dice = Dice.getInstance();
		//to hold a dynamic playerList size
		int playerListSize = Game.playerList.size();
		//integer comparer
		int playerListComparer;
		//end game when 2 players have become bankrupt
		while(Checks.checkIfValidGame()) {
			//Loop through the players of the game to handle their turn
			for(int i = 0; i<playerListSize; i++){
			//for(Player currentPlayer:playerList) {
				Player currentPlayer = Game.playerList.get(i);
                //even if doubles are rolled to get out of jail, the player does not make another turn,
                if (currentPlayer.isInJail()) {
                    Jail.handleJailMove(currentPlayer);
                }
                else {
                	//handle a 'non-jail' move
					//marker to track double rolls
                    boolean doubleRoll = true;
                    //Loop to maintain a player's turn if they roll a double
                    while(true) {
                        //Check user status and handle input
						Checks.checkPlayerStatus(currentPlayer);
                        InputOutput.handleUserOption(currentPlayer, doubleRoll, userInput);

                        //roll the dice once they have finished their operations
                        //check if a double has been rolled
                        doubleRoll=dice.handlePlayerRoll(currentPlayer);
                        //move player
						currentPlayer.movePlayer(dice.getDieVals());
						//Display information about the square
						InputOutput.squareInformation(currentPlayer.getLocation());
						//handle the required action on the square
						Checks.checkSquare(currentPlayer.getLocation(), currentPlayer, userInput);
                        //Check if further operations after dice roll are required

						playerListComparer = Game.playerList.size();
						//check if a player has been removed through bankruptcy
						if(playerListComparer!=playerListSize){
							playerListSize--;
							i--;
							break;
						}
                        while (!InputOutput.yesNoInput(currentPlayer.getName()+", are you done with your turn? (y/n)", currentPlayer,userInput)) {
                            InputOutput.handleUserOption(currentPlayer, false, userInput);
                        }
                        //end turn if doubles not rolled, repeat if doubles rolled
                        if (!doubleRoll || currentPlayer.isInJail()) {
                            break;
                        } else {
                            System.out.println("\n"+currentPlayer.getName() + ", you have rolled doubles, you will roll again");
                        }
                    }
                }
            }
		}
        //Check the winner and finish the game
		Checks.checkWinner();
	}
}