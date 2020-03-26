package com.example.tictactoe;
//import statements
import android.util.Log;

import java.util.Random;

import static java.lang.Integer.*;

class TicTacToe {

    //instance variables
    private final boolean playerOwnsX;
    private final boolean playerTurn;
    private int turnCount = 0;
    private final String [][] gameBoard = new String[3][3];
    private final String playerMarker;
    private final String cpuMarker;

    //class variables
    private static final String TAG = "TicTacToe";

    //class constructor
    public TicTacToe(boolean playerTurn, boolean playerOwnsX){
        this.playerTurn = playerTurn;
        this.playerOwnsX = playerOwnsX;
        if (this.playerOwnsX) {
            playerMarker = "X";
            cpuMarker = "O";
        }
        else {
            playerMarker = "O";
            cpuMarker = "X";
        }
        //creates a gameboard filled with blank indices rather than null
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameBoard[i][j] = " ";
            }
        }
    }

    //a method that plays a turn based on the row and col
    public String[][] playerTurn(int row, int col) {
        if (!gameBoard[row][col].equals(" "))
            return gameBoard;
        else
            gameBoard[row][col] = playerMarker;
        Log.v(TAG, gameBoard[0][0] + "|" + gameBoard[0][1] + "|" + gameBoard[0][2] + "\n" + gameBoard[1][0] + "|" + gameBoard[1][1] + "|" + gameBoard[1][2] + "\n" + gameBoard[2][0] + "|" + gameBoard[2][1] + "|" + gameBoard[2][2]);
        turnCount++;
        return gameBoard;
    }

    //ca;culates the terminal state when using the minimax function
    private int checkScore(String[][] gameBoard)
    {
        //checks who wins an iterable row
        for (int i = 0; i < 3; i++) {
            if (gameBoard[i][0].equals(gameBoard[i][1]) && gameBoard[i][1].equals(gameBoard[i][2])) {
                if (gameBoard[i][0].equals(cpuMarker))
                    return 10;
                else if (gameBoard[i][0].equals(playerMarker))
                    return -10;
            }
        }
        //check who wins an iterable column
        for (int j = 0; j < 3; j++) {
            if (gameBoard[0][j].equals(gameBoard[1][j]) && gameBoard[1][j].equals(gameBoard[2][j])) {
                if (gameBoard[0][j].equals(cpuMarker))
                    return 10;
                else if (gameBoard[0][j].equals(playerMarker))
                    return -10;
            }
        }
        //check who wins diagonal
        if (gameBoard[0][0].equals(gameBoard[1][1]) && gameBoard[1][1].equals(gameBoard[2][2])) {
            if (gameBoard[0][0].equals(cpuMarker))
                return +10;
            else if (gameBoard[0][0].equals(playerMarker))
                return -10;
        }

        //check who wins antidiagonal
        if (gameBoard[0][2].equals(gameBoard[1][1]) && gameBoard[1][1].equals(gameBoard[2][0])) {
            if (gameBoard[0][2].equals(cpuMarker))
                return 10;
            else if (gameBoard[0][2].equals(playerMarker))
                return -10;
        }
        return 0;
    }

    //determines if there are any more moves left
    private Boolean movesRemaining(String[][] gameBoard)
    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (gameBoard[i][j].equals(" "))
                    return true;
        return false;
    }

    //minimax recursive function which is the heart of the AI
    // this function allows the program to do a deep level search to optimize selection
    private int minimax(String[][] gameBoard, int depth, Boolean isMax)
    {
        //sets score to the final state
        int score = checkScore(gameBoard);
        if (score == 10)
            return score;
        if (score == -10)
            return score;
        if (!movesRemaining(gameBoard))
            return 0;
        //if there are moves remaining, this part of the function gets executed
        if (isMax) {
            int bestScore = -1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (gameBoard[i][j].equals(" ")) { //if this slot is empty
                        gameBoard[i][j] = cpuMarker; //temporarily put the cpu marker
                        score = minimax(gameBoard,depth+1, false); //and find the score of that play
                        gameBoard[i][j] = " "; // remove the cpu marker
                        //finds tbe better score
                        bestScore = max(score, bestScore);
                    }
                }
            }
            return bestScore;   //return bestScore
        }
        else {
            int bestScore = 1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (gameBoard[i][j].equals(" ")) {
                        gameBoard[i][j] = playerMarker;
                        score = minimax(gameBoard,depth+1, true);
                        gameBoard[i][j] = " ";
                        bestScore = min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    //actual method that runs the turn of the CPU
    public String[][] cpuTurn(String difficulty) {
        if (difficulty.equals("hard")) {
            int bestScore = -1000;
            int row = 0;
            int col = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // checks if slot is empty
                    if (gameBoard[i][j].equals(" ")) {
                        gameBoard[i][j] = cpuMarker;
                        //finds the best play
                        int score = minimax(gameBoard, 0, false);
                        gameBoard[i][j] = " ";
                        if (score > bestScore) {
                            bestScore = score;
                            row = i;
                            col = j;
                        }
                    }
                }
            }
            gameBoard[row][col] = cpuMarker;
            turnCount++;
        }
        else {
            Random r = new Random();
            int row = 0;
            int col = 0;
            do {
                row = r.nextInt(3);
                col = r.nextInt(3);
            }
            while (!gameBoard[row][col].equals(" "));
            gameBoard[row][col] = cpuMarker;
            turnCount++;
        }

            Log.v(TAG, gameBoard[0][0] + "|" + gameBoard[0][1] + "|" + gameBoard[0][2] + "\n" + gameBoard[1][0] + "|" + gameBoard[1][1] + "|" + gameBoard[1][2] + "\n" + gameBoard[2][0] + "|" + gameBoard[2][1] + "|" + gameBoard[2][2]);
            return gameBoard;
        }

    //checks for a win and returns the winning marker
    public String checkWin(){
        if (gameBoard[0][0].equals(gameBoard[1][1]) && gameBoard[1][1].equals(gameBoard[2][2]))
            return gameBoard[0][0];
        else if (gameBoard[0][2].equals(gameBoard[1][1]) && gameBoard[1][1].equals(gameBoard[2][0]))
            return gameBoard[0][2];
        else
            for (int i = 0; i < 3 ; i++) {
                if (gameBoard[i][0].equals(gameBoard[i][1]) && gameBoard[i][1].equals(gameBoard[i][2]))
                    return gameBoard[i][0];
                else if (gameBoard[0][i].equals(gameBoard[1][i]) && gameBoard[1][i].equals(gameBoard[2][i]))
                    return gameBoard[0][i];
            }
            return "";
    }
//relevant setter and getter methods
    public void setTurnCount(int turnCount) { this.turnCount = turnCount; }
    public int getTurnCount() { return turnCount; }
    public String[][] getGameBoard() { return gameBoard; }
    public boolean isPlayerTurn() { return playerTurn; }
    public boolean isPlayerOwnsX() { return playerOwnsX; }

}


