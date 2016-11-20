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

	public void testReserveToFoundation(){
		//check if initial state is what we think
		assertTrue(archway.reservePile2.toString().equals("[Pile:rp2:2S,2H,2D,2C]"));
		assertTrue(archway.foundationPile4.toString().equals("[Pile:fp4:AC]"));
		//create FromReserveToUp Move
		Move m = new PlayFromReserveToUpMove(archway.reservePile2, archway.reservePile2.get(), archway.foundationPile4);
		//check that the move ran
		assertTrue(m.doMove(archway));
		//check that top card from reserve pile is now top card of foundation and is gone from reserve
		assertTrue(archway.foundationPile4.toString().equals("[Pile:fp4:AC,2C]"));
		assertTrue(archway.reservePile2.toString().equals("[Pile:rp2:2S,2H,2D]"));
		assertTrue(archway.getScoreValue()==1);
		//redo
		assertFalse(m.doMove(archway));
		assertTrue(archway.foundationPile4.toString().equals("[Pile:fp4:AC,2C]"));
		assertTrue(archway.reservePile2.toString().equals("[Pile:rp2:2S,2H,2D]"));
		assertTrue(archway.getScoreValue()==1);
		//check undo ran
		assertTrue(m.undo(archway));
		//check back at initial values
		assertTrue(archway.reservePile2.toString().equals("[Pile:rp2:2S,2H,2D,2C]"));
		assertTrue(archway.foundationPile4.toString().equals("[Pile:fp4:AC]"));
		assertTrue(archway.getScoreValue()==0);
	}

	public void testReserveToFoundation2(){
		//check if initial state is what we think
		assertTrue(archway.reservePile2.toString().equals("[Pile:rp2:2S,2H,2D,2C]"));
		assertTrue(archway.foundationPile4.toString().equals("[Pile:fp4:AC]"));
		//create FromReserveToUp Move
		Move m = new PlayFromReserveToUpMove(archway.reservePile2, archway.reservePile2.get(), archway.foundationPile4);
		//check that the move ran
		assertTrue(m.doMove(archway));
		//check that top card from reserve pile is now top card of foundation and is gone from reserve
		assertTrue(archway.foundationPile4.toString().equals("[Pile:fp4:AC,2C]"));
		assertTrue(archway.reservePile2.toString().equals("[Pile:rp2:2S,2H,2D]"));
		assertTrue(archway.getScoreValue()==1);
		//create FromReserveToUp Move
		Move m1 = new PlayFromReserveToUpMove(archway.reservePile3, archway.reservePile3.get(), archway.foundationPile4);
		//check that the move ran
		assertTrue(m1.doMove(archway));
		//check that top card from reserve pile is now top card of foundation and is gone from reserve
		assertTrue(archway.foundationPile4.toString().equals("[Pile:fp4:AC,2C,3C]"));
		assertTrue(archway.reservePile2.toString().equals("[Pile:rp2:2S,2H,2D]"));
		assertTrue(archway.reservePile3.toString().equals("[Pile:rp3:3S,3H,3D]"));
		assertTrue(archway.getScoreValue()==2);
	}

	public void testTableauToFoundation(){
		//check if initial state is what we think
		assertTrue(archway.tableauColumn2.toString().equals("[Column:tc2:JS,7S,3S,10H,6H,2H,9D,5D,QC,8C,4C,QS]"));
		assertTrue(archway.foundationPile6.toString().equals("[Pile:fp6:KS]"));
		//create FromReserveToUp Move
		Move m = new PlayFromTableauToFoundationDownMove(archway.tableauColumn2, archway.tableauColumn2.get(), archway.foundationPile6);
		//check that the move ran
		assertTrue(m.doMove(archway));
		//check that top card from tableau column is now top card of foundation and is gone from tableau
		assertTrue(archway.foundationPile6.toString().equals("[Pile:fp6:KS,QS]"));
		assertTrue(archway.tableauColumn2.toString().equals("[Column:tc2:JS,7S,3S,10H,6H,2H,9D,5D,QC,8C,4C]"));
		assertTrue(archway.getScoreValue()==1);	
		//try second move
		Move m1 = new PlayFromTableauToFoundationDownMove(archway.tableauColumn3, archway.tableauColumn3.get(), archway.foundationPile6);
		assertTrue(m1.doMove(archway));
		//check that top card from tableau column is now top card of foundation and is gone from tableau
		assertTrue(archway.foundationPile6.toString().equals("[Pile:fp6:KS,QS,JS]"));
		assertTrue(archway.tableauColumn2.toString().equals("[Column:tc2:JS,7S,3S,10H,6H,2H,9D,5D,QC,8C,4C]"));
		assertTrue(archway.tableauColumn3.toString().equals("[Column:tc3:10S,6S,2S,9H,5H,QD,8D,4D,JC,7C,3C]"));
		assertTrue(archway.getScoreValue()==2);
		//test undo
		assertTrue(m1.undo(archway));
		assertTrue(archway.foundationPile6.toString().equals("[Pile:fp6:KS,QS]"));
		assertTrue(archway.tableauColumn2.toString().equals("[Column:tc2:JS,7S,3S,10H,6H,2H,9D,5D,QC,8C,4C]"));
		assertTrue(archway.getScoreValue()==1);
	}
	
	
}
