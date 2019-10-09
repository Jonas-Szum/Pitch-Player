package prog2;

import java.util.ArrayList;
import java.util.Collections;


public class Deck {		//this deck will be a standard playing deck
	private ArrayList<Card> cards;
	private int size;
	
	public Deck()
	{
		cards = new ArrayList<Card>();
		int fileNameFinder = 1; //I named my card pictures 1.png through 52.png
		for(int i = 0; i < 4; i++)	//Nested for loop to fill the deck with new cards and correct images
		{
			for(int j = 2; j <= 14; j++)
			{
				String fileName = Integer.toString(fileNameFinder) + ".png";
				cards.add(new Card(i, j, fileName));
				fileNameFinder++;
			}
		}
		size = cards.size();
	}
	
	public int getSize() { return size; }
	
	public void shuffle()
	{
		Collections.shuffle(cards);	//shuffle the deck
	}
	
	public void resetDeck()
	{
		ArrayList<Card> myTempDeck = new ArrayList<Card>();
		
		int fileNameFinder = 1;
		for(int i = 0; i < 4; i++) //basically recreate the constructor, creates a sorted deck
		{
			for(int j = 2; j <= 14; j++)
			{
				String fileName = Integer.toString(fileNameFinder) + ".png";
				myTempDeck.add(new Card(i, j, fileName));
				fileNameFinder++;
			}
		}
		cards = myTempDeck;
		size = cards.size();
	}

	public Card giveCard()
	{
		Card returnMe = cards.get(0);
		cards.remove(0);
		size--;
		return returnMe;
	}
	
	public ArrayList<Card> giveCards(int n) //return N cards
	{
		if(n > size)
			throw new IllegalArgumentException("If the deck has 52 cards, why are you trying to get more?");
		
		ArrayList<Card> returnMe = new ArrayList<Card>();
		for(int i = 0; i < n; i++)
		{
			returnMe.add(cards.get(0));		//I say 0 because the remove statement moves all cards down one space
			cards.remove(0);
			size--;
		}
		
		return returnMe;
	}
}
