package rpcole;

import java.awt.event.MouseEvent;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;
import ks.tests.model.ModelFactory;

public class TestControllers extends KSTestCase{
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

	public void testViewReserveCard() {
		//check is initialized correctly
		assertEquals ("[Pile:rp4:4S,4H,4D,4C]", archway.reservePile4.toString()); 
		// create a double click mouse press at (0,0) within the R4Stack; should switch top card
		MouseEvent press = this.createDoubleClicked(archway, archway.R4Stack, 0, 0);
		archway.R4Stack.getMouseManager().handleMouseEvent(press);

		// moves second card to top of pile and first card to bottom
		assertEquals ("[Pile:rp4:4C,4S,4H,4D]", archway.reservePile4.toString()); 
	}

	public void testReserveToFoundationCard() {
		//check is initialized correctly
		assertEquals ("[Pile:rp2:2S,2H,2D,2C]", archway.reservePile2.toString()); 
		// create a double click mouse press at (0,0) within the R2Stack
		MouseEvent press = this.createPressed(archway, archway.R2Stack, 0, 0);
		archway.R2Stack.getMouseManager().handleMouseEvent(press);

		//create released on proper foundation pile
		MouseEvent release = this.createReleased(archway, archway.ACStack, 0, 0);
		archway.ACStack.getMouseManager().handleMouseEvent(release);

		// moves top card of reserve pile to top of foundation pile
		assertEquals ("[Pile:rp2:2S,2H,2D]", archway.reservePile2.toString()); 
		assertEquals("[Pile:fp4:AC,2C]",archway.foundationPile4.toString());
	}
	
	public void testReserveToFoundationCard2() {
		//check is initialized correctly
		assertEquals ("[Pile:rp12:QH,QD,QC]", archway.reservePile12.toString()); 
		// create a double click mouse press at (0,0) within the R12Stack
		MouseEvent press = this.createPressed(archway, archway.RQStack, 0, 0);
		archway.RQStack.getMouseManager().handleMouseEvent(press);

		//create released on proper foundation pile
		MouseEvent release = this.createReleased(archway, archway.KCStack, 0, 0);
		archway.KCStack.getMouseManager().handleMouseEvent(release);

		// moves top card of reserve pile to top of foundation pile
		assertEquals ("[Pile:rp12:QH,QD]", archway.reservePile12.toString()); 
		assertEquals("[Pile:fp8:KC,QC]",archway.foundationPile8.toString());
	}

	public void testNegativeReserveToFoundationCard() {
		//check is initialized correctly
		assertEquals ("[Pile:rp4:4S,4H,4D,4C]", archway.reservePile4.toString()); 
		// create a mouse press at (0,0) within the R4Stack
		MouseEvent press = this.createPressed(archway, archway.R4Stack, 0, 0);
		archway.R4Stack.getMouseManager().handleMouseEvent(press);

		//create released on the wrong foundation pile
		MouseEvent release = this.createReleased(archway, archway.ACStack, 0, 0);
		archway.ACStack.getMouseManager().handleMouseEvent(release);

		// nothing happens
		assertEquals ("[Pile:rp4:4S,4H,4D,4C]", archway.reservePile4.toString());
		assertEquals ("[Pile:rp2:2S,2H,2D,2C]", archway.reservePile2.toString()); 
		assertEquals("[Pile:fp4:AC]",archway.foundationPile4.toString());
	}

	public void testTableauToFoundationCard() {
		//check is initialized correctly
		assertEquals("[Column:tc2:JS,7S,3S,10H,6H,2H,9D,5D,QC,8C,4C,QS]",archway.tableauColumn2.toString());
		//create a mouse press at (31,286) within the tv2; press on bottom card
		MouseEvent press = this.createPressed(archway, archway.tv2, 31, 286);
		archway.tv2.getMouseManager().handleMouseEvent(press);

		//create released on proper foundation pile
		MouseEvent release = this.createReleased(archway, archway.KSStack, 0, 0);
		archway.KSStack.getMouseManager().handleMouseEvent(release);

		// moves top card of reserve pile to top of foundation pile
		assertEquals("[Column:tc2:JS,7S,3S,10H,6H,2H,9D,5D,QC,8C,4C]",archway.tableauColumn2.toString());
		assertEquals("[Pile:fp6:KS,QS]",archway.foundationPile6.toString());
	}

