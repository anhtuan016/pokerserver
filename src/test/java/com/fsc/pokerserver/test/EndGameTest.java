/*
The MIT License (MIT)
Copyright (c) 2018 by habogay
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

package com.fsc.pokerserver.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.fcs.pokerserver.*;
import com.fcs.pokerserver.holder.Board;
import com.fcs.pokerserver.holder.Hand;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * The class to test the end game.
 *
 * @category com > fcs > pokerserver > test
 */

public class EndGameTest {
    private Player master;
    private Room room;

    @Before
    public void setUp() throws Exception {
        master = new Player("Room master");
        room = new Room(master, BlindLevel.BLIND_10_20);

    }
    /*--------------------- End Game -----------------------*/

    /**
     * Error order number from player Check, bet, fold before end game.
     */
    @Test(expected = AssertionError.class)
    public void testCheckBetFoldBeforeEndGame() {
        Game game = room.createNewGame();

        Player player2 = new Player("Player 2");
        game.addPlayer(player2);
        Player player3 = new Player("Player 3");
        game.addPlayer(player3);
        Player player4 = new Player("Player 4");
        game.addPlayer(player4);
        Player player5 = new Player("Player 5");
        game.addPlayer(player5);

        game.setDealer(player5);

        master.setBalance(1000);
        player2.setBalance(1000);
        player3.setBalance(1000);
        player4.setBalance(1000);
        player5.setBalance(1000);

        game.startGame();

        game.preflop();
        player3.fold();
        player4.bet(20);
        player5.bet(30);
        master.bet(20);
        player2.bet(10);
        player4.bet(10);

        game.flop();
        master.check();
        player2.check();
        player4.fold();
        player5.bet(10);
        master.bet(10);
        player2.bet(10);

        game.turn();
        master.check();
        player2.bet(20);
        player5.bet(20);
        master.fold();

        game.river();
        player2.bet(20);
//		player5 need bet before player2
        player2.fold();
        player5.bet(30);

    }


    /**
     * call end game although current round bet not equal
     */
    @Test(expected = AssertionError.class)
    public void testCallEndGameAlthoughCurrentRoundBetNotEqual() {

        Game game = room.createNewGame();

        Player player2 = new Player("Player 2");
        game.addPlayer(player2);
        Player player3 = new Player("Player 3");
        game.addPlayer(player3);
        Player player4 = new Player("Player 4");
        game.addPlayer(player4);
        Player player5 = new Player("Player 5");
        game.addPlayer(player5);

        game.setDealer(player5);

        master.setBalance(1000);
        player2.setBalance(1000);
        player3.setBalance(1000);
        player4.setBalance(1000);
        player5.setBalance(1000);

        game.startGame();

        game.preflop();
        player3.fold();
        player4.bet(20);
        player5.bet(30);
        master.bet(20);
        player2.bet(10);
        player4.bet(10);

        game.flop();
        master.check();
        player2.check();
        player4.fold();
        player5.bet(10);
        master.bet(10);
        player2.bet(10);

        game.turn();
        master.check();
        player2.bet(20);
        player5.bet(20);
        master.fold();

        game.river();
        player2.bet(20);
        player5.bet(30);
        player2.bet(20);

        game.endGame();
        assertEquals(game.getPotBalance(), 270);
    }

    /**
     * Get pot from game after end game.
     */
    @Test
    public void testGetPotAfterEndGame() {
        Game game = room.createNewGame();

        Player player2 = new Player("Player 2");
        game.addPlayer(player2);
        Player player3 = new Player("Player 3");
        game.addPlayer(player3);
        Player player4 = new Player("Player 4");
        game.addPlayer(player4);
        Player player5 = new Player("Player 5");
        game.addPlayer(player5);

        game.setDealer(player5);

        master.setBalance(1000);
        player2.setBalance(1000);
        player3.setBalance(1000);
        player4.setBalance(1000);
        player5.setBalance(1000);

        game.startGame();

        game.preflop();
        player3.fold();
        player4.bet(20);
        player5.bet(30);
        master.bet(20);
        player2.bet(10);
        player4.bet(10);

        game.flop();
        master.check();
        player2.check();
        player4.fold();
        player5.bet(10);
        master.bet(10);
        player2.bet(10);

        game.turn();
        master.check();
        player2.bet(20);
        player5.bet(20);
        master.fold();

        game.river();
        player2.bet(20);
        player5.bet(30);
        player2.fold();

        game.endGame();

        assertEquals(game.getPotBalance(), 240);
    }

