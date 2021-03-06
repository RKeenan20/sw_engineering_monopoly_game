package operations;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cards.CommunityChest;
import game.Board;
import game.Game;
import game.Player;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

class TransactionsTest {
	private CommunityChest temp = new CommunityChest("GET_OUT_OF_JAIL","Get out of jail free. This card may be kept until needed or sold",0);
	private InputStream instructionInputStream;
	
	@BeforeEach
	void setUp() {
		Board.initialiseBoard();
		String instruction1 = "2\r\nRob,red\r\nCiaran,blue\r\n";
		instructionInputStream = new ByteArrayInputStream(instruction1.getBytes());
		System.setIn(instructionInputStream);
		System.out.println("\n----------\nTEST PLEASE ENTER THE FOLLOWING:");
		System.out.println("2 ENTER Rob,red ENTER Ciaran,blue ENTER\n----------\n");
		Game.playerList = InputOutput.createListPlayers(null);
		
	}

	@AfterEach
	void tearDown() {
		Board.clearBoard();
		Game.playerList.clear();
	}

	@Test
	void testPlayerToPlayerTrade1() {

		String instruction = "0\r\n2\r\n0\r\nn\r\n2\r\n0\r\ny\r\n2\r\n2\r\ny\r\n1\r\nn\r\n1\r\ny\r\n3\r\n10\r\ny\r\n2\r\n0\r\nn\r\n1\r\nn\r\n3\r\n10\r\ny\r\ny\r\ny\r\n";
		instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
		System.setIn(instructionInputStream);
		
		Board.communityChests.set(0, temp);
		Board.communityChests.set(1, temp);
		Player p1 = Game.playerList.get(0);
		Player p2 = Game.playerList.get(1);
		
		p1.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
		p1.addPurchasedTitleDeed(Board.properties.get(1).getTitleDeedCard());
		p1.pickCommChestCard();
		
		p2.addPurchasedTitleDeed(Board.properties.get(2).getTitleDeedCard());
		p2.addPurchasedTitleDeed(Board.properties.get(3).getTitleDeedCard());
		p2.pickCommChestCard();
		
		//Info about the test
		System.out.println("Player 1: Add a property, attempt to add the same property. Attempt to add another property, but cancel before. "+
		"Add a jail free card, attempt to add another jail free card you dont have. Add some cash");
		System.out.println("Player 2: Add a property,. Add a jail free card. Add some cash. Complete trade\n");

		Transactions.playerToPlayerTrade(p1);
		assertTrue(p1.getTitleDeedList().contains(Board.properties.get(2).getTitleDeedCard()));

		Game.playerList.get(0).getTitleDeedList().clear();
		Game.playerList.get(1).getTitleDeedList().clear();
		
		Game.playerList.get(0).getJailCard().clear();
		Game.playerList.get(0).getJailCard().clear();
	}
	
	@Test
	void testPlayerToPlayerTrade2() {
		String instruction = "0\r\n2\r\n0\r\nn\r\n3\r\n10\r\ny\r\n1\r\ny\r\n2\r\n0\r\ny\r\ny\r\nn\r\n";
		instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
		System.setIn(instructionInputStream);


		Board.communityChests.set(0, temp);
		Board.communityChests.set(1, temp);
		
		Game.playerList.get(0).addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
		Game.playerList.get(0).addPurchasedTitleDeed(Board.properties.get(1).getTitleDeedCard());
		Game.playerList.get(0).pickCommChestCard();
		
		Game.playerList.get(1).addPurchasedTitleDeed(Board.properties.get(2).getTitleDeedCard());
		Game.playerList.get(1).addPurchasedTitleDeed(Board.properties.get(3).getTitleDeedCard());
		//Info about the test
		System.out.println("both players trade. PLayer two attempt to add jail free card that you do not have, player 2 DONT ACCEPT trade terms");
		
		Transactions.playerToPlayerTrade(Game.playerList.get(0));
		
		Game.playerList.get(0).getTitleDeedList().clear();
		Game.playerList.get(1).getTitleDeedList().clear();
		
		Game.playerList.get(0).getJailCard().clear();
		Game.playerList.get(0).getJailCard().clear();
		
	}
	//Cancelling the trade
	@Test
	void testPlayerToPlayerTrade3() {
		System.out.println("Player 1 immediately cancel trade");

		String instruction = "0\r\n0\r\n";
		instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
		System.setIn(instructionInputStream);

		Transactions.playerToPlayerTrade(Game.playerList.get(0));
		System.out.println("*********\n"+
			"Player 1 Press:\n"
			+ "0 ENTER 0 ENTER");
	}
	//A trade which saves you from bankruptcy
	@Test
	void testSaveFromBankruptcyTrade1() {
		String instruction = "y\r\n0\r\n0\r\n500\r\ny\r\n";
		instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
		System.setIn(instructionInputStream);

		System.out.println("Make a normal Trade");
		Player p1 = Game.playerList.get(0);
		Player p2 = Game.playerList.get(1);
		
		Game.playerList.get(0).addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
		Game.playerList.get(0).addPurchasedTitleDeed(Board.properties.get(1).getTitleDeedCard());
		
		Game.playerList.get(1).addPurchasedTitleDeed(Board.properties.get(2).getTitleDeedCard());
		Game.playerList.get(1).addPurchasedTitleDeed(Board.properties.get(3).getTitleDeedCard());
		Transactions.saveFromBankruptcyTrade(p1, null);
		assertEquals(1000, p2.getMoney());	
	}
	@Test
	void testSaveFromBankruptcyTrade2() {
		String instruction = "y\r\n1\r\ny\r\n200\r\ny\r\n250\r\ny\r\n300\r\nn\r\n";
		instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
		System.setIn(instructionInputStream);
		System.out.println("Players two and three bid for chosen property, until one player decides to no longer bid");
		
		Player p1 = Game.playerList.get(0);
		Player p3 = new Player("p3","Green");
		Game.playerList.add(p3);
		
		Game.playerList.get(0).addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
		Game.playerList.get(0).addPurchasedTitleDeed(Board.properties.get(1).getTitleDeedCard());
		
		Game.playerList.get(1).addPurchasedTitleDeed(Board.properties.get(2).getTitleDeedCard());
		Game.playerList.get(1).addPurchasedTitleDeed(Board.properties.get(3).getTitleDeedCard());
		Transactions.saveFromBankruptcyTrade(p1, null);
	}
}
