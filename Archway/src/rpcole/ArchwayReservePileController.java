package rpcole;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ks.common.model.Move;
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

	@Override
	public void mouseClicked(MouseEvent me){
		//System.out.println("took press");
		//attempting to view the next card in the Reserve Pile
		Move m = new ViewReserveCardsMove(pile);
		//System.out.println("made new move");
		if(m.doMove(theGame)){
			//System.out.println("new move ran");
			theGame.pushMove (m);     // Successful ViewReserveCards Move
			//System.out.println("new move pushed");
			theGame.refreshWidgets(); // refresh updated widgets
			//System.out.println("repaint");
		}
	}
}
