package rpcole;

import ks.common.games.Solitaire;
import ks.common.model.*;

public class PlayFromReserveToDownMove extends Move{

	Pile source, target;
	Card activeCard;

	public PlayFromReserveToDownMove(Pile source, Card card, Pile target){
		this.source = source;
		this.activeCard = card;
		this.target = target;
	}

	public Pile getSource(){
		return this.source;
	}
	
	public Pile getTarget(){
		return this.target;
	}
	
	public Card getActiveCard(){
		return this.activeCard;
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
		int cs = target.peek(target.count()-1).getSuit();
		int cr = target.peek(target.count()-1).getRank();
		if(activeCard.equals(new Card(cr-1, cs))){
			return true;
		}
		return false;
	}

}
