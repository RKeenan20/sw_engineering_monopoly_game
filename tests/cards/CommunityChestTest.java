package cards;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cards.CommunityChest;
import game.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class CommunityChestTest {
    private CommunityChest commChestTest;
    private Player playerTest = new Player("John", "blue");

    @BeforeEach
    void setUp() {
        commChestTest = new CommunityChest("PAY","Pay doctor's fees of £20", 20);
    }

    @Test
    void testDealWithCardPAY() {
        int initialMoney = playerTest.getMoney();
        //PAY
        commChestTest.dealWithCard(playerTest, null);
        assertEquals(initialMoney-commChestTest.getCardValue(), playerTest.getMoney(),"Check money is reduced from Player");
    }  
    @Test
    void testDealWithCardINCOME() {
        //INCOME
    	int initialMoney = playerTest.getMoney();
        commChestTest.setCardType("INCOME");
        commChestTest.dealWithCard(playerTest, null);
        assertEquals(initialMoney+commChestTest.getCardValue(),playerTest.getMoney(),"Checking income works");
    }  
    @Test
    void testDealWithCardMOVE() {
       //MOVE
    	int currLocation = playerTest.getLocation();
        commChestTest.setCardType("MOVE");
        commChestTest.setCardValue(5);
        commChestTest.dealWithCard(playerTest, null);
        assertEquals(currLocation+5,playerTest.getLocation(),"Checking a player can move squares");
    }
    @Test
    void testDealWithCardGETOUTOFJAIL() {
        //GET OUT OF JAIL FREE
        commChestTest.setCardType("GET_OUT_OF_JAIL");
        commChestTest.dealWithCard(playerTest, null);
        assertNotEquals(0, playerTest.getJailCard().size(), "Checking the jail card was added to their jail card array list");
    }
    @Test
    void testDealWithCardCHOICE() {
        //CHOICE CARD
        //Fine is input of 0 and Chance is input of 1
        //FIXME @@ciarannolan....unsure about how to process the choice on this
        String instruction = "0\r\n";
        InputStream instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
        System.setIn(instructionInputStream);
    	int initialMoney = playerTest.getMoney();
    	commChestTest.setCardDesc("Pay fine of £25 or pick Chance card");
        commChestTest.setCardType("CHOICE");
        System.out.println("\n***********************************");
        System.out.println("TEST: PLEASE PRESS FINE");
        System.out.println("***********************************\n");

        commChestTest.dealWithCard(playerTest, null);

        //Do it in the case of minusing the value
        assertEquals(initialMoney-commChestTest.getCardValue(),playerTest.getMoney(),"Checking on choice");
    }
    @Test
    void testDealWithCardJAIL() {
        //JAIL
        commChestTest.setCardType("JAIL");
        commChestTest.dealWithCard(playerTest, null);
        assertTrue(playerTest.isInJail());
    }

    @AfterEach
    void tearDown() {
        commChestTest = null;
    }
}