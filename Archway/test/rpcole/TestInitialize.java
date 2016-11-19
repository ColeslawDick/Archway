package rpcole;

import junit.framework.TestCase;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.client.gamefactory.GameWindow;

public class TestInitialize extends TestCase {

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

	public void testInitializeFoundationCards(){
		//check that the proper card is in each foundation pile
		assertTrue(archway.foundationPile1.toString().equals("[Pile:fp1:AH]"));
		assertTrue(archway.foundationPile2.toString().equals("[Pile:fp2:AS]"));
		assertTrue(archway.foundationPile3.toString().equals("[Pile:fp3:AD]"));
		assertTrue(archway.foundationPile4.toString().equals("[Pile:fp4:AC]"));
		assertTrue(archway.foundationPile5.toString().equals("[Pile:fp5:KH]"));
		assertTrue(archway.foundationPile6.toString().equals("[Pile:fp6:KS]"));
		assertTrue(archway.foundationPile7.toString().equals("[Pile:fp7:KD]"));
		assertTrue(archway.foundationPile8.toString().equals("[Pile:fp8:KC]"));
	}
	
	public void testInitializeTableauCards(){
		//check that the proper card is in on top of each tableau pile
		assertTrue(archway.tableauColumn1.toString().equals("[Column:tc1:QS,8S,4S,JH,7H,3H,10D,6D,2D,9C,5C,KS]"));
		assertTrue(archway.tableauColumn2.toString().equals("[Column:tc2:JS,7S,3S,10H,6H,2H,9D,5D,QC,8C,4C,QS]"));
		assertTrue(archway.tableauColumn3.toString().equals("[Column:tc3:10S,6S,2S,9H,5H,QD,8D,4D,JC,7C,3C,JS]"));
		assertTrue(archway.tableauColumn4.toString().equals("[Column:tc4:9S,5S,QH,8H,4H,JD,7D,3D,10C,6C,2C,10S]"));
	}
	
	public void testInitializeReserveCards(){
		//check that the proper card is on top of each reserve pile
		assertTrue(archway.reservePile1.toString().equals("[Pile:rp1:AS,AH,AD,AC]"));
		assertTrue(archway.reservePile2.toString().equals("[Pile:rp2:2S,2H,2D,2C]"));
		assertTrue(archway.reservePile3.toString().equals("[Pile:rp3:3S,3H,3D,3C]"));
		assertTrue(archway.reservePile4.toString().equals("[Pile:rp4:4S,4H,4D,4C]"));
		assertTrue(archway.reservePile5.toString().equals("[Pile:rp5:5S,5H,5D,5C]"));
		assertTrue(archway.reservePile6.toString().equals("[Pile:rp6:6S,6H,6D,6C]"));
		assertTrue(archway.reservePile7.toString().equals("[Pile:rp7:7S,7H,7D,7C]"));
		assertTrue(archway.reservePile8.toString().equals("[Pile:rp8:8S,8H,8D,8C]"));
		assertTrue(archway.reservePile9.toString().equals("[Pile:rp9:9S,9H,9D,9C]"));
		assertTrue(archway.reservePile10.toString().equals("[Pile:rp10:10H,10D,10C]"));
		assertTrue(archway.reservePile11.toString().equals("[Pile:rp11:JH,JD,JC]"));
		assertTrue(archway.reservePile12.toString().equals("[Pile:rp12:QH,QD,QC]"));
		assertTrue(archway.reservePile13.toString().equals("[Pile:rp13:KH,KD,KC]"));
	}
}
