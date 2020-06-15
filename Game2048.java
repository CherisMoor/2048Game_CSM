package com.codegym.games.game2048;
import com.codegym.engine.cell.*;
import java.util.Arrays;

public class Game2048 extends Game {
    private static final int SIDE = 4;
    private boolean isGameStopped = false;
    private int score = 0;
    private int[][] gameField= new int[SIDE][SIDE];
    
    private void createGame(){
        gameField = new int[SIDE][SIDE];
        createNewNumber();
        createNewNumber();
        score = 0;
        setScore(score);
    }
    
    private boolean mergeRow(int[] row){
        boolean moved = false;
        
    for (int i=0; i< row.length-1;i++){
        if ((row[i] == row[i+1])&&(row[i]!=0)){
            row[i] = 2*row[i];
            row[i+1] = 0;
            moved = true;
            score += row[i];
            setScore(score);
        }
    }
    return moved;
    }
    
    private boolean compressRow(int[] row){
        boolean moved = false;
        int temp = 0;
        int[] tempRow = row.clone();
        
        for(int i = 0; i < row.length; i++) {
            for(int j = 0; j < row.length-i-1; j++){
                if(row[j] == 0) {
                    temp = row[j];
                    row[j] = row[j+1];
                    row[j+1] = temp;
                }
            }
        }
        if(!Arrays.equals(row,tempRow)){
            moved = true;
        }
        return moved;
    }
    
    private boolean canUserMove() {
        boolean canUserMove = false;
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                if (gameField[i][j] == 0) {
                    canUserMove = true;
                }else if ((i - 1) > 0 && (gameField[i][j] == gameField[i - 1][j])) {
                    canUserMove = true;
                }else if ((i + 1) < SIDE && (gameField[i][j] == gameField[i + 1][j])) {
                    canUserMove = true;
                }
                if ((j - 1) > 0 && (gameField[i][j] == gameField[i][j - 1])) {
                    canUserMove = true;
                }else if ((j + 1) < SIDE && (gameField[i][j] == gameField[i][j + 1])) {
                    canUserMove = true;
                }
            }
        }
        return canUserMove;
    }
    
    private void moveRight(){
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
    }
    
    private void moveLeft() {
        boolean isChanged = false;
        for (int i = 0; i < SIDE; i++) {
            if (compressRow(gameField[i])) isChanged = true;
            if (mergeRow(gameField[i])) isChanged = true;
            if (compressRow(gameField[i])) isChanged = true;
        } if (isChanged) {
            createNewNumber();
        }
    }
    
    private void rotateClockwise(){
    int [][] temp = new int [SIDE][SIDE];
    for (int i = 0; i < gameField.length; i++){
        for (int j = 0; j < gameField.length; j++){
        temp [i][j] = gameField[gameField.length -j -1][i];
        }
    }
      gameField = temp;
}
   
    private void moveUp(){
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
    }
    
    private void moveDown(){
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
    }
    
    @Override
     public void onKeyPress(Key x){
        if (isGameStopped) {
            if (x == Key.SPACE) {
                isGameStopped = false;
                createGame();
                drawScene();
            }
            return;
        }
        if (!canUserMove()) {
            gameOver();
            if (x == Key.SPACE) {
                isGameStopped = false;
                createGame();
                drawScene();
            }
            return;
        }
        if( x == Key.LEFT) {
            moveLeft();
            drawScene();
        }
        else if(x == Key.RIGHT) {
            moveRight();
            drawScene();
        }
        else if(x == Key.UP) {
            moveUp();
            drawScene();
        }
        else if(x == Key.DOWN){
            moveDown();
            drawScene();
        }
    }
    
    private Color getColorByValue(int value){
       Color color = null;
       if (value == 0){
           color = Color.WHITE;
            return color;
        } else if (value == 2){
            color = Color.ALICEBLUE;
            return color;
        } else if (value == 4){
            color = Color.ANTIQUEWHITE;
            return color;
        } else if (value == 8){
            color = Color.AQUA;
            return color;
        } else if (value == 16){
            color = Color.AQUAMARINE;
            return color;
        } else if (value == 32){
            color = Color.AZURE;
            return color;
        } else if (value == 64){
            color = Color.BEIGE;
            return color;
        } else if (value == 128){
            color = Color.BISQUE;
            return color;
        } else if (value == 256){
            color = Color.BLACK;
            return color;
        } else if (value == 512){
            color = Color.BLANCHEDALMOND;
            return color;
        } else if (value == 1024){
            color = Color.BLUE;
            return color;
        } else if (value == 2048) {
            color = Color.BLUEVIOLET;
            return color;
        } else {
            return Color.WHITE;
        }
    }
    
    private void setCellColoredNumber(int x, int y, int value){
        if (value > 0) {
            setCellValueEx(x, y, getColorByValue(value), Integer.toString(value));
        } else {
            setCellValueEx(x, y, getColorByValue(value), "");

        }
    }
    
    private void drawScene(){
        for (int i = 0; i < SIDE; i++){
            for (int y = 0; y < SIDE; y++){
                setCellColoredNumber(y,i,gameField[i][y]);
            }
        }
    }
    
     private int getMaxTileValue() {
        int maxTileValue = gameField[0][0];
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                if (gameField[i][j] > maxTileValue) {
                    maxTileValue = gameField[i][j];
                }
            }
        }
        return maxTileValue;
    }
    
    private void win() {
        isGameStopped = true;
        showMessageDialog(Color.GRAY, "You did it! You won!", Color.RED, 10);
    }
    
    private void gameOver() {
        isGameStopped = true;
        showMessageDialog(Color.GRAY, "You lost!", Color.RED, 10);
    }
    
    private void createNewNumber(){
        int maxTileValue = getMaxTileValue();
        int x;
        int y;
        int chance = getRandomNumber(10);

        if (maxTileValue == 2048) {
            win();
        }
        
        do {
            x = getRandomNumber(SIDE); 
            y = getRandomNumber(SIDE);
        } while(gameField[x][y] != 0);   

        if (chance == 9){
            gameField[x][y] = 4; //10% chance for a 4
        }else{
            gameField[x][y] = 2; //90% chance for a 2
        }
    }
    
    @Override
    public void initialize(){
        setScreenSize(SIDE, SIDE);
        createGame();
        drawScene();
    }
}