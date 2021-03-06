package cards;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    private Card commChestTest;
    private Card chanceTest;
    @BeforeEach
    void setUp() {
        //In this I will set up a Community Chest, Chance and Title Deed Card
        commChestTest = new CommunityChest("PAY","Pay £100 in taxes", 100);
        chanceTest = new Chance("INCOME", "You won £100", 100);
    }

    @Test
    void testGetCardType() {
    	//Getting a card type
        String cardTypeTest = "PAY";
        assertEquals(cardTypeTest, commChestTest.getCardType(),"Checking Card Type");
        cardTypeTest = "INCOME";
        assertEquals(cardTypeTest, chanceTest.getCardType(),"Checking Card Type");
    }

    @Test
    void testGetCardDesc() {
    	//Getting the card description
        String cardDescTest = "Pay";
        assertNotEquals(cardDescTest,commChestTest.getCardDesc(),"Checking Card Description");
        cardDescTest = "Pay £100 in taxes";
        assertEquals(cardDescTest,commChestTest.getCardDesc(),"Checking Card Description Same");
    }

    @Test
    void testGetCardValue() {
    	//Getting the card value
        int cardValueTest = 50;
        assertNotEquals(cardValueTest,commChestTest.getCardValue(),"Checking card value different");
        cardValueTest = 100;
        assertEquals(cardValueTest,commChestTest.getCardValue(),"Checking values are same");
    }

    @Test
    void testSetCardType() {
    	//Setting the card type
        chanceTest.setCardType("PAY");
        assertEquals("PAY",chanceTest.getCardType(),"Checking Card type can be changed");
    }

    @Test
    void testSetCardDesc() {
    	//Setting the card description
        chanceTest.setCardDesc("You won £20");
        assertEquals("You won £20", chanceTest.getCardDesc(),"Checking card description can be changed");
    }

    @Test
    void testSetCardValue() {
    	//Setting card value
        chanceTest.setCardValue(20);
        assertEquals(20,chanceTest.getCardValue(),"Checking card value can be set to £20");
    }
    
    @AfterEach
    void tearDown() {
        commChestTest = null;
        chanceTest = null;
    }
}