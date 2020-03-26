package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //instance variables
    private boolean gameOver = false;
    private Button button3;
    private Button button5;
    private Button button7;
    private Button difficulty;
    private Button[][] buttons;
    private TextView playerScoreboard;
    private TextView cpuScoreboard;
    private int playerScore = 0;
    private int cpuScore = 0;
    private boolean playerTurn;
    private String[][] gameBoard = new String[3][3];
    private TicTacToe game;
    private String gameMode = "easy";

    //class variables
    private static final String GAMEBOARD_KEY1 = "row1";
    private static final String GAMEBOARD_KEY2 = "row2";
    private static final String GAMEBOARD_KEY3 = "row3";
    private static final String TURNCOUNT_KEY = "turnCount";
    private static final String PLAYERSCORE_KEY = "playerScore";
    private static final String CPUSCORE_KEY = "cpuScore";
    private static final int drawAmount = 8;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
        //allows the current game to be saved during screen rotation
        savedInstanceState.putStringArray(GAMEBOARD_KEY1, gameBoard[0]);
        savedInstanceState.putStringArray(GAMEBOARD_KEY2, gameBoard[1]);
        savedInstanceState.putStringArray(GAMEBOARD_KEY3, gameBoard[2]);
        savedInstanceState.putInt(TURNCOUNT_KEY, game.getTurnCount());
        savedInstanceState.putInt(PLAYERSCORE_KEY, playerScore);
        savedInstanceState.putInt(CPUSCORE_KEY, cpuScore);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        difficulty = findViewById(R.id.difficulty);
        buttons = new Button[][]{{button1, button2, button3}, {button4, button5, button6}, {button7, button8, button9}};

        createNewGame();
        //retrieves saved game
        if (savedInstanceState != null) {
            gameBoard[0] = savedInstanceState.getStringArray(GAMEBOARD_KEY1);
            gameBoard[1] = savedInstanceState.getStringArray(GAMEBOARD_KEY2);
            gameBoard[2] = savedInstanceState.getStringArray(GAMEBOARD_KEY3);
            game.setTurnCount(savedInstanceState.getInt(TURNCOUNT_KEY));
            playerScore = savedInstanceState.getInt(PLAYERSCORE_KEY);
            cpuScore = savedInstanceState.getInt(CPUSCORE_KEY);
        }
        super.onCreate(savedInstanceState);


        playerScoreboard = findViewById(R.id.playerScoreboard);
        cpuScoreboard = findViewById(R.id.cpuScoreboard);
        updateGameboard(gameBoard);

        difficulty.setOnClickListener(v -> {
            if (gameMode.equals("easy")){
                difficulty.setText(R.string.HardMode);
                gameMode = "hard";
            }
            else{
                difficulty.setText(R.string.EasyMode);
                gameMode = "easy";
            }});
        button1.setOnClickListener(v -> playRound(0, 0));
        button2.setOnClickListener(v -> playRound(0, 1));
        button3.setOnClickListener(v -> playRound(0, 2));
        button4.setOnClickListener(v -> playRound(1, 0));
        button5.setOnClickListener(v -> playRound(1, 1));
        button6.setOnClickListener(v -> playRound(1, 2));
        button7.setOnClickListener(v -> playRound(2, 0));
        button8.setOnClickListener(v -> playRound(2, 1));
        button9.setOnClickListener(v -> playRound(2, 2));
    }

    //updates gameboard with recent plays
    private void updateGameboard(String[][] gameBoard) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!gameBoard[i][j].equals(" "))
                    buttons[i][j].setText(gameBoard[i][j]);
            }
        }
    }

    //resets game
    private void createNewGame() {
        if (playerTurn) {
            game = new TicTacToe(false, false);
            playerTurn = game.isPlayerTurn();
        } else {
            game = new TicTacToe(true, true);
            playerTurn = game.isPlayerTurn();
        }
        gameBoard = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].getBackground().setColorFilter(null);
                buttons[i][j].setText(" ");
            }
        }
        gameBoard = game.getGameBoard();
    }

    //highlights win
    private void highlightWin(String[][] gameBoard) {
        if (gameBoard[0][0].equals(gameBoard[1][1]) && gameBoard[1][1].equals(gameBoard[2][2])) {
            for (int i = 0; i < 3; i++)
                buttons[i][i].getBackground().setColorFilter(new LightingColorFilter(0x000000, 0x103f9100));
        } else if (gameBoard[0][2].equals(gameBoard[1][1]) && gameBoard[1][1].equals(gameBoard[2][0])) {
            button3.getBackground().setColorFilter(new LightingColorFilter(0x000000, 0x103f9100));
            button5.getBackground().setColorFilter(new LightingColorFilter(0x000000, 0x103f9100));
            button7.getBackground().setColorFilter(new LightingColorFilter(0x000000, 0x103f9100));
        } else
            for (int i = 0; i < 3; i++) {
                if (gameBoard[i][0].equals(gameBoard[i][1]) && gameBoard[i][1].equals(gameBoard[i][2])) {
                    buttons[i][0].getBackground().setColorFilter(new LightingColorFilter(0x000000, 0x103f9100));
                    buttons[i][1].getBackground().setColorFilter(new LightingColorFilter(0x000000, 0x103f9100));
                    buttons[i][2].getBackground().setColorFilter(new LightingColorFilter(0x000000, 0x103f9100));
                } else if (gameBoard[0][i].equals(gameBoard[1][i]) && gameBoard[1][i].equals(gameBoard[2][i])) {
                    buttons[0][i].getBackground().setColorFilter(new LightingColorFilter(0x000000, 0x103f9100));
                    buttons[1][i].getBackground().setColorFilter(new LightingColorFilter(0x000000, 0x103f9100));
                    buttons[2][i].getBackground().setColorFilter(new LightingColorFilter(0x000000, 0x103f9100));
                }
            }
    }

    //checks if the game is over, checks for a win and updates player scoreboards
    //also displays a toast depending on the outcome of a game
    private void processRound(){
        if (gameOver)
            return;
        if (game.checkWin().equals("X")) {
            Toast.makeText(MainActivity.this, R.string.x_win_result, Toast.LENGTH_SHORT).show();
            highlightWin(gameBoard);
            gameOver = true;
            if (game.isPlayerOwnsX()){
                playerScore++;
                playerScoreboard.setText(String.valueOf(playerScore));
            }
            else{
                cpuScore++;
                cpuScoreboard.setText(String.valueOf(cpuScore));
            }
        }
        else if (game.checkWin().equals("O")) {
            Toast.makeText(MainActivity.this, R.string.o_win_result, Toast.LENGTH_SHORT).show();
            highlightWin(gameBoard);
            gameOver = true;
            if (!game.isPlayerOwnsX()){
                playerScore++;
                playerScoreboard.setText(String.valueOf(playerScore));
            }
            else{
                cpuScore++;
                cpuScoreboard.setText(String.valueOf(cpuScore));
            }
        }
        if (game.getTurnCount()>= drawAmount){
            Toast.makeText(MainActivity.this, R.string.draw_result, Toast.LENGTH_SHORT).show();
            gameOver = true;
        }
    }

    //method that is ran when a button is played
    private void playRound(int row, int col){
        if (gameOver){
            createNewGame();
            if (!game.isPlayerTurn()){
                gameOver = false;
                gameBoard = game.cpuTurn(gameMode);
                updateGameboard(gameBoard);
                return;
            }
            gameOver = false;
            return;
        }
        if (!gameBoard[row][col].equals(" "))
            return;
        gameBoard = game.playerTurn(row,col);
        updateGameboard(gameBoard);
        processRound();
        if (gameOver)
            return;
        gameBoard = game.cpuTurn(gameMode);
        updateGameboard(gameBoard);
        processRound();
    }
}

