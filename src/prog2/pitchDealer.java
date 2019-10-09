package prog2;

import java.util.ArrayList;

public class pitchDealer implements Dealer {

	private Deck dealerDeck;
	
	public pitchDealer()
	{
		dealerDeck = new Deck();
	}
	
	public void refreshDeck()
	{
		dealerDeck.resetDeck();
	}
	
	public void shuffleDeck()
	{
		dealerDeck.shuffle();
	}
	
	@Override
	public ArrayList<Card> dealHand() {
		return dealerDeck.giveCards(6); //6 cards because pitch dealers give you 6 cards
	}

}