    /**
     * Get cards from game after end game.
     */
    @Test
    public void testGetCardsAfterEndGame() {
        Game game = room.createNewGame();

        Player player2 = new Player("Player 2");
        game.addPlayer(player2);
        Player player3 = new Player("Player 3");
        game.addPlayer(player3);
        Player player4 = new Player("Player 4");
        game.addPlayer(player4);
        Player player5 = new Player("Player 5");
        game.addPlayer(player5);

        game.setDealer(player5);

        master.setBalance(1000);
        player2.setBalance(1000);
        player3.setBalance(1000);
        player4.setBalance(1000);
        player5.setBalance(1000);

        game.startGame();

        game.preflop();
        player3.fold();
        player4.bet(20);
        player5.bet(30);
        master.bet(20);
        player2.bet(10);
        player4.bet(10);

        game.flop();
        master.check();
        player2.check();
        player4.fold();
        player5.bet(10);
        master.bet(10);
        player2.bet(10);

        game.turn();
        master.check();
        player2.bet(20);
        player5.bet(20);
        master.fold();

        game.river();
        player2.bet(20);
        player5.bet(30);
        player2.fold();

        game.endGame();

        assertEquals(game.getBoard().getCardNumber(), 5);
    }

    @Test
    public void testNextGame_noOneLeft() {
        Player master = new Player("master");
        Room room = new Room(master, BlindLevel.BLIND_10_20);
        Game game = room.createNewGame();

        Player player2 = new Player("Player 2");
        game.addPlayer(player2);
        Player player3 = new Player("Player 3");
        game.addPlayer(player3);
        Player player4 = new Player("Player 4");
        game.addPlayer(player4);
        Player player5 = new Player("Player 5");
        game.addPlayer(player5);

        game.setDealer(player5);

        master.setBalance(1000);
        player2.setBalance(1000);
        player3.setBalance(1000);
        player4.setBalance(1000);
        player5.setBalance(1000);

        game.startGame();
        game.preflop();
        player3.bet(20);
        player4.bet(20);
        player5.bet(20);
        master.bet(10);

        game.flop();
        master.check();
        player2.check();
        player3.check();
        player4.check();
        player5.check();


        game.turn();
        master.check();
        player2.check();
        player3.check();
        player4.check();
        player5.check();

        game.river();
        master.check();
        player2.check();
        player3.check();
        player4.check();
        player5.check();

        game.endGame();
        game = room.nextGame();
        /**
         * Dealer will be set on player1 (master)*/
        assertSame(game.getDealer(), master);
    }

    @Test
    public void testNextGame_newPlayerJoin() {
        Player master = new Player("master");
        Room room = new Room(master, BlindLevel.BLIND_10_20);
        Game game = room.createNewGame();

        Player player2 = new Player("Player 2");
        game.addPlayer(player2);
        Player player3 = new Player("Player 3");
        game.addPlayer(player3);
        Player player4 = new Player("Player 4");
        game.addPlayer(player4);
        Player player5 = new Player("Player 5");
        game.addPlayer(player5);

        game.setDealer(player5);

        master.setBalance(1000);
        player2.setBalance(1000);
        player3.setBalance(1000);
        player4.setBalance(1000);
        player5.setBalance(1000);

        game.startGame();
        game.preflop();
        player3.bet(20);
        player4.bet(20);
        player5.bet(20);
        master.bet(10);

        game.flop();
        master.check();
        player2.check();
        player3.check();
        player4.check();
        player5.check();


        game.turn();
        master.check();
        player2.check();
        player3.check();
        player4.check();
        player5.check();

        game.river();
        master.check();
        player2.check();
        player3.check();
        player4.check();
        player5.check();

        game.endGame();
        Player player6 = new Player("Player6");
        room.addPlayer(player6);
        game = room.nextGame();
        /**
         * Dealer will be set on player6*/
        assertSame(game.getDealer(), player6);
    }

