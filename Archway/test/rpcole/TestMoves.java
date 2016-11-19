package rpcole;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.launcher.Main;

public class TestMoves extends TestCase {
	Archway archway;
	GameWindow gw;

	@Override
	protected void setUp() {
		archway = new Archway();
		gw = Main.generateWindow(archway, Deck.OrderBySuit);
	}

	@Override
	protected void tearDown() { 
		gw.dispose();
	}

	public void testViewReserveCardsMove(){	
		//check if initial state is what we think
		assertTrue(archway.reservePile1.toString().equals("[Pile:rp1:AS,AH,AD,AC]"));
		//create ViewReserveCard Move
		Move m = new ViewReserveCardsMove(archway.reservePile1);
		//check that move ran
		assertTrue(m.doMove(archway));
		//check that top card is now on the bottom and everything else has been shifted up
		assertTrue(archway.reservePile1.toString().equals("[Pile:rp1:AC,AS,AH,AD]"));
		//check that move ran
		assertTrue(m.doMove(archway));
		//check that top card is now on the bottom and everything else has been shifted up
		assertTrue(archway.reservePile1.toString().equals("[Pile:rp1:AD,AC,AS,AH]"));
		//check undo ran
		assertTrue(m.undo(archway));
		//check that top card is now on the bottom and everything else has been shifted up
		assertTrue(archway.reservePile1.toString().equals("[Pile:rp1:AC,AS,AH,AD]"));
		//check undo ran
		assertTrue(m.undo(archway));
		//check that top card is now on the bottom and everything else has been shifted up
		assertTrue(archway.reservePile1.toString().equals("[Pile:rp1:AS,AH,AD,AC]"));
	}
}
