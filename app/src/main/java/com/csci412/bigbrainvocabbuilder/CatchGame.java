package com.csci412.bigbrainvocabbuilder;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

public class CatchGame {

    private int round = 1;
    private int difficulty = PlayActivity.difficultyFlag;
    private int score = 0;
    private DatabaseManager dbManager;

    private String[] selectWord;
    private String[] otherWords;
    private Point[] wordPositions;

    private int height;
    private int width;

    private boolean moveLeft = false;
    private boolean moveRight = false;

    private Rect catcherRect;

    private boolean gameOver = false;
    private Rect backRect;
    private Point scorePos;

    public boolean tryThunk;

    public CatchGame(Context context, int width, int height) {
        dbManager = new DatabaseManager(context);
        this.height = height;
        this.width = width;
        catcherRect = new Rect(width/2 - width / 8, height - height / 8,
                width/2 + width / 8, height - height / 10);

        backRect = new Rect(width/2 - width / 8, height/2 - width / 10,
                width/2 + width / 8, height/2 + width / 10);
        scorePos = new Point(width / 2 - width / 10, height/2 - width / 8);
    }

    public Rect getBackRect() {
        return backRect;
    }

    public Point getScorePos() {
        return scorePos;
    }

    // Get words other than the selected main word
    private void getOtherWords(int otherWordCount) {
        otherWords = new String[otherWordCount];
        for (int i = 0; i < otherWordCount; i++) {
            String[] otherWord = dbManager.getRandomWord(-1);
            otherWords[i] = otherWord[0];
            if (otherWords[0].equals(selectWord[0])) {
                i--;
            }
        }
    }

    // Get a random word to be correct word
    private void getMainWord() {
        selectWord = dbManager.getRandomWord(-1);
    }

    // Set words and starting positions
    public void startWordPositions(int wordCount) {
        getMainWord();
        getOtherWords(wordCount-1);
        wordPositions = new Point[wordCount];
        Random rand = new Random();
        for (int i = 0; i < wordPositions.length; i++) {
            int xPos = rand.nextInt((int) (width * .8f));
            int yPos = -rand.nextInt((int) (height * .5f));
            wordPositions[i] = new Point(xPos, yPos);
        }
    }

    // Get list of all words, first is always correct word
    public String[] getWords() {
        String[] allWords = new String[otherWords.length + 1];
        allWords[0] = selectWord[0];
        System.arraycopy(otherWords, 0, allWords, 1, otherWords.length);
        return allWords;
    }

    // Get definition of correct word
    public String getDefinition() {
        return selectWord[1];
    }

    // Get positions of words in same order as getWords()
    public Point[] getPositions() {
        return wordPositions;
    }

    public Rect getCatcherRect() {
        return catcherRect;
    }

    // Moves all words down screen
    public void moveWordsDown() {
        for (Point p : wordPositions) {
            p.y += 9 + difficulty;
        }
    }


    // Move the catcher and prevent catcher from going off screen
    public void moveCatcher() {
        if (moveRight) {
            catcherRect.left += 12;
            catcherRect.right += 12;
            if (catcherRect.right > width) {
                int rightAdjust = catcherRect.right;
                rightAdjust -= width;
                catcherRect.left -= rightAdjust;
                catcherRect.right -= rightAdjust;
            }
            moveRight = false;
        } else if (moveLeft) {
            catcherRect.left -= 12;
            catcherRect.right -= 12;
            if (catcherRect.left < 0) {
                int leftAdjust = catcherRect.left;
                catcherRect.left -= leftAdjust;
                catcherRect.right -= leftAdjust;
            }
            moveLeft = false;
        }
    }

    // Set catcher to move on next timer task based on user's touch position (left/right screen)
    public void moveInput(int xPos) {
        if (xPos <= width/2) {
            moveLeft = true;
            moveRight = false;
        } else {
            moveRight = true;
            moveLeft = false;
        }
    }

    // Returns true if user pressed back button rect
    public boolean backInput(int xPos, int yPos) {
        if (gameOver && backRect.contains(xPos, yPos)) {
            Log.i("Catch", "Press Back");
            return true;
        }
        return false;
    }

    // Checks word collided with catcher or correct word fell below screen
    public void checkHit() {
        for (int i = 0; i < wordPositions.length; i++) {
            Point p = wordPositions[i];
            if (catcherRect.intersects(p.x, p.y, p.x + width / 5, p.y)) {

                if (i == 0) {
                    endRound(true);
                    tryThunk = true;
                    return;
                }
                p.y = height * 2;
                tryThunk = true;
                endRound(false);
                return;
            }
        }
        if (wordPositions[0].y > height + 10) {
            endRound(false);
        }
    }

    // End round then continue to next or end game
    private void endRound (boolean won) {
        if (won) {
            score++;
        }
        round++;
        if (round > 3 + difficulty) {
            gameOver = true;
        } else {
            startWordPositions(3);
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getScore() {
        return score;
    }
}
