package prog2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

public class PitchTest extends Application {
	Pitch pitch;
	public static void main(String[] args)
	{
		launch(args);
	}
	
	//I did very few test cases with this class, because most of the elements were used for the GUI
	//and were coded specifically to handle a GUI driver class
	public void start(Stage primaryStage) throws Exception 
	{
		testSetNumPlayers();
		testPlayCard();
		testResetGame();
	}
	@Test
	void testSetNumPlayers() {
		pitch = new Pitch();
		pitch.setNumPlayers(4);
		assertEquals(pitch.getGameWinner(), -1, "Game-winner was set before game even started");
		ArrayList<Card> testHand = pitch.showHand(2);
		assertNotNull(testHand, "Players hands are null when they shouldn't be");
		assertEquals(testHand.size(), 6, "6 cards weren't dealt to every player");
	}

	@Test
	void testPlayCard() {
		pitch = new Pitch();
		pitch.setNumPlayers(4);
		ArrayList<Card> tempHand = pitch.showHand(2);
		
		Card shouldBeRemoved = tempHand.get(3); 
		assertTrue(tempHand.contains(shouldBeRemoved)); //when a card should still be inside the ArrayList
		assertTrue(pitch.playCard(shouldBeRemoved, 2)); //card to be played, ID. If successful, the function should return true
		assertFalse(tempHand.contains(shouldBeRemoved)); //by now, the card should not be in the ArrayList
	}

	@Test
	void testResetGame() {
		//by now, pitch would be altered by the previous function that was called
		pitch.resetGame();
		pitch.setNumPlayers(4);
		assertEquals(pitch.getGameWinner(), -1, "Game-winner was set before game even started");
		ArrayList<Card> testHand = pitch.showHand(3); //I use index 3, because the number of players should
		assertNotNull(testHand, "Players hands are null when they shouldn't be");
		assertEquals(testHand.size(), 6, "6 cards weren't dealt to every player");
	}

}
