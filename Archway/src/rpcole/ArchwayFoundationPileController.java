package rpcole;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ks.common.model.Card;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.model.Column;
import ks.common.model.Stack;
import ks.common.view.CardView;
import ks.common.view.Container;
import ks.common.view.PileView;
import ks.common.view.Widget;

public class ArchwayFoundationPileController extends MouseAdapter{

	Pile fPile;
	Archway theGame;
	PileView fPileView;

	public ArchwayFoundationPileController(Archway game, Pile pile, PileView view){
		this.theGame = game;
		this.fPile = pile;
		this.fPileView = view;
	}

	//click and drag to move card to another foundation
	@Override
	public void mousePressed(MouseEvent me) {
		//get container
		Container c = theGame.getContainer();

		//exit if pile has no cards
		if (fPile.count() == 0) {
			c.releaseDraggingObject();
			return;
		}

		// Get a CardView to move from PileView
		CardView cardView = fPileView.getCardViewForTopCard(me);

		// exit if has an invalid selection
		if (cardView == null) {
			c.releaseDraggingObject();
			return;
		}

		//check if anything is being dragged and return message if so
		Widget w = c.getActiveDraggingObject();
		if (w != Container.getNothingBeingDragged()) {
			System.err.println ("WastePileController::mousePressed(): Unexpectedly encountered a Dragging Object during a Mouse press.");
			return;
		}

		//pass the container the widget and the location of the mouse event
		c.setActiveDraggingObject (cardView, me);

		//set source of drag
		c.setDragSource (fPileView);

		//redraw the PileView even though nothing will change
		fPileView.redraw();
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		//get the container
		Container c = theGame.getContainer();

		//return if no card being dragged
		Widget w = c.getActiveDraggingObject();
		if (w == Container.getNothingBeingDragged()) {
			c.releaseDraggingObject();		
			return;
		}

		//must have a dragged object which must be a card
		//return with error if widget in container has no source
		Widget fromWidget = c.getDragSource();
		if (fromWidget == null) {
			System.err.println ("ArchwayReservePileController::mouseReleased(): somehow no dragSource in container.");
			c.releaseDraggingObject();
			return;
		}

		//if there is a card, it either gets added to the foundation pile, or sent back from whence it came
		//has to be a stack because it had to come from a Pile or Column, both of which are Stacks
		Stack s = (Stack) fromWidget.getModelElement();
		Card card = (Card) c.getActiveDraggingObject().getModelElement();
		int cs = fPile.peek(fPile.count()).getSuit();
		int cr = fPile.peek(fPile.count()).getRank();

		if((fPile == theGame.foundationPile1 || fPile == theGame.foundationPile2 || 
				fPile == theGame.foundationPile3 || fPile == theGame.foundationPile1) 
				&& card.equals(new Card(cr+1, cs))){ //target Pile is a Up Foundation and is valid
			//if from Pile, must be from Foundation or Reserve Pile
			if(fromWidget instanceof PileView){
				if(s == theGame.foundationPile5 || s == theGame.foundationPile6 || 
						s == theGame.foundationPile7 || s == theGame.foundationPile8){ //is from a Foundation Pile
					//attempting to move the card to the Foundation Pile
					Move m = new PlayFromFoundationToUpMove((Pile) s, card, fPile);
					if(m.doMove(theGame)){
						theGame.pushMove (m);     // Successful ViewReserveCards Move
						theGame.refreshWidgets(); // refresh updated widgets
					}
				} else { //must be Reserve Pile
					//attempting to move the card to the Foundation Pile
					Move m = new PlayFromReserveToUpMove((Pile) s, card, fPile);
					if(m.doMove(theGame)){
						theGame.pushMove (m);     // Successful ViewReserveCards Move
						theGame.refreshWidgets(); // refresh updated widgets
					}
				}
			} else { //must be from Tableau Column
				//attempting to move the card to the Foundation Pile
				Move m = new PlayFromTableauToFoundationUpMove((Column) s, card, fPile);
				if(m.doMove(theGame)){
					theGame.pushMove (m);     // Successful ViewReserveCards Move
					theGame.refreshWidgets(); // refresh updated widgets
				}
			}
		} else if ((fPile == theGame.foundationPile5 || fPile == theGame.foundationPile6 || 
				fPile == theGame.foundationPile7 || fPile == theGame.foundationPile8) 
				&& card.equals(new Card(cr-1, cs))){ //target Pile is a Down Foundation and is valid
			//if from Pile, must be from Foundation or Reserve Pile
			if(fromWidget instanceof PileView){
				if(s == theGame.foundationPile1 || s == theGame.foundationPile2 || 
						s == theGame.foundationPile3 || s == theGame.foundationPile1){ //is from a Foundation Pile
					//attempting to move the card to the Foundation Pile
					Move m = new PlayFromFoundationToDownMove((Pile) s, card, fPile);
					if(m.doMove(theGame)){
						theGame.pushMove (m);     // Successful ViewReserveCards Move
						theGame.refreshWidgets(); // refresh updated widgets
					}
				} else { //must be Reserve Pile
					//attempting to move the card to the Foundation Pile
					Move m = new PlayFromReserveToDownMove((Pile) s, card, fPile);
					if(m.doMove(theGame)){
						theGame.pushMove (m);     // Successful ViewReserveCards Move
						theGame.refreshWidgets(); // refresh updated widgets
					}
				}
			} else { //must be from Tableau Column
				//attempting to move the card to the Foundation Pile
				Move m = new PlayFromTableauToFoundationDownMove((Column) s, card, fPile);
				if(m.doMove(theGame)){
					theGame.pushMove (m);     // Successful ViewReserveCards Move
					theGame.refreshWidgets(); // refresh updated widgets
				}
			}
		} else { // move is not valid
			s.add(card);
		}

		// release the dragging object, (container will reset dragSource)
		c.releaseDraggingObject();

		c.repaint();
	}
}

