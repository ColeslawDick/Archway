package rpcole;

import ks.common.games.Solitaire;
import ks.common.model.*;

public class PlayFromFoundationToDownMove extends Move{

	Pile source, target;
	Card activeCard;
	
	public PlayFromFoundationToDownMove(Pile fromPile, Card card, Pile toPile){
		this.source = fromPile;
		this.activeCard = card;
		this.target = toPile;
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
		int cs = target.peek(target.count()-1).getSuit();
		int cr = target.peek(target.count()-1).getRank();
		if(activeCard.equals(new Card(cr-1, cs))){
			return true;
		}
		return false;
	}

}

