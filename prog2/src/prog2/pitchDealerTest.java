package prog2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

public class pitchDealerTest extends Application {
	pitchDealer dealer;
	public static void main(String[] args)
	{
		launch(args);
	}
	
	//NOTE: I only did one test case, because all the other functions were one line, and they called Deck functions that were already tested.
	public void start(Stage primaryStage) throws Exception 
	{
		testDealHand();
	}
	
	@Test
	void testDealHand() {
		dealer = new pitchDealer(); //the constructor is one line: calling a new deck
		ArrayList<Card> tempHand = dealer.dealHand();
		assertNotNull(tempHand);
		for(int i = 0; i < 6; i++)
		{
			assertTrue(tempHand.get(i).getSuit() >= 0 && tempHand.get(i).getSuit() <= 3);
			assertTrue(tempHand.get(i).getNum() >= 2 && tempHand.get(i).getNum() <= 14);
		}
		
	}

}
