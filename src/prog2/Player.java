package prog2;

import java.util.ArrayList;

public class Player {
	int playerID;
	int score;
	int tricksWon;
	ArrayList<Card> cardsWon;
	Pitch pitch;
	
	public Player(int id)
	{
		cardsWon = new ArrayList<Card>();
		playerID = id;
		score = 0;
		tricksWon = 0;
	}
	public Player(int id, Pitch currentInstance)
	{
		cardsWon = new ArrayList<Card>();
		playerID = id;
		score = 0;
		tricksWon = 0;
		pitch = currentInstance;
	}
	
	public void reset()
	{
		cardsWon = new ArrayList<Card>();
		score = 0;
		tricksWon = 0;
	}
	
	public void setHand(ArrayList<Card> newHand) {} //user doesn't do anything with his hand from the player class
	
	public int getPlayerID()
	{
		return playerID;
	}
	public int getScore()
	{
		return score;
	}
	public void addScore(int gameScore)
	{
		score += gameScore;
	}
	public void takeTurn(ArrayList<Card> onTable, int trumpSuit, int originalSuit)
	{
		return; //let control go back to Pitch, which lets control go back to main
	}
	public void makeBid()
	{
		return; //let control go back to Pitch, which lets control go back to user, and user should call pitch again
	}
	public void giveCardsWon(ArrayList<Card> myCardsWon)
	{
		cardsWon.addAll(myCardsWon);
	}
	public void wonTrick()
	{
		tricksWon++;
	}
	public void resetTricks()
	{
		cardsWon.clear();
		tricksWon = 0;
	}
	public void resetScore()
	{
		cardsWon.clear();
		score = 0;
	}
	public int trickNum()
	{
		return tricksWon;
	}
	public ArrayList<Card> showCardsWon()
	{
		return cardsWon;
	}
	public void clearCardsWon()
	{
		cardsWon.clear();
	}
}
