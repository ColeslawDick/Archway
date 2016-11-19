package rpcole;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.view.CardView;
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

	@Override
	public void mousePressed(MouseEvent me){
		//System.out.println("took press");
		//attempting to view the next card in the Reserve Pile
		Move m = new ViewReserveCardsMove(rPile);
		//System.out.println("made new move");
		if(m.doMove(theGame)){
			//System.out.println("new move ran");
			theGame.pushMove (m);     // Successful ViewReserveCards Move
			//System.out.println("new move pushed");
			theGame.refreshWidgets(); // refresh updated widgets
			//System.out.println("repaint");
		}
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		//get container
		Container c = theGame.getContainer();

		//exit if pile has no cards
		if (rPile.count() == 0) {
			c.releaseDraggingObject();
			return;
		}

		// Get a card view to move from PileView
		CardView cardView = rPileView.getCardViewForTopCard(me);

		// exit if has an invalid selection
		if (cardView == null) {
			c.releaseDraggingObject();
			return;
		}

		//check if anything is being dragged and retunr message if so
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
}