    @Test
    public void testNextGame_PlayersFold() {
        Player master = new Player("master");
        Room room = new Room(master, BlindLevel.BLIND_10_20);
        Game game = room.createNewGame();

        Player player2 = new Player("Player 2");
        room.addPlayer(player2);
        Player player3 = new Player("Player 3");
        room.addPlayer(player3);
        Player player4 = new Player("Player 4");
        room.addPlayer(player4);
        Player player5 = new Player("Player 5");
        room.addPlayer(player5);

        game.setDealer(player5);

        master.setBalance(1000);
        player2.setBalance(1000);
        player3.setBalance(1000);
        player4.setBalance(1000);
        player5.setBalance(1000);

        game.startGame();
        game.preflop();
        player3.bet(20);
        player4.bet(20);
        player5.bet(20);
        master.bet(10);

        game.flop();
        master.check();
        player2.check();
        player3.check();
        player4.check();
        player5.check();


        game.turn();
        master.check();
        player2.check();
        player3.check();
        player4.check();
        player5.check();

        game.river();
        master.check();
        player2.fold();
        player3.fold();
        player4.check();
        player5.check();

        game.endGame();
//        for (Player p : game.getListPlayer()){
//            System.out.println(p.getName());
//        }

        game = room.nextGame();


        /**
         * Dealer will be set on player 2*/
        assertSame(game.getDealer(), player2);
    }

    @Test
    public void testNextGame_DealerQuit() {
        Player master = new Player("master");
        Room room = new Room(master, BlindLevel.BLIND_10_20);
        Game game = room.createNewGame();

        Player player2 = new Player("Player 2");
        room.addPlayer(player2);
        Player player3 = new Player("Player 3");
        room.addPlayer(player3);
        Player player4 = new Player("Player 4");
        room.addPlayer(player4);
        Player player5 = new Player("Player 5");
        room.addPlayer(player5);

        game.setDealer(player5);

        master.setBalance(1000);
        player2.setBalance(1000);
        player3.setBalance(1000);
        player4.setBalance(1000);
        player5.setBalance(1000);

        game.startGame();
        game.preflop();
        player3.bet(20);
        player4.bet(20);
        player5.bet(20);
        master.bet(10);

        game.flop();
        master.check();
        player2.check();
        player3.check();
        player4.check();
        player5.check();


        game.turn();
        master.check();
        player2.check();
        player3.check();
        player4.check();
        player5.check();

        game.river();
        master.check();
        player2.check();
        player3.check();
        player4.check();
        player5.fold();

        game.endGame();
        room.getListPlayer().remove(4);
        game = room.nextGame();


        /**
         * Dealer will be set on P1(master)*/
        assertSame(game.getDealer(), master);
    }

    @Test
    public void testNextGame_DealerQuit2() {
        /**
         * Player 3 was set as dealer*/
        Player master = new Player("master");
        Room room = new Room(master, BlindLevel.BLIND_10_20);
        Game game = room.createNewGame();

        Player player2 = new Player("Player 2");
        room.addPlayer(player2);
        Player player3 = new Player("Player 3");
        room.addPlayer(player3);
        Player player4 = new Player("Player 4");
        room.addPlayer(player4);
        Player player5 = new Player("Player 5");
        room.addPlayer(player5);

        game.setDealer(player3);

        master.setBalance(1000);
        player2.setBalance(1000);
        player3.setBalance(1000);
        player4.setBalance(1000);
        player5.setBalance(1000);

        game.startGame();
        game.preflop();
        master.bet(20);
        player2.bet(20);
        player3.bet(20);
        player4.bet(10);

        game.flop();
        player4.check();
        player5.check();
        master.check();
        player2.check();
        player3.check();


        game.turn();
        player4.check();
        player5.check();
        master.check();
        player2.check();
        player3.check();

        game.river();
        player4.check();
        player5.check();
        master.check();
        player2.check();
        player3.check();

        game.endGame();
        room.getListPlayer().remove(2);
        game.getListPlayer().remove(2);
        game = room.nextGame();


        /**
         * Dealer will be set on P5*/
        assertSame(game.getDealer(), player5);
    }

