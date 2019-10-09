package prog2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

public class AIPlayerTest extends Application {
	AIPlayer bot;
	public static void main(String[] args)
	{
		launch(args);
	}
	
	public void start(Stage primaryStage) throws Exception {
		testConstructor();
		testAddScore();
		testGiveCardsWon();
		testClearCardsWon();
		testReset();
	}
	
	@Test
	void testConstructor() {
		bot = new AIPlayer(4); //should set ID
		assertNotNull(bot.showCardsWon()); //assert that the class has been initialized
		assertEquals(bot.showCardsWon().size(), 0, "Bot starts off with a hand of cards already won");
		assertEquals(bot.getScore(), 0, "Bot starts off with a score that's not 0");
		assertEquals(bot.getPlayerID(), 4, "Bot's ID is incorrect");
		assertEquals(bot.trickNum(), 0, "Bot starts off with incorrect number of tricks won");
	}

	@Test
	void testAddScore() {
		bot = new AIPlayer(4);
		bot.addScore(7);
		bot.addScore(2);
		assertEquals(bot.getScore(), 9, "Bot does not correctly add score");
	}

	@Test
	void testGiveCardsWon() {
		bot = new AIPlayer(4);
		bot.wonTrick();
		bot.wonTrick();
		assertEquals(bot.trickNum(), 2, "Bot does not correctly increment tricks won");
	}

	@Test
	void testClearCardsWon() {
		bot = new AIPlayer(2);
		ArrayList<Card> tempHand = new ArrayList<Card>();
		tempHand.add(new Card(-1, -1));
		tempHand.add(new Card(-2, -2));
		bot.giveCardsWon(tempHand);
		assertEquals(bot.showCardsWon().size(), 2, "giveCardsWon not adding the correct number of cards");
		bot.clearCardsWon();
		assertEquals(bot.showCardsWon().size(), 0, "clearCardsWon does not clear the array of cards");
		
	}
	
	@Test
	void testReset() {
		bot = new AIPlayer(2);
		ArrayList<Card> tempHand = new ArrayList<Card>();
		tempHand.add(new Card(-1, -1));
		tempHand.add(new Card(-2, -2));
		bot.giveCardsWon(tempHand);	//after these 4 lines, bot's hand will not be empty, score will be 4, tricksWon will be 2
		bot.addScore(4);
		bot.wonTrick();
		bot.wonTrick();
		
		bot.reset(); //check to make sure all of these are now cleared, except for ID
		
		assertNotNull(bot.showCardsWon());
		assertEquals(bot.showCardsWon().size(), 0, "Bot resets with a hand of cards already won");
		assertEquals(bot.getScore(), 0, "Bot resets with a score that's not 0");
		assertEquals(bot.getPlayerID(), 2, "Bot's ID is incorrect");
		assertEquals(bot.trickNum(), 0, "Bot starts off with incorrect number of tricks won");
	}

}
