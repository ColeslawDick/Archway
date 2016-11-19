package rpcole;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import heineman.klondike.MoveColumnMove;
import heineman.klondike.MoveWasteToPileMove;
import ks.common.model.BuildablePile;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.model.Stack;
import ks.common.view.BuildablePileView;
import ks.common.view.CardView;
import ks.common.view.ColumnView;
import ks.common.view.Container;
import ks.common.view.PileView;
import ks.common.view.Widget;

public class ArchwayReservePileController extends MouseAdapter{

	Pile rPile;
	Archway theGame;
	PileView rPileView;

	public ArchwayReservePileController(Archway game, Pile pile, PileView view){
		this.theGame = game;
		this.rPile = pile;
		this.rPileView = view;
	}

	//double click to see the next card in the Reserve Pile
	@Override
	public void mouseClicked(MouseEvent me){
		if(me.getClickCount() > 1){
			//attempting to view the next card in the Reserve Pile
			Move m = new ViewReserveCardsMove(rPile);
			if(m.doMove(theGame)){
				theGame.pushMove (m);     // Successful ViewReserveCards Move
				theGame.refreshWidgets(); // refresh updated widgets
			}
		}
	}

	//click and drag to move card to foundation
	@Override
	public void mousePressed(MouseEvent me) {
		//get container
		Container c = theGame.getContainer();

		//exit if pile has no cards
		if (rPile.count() == 0) {
			c.releaseDraggingObject(); //do I need this?? will it let me place a card in an empty pile?
			return;
		}

		// Get a card view to move from PileView
		CardView cardView = rPileView.getCardViewForTopCard(me);

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
		c.setDragSource (rPileView);

		//redraw the PileView even though nothing will change
		rPileView.redraw();
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

		//if there is a card, it should be returned to whence it came
		//has to be a stack because it had to come from a Pile or Column, both of which are Stacks
		Stack s = (Stack) fromWidget.getModelElement();
		Card card = (Card) c.getActiveDraggingObject().getModelElement();
		s.add(card);

		// release the dragging object, (container will reset dragSource)
		c.releaseDraggingObject();

		c.repaint();
	}
}
