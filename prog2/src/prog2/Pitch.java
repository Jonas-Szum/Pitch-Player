package prog2;

import java.util.ArrayList;
import java.util.Collections;

public class Pitch implements DealerType {
	boolean bidFirst;
	int trumpSuit;
	ArrayList<Player> allPlayers;
	int currentBid;
	int currentTurn;
	int numPlayers;
	int highestBidder;
	int lastWinner;
	int gameWinner;
	int originalSuit;
	int jackMan;
	int trickMan;
	int lowMan;
	int highMan;
	int smudgeMan;
	ArrayList<Boolean> tookTurn;
	ArrayList<Boolean> madeBid;
	ArrayList<ArrayList<Card>> playerHands;
	ArrayList<Card> onTable;
	pitchDealer myDealer;
	
	public Pitch()
	{		
		bidFirst = true;
		trumpSuit = -1;
		originalSuit = -1;
		lastWinner = -1;
		currentTurn = -1;
		currentBid = -1;
		numPlayers = -1;
		gameWinner = -1;
		highestBidder = -1;
		jackMan = -1;
		trickMan = -1;
		lowMan = -1;
		highMan = -1;
		smudgeMan = -1;
		myDealer = new pitchDealer();
	}
	
	public int smudgeWinner()
	{
		int temp = smudgeMan;
		smudgeMan = -1;
		return temp;
	}
	public int jackWinner()
	{
		int temp = jackMan;
		jackMan = -1; //I only call this function when I need to clear the values again
		return temp;
	}
	public int trickWinner()
	{
		int temp = trickMan;
		trickMan = -1; //I only call this function when I need to clear the values again
		return temp;
	}
	public int lowWinner()
	{
		int temp = lowMan;
		lowMan = -1; //I only call this function when I need to clear the values again
		return temp;
	}
	public int highWinner()
	{
		int temp = highMan;
		highMan = -1; //I only call this function when I need to clear the values again
		return temp;
	}
	
	public void setNumPlayers(int num)
	{
		bidFirst = true;
		highestBidder = -1;
		numPlayers = num;
		allPlayers = new ArrayList<Player>();
		allPlayers.add(new Player(0, this)); //human
		onTable = new ArrayList<Card>();
		madeBid = new ArrayList<Boolean>();
		tookTurn = new ArrayList<Boolean>();
		int i = 0;
		while(i < numPlayers)
		{
			onTable.add(new Card(-1, -1));
			madeBid.add(false);
			tookTurn.add(false);
			i++;
		}
		for(int x = 1; x < numPlayers; x++)
		{
			allPlayers.add(new AIPlayer(x, this)); //robots
		}
		myDealer.shuffleDeck();
		dealHands();
	}
	
	public void dealHands()
	{
		playerHands = new ArrayList<ArrayList<Card>>();
		//playerHands.add(new ArrayList<Card>(myDealer.dealHand()));
		for(int i = 0; i < numPlayers; i++)
		{
			playerHands.add(new ArrayList<Card>(myDealer.dealHand())); //human is ID 0
			allPlayers.get(i).setHand(playerHands.get(i));
		}
		
	}
	
	public void takeTurns()
	{
		if(bidFirst == true) 
			{
			return; //return control to user to make bid
			}
		if(tookTurn.get(currentTurn) == true) //ends on the person who took the turn
		{
			calculateTrick();
			return;
		}
		if(currentTurn != 0)
			allPlayers.get(currentTurn).takeTurn(onTable, trumpSuit, originalSuit);
			
		if(currentTurn + 1 == numPlayers) currentTurn = 0;
		else currentTurn++;
		
		if(currentTurn == 1) 
			{
			System.out.println("Your turn!");
			return; //let User take a turn, when user calls again, it will be 1's turn
			}
		
		takeTurns();
	}
	
	public ArrayList<Card> showTable()
	{
		return onTable;
	}
	
	public void clearTable()
	{
		onTable.clear();
		for(int x = 0; x < numPlayers; x++)
			onTable.add(new Card(-1, -1));
	}
	
