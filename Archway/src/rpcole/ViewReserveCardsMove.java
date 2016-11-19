package rpcole;

import ks.common.games.Solitaire;
import ks.common.model.*;

public class ViewReserveCardsMove extends Move{

	Pile source;
	
	public ViewReserveCardsMove(Pile pile){
		this.source = pile;
	}
	
	//places the top card on the bottom of the pile and shows the second card in the pile
	@Override
	public boolean doMove(Solitaire game) {
		if(valid(game) == false){
			return false;
		}
		
		Pile p = new Pile(); //create hidden pile
		p.add(source.get()); //add the top card to the hidden pile
		p.push(source); //add the remaining cards in the reserve pile to the hidden pile from the bottom up
		source.removeAll(); //clear the source pile
		source.push(p); //add the cards in the hidden pile to the source pile
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		Pile p = new Pile(); //create hidden pile
		// must add stack from the bottom up...
		for (int i = 1; i < source.count(); i++) {
			p.add(source.peek (i));
		}
		p.add(source.peek(0));
		source.removeAll(); //clear source
		source.push(p); //add cards in hidden pile to source pile from the bottom up
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		if(source.count() > 0){
			return true;
		} else {
		return false;
		}
	}

}
