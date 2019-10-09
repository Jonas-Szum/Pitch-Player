package prog2;


import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Iterator;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.application.Application;
//import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
//import javafx.scene.Group;
import javafx.scene.Node;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class driverClass extends Application{
	
	Pitch pitch;
	TextField text;
	Button exitGame, enterGame, x, y, z, gameButton, pass, bid2, bid3, bid4, bidSmudge;
	ArrayList<Button> cardButtons;
	Stage myStage;
	Scene welcomeScreen, playerOptions, gamePlay;
	HashMap<String, Scene> sceneMap;
	int numOpponents, width, height;

public static void main(String[] args) {
launch(args);
}
 

public void start(Stage primaryStage) throws Exception {
	//The following code block sets up the game variables, and some of the buttons
	pitch = new Pitch();
	cardButtons = new ArrayList<Button>();
	primaryStage.setTitle("Ready, Player 0!");
	BorderPane gamePane = new BorderPane();
	numOpponents = 0;
	myStage = primaryStage;
	HBox horizCards = new HBox(10);
	width = 1000;
	height = 500;
	exitGame = new Button("Exit game");
	enterGame = new Button("Go to game options");
	sceneMap = new HashMap<String, Scene>();
	x = new Button("One opponent");
	y = new Button("Two opponents");
	z = new Button("Three opponents");
	
	pass = new Button("Pass");
	bid2 = new Button("Bid 2");
	bid3 = new Button("Bid 3");
	bid4 = new Button("Bid 4");
	bidSmudge = new Button("Smudge");
	
	//This code block sets up the background image for the game
	ImageView myOverlayImage = new ImageView("emptyTable.png");
	ArrayList<Button> AIButtons = new ArrayList<Button>();
	gamePane.setPadding(new Insets(200, 300, 0, 300)); //top right bottom left
	EventHandler<ActionEvent> startGame = new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event){
			
			Button b = (Button)event.getSource();
			if(b == x)
				{
				//Set up game
				pitch.setNumPlayers(2);
				//pitch.dealHands();
				
				//Set the background image
				myOverlayImage.setImage(new Image("OneOpponent.png"));
				BackgroundSize backgroundSize = new BackgroundSize(width, height, false, false, false, false);
			    BackgroundImage backgroundImage = new BackgroundImage(myOverlayImage.getImage(), BackgroundRepeat.REPEAT, 
			            BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, backgroundSize);
			    Background myBackGround = new Background(backgroundImage);
				gamePane.setBackground(myBackGround);
				
				//change number of components and add a button
				numOpponents = 1;
				AIButtons.add(new Button());
				}
			else if(b == y)
				{
				//set up game for 3 players
				pitch.setNumPlayers(3);
				myOverlayImage.setImage(new Image("TwoOpponents.png"));
				
				BackgroundSize backgroundSize = new BackgroundSize(width, height, false, false, false, false);
			    BackgroundImage backgroundImage = new BackgroundImage(myOverlayImage.getImage(), BackgroundRepeat.REPEAT, 
			            BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, backgroundSize);
			    Background myBackGround = new Background(backgroundImage);
				gamePane.setBackground(myBackGround);
				
				//change number of components and add a button
				numOpponents = 2;
				AIButtons.add(new Button());
				AIButtons.add(new Button());
				}
			else
				{
				pitch.setNumPlayers(4);
				
				//Set the background image
				myOverlayImage.setImage(new Image("ThreeOpponents.png"));
				BackgroundSize backgroundSize = new BackgroundSize(width, height, false, false, false, false);
			    BackgroundImage backgroundImage = new BackgroundImage(myOverlayImage.getImage(), BackgroundRepeat.REPEAT, 
			            BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, backgroundSize);
			    Background myBackGround = new Background(backgroundImage);
				gamePane.setBackground(myBackGround);
				
				//change number of components and add a button
				numOpponents = 3;	
				AIButtons.add(new Button());
				AIButtons.add(new Button());
				AIButtons.add(new Button());
				}
			myStage.setScene(sceneMap.get("gamePlay"));
			int k = 0;
			ArrayList<Card> myCards = pitch.showHand(0);
			for(Card oneCard : myCards)
			{
				cardButtons.add(new Button());
				ImageView v = new ImageView(oneCard.getPic());
				v.setFitHeight(75);
				v.setFitWidth(37);
				v.setPreserveRatio(true);
				cardButtons.get(k).setGraphic(v);
				k++;
			}
			
			for(int i = 0; i < 6; i++)
			{
				horizCards.getChildren().add(cardButtons.get(i));
				horizCards.getChildren().get(i).setDisable(true);
			}
			horizCards.setAlignment(Pos.CENTER);
			gamePane.setBottom(horizCards);
		}
	};

	gameButton = new Button(Integer.toString(numOpponents)); //make sure the number of opponents is correct

	x.setOnAction(startGame);
	y.setOnAction(startGame);
	z.setOnAction(startGame);
	
	enterGame.setOnAction(e ->{
				myStage.setScene(sceneMap.get("gameOptions"));
		});
	
	
	exitGame.setOnAction(e -> {
			primaryStage.close();
		});
	
	gameButton.setOnAction(e -> {
			primaryStage.close();
		});
	//replace param with name of your own picture. Make sure
	//its in the src folder
	

	//Each Card class should have an image value
	BorderPane welcomePane = new BorderPane();	//this should be the starting screen
	VBox paneCenter = new VBox(10, enterGame, exitGame);
	paneCenter.setAlignment(Pos.CENTER);
	welcomePane.setCenter(paneCenter);
	
	//Scene 2, player selection screen
	VBox newScene = new VBox(10, x,y,z);	//x, y, z are 1 player, 2 player, and 3 player respectively
	newScene.setAlignment(Pos.CENTER);
	HBox bidValues = new HBox(0, pass, bid2, bid3, bid4, bidSmudge);
	

	EventHandler<MouseEvent> playCard = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent event)
		{
			Button tempButt = (Button)event.getSource();
			//find the source of the button, delete the button and delete the card from the hand
			int cardIndex = horizCards.getChildren().indexOf(tempButt);
			Card cardPlayed = pitch.showHand(0).get(cardIndex);
			boolean success = pitch.playCard(cardPlayed, 0);
			if(success == true)
			{
				horizCards.getChildren().remove(tempButt);
				cardButtons.remove(cardIndex);
				for(Node myNode : horizCards.getChildren())
				{
				myNode.setDisable(true);
				}
			}
			pitch.takeTurns();
			ArrayList<Card> table = new ArrayList<Card>(pitch.showTable());
			ArrayList<Button> tempButts = new ArrayList<Button>();
			ArrayList<VBox> tempBox = new ArrayList<VBox>();
			//show the cards that were played
			if(success == true)
			{
				pitch.clearTable();
				for(int i = 0; i < numOpponents + 1; i++)
				{
				tempButts.add(new Button());
				if(table.get(i).getSuit() != -1)
				{
					ImageView v = new ImageView((table.get(i).getPic()));
					v.setFitHeight(75);
					v.setFitWidth(37);
					v.setPreserveRatio(true);
					tempButts.get(i).setGraphic(v);
					tempBox.add(new VBox(tempButts.get(i)));
				}
				else
				{
					tempBox.add(null);
				}
				//show the bots' played cards, only if they took their turns already
				if(i == 0 && tempBox.get(i) != null)
				{
					tempBox.get(i).setAlignment(Pos.CENTER);
					gamePane.setCenter(tempBox.get(i));
				}
				if (i == 1 && tempBox.get(i) != null)
				{
					tempBox.get(i).setAlignment(Pos.TOP_LEFT);
					gamePane.setLeft(tempBox.get(i));
				}
				else if (i == 2 && tempBox.get(i) != null)
				{
					tempBox.get(i).setAlignment(Pos.TOP_CENTER);
					gamePane.setTop(tempBox.get(i));
				}
				else if (i == 3 && tempBox.get(i) != null)
				{
					tempBox.get(i).setAlignment(Pos.TOP_RIGHT);
					gamePane.setRight(tempBox.get(i));
				}
				//when all cards have been played, user must click one to move on
				tempButts.get(i).setOnAction(new EventHandler<ActionEvent>() { 
						public void handle(ActionEvent event)
						{
							for(int i = 0; i < tempBox.size(); i++)
								{
								tempBox.get(i).getChildren().remove(0);
								}
							for(Node myNode : horizCards.getChildren())
							{
								myNode.setDisable(false);
							}
							if(horizCards.getChildren().isEmpty())
							{
								if(pitch.getGameWinner() != -1) //check for winner
								{
									int jackWinner = pitch.jackWinner();
									int tricksWinner = pitch.trickWinner();
									int lowestWinner = pitch.lowWinner();
									int highestWinner = pitch.highWinner();
									int smudgeWinner = pitch.smudgeWinner();
									
									ArrayList<Integer> winnerDudes = new ArrayList<Integer>();
									winnerDudes.add(jackWinner);
									winnerDudes.add(tricksWinner);
									winnerDudes.add(lowestWinner);
									winnerDudes.add(highestWinner);
									winnerDudes.add(smudgeWinner);
									
									VBox winnerBox = new VBox(10);
									if(jackWinner == -1)
										winnerBox.getChildren().add(new Button("No one won jacks"));
									else
										winnerBox.getChildren().add(new Button("Player " + jackWinner + " won jacks"));
									
									if(tricksWinner == -1)
										winnerBox.getChildren().add(new Button("No one won number of tricks"));
									else
										winnerBox.getChildren().add(new Button("Player " + tricksWinner + " won the highest number of tricks"));
									
									if(smudgeWinner == -1)
										winnerBox.getChildren().add(new Button("No one got smudge"));
									else
										winnerBox.getChildren().add(new Button("Player " + tricksWinner + " got smudge!"));
									
									winnerBox.getChildren().add(new Button("Player " + lowestWinner + " won the lowest trump card"));
									winnerBox.getChildren().add(new Button("Player " + highestWinner + " won the highest trump card"));
									gamePane.setCenter(null);
									gamePane.setTop(null);
									gamePane.setLeft(null);
									gamePane.setRight(null);
									
									winnerBox.setAlignment(Pos.CENTER);
									gamePane.setCenter(winnerBox);
									for(Node buttNode : winnerBox.getChildren())
									{
										buttNode.setOnMouseReleased(e -> {
											gamePane.setCenter(null);
											gamePane.setTop(null);
											gamePane.setBottom(null);
											gamePane.setLeft(null);
											gamePane.setRight(null);
											Button b;
											if(pitch.getGameWinner() == 0)
												b = new Button("You have won the game!");
											else
												b = new Button("Bot " + pitch.getGameWinner() + " has won the game! Better luck next time, loser!");
											pitch.resetGame();
											VBox singleButton = new VBox(b);
											singleButton.setAlignment(Pos.CENTER);
											gamePane.setCenter(singleButton);
											b.setOnAction(new EventHandler<ActionEvent>() {
												public void handle(ActionEvent event)
												{
													myStage.setScene(sceneMap.get("postGame"));
													pass.setDisable(false);	//disable all bid buttons until hand is played
													bid2.setDisable(false);
													bid3.setDisable(false);
													bid4.setDisable(false);
													bidSmudge.setDisable(false);
													gamePane.setCenter(bidValues);
													horizCards.getChildren().clear();
													/*int tempSize = AIButtons.size();
													for(int x = 0; x < tempSize; x++) 
														{
														AIButtons.remove(0);
														if(x == 0) gamePane.setLeft(null);
														if(x == 1) gamePane.setCenter(null);
														if(x == 2) gamePane.setRight(null);
														}*/
												}
											});
										});
									}
									/////////////////////////////////////////////////////////
									
									
								}
								else
								{
									
									int k = 0;
									ArrayList<Card> myCards = pitch.showHand(0);
									for(Card oneCard : myCards)
									{
										cardButtons.add(new Button());
										ImageView v = new ImageView(oneCard.getPic());
										v.setFitHeight(75);
										v.setFitWidth(37);
										v.setPreserveRatio(true);
										cardButtons.get(k).setGraphic(v);
										cardButtons.get(k).setDisable(true);
										horizCards.getChildren().add(cardButtons.get(k));
										k++;
									}
									int jackWinner = pitch.jackWinner();
									int tricksWinner = pitch.trickWinner();
									int lowestWinner = pitch.lowWinner();
									int highestWinner = pitch.highWinner();
									int smudgeWinner = pitch.smudgeWinner();
									
									ArrayList<Integer> winnerDudes = new ArrayList<Integer>();
									winnerDudes.add(jackWinner);
									winnerDudes.add(tricksWinner);
									winnerDudes.add(lowestWinner);
									winnerDudes.add(highestWinner);
									winnerDudes.add(smudgeWinner);
									
									VBox winnerBox = new VBox(10);
									if(jackWinner == -1)
										winnerBox.getChildren().add(new Button("No one won jacks"));
									else
										winnerBox.getChildren().add(new Button("Player " + jackWinner + " won jacks"));
									
									if(tricksWinner == -1)
										winnerBox.getChildren().add(new Button("No one won number of tricks"));
									else
										winnerBox.getChildren().add(new Button("Player " + tricksWinner + " won the highest number of tricks"));
									
									if(smudgeWinner == -1)
										winnerBox.getChildren().add(new Button("No one got smudge"));
									else
										winnerBox.getChildren().add(new Button("Player " + tricksWinner + " got smudge!"));
									
									winnerBox.getChildren().add(new Button("Player " + lowestWinner + " won the lowest trump card"));
									winnerBox.getChildren().add(new Button("Player " + highestWinner + " won the highest trump card"));
									gamePane.setCenter(null);
									gamePane.setTop(null);
									gamePane.setLeft(null);
									gamePane.setRight(null);
									
									winnerBox.setAlignment(Pos.CENTER);
									gamePane.setCenter(winnerBox);
									for(Node b : winnerBox.getChildren())
									{
										b.setOnMouseReleased(new EventHandler<MouseEvent>() {
											public void handle(MouseEvent event)
											{
												pass.setDisable(false);	//disable all bid buttons until hand is played
												bid2.setDisable(false);
												bid3.setDisable(false);
												bid4.setDisable(false);
												bidSmudge.setDisable(false);
												gamePane.setCenter(bidValues);
												gamePane.setLeft(null);
												gamePane.setRight(null);
												gamePane.setTop(null);
											}
										});
									}
								}
								
							}
							else //cards are not empty
							{
								gamePane.setLeft(null);
								gamePane.setRight(null);
								gamePane.setTop(null);
								gamePane.setCenter(null);
								pitch.clearTable();
								pitch.takeTurns();
								ArrayList<Card> table = new ArrayList<Card>(pitch.showTable());
								ArrayList<Button> tempButts = new ArrayList<Button>();
								ArrayList<VBox> tempBox = new ArrayList<VBox>();
								for(int i = 0; i < numOpponents + 1; i++)
								{
									if(table.get(i).getSuit() != -1)
									{
										tempButts.add(new Button());
										ImageView v = new ImageView((table.get(i).getPic()));
										v.setFitHeight(75);
										v.setFitWidth(37);
										v.setPreserveRatio(true);
										tempButts.get(i).setGraphic(v);
										tempBox.add(new VBox(tempButts.get(i)));
									
										if(i == 0)
										{
											tempBox.get(i).setAlignment(Pos.CENTER);
											gamePane.setCenter(tempBox.get(i));
										}
										if (i == 1)
										{
											tempBox.get(i).setAlignment(Pos.TOP_LEFT);
											gamePane.setLeft(tempBox.get(i));
										}
										else if (i == 2)
										{
											tempBox.get(i).setAlignment(Pos.TOP_CENTER);
											gamePane.setTop(tempBox.get(i));
										}
										else if (i == 3)
										{
											tempBox.get(i).setAlignment(Pos.TOP_RIGHT);
											gamePane.setRight(tempBox.get(i));
										}
									}
									else
									{
										tempBox.add(null);
										tempButts.add(null);
									}
								}
							}
						}
					});
				
				}
			}			
		}
	};
	
	
	
	bidValues.setAlignment(Pos.CENTER);
	gamePane.setCenter(bidValues);
	EventHandler<ActionEvent> makeBid = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event)
		{
			Button tempButt = (Button)event.getSource();	//set the player's bid
			if(tempButt == pass) {pitch.bid(0, 0);}
			else if(tempButt == bid2) {pitch.bid(2, 0);}
			else if(tempButt == bid3) {pitch.bid(3, 0);}
			else if(tempButt == bid4) {pitch.bid(4, 0);}
			else if(tempButt == bidSmudge) {pitch.bid(5, 0);}
			
			int highestBidder = pitch.getTurn();
			int highestBid = pitch.getBid();
			Button myB = new Button("Player " + highestBidder + " had the highest bid: " + highestBid);
			VBox tempB = new VBox(myB);
			tempB.setAlignment(Pos.CENTER);
			//once the user acknowledges who won the bid, they can continue the game
			myB.setOnAction(e -> {
				gamePane.setCenter(null);
				for(Node myNode : horizCards.getChildren())
					{
					myNode.setDisable(false);
					myNode.setOnMouseReleased(playCard);
					}
				pitch.takeTurns();
				ArrayList<Card> table = new ArrayList<Card>(pitch.showTable());
				ArrayList<Button> tempButts = new ArrayList<Button>();
				ArrayList<VBox> tempBox = new ArrayList<VBox>();
				for(int i = 0; i < numOpponents + 1; i++)
				{
					if(table.get(i).getSuit() != -1)
					{
						tempButts.add(new Button());
						ImageView v = new ImageView((table.get(i).getPic()));
						v.setFitHeight(75);
						v.setFitWidth(37);
						v.setPreserveRatio(true);
						tempButts.get(i).setGraphic(v);
						tempBox.add(new VBox(tempButts.get(i)));
					
						if(i == 0)
						{
							tempBox.get(i).setAlignment(Pos.CENTER);
							gamePane.setCenter(tempBox.get(i));
						}
						if (i == 1)
						{
							tempBox.get(i).setAlignment(Pos.TOP_LEFT);
							gamePane.setLeft(tempBox.get(i));
						}
						else if (i == 2)
						{
							tempBox.get(i).setAlignment(Pos.TOP_CENTER);
							gamePane.setTop(tempBox.get(i));
						}
						else if (i == 3)
						{
							tempBox.get(i).setAlignment(Pos.TOP_RIGHT);
							gamePane.setRight(tempBox.get(i));
						}
					}
					else
					{
						tempBox.add(null);
						tempButts.add(null);
					}
				}
			});
			
			pass.setDisable(true);	//disable all bid buttons until hand is played
			bid2.setDisable(true);
			bid3.setDisable(true);
			bid4.setDisable(true);
			bidSmudge.setDisable(true);
			gamePane.setCenter(tempB);
			
		}
	};
	


		//horizCards.getChildren().remove(1); //for deleting the cards

	
	pass.setOnAction(makeBid);
	bid2.setOnAction(makeBid);
	bid3.setOnAction(makeBid);
	bid4.setOnAction(makeBid);
	bidSmudge.setOnAction(makeBid);
	
	
	welcomeScreen = new Scene(welcomePane, width, height);
	playerOptions = new Scene(newScene, width, height);
	gamePlay = new Scene(gamePane, width, height);	
    
	sceneMap.put("welcome", welcomeScreen);
	sceneMap.put("gameOptions", playerOptions);
	sceneMap.put("gamePlay", gamePlay);
	
	
	BorderPane postGamePane = new BorderPane();
	Button replayGame = new Button("Replay game?");
	Button exitInstead = new Button("Exit game?");
	
	exitInstead.setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event){
			primaryStage.close();
		}
	});
	
	replayGame.setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event){
			myStage.setScene(sceneMap.get("welcome"));
		}
	});
	VBox postGameVBox = new VBox(10, replayGame, exitInstead);
	postGameVBox.setAlignment(Pos.CENTER);
	postGamePane.setCenter(postGameVBox);
	Scene postGame = new Scene(postGamePane, width, height);
	sceneMap.put("postGame", postGame);
	
	primaryStage.setScene(sceneMap.get("welcome"));
	primaryStage.show();
	
	
	}

}