	public void testNegativeTableauToFoundationCard() {
		//check is initialized correctly
		assertEquals("[Column:tc3:10S,6S,2S,9H,5H,QD,8D,4D,JC,7C,3C,JS]",archway.tableauColumn3.toString());
		assertEquals("[Column:tc2:JS,7S,3S,10H,6H,2H,9D,5D,QC,8C,4C,QS]",archway.tableauColumn2.toString());
		//create a mouse press at (31,286) within the tv2; press on bottom card
		MouseEvent press = this.createPressed(archway, archway.tv3, 31, 286);
		archway.tv3.getMouseManager().handleMouseEvent(press);

		//create released on proper foundation pile
		MouseEvent release = this.createReleased(archway, archway.KSStack, 0, 0);
		archway.KSStack.getMouseManager().handleMouseEvent(release);

		// check nothing happened
		assertEquals("[Column:tc2:JS,7S,3S,10H,6H,2H,9D,5D,QC,8C,4C,QS]",archway.tableauColumn2.toString());
		assertEquals("[Column:tc3:10S,6S,2S,9H,5H,QD,8D,4D,JC,7C,3C,JS]",archway.tableauColumn3.toString());
		assertEquals("[Pile:fp6:KS]",archway.foundationPile6.toString());
	}

	public void testFoundationToFoundationCard() {
		//set up foundation piles and check them
		ModelFactory.init(archway.foundationPile2, "AS 2S 3S 4S 5S 6S 7S 8S 9S");
		ModelFactory.init(archway.foundationPile6, "KS QS JS 10S");
		assertEquals ("[Pile:fp2:AS,2S,3S,4S,5S,6S,7S,8S,9S]", archway.foundationPile2.toString());
		assertEquals ("[Pile:fp6:KS,QS,JS,10S]", archway.foundationPile6.toString());
		//create a mouse press at (0,0) within the ASStack
		MouseEvent press = this.createPressed(archway, archway.ASStack, 0, 0);
		archway.ASStack.getMouseManager().handleMouseEvent(press);

		//create released on proper foundation pile
		MouseEvent release = this.createReleased(archway, archway.KSStack, 0, 0);
		archway.KSStack.getMouseManager().handleMouseEvent(release);

		// moves top card of foundation pile to top of other foundation pile
		assertEquals ("[Pile:fp2:AS,2S,3S,4S,5S,6S,7S,8S]", archway.foundationPile2.toString());
		assertEquals ("[Pile:fp6:KS,QS,JS,10S,9S]", archway.foundationPile6.toString());
	}
	
	public void testFoundationToFoundationCard2() {
		//set up foundation piles and check them
		ModelFactory.init(archway.foundationPile2, "AS 2S 3S 4S 5S 6S 7S 8S 9S");
		ModelFactory.init(archway.foundationPile6, "KS QS JS 10S");
		assertEquals ("[Pile:fp2:AS,2S,3S,4S,5S,6S,7S,8S,9S]", archway.foundationPile2.toString());
		assertEquals ("[Pile:fp6:KS,QS,JS,10S]", archway.foundationPile6.toString());
		//create a mouse press at (0,0) within the ASStack
		MouseEvent press = this.createPressed(archway, archway.KSStack, 0, 0);
		archway.KSStack.getMouseManager().handleMouseEvent(press);

		//create released on proper foundation pile
		MouseEvent release = this.createReleased(archway, archway.ASStack, 0, 0);
		archway.ASStack.getMouseManager().handleMouseEvent(release);

		// moves top card of foundation pile to top of other foundation pile
		assertEquals ("[Pile:fp2:AS,2S,3S,4S,5S,6S,7S,8S,9S,10S]", archway.foundationPile2.toString());
		assertEquals ("[Pile:fp6:KS,QS,JS]", archway.foundationPile6.toString());
	}

	public void testNegativeFoundationToFoundationCard() {
		//set up foundation piles and check them
		ModelFactory.init(archway.foundationPile2, "AS 2S 3S 4S 5S 6S 7S 8S");
		ModelFactory.init(archway.foundationPile6, "KS QS JS 10S");
		assertEquals ("[Pile:fp2:AS,2S,3S,4S,5S,6S,7S,8S]", archway.foundationPile2.toString());
		assertEquals ("[Pile:fp6:KS,QS,JS,10S]", archway.foundationPile6.toString());
		//create a mouse press at (0,0) within the ASStack
		MouseEvent press = this.createPressed(archway, archway.ASStack, 0, 0);
		archway.ASStack.getMouseManager().handleMouseEvent(press);

		//create released on wrong foundation pile
		MouseEvent release = this.createReleased(archway, archway.KSStack, 0, 0);
		archway.KSStack.getMouseManager().handleMouseEvent(release);

		// check nothing happened
		assertEquals ("[Pile:fp2:AS,2S,3S,4S,5S,6S,7S,8S]", archway.foundationPile2.toString());
		assertEquals ("[Pile:fp6:KS,QS,JS,10S]", archway.foundationPile6.toString());
	}

