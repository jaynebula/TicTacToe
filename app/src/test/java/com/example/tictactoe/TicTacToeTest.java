package com.example.tictactoe;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;



public class TicTacToeTest {
    Random random = new Random();


    @Test
    public void testRow1Win() {
        TicTacToe game = new TicTacToe(true, true);
        game.playerTurn(0,0);
        game.playerTurn(0,1);
        game.playerTurn(0,2);
        assertEquals("X", game.checkWin());
    }
    @Test
    public void testRow2Win() {
        TicTacToe game = new TicTacToe(true, true);
        game.playerTurn(1,0);
        game.playerTurn(1,1);
        game.playerTurn(1,2);
        assertEquals("X", game.checkWin());
    }
    @Test
    public void testRow3Win() {
        TicTacToe game = new TicTacToe(true, true);
        game.playerTurn(2,0);
        game.playerTurn(2,1);
        game.playerTurn(2,2);
        assertEquals("X", game.checkWin());
    }

    @Test
    public void testCol1Win() {
        TicTacToe game = new TicTacToe(true, true);
        game.playerTurn(0,0);
        game.playerTurn(1,0);
        game.playerTurn(2,0);
        assertEquals("X", game.checkWin());
    }
    @Test
    public void testCol2Win() {
        TicTacToe game = new TicTacToe(true, true);
        game.playerTurn(0,1);
        game.playerTurn(1,1);
        game.playerTurn(2,1);
        assertEquals("X", game.checkWin());
    }
    @Test
    public void testCol3Win() {
        TicTacToe game = new TicTacToe(true, true);
        game.playerTurn(0,2);
        game.playerTurn(1,2);
        game.playerTurn(2,2);
        assertEquals("X", game.checkWin());
    }
    @Test
    public void testDiagWin() {
        TicTacToe game = new TicTacToe(true, true);
        game.playerTurn(0,0);
        game.playerTurn(1,1);
        game.playerTurn(2,2);
        assertEquals("X", game.checkWin());
    }
    @Test
    public void testOppDiagWin() {
        TicTacToe game = new TicTacToe(true, true);
        game.playerTurn(0,2);
        game.playerTurn(1,1);
        game.playerTurn(2,0);
        assertEquals("X", game.checkWin());
    }

    @Test
    public void testCPU(){
        TicTacToe game = new TicTacToe(true, true);
        game.cpuTurn();
        game.cpuTurn();
        game.cpuTurn();
        assertEquals("O", game.checkWin());
    }

    @Test
    public void game(){
        TicTacToe game = new TicTacToe(true, true);
        while (true){
            game.playerTurn(random.nextInt(3), random.nextInt(3));
            System.out.println(game.getGameBoard()[0][0] + "|" + game.getGameBoard()[0][1] + "|" + game.getGameBoard()[0][2] + "\n" + game.getGameBoard()[1][0] + "|" + game.getGameBoard()[1][1] + "|" + game.getGameBoard()[1][2] + "\n" + game.getGameBoard()[2][0] + "|" + game.getGameBoard()[2][1] + "|" + game.getGameBoard()[2][2]);
            game.cpuTurn();
            System.out.println(game.getGameBoard()[0][0] + "|" + game.getGameBoard()[0][1] + "|" + game.getGameBoard()[0][2] + "\n" + game.getGameBoard()[1][0] + "|" + game.getGameBoard()[1][1] + "|" + game.getGameBoard()[1][2] + "\n" + game.getGameBoard()[2][0] + "|" + game.getGameBoard()[2][1] + "|" + game.getGameBoard()[2][2]);
            if (game.checkWin().equals("X")||game.checkWin().equals("O"))
                break;
        }
        assertEquals("O", game.checkWin());
    }


}