    /**
     * Test End Game if Playing The Board situation occured(All players are winners)
     */
    @Test
    public void testGetPotIfPlayingTheBoard() {
        Game game = room.createNewGame();

        Player player2 = new Player("Player 2");
        game.addPlayer(player2);
        Player player3 = new Player("Player 3");
        game.addPlayer(player3);
        Player player4 = new Player("Player 4");
        game.addPlayer(player4);
        Player player5 = new Player("Player 5");
        game.addPlayer(player5);
        game.setDealer(player5);

        master.setBalance(1000);
        player2.setBalance(1000);
        player3.setBalance(1000);
        player4.setBalance(1000);
        player5.setBalance(1000);

        game.startGame();
        //P1 and P2 is SB and BB.

        game.preflop();
        //Each P bet 30. P3 folded.
        player3.fold();
        player4.bet(20);
        player5.bet(30);
        master.bet(20);
        player2.bet(10);
        player4.bet(10);
        game.flop();
        //p4 folded. p1, p2, p5 bet 10 more (40 in total).
        master.check();
        player2.check();
        player4.fold();
        player5.bet(10);
        master.bet(10);
        player2.bet(10);

        game.turn();
        //p1,p3,p4 fold. p2,p5 bet 20 more(60 in total)
        master.check();
        player2.bet(20);
        player5.bet(20);
        master.fold();

        game.river();
        //p2,p5 bet 30 more (90 in total)
        player2.bet(30);
        player5.bet(30);
        //total Pot is 250
        /**
         * Setup Playing the Board situation*/
        Board royalFlush = new Board(Card.TEN_OF_HEARTS, Card.JACK_OF_HEARTS, Card.QUEEN_OF_HEARTS, Card.KING_OF_HEARTS, Card.ACE_OF_HEARTS);
        game.setBoard(royalFlush);
        player2.setPlayerHand(new Hand(Card.THREE_OF_HEARTS, Card.SIX_OF_DIAMONDS));
        player5.setPlayerHand(new Hand(Card.THREE_OF_SPADES, Card.SIX_OF_CLUBS));
        game.endGame();
        System.out.println("winners: " + game.getWinners());
        assertTrue(player5.getBalance() == player2.getBalance());
        assertEquals(1035, player5.getBalance());
    }

    @Test
    public void testGetPotIfPlayingTheBoard2() {
        Game game = room.createNewGame();

        Player player2 = new Player("Player 2");
        game.addPlayer(player2);
        Player player3 = new Player("Player 3");
        game.addPlayer(player3);
        Player player4 = new Player("Player 4");
        game.addPlayer(player4);
        Player player5 = new Player("Player 5");
        game.addPlayer(player5);
        game.setDealer(player5);

        master.setBalance(1000);
        player2.setBalance(1000);
        player3.setBalance(1000);
        player4.setBalance(1000);
        player5.setBalance(1000);

        game.startGame();
        //P1 and P2 is SB and BB.

        game.preflop();
        //Each P bet 30. P3 folded.
        player3.fold();
        player4.bet(20);
        player5.bet(30);
        master.bet(20);
        player2.bet(10);
        player4.bet(10);
        game.flop();
        //p4 folded. p1, p2, p5 bet 10 more (40 in total).
        master.check();
        player2.check();
        player4.fold();
        player5.bet(10);
        master.bet(10);
        player2.bet(10);

        game.turn();
        // p1, p2,p5 bet 20 more(60 in total)
        master.bet(20);
        player2.bet(20);
        player5.bet(20);

        game.river();
        //p2,p5 bet 30 more (90 in total)
        master.bet(30);
        player2.bet(30);
        player5.bet(30);
        System.out.println(player5.getBalance());
        //total Pot is 250
        /**
         * Setup Playing the Board situation*/
        Board royalFlush = new Board(Card.TEN_OF_HEARTS, Card.JACK_OF_HEARTS, Card.QUEEN_OF_HEARTS, Card.KING_OF_HEARTS, Card.ACE_OF_HEARTS);
        game.setBoard(royalFlush);
        player2.setPlayerHand(new Hand(Card.THREE_OF_HEARTS, Card.SIX_OF_DIAMONDS));
        player5.setPlayerHand(new Hand(Card.THREE_OF_SPADES, Card.SIX_OF_CLUBS));
        master.setPlayerHand(new Hand(Card.FOUR_OF_CLUBS, Card.FIVE_OF_SPADES));
        game.endGame();
        System.out.println("winners: " + game.getWinners());
        assertEquals(1010, master.getBalance());
    }
}