	public void testTableauToTableauCard() {
		//set up tableau piles and check them
		ModelFactory.init(archway.tableauColumn2, "");
		assertEquals ("[Column:tc2:<empty>]", archway.tableauColumn2.toString());
		assertTrue(archway.tableauColumn3.toString().equals("[Column:tc3:10S,6S,2S,9H,5H,QD,8D,4D,JC,7C,3C,JS]"));
		//create a mouse press at (31,286) within the tv2; press on bottom card
		MouseEvent press = this.createPressed(archway, archway.tv3, 31, 286);
		archway.tv3.getMouseManager().handleMouseEvent(press);

		//create released on proper foundation pile
		MouseEvent release = this.createReleased(archway, archway.tv2, 0, 0);
		archway.tv2.getMouseManager().handleMouseEvent(release);

		// moves top card of foundation pile to top of other foundation pile
		assertEquals ("[Column:tc2:JS]", archway.tableauColumn2.toString());
		assertTrue(archway.tableauColumn3.toString().equals("[Column:tc3:10S,6S,2S,9H,5H,QD,8D,4D,JC,7C,3C]"));
	}
	
	public void testNegativeTableauToTableauCard() {
		//set up tableau piles and check them
		ModelFactory.init(archway.tableauColumn2, "JS");
		assertEquals ("[Column:tc2:JS]", archway.tableauColumn2.toString());
		assertTrue(archway.tableauColumn3.toString().equals("[Column:tc3:10S,6S,2S,9H,5H,QD,8D,4D,JC,7C,3C,JS]"));
		//create a mouse press at (31,286) within the tv2; press on bottom card
		MouseEvent press = this.createPressed(archway, archway.tv3, 31, 286);
		archway.tv3.getMouseManager().handleMouseEvent(press);

		//create released on proper foundation pile
		MouseEvent release = this.createReleased(archway, archway.tv2, 0, 0);
		archway.tv2.getMouseManager().handleMouseEvent(release);

		// moves top card of foundation pile to top of other foundation pile
		assertEquals ("[Column:tc2:JS]", archway.tableauColumn2.toString());
		assertTrue(archway.tableauColumn3.toString().equals("[Column:tc3:10S,6S,2S,9H,5H,QD,8D,4D,JC,7C,3C,JS]"));
	}
	
	public void testReleaseOnScoreViewCard() {
		//check is initialized correctly
		assertEquals("[Column:tc2:JS,7S,3S,10H,6H,2H,9D,5D,QC,8C,4C,QS]",archway.tableauColumn2.toString());
		//create a mouse press at (31,286) within the tv2; press on bottom card
		MouseEvent press = this.createPressed(archway, archway.tv2, 31, 286);
		archway.tv2.getMouseManager().handleMouseEvent(press);

		//create released on scoreView
		MouseEvent release = this.createReleased(archway, archway.scoreView, 0, 0);
		archway.scoreView.getMouseManager().handleMouseEvent(release);

		// check nothing happens
		assertEquals("[Column:tc2:JS,7S,3S,10H,6H,2H,9D,5D,QC,8C,4C,QS]",archway.tableauColumn2.toString());
	}
	
	public void testReleaseOnCardsLeftViewCard() {
		//check is initialized correctly
		assertEquals("[Column:tc2:JS,7S,3S,10H,6H,2H,9D,5D,QC,8C,4C,QS]",archway.tableauColumn2.toString());
		//create a mouse press at (31,286) within the tv2; press on bottom card
		MouseEvent press = this.createPressed(archway, archway.tv2, 31, 286);
		archway.tv2.getMouseManager().handleMouseEvent(press);

		//create released on cardsLeftView
		MouseEvent release = this.createReleased(archway, archway.cardsLeftView, 0, 0);
		archway.cardsLeftView.getMouseManager().handleMouseEvent(release);

		// check nothing happens
		assertEquals("[Column:tc2:JS,7S,3S,10H,6H,2H,9D,5D,QC,8C,4C,QS]",archway.tableauColumn2.toString());
	}
	
	public void testReleaseOnReservePileViewCard() {
		//check is initialized correctly
		assertEquals("[Column:tc2:JS,7S,3S,10H,6H,2H,9D,5D,QC,8C,4C,QS]",archway.tableauColumn2.toString());
		//create a mouse press at (31,286) within the tv2; press on bottom card
		MouseEvent press = this.createPressed(archway, archway.tv2, 31, 286);
		archway.tv2.getMouseManager().handleMouseEvent(press);

		//create released on R6Stack
		MouseEvent release = this.createReleased(archway, archway.R6Stack, 0, 0);
		archway.R6Stack.getMouseManager().handleMouseEvent(release);

		// check nothing happens
		assertEquals("[Column:tc2:JS,7S,3S,10H,6H,2H,9D,5D,QC,8C,4C,QS]",archway.tableauColumn2.toString());
		assertTrue(archway.reservePile6.toString().equals("[Pile:rp6:6S,6H,6D,6C]"));
	}
}
