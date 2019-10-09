package prog2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

public class CardTest extends Application {
	Card dummy;
	public static void main(String[] args)
	{
		launch(args);
	}
	public void start(Stage primaryStage) throws Exception
	{
		testCard();
		testCardIntInt();
		testCardIntIntString();
		testGetSuitName();
		testGetNumName();
	}

	@Test
	void testCard() {
		dummy = new Card();
		assertEquals(dummy.getSuit(), 0, "Default suit is not 0");
		assertEquals(dummy.getNum(), 0, "Default number is not 0");
	}

	@Test
	void testCardIntInt() {
		dummy = new Card(2, 11);
		assertEquals(dummy.getSuit(), 2, "Given suit should be 2, but is not");
		assertEquals(dummy.getNum(), 11, "Given number should be 11, but is not");
	}

	@Test
	void testCardIntIntString() {
		dummy = new Card(2, 11, "1.png");
		assertEquals(dummy.getSuit(), 2, "Given suit should be 2, but is not");
		assertEquals(dummy.getNum(), 11, "Given number should be 11, but is not");
		assertNotNull(dummy.getPic(), "Picture should not be null");
	}

	@Test
	void testGetSuitName() {
		dummy = new Card(0, 11);
		assertEquals(dummy.getSuitName(), "Clubs");
		dummy = new Card(1, 11);
		assertEquals(dummy.getSuitName(), "Diamonds");
		dummy = new Card(2, 11);
		assertEquals(dummy.getSuitName(), "Hearts");
		dummy = new Card(3, 11);
		assertEquals(dummy.getSuitName(), "Spades");
	}

	@Test
	void testGetNumName() {
		dummy = new Card(0, 2);
		assertEquals(dummy.getNumName(), "Two");
		dummy = new Card(1, 3);
		assertEquals(dummy.getNumName(), "Three");
		dummy = new Card(2, 4);
		assertEquals(dummy.getNumName(), "Four");
		dummy = new Card(3, 5);
		assertEquals(dummy.getNumName(), "Five");
		dummy = new Card(0, 6);
		assertEquals(dummy.getNumName(), "Six");
		dummy = new Card(1, 7);
		assertEquals(dummy.getNumName(), "Seven");
		dummy = new Card(2, 8);
		assertEquals(dummy.getNumName(), "Eight");
		dummy = new Card(3, 9);
		assertEquals(dummy.getNumName(), "Nine");
		dummy = new Card(0, 10);
		assertEquals(dummy.getNumName(), "Ten");
		dummy = new Card(1, 11);
		assertEquals(dummy.getNumName(), "Jack");
		dummy = new Card(2, 12);
		assertEquals(dummy.getNumName(), "Queen");
		dummy = new Card(2, 13);
		assertEquals(dummy.getNumName(), "King");
		dummy = new Card(2, 14);
		assertEquals(dummy.getNumName(), "Ace");
		
	}

}