	public void calculateScores()
	{
		System.out.println("___________________________________________________________________________________________________");
		ArrayList<Integer> roundScores = new ArrayList<Integer>();
		for(int i = 0; i < numPlayers; i++)
			roundScores.add(0);
		
		int highTrickID = 0;
		int highTrickNum = 0;
		boolean tiedTricks = false;
		//find the person who had the highest amount of tricks
		for(Player myP : allPlayers)
			if(myP.trickNum() > highTrickNum)
				{
				highTrickID = myP.getPlayerID();
				highTrickNum = myP.trickNum();
				}
		int it = 0;
		for(Player myP : allPlayers)
		{
			//if there was a tie for highest amount, no one gets it
			if(myP.trickNum() == highTrickNum && myP.getPlayerID() != highTrickID)
				tiedTricks = true;
			//check for points based on 2's and Jacks
			for(Card cardCheck : myP.showCardsWon())
			{
				int cardSuit = cardCheck.getSuit();
				int cardNum = cardCheck.getNum();
				
				//check for 2 of trump and jack of trump, High will be found later
				if(cardSuit == trumpSuit && cardNum == 11)
					{
					System.out.println("Jack of " + cardCheck.getSuitName() + " goes to player " + it);
					jackMan = it;
					roundScores.set(it, roundScores.get(it) + 1);
					}
			}
			it++;
		}
		
		Card highTrump = null;
		Card lowTrump = null;
		int lowCardID = -1;
		int highCardID = -1;
		it = 0;
		//check for points based on who played the highest trump and the lowest trump
		for(Player myP : allPlayers)
		{
			for(Card checkHigh : myP.showCardsWon())
			{
				if(checkHigh.getSuit() == trumpSuit && highTrump == null)
				{
					highTrump = checkHigh;
					lowTrump = checkHigh;
					highCardID = it;
					lowCardID = it;
				}
				else if(checkHigh.getSuit() == trumpSuit && checkHigh.getNum() > highTrump.getNum())
				{
					highTrump = checkHigh;
					highCardID = it;
				}
				else if(checkHigh.getSuit() == trumpSuit && checkHigh.getNum() < lowTrump.getNum())
				{
					lowTrump = checkHigh;
					lowCardID = it;
				}
			}
			it++;
		}
		if(highCardID == -1) System.out.println("The highest card should not be null");
		
		System.out.println("Lowest card of " + lowTrump.getSuitName() + " goes to player " + lowCardID + " with a " + lowTrump.getNumName() + " of " + lowTrump.getSuitName());
		lowMan = lowCardID;
		System.out.println("Highest card of " + highTrump.getSuitName() + " goes to player " + highCardID + " with a " + highTrump.getNumName() + " of " + highTrump.getSuitName());
		highMan = highCardID;
		roundScores.set(highCardID, roundScores.get(highCardID) + 1);
		roundScores.set(lowCardID, roundScores.get(lowCardID) + 1);
		//give user who won the most tricks a tentative point
		if(tiedTricks == false)
		{
			System.out.println("Number of tricks goes to player " + highTrickID);
			trickMan = highTrickID;
			roundScores.set(highTrickID, roundScores.get(highTrickID) + 1);
		}
		//check for a smudge
		for(int i = 0; i < numPlayers; i++)
		{
			if(roundScores.get(i) == 4 && allPlayers.get(i).trickNum() == 6 && highestBidder == i && currentBid == 5) 
			{
				roundScores.set(i, 5);
				System.out.println("Player " + i + " got a smudge!");
				smudgeMan = i;
			}
		}
		if(roundScores.get(highestBidder) < currentBid)
		{
			int makeNegative = -1 * currentBid;
			System.out.println("Player " + highestBidder + " couldn't live up to their bid of " + currentBid + " !");
			allPlayers.get(highestBidder).addScore((-1*currentBid));
			roundScores.set(highestBidder, makeNegative);
		}
		else
		{
			System.out.println("Player " + highestBidder + " has lived up to their bid of " + currentBid + ", they will receive " + roundScores.get(highestBidder)
			+ " points from the cards they played");
			allPlayers.get(highestBidder).addScore(roundScores.get(highestBidder));
		}
		for(int i = 0; i < numPlayers; i++)
		{
			if(i != highestBidder) allPlayers.get(i).addScore(roundScores.get(i));
			System.out.println("Player " + i + " scored " + roundScores.get(i) + " points this round, and has " + allPlayers.get(i).getScore() + " total points");
		}
	System.out.println("___________________________________________________________________________________________________");
	int maxScore = 6; //player has to be higher than 6
	for(int i = 0; i < numPlayers; i++)
	{
		if(allPlayers.get(i).getScore() > maxScore)
		{
			gameWinner = i;
			maxScore = allPlayers.get(i).getScore();
		}
	}
	bidFirst = true;
	highestBidder = -1;
	currentBid = -1;	
	trumpSuit = -1;
	for(Player p : allPlayers)
	{
		p.resetTricks();
	}
	}
	
