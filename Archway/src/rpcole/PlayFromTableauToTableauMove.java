package rpcole;

import ks.common.games.Solitaire;
import ks.common.model.*;

public class PlayFromTableauToTableauMove extends Move{

	Column source, target;
	Card activeCard;
	
	public PlayFromTableauToTableauMove(Column fromColumn, Card card, Column toColumn){
		this.source = fromColumn;
		this.activeCard = card;
		this.target = toColumn;
	}
	
	@Override
	public boolean doMove(Solitaire game) {
		if (!valid(game)){//invalid move
			return false; 
		}
		if (activeCard != null) {//has an active card
			target.add(activeCard);
		} else { //does not have active card
			target.add(source.get());
		}
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		Card c = target.get();
		source.add(c);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		if(target.count()==0){
			return true;
		}
		return false;
	}

}
