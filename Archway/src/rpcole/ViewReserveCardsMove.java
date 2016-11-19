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
		for(int i = 0; i < source.count(); i++){
			//add top card from source to hidden pile
			Card c = source.get(); 
			p.add(c); 
		}
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
