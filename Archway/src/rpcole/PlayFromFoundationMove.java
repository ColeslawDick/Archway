package rpcole;

import ks.common.games.Solitaire;
import ks.common.model.*;

public class PlayFromFoundationMove extends Move{

	Pile source, target;
	Card activeCard;
	
	public PlayFromFoundationMove(Pile fromPile, Card card, Pile toPile){
		this.source = fromPile;
		this.activeCard = card;
		this.target = toPile;
	}
	
	@Override
	public boolean doMove(Solitaire game) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean undo(Solitaire game) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean valid(Solitaire game) {
		// TODO Auto-generated method stub
		return false;
	}

}
