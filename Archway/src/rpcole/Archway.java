package rpcole;

import java.awt.Dimension;

import ks.client.gamefactory.GameWindow;
import ks.common.controller.SolitaireMouseMotionAdapter;
import ks.common.games.Solitaire;
import ks.common.games.SolitaireUndoAdapter;
import ks.common.model.*;
import ks.common.view.*;
import ks.launcher.Main;

public class Archway extends Solitaire {

	MultiDeck deck;
	Pile reservePile1, reservePile2, reservePile3, reservePile4, reservePile5;
	Pile reservePile6, reservePile7, reservePile8, reservePile9, reservePile10;
	Pile reservePile11, reservePile12, reservePile13;
	Column tableauColumn1, tableauColumn2, tableauColumn3, tableauColumn4;
	Pile foundationPile1, foundationPile2, foundationPile3, foundationPile4,foundationPile5, foundationPile6, foundationPile7, foundationPile8;
	PileView RAStack, R2Stack, R3Stack, R4Stack, R5Stack, R6Stack, R7Stack;
	PileView R10Stack, RJStack, RQStack, RKStack, AHStack, ACStack, ADStack, ASStack;
	PileView KSStack, KCStack, KDStack, KHStack, R8Stack, R9Stack;
	ColumnView tv1,tv2, tv3, tv4;
	IntegerView scoreView;
	IntegerView cardsLeftView;

	@Override
	public String getName() {
		return "Archway";
	}

