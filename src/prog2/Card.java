package prog2;



import javafx.scene.image.Image;

public class Card { //can be used for any possible Deck, the Deck class will make a standard set of cards
	private int suit; //0, 1, 2, 3 are clubs, diamonds, hearts, spades
	private int num; //11, 12, 13, 14 are Jack, Queen, King, and Ace (Ace could be 1, but since ace is a high card I made it 14)
	public Image cardPic;
	private boolean imageValid;
	
	public Card()
	{
		suit = 0; num = 0; imageValid = false;
	}
	
	public Card(int mySuit, int myNum)
	{
		suit = mySuit; num = myNum; imageValid = false;
	}
	
	public Card(int mySuit, int myNum, String myImage)
	{
		suit = mySuit; num = myNum; imageValid = true;
		try {cardPic = new Image(myImage);}
		
		catch(Exception e) {cardPic = new Image("param.png");}
	}
	
	public int getSuit() { return suit; }
	public String getSuitName()
	{
		if(suit == 0) return "Clubs";
		else if (suit == 1) return "Diamonds";
		else if (suit == 2) return "Hearts";
		else return "Spades";
	}
	
	public int getNum() { return num; }
	public String getNumName()
	{
		if(num == 2) return "Two";
		else if(num == 3) return "Three";
		else if(num == 4) return "Four";
		else if(num == 5) return "Five";
		else if(num == 6) return "Six";
		else if(num == 7) return "Seven";
		else if(num == 8) return "Eight";
		else if(num == 9) return "Nine";
		else if(num == 10) return "Ten";
		else if(num == 11) return "Jack";
		else if(num == 12) return "Queen";
		else if(num == 13) return "King";
		else return "Ace";
	}
	
	public Image getPic() 
	{
		if(imageValid == true)
			return cardPic; 
		else
			throw new IllegalArgumentException("No image was set, so there's no image to get");

	}
}
