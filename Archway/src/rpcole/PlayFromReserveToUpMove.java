package rpcole;

import ks.common.games.Solitaire;
import ks.common.model.*;

public class PlayFromReserveToUpMove extends Move{

	Pile source, target;
	Card activeCard;
	
	public PlayFromReserveToUpMove(Pile source, Card card, Pile target){
		this.source = source;
		this.activeCard = card;
		this.target = target;
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
		game.updateScore(1);//add one to score
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
		int cs = target.peek(target.count()).getSuit();
		int cr = target.peek(target.count()).getRank();
		if(activeCard.equals(new Card(cr+1, cs))){
			return true;
		}
		return false;
	}

}
