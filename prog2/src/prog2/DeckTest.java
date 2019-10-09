package prog2;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

public class DeckTest extends Application {
	Deck testDeck;
	public static void main(String[] args)
	{
		launch(args);
	}
	public void start(Stage primaryStage) throws Exception 
	{
		testDeck = new Deck();
		testResetDeck();
		testGiveCard();
		testGetSize();
		testShuffle();
		testSuitNameAndNumName();
	}

	@Test
	void testResetDeck() {
		testDeck.giveCards(52); //clear the deck
		testDeck.resetDeck();
		ArrayList<Card> testDeckReset = testDeck.giveCards(52); //also tests giveCards
		int it = 0;
		for(int i = 0; i <= 3; i++)
		{
			for(int j = 2; j <= 14; j++)
			{
				assertEquals(testDeckReset.get(it).getNum(), j, "Card numbers out of order");
				assertEquals(testDeckReset.get(it).getSuit(), i, "Card suits out of order");
				it++;
			}
		}
		testDeck.resetDeck();
	}
	
	@Test
	void testGiveCard() {
		Card c = testDeck.giveCard();
		assertNotNull(c);
		assertTrue(c.getSuit() <= 3);
		assertTrue(c.getSuit() >= 0);
		assertTrue(c.getNum() <= 14);
		assertTrue(c.getNum() >= 2);
		testDeck.resetDeck();
	}
	
	@Test
	void testGetSize() {
		assertEquals(52, testDeck.getSize(), "Initial deck is not 52 cards");
		testDeck.giveCard(); //should return a card, but we're only testing size here
		assertEquals(51, testDeck.getSize(), "Size not decremented correctly after removing a card");
		testDeck.giveCards(5);
		assertEquals(46, testDeck.getSize(), "giveCards does not set size correctly");
		testDeck.resetDeck(); //reset deck for future uses
		assertEquals(52, testDeck.getSize(), "resetDeck does not set size correctly");
	}
	
	@Test
	void testShuffle() {
		Card oneCard = testDeck.giveCard();
		assertEquals(oneCard.getSuit(), 0);
		assertEquals(oneCard.getNum(), 2);
		testDeck.resetDeck();
		testDeck.shuffle();
		Card c = testDeck.giveCard();
		if(c.getSuit() == 0 && c.getNum() == 2)
			fail("Deck not shuffled correctly");
		testDeck.resetDeck();
	}
	
	void testSuitNameAndNumName()
	{
		ArrayList<Card> cList = testDeck.giveCards(52);
		for(Card c : cList)
		{
			if(c.getNum() == 2)
				assertEquals(c.getNumName(), "Two", "Two incorrectly named");
			if(c.getNum() == 3)
				assertEquals(c.getNumName(), "Three", "Three incorrectly named");
			if(c.getNum() == 4)
				assertEquals(c.getNumName(), "Four", "Four incorrectly named");
			if(c.getNum() == 5)
				assertEquals(c.getNumName(), "Five", "Five incorrectly named");
			if(c.getNum() == 6)
				assertEquals(c.getNumName(), "Six", "Six incorrectly named");
			if(c.getNum() == 7)
				assertEquals(c.getNumName(), "Seven", "Seven incorrectly named");
			if(c.getNum() == 8)
				assertEquals(c.getNumName(), "Eight", "Eight incorrectly named");
			if(c.getNum() == 9)
				assertEquals(c.getNumName(), "Nine", "Nine incorrectly named");
			if(c.getNum() == 10)
				assertEquals(c.getNumName(), "Ten", "Ten incorrectly named");
			if(c.getNum() == 11)
				assertEquals(c.getNumName(), "Jack", "Jack incorrectly named");
			if(c.getNum() == 12)
				assertEquals(c.getNumName(), "Queen", "Queen incorrectly named");
			if(c.getNum() == 13)
				assertEquals(c.getNumName(), "King", "King incorrectly named");
			if(c.getNum() == 14)
				assertEquals(c.getNumName(), "Ace", "Ace incorrectly named");
			
			if(c.getSuit() == 0)
				assertEquals(c.getSuitName(), "Clubs", "Clubs inccorrectly named");
			if(c.getSuit() == 1)
				assertEquals(c.getSuitName(), "Diamonds", "Diamonds incorrectly named");
			if(c.getSuit() == 2)
				assertEquals(c.getSuitName(), "Hearts", "Hearts incorrectly named");
			if(c.getSuit() == 3)
				assertEquals(c.getSuitName(), "Spades", "Spades incorrectly named");
		}
	}
}
