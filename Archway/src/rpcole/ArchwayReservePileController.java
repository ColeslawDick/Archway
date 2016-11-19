package rpcole;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import ks.common.model.MultiDeck;
import ks.common.model.Pile;

public class ArchwayReservePileController extends MouseAdapter{
	MultiDeck deck;
	Pile pile;
	Archway theGame;
	
	public ArchwayReservePileController(Archway game, MultiDeck deck, Pile pile){
		this.deck = deck;
		this.theGame = game;
		this.pile = pile;
	}
	
	
}
