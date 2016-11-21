package rpcole;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ks.common.model.Card;
import ks.common.model.Stack;
import ks.common.view.Container;
import ks.common.view.IntegerView;
import ks.common.view.Widget;

public class ArchwayScoreViewController extends MouseAdapter{

	Archway theGame;
	IntegerView scoreView;

	public ArchwayScoreViewController(Archway game, IntegerView score){
		this.scoreView = score;
		this.theGame = game;
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

		//have released a card here and it is a proper card
		Stack s = (Stack) fromWidget.getModelElement();
		Card card = (Card) c.getActiveDraggingObject().getModelElement();
		s.add(card);

		// release the dragging object, (container will reset dragSource)
		c.releaseDraggingObject();

		c.repaint();
	}
}
