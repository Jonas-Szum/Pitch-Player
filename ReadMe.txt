actual classes found in prog2/src/prog2
images found in prog2/src
This was made using eclipse, and should work if you put the prog2 folder (the one that I turned in) in your
eclipse-workspace folder


Hello,

I included the folder that contains everything you need to run the program. I already have
the test cases in the source folder, along with the other classes that are used in the program. I made
a copy of all the test cases and put them in a separate folder to make it easier to find them, should
you choose to read them.

I didn't write test cases for the Player class, because AIPlayer is almost an exact copy of it. The only difference
is that, when AIPlayer makes decisions on bidding and playing, the Player class simply returns immediately.

That being said, I didn't write test cases to verify the accuracy of the bots' bids and decisions to play
cards. I also didn't test much of the pitch class, because a lot of it is recursion and stack control,
so the GUI gets returned to only when it's time to take a turn. Because of that, I can't really write test
cases to show the interaction between the GUI, pitch, and the bots. The main quality assurance for the pitch class
comes from actually playing the game.

I did not add a notification bar that notifies the player when it's their turn. I didn't do this because
the screen is only ever updated when the user has an option to play a card or make a bid. So basically, when the
cards are being played, the user will be able to see the cards that were already played by the bots before him/her.
When he/she sees the cards, the only option will be to play a card.

Also, I may or may not have added a bar to show who won the bid. I don't think that's very necessary, because
the user will be able to tell who won the bid based on who played their cards (if bot 1 won, there will be
three turns taken before you. if it's bot two, two cards will be played before you, etc). I have also added
some System.out.println statements that help the game go along, if you want to read the raw stats of the game
vs using the interface. It's like using the strategic view in CIV 5. Update: I have not

Enjoy the game! have fun playing against Hummel, Hallenbeck MK. II, and Theys. 