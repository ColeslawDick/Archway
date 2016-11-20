package rpcole;

import ks.common.games.Solitaire;
import ks.common.model.*;

public class PlayFromTableauToFoundationUpMove extends Move{

	Column source;
	Pile target;
	Card activeCard;
	
	public PlayFromTableauToFoundationUpMove(Column fromColumn, Card card, Pile toPile){
		this.source = fromColumn;
		this.activeCard = card;
		this.target = toPile;
	}

	public Column getSource(){
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
		game.updateScore(-1);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		int cs = target.peek(target.count()-1).getSuit();
		int cr = target.peek(target.count()-1).getRank();
		if(activeCard.equals(new Card(cr+1, cs))){
			return true;
		}
		return false;
	}

}
