package rpcole;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Stack;
import ks.common.view.CardView;
import ks.common.view.ColumnView;
import ks.common.view.Container;
import ks.common.view.Widget;

public class ArchwayTableauColumnController extends MouseAdapter{
	Column tColumn;
	Archway theGame;
	ColumnView tColumnView;

	public ArchwayTableauColumnController(Archway game, Column column, ColumnView view){
		this.theGame = game;
		this.tColumn = column;
		this.tColumnView = view;
	}

	//click and drag to move card to foundation
	@Override
	public void mousePressed(MouseEvent me) {
		//get container
		Container c = theGame.getContainer();

		//exit if pile has no cards
		if (tColumn.count() == 0) {
			c.releaseDraggingObject();
			return;
		}

		// Get a card view to move from PileView
		CardView cardView = tColumnView.getCardViewForTopCard(me);

		// exit if has an invalid selection
		if (cardView == null) {
			c.releaseDraggingObject();
			return;
		}

		//check if anything is being dragged and return message if so
		Widget w = c.getActiveDraggingObject();
		if (w != Container.getNothingBeingDragged()) {
			System.err.println ("ArchwayTableauColumnController::mousePressed(): Unexpectedly encountered a Dragging Object during a Mouse press.");
			return;
		}

		//pass the container the widget and the location of the mouse event
		c.setActiveDraggingObject (cardView, me);

		//set source of drag
		c.setDragSource (tColumnView);

		//redraw the PileView even though nothing will change
		tColumnView.redraw();
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

		//if there is a card and it is not from a tableau (and this tableau is not empty) 
		//it should be returned to whence it came
		//has to be a stack because it had to come from a Pile or Column, both of which are Stacks
		Stack s = (Stack) fromWidget.getModelElement();
		Card card = (Card) c.getActiveDraggingObject().getModelElement();
		if(tColumn.count()==0 && (s == theGame.tableauColumn1 || s == theGame.tableauColumn2 || s == theGame.tableauColumn3
				|| s == theGame.tableauColumn4)){ //tableau is empty and the card came from another tableau
			//attempting to move the card to the Foundation Pile
			Move m = new PlayFromTableauToTableauMove((Column) s, card, tColumn);
			if(m.doMove(theGame)){
				theGame.pushMove (m);     // Successful ViewReserveCards Move
				theGame.refreshWidgets(); // refresh updated widgets
			}
		} else { //conditions for move were not met
			s.add(card);
		}


		// release the dragging object, (container will reset dragSource)
		c.releaseDraggingObject();

		c.repaint();
	}
}