	@Override
	public boolean hasWon() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension (980, 720);
	}

	@Override
	public void initialize() {

		//flags for whether the foundation aces and kings are obtained
		boolean hasAH = false;
		boolean hasAS = false;
		boolean hasAD = false;
		boolean hasAC = false;
		boolean hasKH = false;
		boolean hasKS = false;
		boolean hasKD = false;
		boolean hasKC = false;

		//initialize the model with random seed
		initializeModel(getSeed());
		//initialize the GUI
		initializeView();
		//initialize the controllers and attach them to the GUI
		//initializeControllers();

		//pull the top 48 cards off of the deck one by one and add them to the tableau
		//piles one pile after another (first card goes in pile one, second in pile two, ...etc.)
		for (int i=0; i < 48; i++){		
			Card c = deck.get();
			//if one of the cards pulled is an ace or king that is needed on the foundations
			//place it on the proper foundation and set the flag to true before adding to tableau
			if(c.equals(new Card(1,1)) && !hasAC){//if card is Ace of Clubs and is needed
				foundationPile4.add(c);
				hasAC = true;
				i--;//need to decrement i so that 48 cards still go in the tableau
			} else if(c.equals(new Card(1,2)) && !hasAD){//if card is Ace of Diamonds and is needed
				foundationPile3.add(c);
				hasAD = true;
				i--;//need to decrement i so that 48 cards still go in the tableau
			} else if(c.equals(new Card(1,3)) && !hasAH){//if card is Ace of Hearts and is needed
				foundationPile1.add(c);
				hasAH = true;
				i--;//need to decrement i so that 48 cards still go in the tableau
			} else if(c.equals(new Card(1,4)) && !hasAS){//if card is Ace of Spades and is needed
				foundationPile2.add(c);
				hasAS = true;
				i--;//need to decrement i so that 48 cards still go in the tableau
			} else if(c.equals(new Card(13,1)) && !hasKC){//if card is King of Clubs and is needed
				foundationPile8.add(c);
				hasKC = true;
				i--;//need to decrement i so that 48 cards still go in the tableau
			} else if(c.equals(new Card(13,2)) && !hasKD){//if card is King of Diamonds and is needed
				foundationPile7.add(c);
				hasKD = true;
				i--;//need to decrement i so that 48 cards still go in the tableau
			} else if(c.equals(new Card(13,3)) && !hasKH){//if card is King of Hearts and is needed
				foundationPile5.add(c);
				hasKH = true;
				i--;//need to decrement i so that 48 cards still go in the tableau
			} else if(c.equals(new Card(13,4)) && !hasKS){//if card is King of Spades and is needed
				foundationPile6.add(c);
				hasKS = true;
				i--;//need to decrement i so that 48 cards still go in the tableau
			}//if it was not one of the aces or kings, continue with adding to tableau
			else if(i%4==0){//should go in first tableau
				tableauColumn1.add(c);
			} else if(i%4==1){//should go in second tableau
				tableauColumn2.add(c);
			} else if(i%4==2){//should go in third tableau
				tableauColumn3.add(c);
			} else if(i%4==3){//should go in fourth tableau
				tableauColumn4.add(c);
			}
		}

		//pulls the remaining cards in the deck (48 cards) and places
		//them in the proper reserve piles based upon their rank
		for (int i=0; i < 48; i++){
			Card c = deck.get();
			//if one of the cards pulled is an ace or king that is needed on the foundations
			//place it on the proper foundation and set the flag to true before adding to reserve piles
			if(c.equals(new Card(1,1)) && !hasAC){//if card is Ace of Clubs and is needed
				foundationPile4.add(c);
				hasAC = true;
				i--;//need to decrement i so that 48 cards still go in the reserve piles
			} else if(c.equals(new Card(1,2)) && !hasAD){//if card is Ace of Diamonds and is needed
				foundationPile3.add(c);
				hasAD = true;
				i--;//need to decrement i so that 48 cards still go in the reserve piles
			} else if(c.equals(new Card(1,3)) && !hasAH){//if card is Ace of Hearts and is needed
				foundationPile1.add(c);
				hasAH = true;
				i--;//need to decrement i so that 48 cards still go in the reserve piles
			} else if(c.equals(new Card(1,4)) && !hasAS){//if card is Ace of Spades and is needed
				foundationPile2.add(c);
				hasAS = true;
				i--;//need to decrement i so that 48 cards still go in the reserve piles
			} else if(c.equals(new Card(13,1)) && !hasKC){//if card is King of Clubs and is needed
				foundationPile8.add(c);
				hasKC = true;
				i--;//need to decrement i so that 48 cards still go in the reserve piles
			} else if(c.equals(new Card(13,2)) && !hasKD){//if card is King of Diamonds and is needed
				foundationPile7.add(c);
				hasKD = true;
				i--;//need to decrement i so that 48 cards still go in the reserve piles
			} else if(c.equals(new Card(13,3)) && !hasKH){//if card is King of Hearts and is needed
				foundationPile5.add(c);
				hasKH = true;
				i--;//need to decrement i so that 48 cards still go in the reserve piles
			} else if(c.equals(new Card(13,4)) && !hasKS){//if card is King of Spades and is needed
				foundationPile6.add(c);
				hasKS = true;
				i--;//need to decrement i so that 48 cards still go in the tableau
			} //if it was not one of the needed aces or kings, continue with adding to reserve piles
			else if(c.getRank()==1){//if ace
				reservePile1.add(c);
			} else if(c.getRank()==2){//if two
				reservePile2.add(c);
			} else if(c.getRank()==3){//if three
				reservePile3.add(c);
			} else if(c.getRank()==4){//if four
				reservePile4.add(c);
			}else if(c.getRank()==5){//if five
				reservePile5.add(c);
			}else if(c.getRank()==6){//if six
				reservePile6.add(c);
			}else if(c.getRank()==7){//if seven
				reservePile7.add(c);
			}else if(c.getRank()==8){//if eight
				reservePile8.add(c);
			}else if(c.getRank()==9){//if nine
				reservePile9.add(c);
			}else if(c.getRank()==10){//if ten
				reservePile10.add(c);
			}else if(c.getRank()==11){//if jack
				reservePile11.add(c);
			}else if(c.getRank()==12){//if queen
				reservePile12.add(c);
			}else if(c.getRank()==13){//if king
				reservePile13.add(c);
			}
		}
	}

	//initializes the controllers and attaches them to the GUI view objects
	private void initializeControllers() {
		// TODO Auto-generated method stub

	}

	//initializes the model with a seed
	private void initializeModel(int seed) {

		//create new deck and add it to the model
		deck = new MultiDeck("deck", 2);
		deck.create(seed);
		model.addElement(deck);

		//create and add the tableau columns
		tableauColumn1 = new Column ("tc1");
		model.addElement(tableauColumn1);

		tableauColumn2 = new Column ("tc2");
		model.addElement(tableauColumn2);

		tableauColumn3 = new Column ("tc3");
		model.addElement(tableauColumn3);

		tableauColumn4 = new Column ("tc4");
		model.addElement(tableauColumn4);

		//create and add the reserve piles
		reservePile1 = new Pile ("rp1");
		model.addElement (reservePile1);

		reservePile2 = new Pile ("rp2");
		model.addElement (reservePile2);

		reservePile3 = new Pile ("rp3");
		model.addElement (reservePile3);

		reservePile4 = new Pile ("rp4");
		model.addElement (reservePile4);

		reservePile5 = new Pile ("rp5");
		model.addElement (reservePile5);

		reservePile6 = new Pile ("rp6");
		model.addElement (reservePile6);

		reservePile7 = new Pile ("rp7");
		model.addElement (reservePile7);

		reservePile8 = new Pile ("rp8");
		model.addElement (reservePile8);

		reservePile9 = new Pile ("rp9");
		model.addElement (reservePile9);

		reservePile10 = new Pile ("rp10");
		model.addElement (reservePile10);

		reservePile11 = new Pile ("rp11");
		model.addElement (reservePile11);

		reservePile12 = new Pile ("rp12");
		model.addElement (reservePile12);

		reservePile13 = new Pile ("rp13");
		model.addElement (reservePile13);

		//create and add the foundation piles
		foundationPile1 = new Pile ("fp1");
		model.addElement (foundationPile1);

		foundationPile2 = new Pile ("fp2");
		model.addElement (foundationPile2);

		foundationPile3 = new Pile ("fp3");
		model.addElement (foundationPile3);

		foundationPile4 = new Pile ("fp4");
		model.addElement (foundationPile4);

		foundationPile5 = new Pile ("fp5");
		model.addElement (foundationPile5);

		foundationPile6 = new Pile ("fp6");
		model.addElement (foundationPile6);

		foundationPile7 = new Pile ("fp7");
		model.addElement (foundationPile7);

		foundationPile8 = new Pile ("fp8");
		model.addElement (foundationPile8);
	}

	//initializes the View with
	private void initializeView() {
		//get card images
		CardImages ci = getCardImages();

		//set variables for width, height, and overlay
		int w = ci.getWidth();
		int h = ci.getHeight();
		int delta = ci.getOverlap();

		//add widget for Ace Hearts Foundation Pile
		AHStack = new PileView(foundationPile1);
		AHStack.setBounds (50+w,511, w, h);
		container.addWidget (AHStack);

		//add widget for Ace Spades Foundation Pile
		ASStack = new PileView(foundationPile2);
		ASStack.setBounds (60+2*w,511, w, h);
		container.addWidget (ASStack);

		//add widget for Ace Diamonds Foundation Pile
		ADStack = new PileView(foundationPile3);
		ADStack.setBounds (70+3*w,511, w, h);
		container.addWidget (ADStack);

		//add widget for Ace Clubs Foundation Pile
		ACStack = new PileView(foundationPile4);
		ACStack.setBounds (80+4*w,511, w, h);
		container.addWidget (ACStack);

		//add widget for King Hearts Foundation Pile
		KHStack = new PileView(foundationPile5);
		KHStack.setBounds (120+5*w,511, w, h);
		container.addWidget (KHStack);

		//add widget for King Spades Foundation Pile
		KSStack = new PileView(foundationPile6);
		KSStack.setBounds (130+6*w,511, w, h);
		container.addWidget (KSStack);

		//add widget for King Diamonds Foundation Pile
		KDStack = new PileView(foundationPile7);
		KDStack.setBounds (140+7*w,511, w, h);
		container.addWidget (KDStack);

		//add widget for King Clubs Foundation Pile
		KCStack = new PileView(foundationPile8);
		KCStack.setBounds (150+8*w,511, w, h);
		container.addWidget (KCStack);

		//add widget for Ace Reserve Pile
		RAStack = new PileView(reservePile1);
		RAStack.setBounds (10,491, w, h);
		container.addWidget (RAStack);

		//add widget for Two Reserve Pile
		R2Stack = new PileView(reservePile2);
		R2Stack.setBounds (65,481-h, w, h);
		container.addWidget (R2Stack);

		//add widget for Three Reserve Pile
		R3Stack = new PileView(reservePile3);
		R3Stack.setBounds (120,471-2*h, w, h);
		container.addWidget (R3Stack);

		//add widget for Four Reserve Pile
		R4Stack = new PileView(reservePile4);
		R4Stack.setBounds (175,461-3*h, w, h);
		container.addWidget (R4Stack);

		//add widget for Five Reserve Pile
		R5Stack = new PileView(reservePile5);
		R5Stack.setBounds (230,451-4*h, w, h);
		container.addWidget (R5Stack);

		//add widget for Six Reserve Pile
		R6Stack = new PileView(reservePile6);
		R6Stack.setBounds (250+w, 420-4*h, w, h);
		container.addWidget (R6Stack);

		//add widget for Seven Reserve Pile
		R7Stack = new PileView(reservePile7);
		R7Stack.setBounds (280+2*w,400-4*h, w, h);
		container.addWidget (R7Stack);

		//add widget for Eight Reserve Pile
		R8Stack = new PileView(reservePile8);
		R8Stack.setBounds (8*w-50,420-4*h, w, h);
		container.addWidget (R8Stack);

		//add widget for Nine Reserve Pile
		R9Stack = new PileView(reservePile9);
		R9Stack.setBounds (9*w-30,451-4*h, w, h);
		container.addWidget (R9Stack);

		//add widget for Ten Reserve Pile
		R10Stack = new PileView(reservePile10);
		R10Stack.setBounds (9*w+25,461-3*h, w, h);
		container.addWidget (R10Stack);

		//add widget for Jack Reserve Pile
		RJStack = new PileView(reservePile11);
		RJStack.setBounds (9*w+80,471-2*h, w, h);
		container.addWidget (RJStack);

		//add widget for Queen Reserve Pile
		RQStack = new PileView(reservePile12);
		RQStack.setBounds (9*w+135,481-h, w, h);
		container.addWidget (RQStack);

		//add widget for King Reserve Pile
		RKStack = new PileView(reservePile13);
		RKStack.setBounds (9*w+190,491, w, h);
		container.addWidget (RKStack);

		//add widget for First Tableau Pile
		tv1 = new ColumnView(tableauColumn1);
		tv1.setBounds (312,140, w, h + 12*delta);
		container.addWidget (tv1);

		//add widget for Second Tableau Pile
		tv2 = new ColumnView(tableauColumn2);
		tv2.setBounds (317+w,140, w, h + 12*delta);
		container.addWidget (tv2);

		//add widget for Third Tableau Pile
		tv3 = new ColumnView(tableauColumn3);
		tv3.setBounds (322+2*w,140, w, h + 12*delta);
		container.addWidget (tv3);

		//add widget for Fourth Tableau Pile
		tv4 = new ColumnView(tableauColumn4);
		tv4.setBounds (327+3*w,140, w, h + 12*delta);
		container.addWidget (tv4);

		//add widget for the Score View
		scoreView = new IntegerView (getScore());
		scoreView.setBounds (765, 30, 160, 70);
		container.addWidget (scoreView);

		//add widget for the Cards Left View
		cardsLeftView = new IntegerView (getNumLeft());
		cardsLeftView.setFontSize (14);
		cardsLeftView.setBounds (765, 110, 160, 60);
		container.addWidget (cardsLeftView);
	}

	/** Code to launch solitaire variation. */
	public static void main (String []args) {
		// Seed is to ensure we get the same initial cards every time.
		// Here the seed is to "order by suit."
		GameWindow gw = Main.generateWindow(new Archway(), Deck.OrderBySuit);
	}

}