	public void calculateTrick()
	{
		Card tempHigh = onTable.get(0);
		int winner = 0;
		int i = 0;
		for(Card playedCard : onTable)
		{
			//if the card is is a trump and the other is not, it's higher
			if(playedCard.getSuit() == trumpSuit && tempHigh.getSuit() != trumpSuit)
				{
				tempHigh = playedCard;
				winner = i;
				}
			//if the card is the chosen suit and the other is not, and not a trump, it's higher
			else if(playedCard.getSuit() == originalSuit && !(tempHigh.getSuit() == trumpSuit || tempHigh.getSuit() == originalSuit))
				{
				tempHigh = playedCard;
				winner = i;
				}
			//if the suits are equal, and the card has a larger value, it's higher
			else if(playedCard.getSuit() == tempHigh.getSuit() && playedCard.getNum() > tempHigh.getNum())
				{
				tempHigh = playedCard;
				winner = i;
				}
			//else, the other card is a trump or a higher value original, or a higher value card of the same suit
			i++;
		}
		System.out.println("Winner of the trick: player " + winner);
		System.out.println();
		allPlayers.get(winner).wonTrick();
		currentTurn = winner;
		allPlayers.get(winner).giveCardsWon(onTable);
		//whoever wins goes next
		//if someone wins the game, set gameWinner to their ID
		//also print a congratulations message
		originalSuit = -1;
		Collections.fill(tookTurn, false);
		if(playerHands.get(0).isEmpty())
			{
			calculateScores();
			if(gameWinner != -1)
			{
				System.out.println("Player " + gameWinner + " has won the game!");
				return;
			}
			myDealer.refreshDeck();
			myDealer.shuffleDeck();
			dealHands();
			}
		
		//takeTurns(); //if game's not over, return to turns
	}
	
	public int getGameWinner()
	{
		return gameWinner;
	}
	
	public ArrayList<Card> showHand(int id)
	{
		return playerHands.get(id);
	}
	
	public boolean playCard(Card playersCard, int id)
	{
		if(id == 0)
		{
			int suitNum = playersCard.getSuit();
			if(originalSuit != -1 && suitNum != originalSuit && suitNum != trumpSuit) //if the user played a non-trump, non-original suit, but had an original suit, he has to play it
			{
				for(Card cardCheck : playerHands.get(0))
				{
					if(cardCheck.getSuit() == originalSuit)
					{
						if(originalSuit == 0) System.out.println("You have a club, you must play it!");
						else if(originalSuit == 1) System.out.println("You have a diamond, you must play it!");
						else if(originalSuit == 2) System.out.println("You have a heart, you must play it!");
						else if(originalSuit == 3) System.out.println("You have a spade, you must play it!");
						currentTurn--; //takeTurns will be called again, so the user can replay
						return false;	
					}
					
				}
				
			}
			
		}
		boolean setOriginal = true;
		for(boolean anyPlayerTookTurn : tookTurn)
			{
			if(anyPlayerTookTurn == true)
				setOriginal = false;
			}
		
		if(trumpSuit == -1)
		{
			trumpSuit = playersCard.getSuit();
			System.out.println("The trump suit is: " + playersCard.getSuitName());
		}
		//set the main suit for this trick
		if(setOriginal == true) 
			originalSuit = playersCard.getSuit();
		
		playerHands.get(id).remove(playersCard);
		tookTurn.set(id, true);
		onTable.set(id, playersCard); 
		return true;
	}
	
	
	public void resetGame()
	{
		playerHands = new ArrayList<ArrayList<Card>>();
		madeBid = new ArrayList<Boolean>();
		myDealer.refreshDeck();
		trumpSuit = -1;
		originalSuit = -1;
		lastWinner = 0;
		currentTurn = 0;
		currentBid = -1;
		highestBidder = -1;
		numPlayers = 0;
		gameWinner = -1;
		for(Player p : allPlayers)
		{
			p.resetTricks();
			p.resetScore();
		}
	}
	
	public void bid(int bidNum, int playerID)
	{
		bidFirst = false;
		System.out.println("Player " + playerID + " bid " + bidNum);
		madeBid.set(playerID, true);
		if(currentBid < bidNum)
		{
			currentBid = bidNum;
			highestBidder = playerID;
			currentTurn = playerID;
		}
		int newID = 0;
		if(playerID + 1 == numPlayers) newID = 0;
		else newID = playerID + 1;
		
		if(madeBid.get(newID) == true)
		{
			System.out.println("Player " + currentTurn + " has the highest bid, " + currentBid + ", and will go first");
			Collections.fill(madeBid, false);
		}
		else
		{
			if(newID != 0)
				allPlayers.get(newID).makeBid(); //for human player, this will be implemented to let the user pick a button
			else
				{
				return; //user will Bid, call this function again
				}
		}
	}
	
	public int getTurn()
	{
		return currentTurn;
	}
	public int getBid()
	{
		return currentBid;
	}
	@Override
	public Dealer createDealer() {
		return new pitchDealer();
	}

}
