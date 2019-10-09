package prog2;

import java.util.ArrayList;

public class AIPlayer extends Player {
	ArrayList<Card> myHand;
	public AIPlayer(int id)
	{
		super(id);
	}
	public AIPlayer(int id, Pitch CurrentInstance) {
		super(id, CurrentInstance);
		myHand = new ArrayList<Card>();
	}
	
	public void reset()
	{
		myHand = new ArrayList<Card>();
		cardsWon = new ArrayList<Card>();
		score = 0;
		tricksWon = 0;
	}
	
	public void setHand(ArrayList<Card> newHand)
	{
		myHand = newHand;
	}
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
		int maxNum = myHand.get(0).getNum();
		Card tempCard = myHand.get(0);
		Card lowCard = tempCard;
		Card highCard = tempCard;
		boolean decideTrump = false;
		boolean foundTrump = false;
		//decide what suit would be best for a trump card
		if(trumpSuit == -1)
		{
			int clubCount = 0; int diamondCount = 0; int heartCount = 0; int spadeCount = 0;
			for(Card oneCard : myHand)
			{
				if(oneCard.getSuit() == 0) clubCount++;
				else if(oneCard.getSuit() == 1) diamondCount++;
				else if(oneCard.getSuit() == 2) heartCount++;
				else if(oneCard.getSuit() == 3) spadeCount++;
			}

			if(clubCount > diamondCount && clubCount > heartCount && clubCount > spadeCount)
			{
				foundTrump = true;
				decideTrump = true;
				trumpSuit = 0;
			}
			else if(diamondCount > clubCount && diamondCount > heartCount && diamondCount > spadeCount)
			{
				foundTrump = true;
				decideTrump = true;
				trumpSuit = 1;
			}
			else if(heartCount > clubCount && heartCount > diamondCount && heartCount > spadeCount)
			{
				foundTrump = true;
				decideTrump = true;
				trumpSuit = 2;
			}
			else
			{
				foundTrump = true;
				decideTrump = true;
				trumpSuit = 3;
			}
		}
		//find the lowest playable card
		for(Card oneCard : myHand)
		{
			if(originalSuit != -1)
			{
				if((lowCard.getNum() > oneCard.getNum() && oneCard.getSuit() != trumpSuit && oneCard.getSuit() != originalSuit && lowCard.getSuit() != originalSuit)
				|| (lowCard.getNum() > oneCard.getNum() && lowCard.getSuit() == trumpSuit && oneCard.getSuit() == trumpSuit)
				|| (lowCard.getSuit() != originalSuit && oneCard.getSuit() == originalSuit))
				{
					lowCard = oneCard;
				}
			
				if((highCard.getNum() < oneCard.getNum() && oneCard.getSuit() != trumpSuit && oneCard.getSuit() != originalSuit && highCard.getSuit() != originalSuit)
				|| (highCard.getNum() < oneCard.getNum() && highCard.getSuit() == trumpSuit && oneCard.getSuit() == trumpSuit)
				|| (highCard.getSuit() != originalSuit && oneCard.getSuit() == originalSuit))
				{
					highCard = oneCard;
				}
			}
		}
		tempCard = highCard;
		if(decideTrump == false)
		{
			
			for(Card oneCard : myHand)
			{
			//determine the lowest value playing card, and play it if the hand is determined to be un-winnable
			//first case is when the card is definitely lower
			//First line: if both cards are not of the original suit, and the one being checked is lower, set it to the new temp
			//Second line: if they're both trump suits, lowCard becomes the lowest of the 2
			//Third line: if the low card is not the original suit, and the new card is, the lowCard has to be switched according to rules
			
				if(oneCard.getSuit() == trumpSuit)
				{
					foundTrump = true;
					for(Card played : onTable) //if someone played a trump card, and yours is higher, play it
					{
					//if a card on the table is a trump, and you have a higher one, 
					//and the card you would have played is better, play this one
						if(played.getSuit() == trumpSuit && played.getNum() < oneCard.getNum()
							&&(tempCard.getSuit() == trumpSuit && tempCard.getNum() > oneCard.getNum())
							&& lowCard.getSuit() == trumpSuit)
						{
							tempCard = oneCard;
							lowCard = tempCard; //change the lowCard to be medium, so as not to give up the smallest card if possible
						}
					
						else if(played.getSuit() == trumpSuit && played.getNum() < oneCard.getNum() && originalSuit != -1)
							tempCard = oneCard;
					
					//if the lowest card can beat all of the cards on the table, play it
						else if((lowCard.getSuit() == played.getSuit() && lowCard.getNum() > played.getNum())
						|| (lowCard.getSuit() == trumpSuit && played.getSuit() != trumpSuit)
						|| (lowCard.getSuit() == trumpSuit && played.getSuit() == trumpSuit && lowCard.getNum() > played.getNum()))
							tempCard = lowCard;
						//else, change nothing
					}
				}
				//if the two cards are the same suit, but not trumps, and oneCard is a higher number, set the one to be played as the oneCard
				if(oneCard.getSuit() == originalSuit && oneCard.getNum() > tempCard.getNum() && tempCard.getSuit() != trumpSuit && originalSuit != -1)
				{
					tempCard = oneCard;
				}
				//if we dont have a trump to play, and we decide what the original hand is, play the highest card possible
				else if(originalSuit == -1 && oneCard.getSuit() != trumpSuit && foundTrump == false)
				{
					if(oneCard.getNum() > maxNum)
						{
						tempCard = oneCard;
						maxNum = oneCard.getNum();
						}
				}
			}
		}
		else //if you decided the trump, play the highest card in your hand
		{
			tempCard = highCard;
		}
		pitch.playCard(tempCard, playerID);
		myHand.remove(tempCard);
	}

	public void makeBid()
	{
		int max = 0;
		ArrayList<Integer> suits = new ArrayList<Integer>();
		for(int i = 0; i < 4; i++) suits.add(0);
		
		//int i = 0;
		for(Card thisCard : myHand)
			suits.set(thisCard.getSuit(), suits.get(thisCard.getSuit()) + 1);
		int index = 0;
		for(int i = 0; i < suits.size(); i++)
			if(suits.get(i) > max)
				{
				max = suits.get(i);
				index = i;
				}
		
		//crappy hand? pass
		int highNum = 0;
		int highNonTrump = 0;
		boolean haveJack = false;
		int lowNum = 0;
		for(Card c : myHand)
		{
			if(c.getSuit() == index)
			{
				if(c.getNum() - 4 <= 0) //if the cardNum is 2, 3, or 4, place weight on low cards
					lowNum++;
				else if(c.getNum() - 1 >= 11) //if the cardNum is Q, K, A, place weight on high cards
					highNum++;
				else if(c.getNum() == 11) //if you have the jack, add weight to jack
					haveJack = true;
			}
			else
			{
				if(c.getNum() - 1 >= 11) highNonTrump++;
			}
		}
		 
		if(haveJack == true && highNum > 1) //if you have a jack AND at least 2 high cards, you have good chances to take High, low, and jack. Also rounds
			pitch.bid(4, playerID);
		else if(haveJack == true && highNum >= 1 && max - 2 >= 2) //if you have Jack, high and 2 other cards of the same suit, bet 4
			pitch.bid(4, playerID);
		else if(haveJack == true && max - 1 >= 3) //if you have jack and 3 other cards, bid 3 (Jack, numRounds, and possibly low/high, but probably not both)
			pitch.bid(3, playerID);
		else if(haveJack == false && highNum >= 2 && max - 2 >= 2) //if you have no jack, but have 2 highs and 2 other trumps, bet 3
			pitch.bid(3, playerID);
		else if(haveJack == false && highNum >= 2 && max - 2 >= 1) //if you have no jack, but have 2 highs and 2 other trumps, bet 2
			pitch.bid(3, playerID);
		else if(haveJack == false && max >= 3) //if you don't have a jack, but you have 3+ trumps, bet 2
			pitch.bid(2, playerID);
		else if(highNum >= 1 && lowNum >= 2) //if you have at least 1 high, and 2 lows, bid 2
			pitch.bid(2, playerID);
		else if(lowNum == 0 && highNum == 0 && haveJack == false && max > 3) //bid 2 if you don't have lows or highs, or jack, but have a lot of the same suit. you can pick up #rounds and low hand
			pitch.bid(2, playerID);
		else if(max >= 2 && highNonTrump > 2) //if you have 2+ trumps and 3+ high cards, bet 2
			pitch.bid(2, playerID);
		else
			pitch.bid(0, playerID); //else, bid 0
		
	}
	public void giveCardsWon(ArrayList<Card> myCardsWon)
	{
		cardsWon.addAll(myCardsWon);
	}
	public void wonTrick()
	{
		tricksWon++;
	}
	public int trickNum()
	{
		return tricksWon;
	}
	public void resetTricks()
	{
		cardsWon.clear();
		tricksWon = 0;
	}
	public void resetScore()
	{
		score = 0;
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
