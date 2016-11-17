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
	PileView R10Stack, RJStack, RQStack, AHStack, ACStack, ADStack, ASStack;
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
		return new Dimension (950, 700);
	}
	
	@Override
	public void initialize() {
		//initialize the model with random seed
		initializeModel(getSeed());
		//initialize the GUI
		initializeView();
		//initialize the controllers and attach them to the GUI
		//initializeControllers();
		
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
		AHStack.setBounds (50+w,471, w, h);
		container.addWidget (AHStack);
		
		//add widget for Ace Spades Foundation Pile
		ASStack = new PileView(foundationPile2);
		ASStack.setBounds (60+2*w,471, w, h);
		container.addWidget (ASStack);
		
		//add widget for Ace Diamonds Foundation Pile
		ADStack = new PileView(foundationPile3);
		ADStack.setBounds (70+3*w,471, w, h);
		container.addWidget (ADStack);
		
		//add widget for Ace Clubs Foundation Pile
		ACStack = new PileView(foundationPile4);
		ACStack.setBounds (80+4*w,471, w, h);
		container.addWidget (ACStack);
		
		//add widget for King Hearts Foundation Pile
		KHStack = new PileView(foundationPile5);
		KHStack.setBounds (120+5*w,471, w, h);
		container.addWidget (KHStack);
		
		//add widget for King Spades Foundation Pile
		KSStack = new PileView(foundationPile6);
		KSStack.setBounds (130+6*w,471, w, h);
		container.addWidget (KSStack);
		
		//add widget for King Diamonds Foundation Pile
		KDStack = new PileView(foundationPile7);
		KDStack.setBounds (140+7*w,471, w, h);
		container.addWidget (KDStack);
		
		//add widget for King Clubs Foundation Pile
		KCStack = new PileView(foundationPile8);
		KCStack.setBounds (150+8*w,471, w, h);
		container.addWidget (KCStack);
	}
	
	/** Code to launch solitaire variation. */
	public static void main (String []args) {
		// Seed is to ensure we get the same initial cards every time.
		// Here the seed is to "order by suit."
		GameWindow gw = Main.generateWindow(new Archway(), Deck.OrderBySuit);
		
	}
	
}
