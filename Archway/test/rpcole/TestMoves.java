package rpcole;

import java.awt.event.MouseEvent;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.launcher.Main;
import ks.tests.model.ModelFactory;

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
		assertEquals(0,archway.getScoreValue());
		//check undo ran
		assertTrue(m.undo(archway));
		//check that top card is now on the bottom and everything else has been shifted up
		assertTrue(archway.reservePile1.toString().equals("[Pile:rp1:AC,AS,AH,AD]"));
		//check undo ran
		assertTrue(m.undo(archway));
		//check that top card is now on the bottom and everything else has been shifted up
		assertTrue(archway.reservePile1.toString().equals("[Pile:rp1:AS,AH,AD,AC]"));
		assertEquals(0,archway.getScoreValue());
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
		assertEquals(1,archway.getScoreValue());
		//redo, should fail
		assertFalse(m.doMove(archway));
		//check nothing changed
		assertTrue(archway.foundationPile4.toString().equals("[Pile:fp4:AC,2C]"));
		assertTrue(archway.reservePile2.toString().equals("[Pile:rp2:2S,2H,2D]"));
		assertEquals(1,archway.getScoreValue());
		//check undo ran
		assertTrue(m.undo(archway));
		//check back at initial values
		assertTrue(archway.reservePile2.toString().equals("[Pile:rp2:2S,2H,2D,2C]"));
		assertTrue(archway.foundationPile4.toString().equals("[Pile:fp4:AC]"));
		assertEquals(0,archway.getScoreValue());
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
		assertEquals(1,archway.getScoreValue());
		//create FromReserveToUp Move
		Move m1 = new PlayFromReserveToUpMove(archway.reservePile3, archway.reservePile3.get(), archway.foundationPile4);
		//check that the move ran
		assertTrue(m1.doMove(archway));
		//check that top card from reserve pile is now top card of foundation and is gone from reserve
		assertTrue(archway.foundationPile4.toString().equals("[Pile:fp4:AC,2C,3C]"));
		assertTrue(archway.reservePile2.toString().equals("[Pile:rp2:2S,2H,2D]"));
		assertTrue(archway.reservePile3.toString().equals("[Pile:rp3:3S,3H,3D]"));
		assertEquals(2,archway.getScoreValue());
		//create FromReserveToDown Move
		Move m2 = new PlayFromReserveToDownMove(archway.reservePile12, archway.reservePile12.get(), archway.foundationPile8);
		//check that the move ran
		assertTrue(m2.doMove(archway));
		//check that top card from reserve pile is now top card of foundation and is gone from reserve
		assertTrue(archway.foundationPile8.toString().equals("[Pile:fp8:KC,QC]"));
		assertTrue(archway.reservePile12.toString().equals("[Pile:rp12:QH,QD]"));
		assertEquals(3,archway.getScoreValue());
		//check undo
		assertTrue(m2.undo(archway));
		//check that top card from reserve pile is now top card of foundation and is gone from reserve
		assertTrue(archway.foundationPile8.toString().equals("[Pile:fp8:KC]"));
		assertTrue(archway.reservePile12.toString().equals("[Pile:rp12:QH,QD,QC]"));
		assertEquals(2,archway.getScoreValue());
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
		assertEquals(1,archway.getScoreValue());
		//try second move
		Move m1 = new PlayFromTableauToFoundationDownMove(archway.tableauColumn3, archway.tableauColumn3.get(), archway.foundationPile6);
		assertTrue(m1.doMove(archway));
		//check that top card from tableau column is now top card of foundation and is gone from tableau
		assertTrue(archway.foundationPile6.toString().equals("[Pile:fp6:KS,QS,JS]"));
		assertTrue(archway.tableauColumn2.toString().equals("[Column:tc2:JS,7S,3S,10H,6H,2H,9D,5D,QC,8C,4C]"));
		assertTrue(archway.tableauColumn3.toString().equals("[Column:tc3:10S,6S,2S,9H,5H,QD,8D,4D,JC,7C,3C]"));
		assertEquals(2,archway.getScoreValue());
		//test undo
		assertTrue(m1.undo(archway));
		assertTrue(archway.foundationPile6.toString().equals("[Pile:fp6:KS,QS]"));
		assertTrue(archway.tableauColumn2.toString().equals("[Column:tc2:JS,7S,3S,10H,6H,2H,9D,5D,QC,8C,4C]"));
		assertEquals(1,archway.getScoreValue());
	}

	public void testTableauToFoundationUp(){
		ModelFactory.init(archway.foundationPile2, "AS 2S 3S 4S 5S 6S 7S 8S 9S");
		assertEquals ("9S", archway.foundationPile2.peek().toString());
		//create FromReserveToUp Move
		Move m = new PlayFromTableauToFoundationUpMove(archway.tableauColumn4, archway.tableauColumn4.get(), archway.foundationPile2);
		//check that the move ran
		assertTrue(m.doMove(archway));
		//check that top card from tableau column is now top card of foundation and is gone from tableau
		assertTrue(archway.foundationPile2.toString().equals("[Pile:fp2:AS,2S,3S,4S,5S,6S,7S,8S,9S,10S]"));
		assertTrue(archway.tableauColumn4.toString().equals("[Column:tc4:9S,5S,QH,8H,4H,JD,7D,3D,10C,6C,2C]"));
		assertEquals(1,archway.getScoreValue());
		//try second move
		Move m1 = new PlayFromTableauToFoundationUpMove(archway.tableauColumn3, archway.tableauColumn3.get(), archway.foundationPile2);
		assertTrue(m1.doMove(archway));
		//check that top card from tableau column is now top card of foundation and is gone from tableau
		assertTrue(archway.foundationPile2.toString().equals("[Pile:fp2:AS,2S,3S,4S,5S,6S,7S,8S,9S,10S,JS]"));
		assertTrue(archway.tableauColumn4.toString().equals("[Column:tc4:9S,5S,QH,8H,4H,JD,7D,3D,10C,6C,2C]"));
		assertTrue(archway.tableauColumn3.toString().equals("[Column:tc3:10S,6S,2S,9H,5H,QD,8D,4D,JC,7C,3C]"));
		assertEquals(2,archway.getScoreValue());
		//test undo
		assertTrue(m1.undo(archway));
		assertTrue(archway.foundationPile2.toString().equals("[Pile:fp2:AS,2S,3S,4S,5S,6S,7S,8S,9S,10S]"));
		assertTrue(archway.tableauColumn4.toString().equals("[Column:tc4:9S,5S,QH,8H,4H,JD,7D,3D,10C,6C,2C]"));
		assertTrue(archway.tableauColumn3.toString().equals("[Column:tc3:10S,6S,2S,9H,5H,QD,8D,4D,JC,7C,3C,JS]"));
		assertEquals(1,archway.getScoreValue());
		//test undo
		assertTrue(m.undo(archway));
		assertTrue(archway.foundationPile2.toString().equals("[Pile:fp2:AS,2S,3S,4S,5S,6S,7S,8S,9S]"));
		assertTrue(archway.tableauColumn4.toString().equals("[Column:tc4:9S,5S,QH,8H,4H,JD,7D,3D,10C,6C,2C,10S]"));
		assertTrue(archway.tableauColumn3.toString().equals("[Column:tc3:10S,6S,2S,9H,5H,QD,8D,4D,JC,7C,3C,JS]"));
		assertEquals(0,archway.getScoreValue());
	}

	public void testFoundationsMove(){
		//set up foundation piles and check them
		ModelFactory.init(archway.foundationPile2, "AS 2S 3S 4S 5S 6S 7S 8S 9S");
		ModelFactory.init(archway.foundationPile6, "KS QS JS 10S");
		assertEquals ("[Pile:fp2:AS,2S,3S,4S,5S,6S,7S,8S,9S]", archway.foundationPile2.toString());
		assertEquals ("[Pile:fp6:KS,QS,JS,10S]", archway.foundationPile6.toString());
		//create FoundationtoUp Move
		Move m = new PlayFromFoundationToUpMove(archway.foundationPile6, archway.foundationPile6.get(), archway.foundationPile2);
		//do toUp Move
		assertTrue(m.doMove(archway));
		//check state is correct
		assertEquals ("[Pile:fp2:AS,2S,3S,4S,5S,6S,7S,8S,9S,10S]", archway.foundationPile2.toString());
		assertEquals ("[Pile:fp6:KS,QS,JS]", archway.foundationPile6.toString());
		//undo toUp Move
		assertTrue(m.undo(archway));
		//check state is correct
		assertEquals ("[Pile:fp2:AS,2S,3S,4S,5S,6S,7S,8S,9S]", archway.foundationPile2.toString());
		assertEquals ("[Pile:fp6:KS,QS,JS,10S]", archway.foundationPile6.toString());
		//create FoundationtoUp Move
		Move m1 = new PlayFromFoundationToDownMove(archway.foundationPile2, archway.foundationPile2.get(), archway.foundationPile6);
		//do toDown Move
		assertTrue(m1.doMove(archway));
		//check state is correct
		assertEquals ("[Pile:fp2:AS,2S,3S,4S,5S,6S,7S,8S]", archway.foundationPile2.toString());
		assertEquals ("[Pile:fp6:KS,QS,JS,10S,9S]", archway.foundationPile6.toString());
		//undo toDown Move
		assertTrue(m1.undo(archway));
		//check state is correct
		assertEquals ("[Pile:fp2:AS,2S,3S,4S,5S,6S,7S,8S,9S]", archway.foundationPile2.toString());
		assertEquals ("[Pile:fp6:KS,QS,JS,10S]", archway.foundationPile6.toString());
	}

	public void testTableauToTableau() {
		//set up tableau piles and check them
		ModelFactory.init(archway.tableauColumn2, "");
		assertEquals ("[Column:tc2:<empty>]", archway.tableauColumn2.toString());
		assertTrue(archway.tableauColumn3.toString().equals("[Column:tc3:10S,6S,2S,9H,5H,QD,8D,4D,JC,7C,3C,JS]"));

		//create PlayFromTableauToTableau Move
		Move m = new PlayFromTableauToTableauMove(archway.tableauColumn3, archway.tableauColumn3.get(), archway.tableauColumn2);

		//do toDown Move
		assertTrue(m.doMove(archway));
		//check card was moved
		assertEquals ("[Column:tc2:JS]", archway.tableauColumn2.toString());
		assertTrue(archway.tableauColumn3.toString().equals("[Column:tc3:10S,6S,2S,9H,5H,QD,8D,4D,JC,7C,3C]"));

		//undo move
		assertTrue(m.undo(archway));
		//check is undone
		assertEquals ("[Column:tc2:<empty>]", archway.tableauColumn2.toString());
		assertTrue(archway.tableauColumn3.toString().equals("[Column:tc3:10S,6S,2S,9H,5H,QD,8D,4D,JC,7C,3C,JS]"));
	}
}
