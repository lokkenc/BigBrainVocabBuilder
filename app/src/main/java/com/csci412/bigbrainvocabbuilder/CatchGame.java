package com.csci412.bigbrainvocabbuilder;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;

import com.csci412.bigbrainvocabbuilder.DatabaseManager;

import java.util.Random;

public class CatchGame {

    private int score = 0;
    private DatabaseManager dbManager;

    private String[] selectWord;
    private String[] otherWords;
    private Point[] wordPositions;

    private int height;
    private int width;

    public CatchGame(Context context, int width, int height) {
        dbManager = new DatabaseManager(context);
        this.height = height;
        this.width = width;
    }

    private void getOtherWords(int otherWordCount) {
        otherWords = new String[otherWordCount];
        for (int i = 0; i < otherWordCount; i++) {
            String[] otherWord = dbManager.getRandomWord();
            otherWords[i] = otherWord[0];
            if (otherWords[0].equals(selectWord[0])) {
                i--;
            }
        }
    }

    private void getMainWord() {
        selectWord = dbManager.getRandomWord();
    }

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

    public String[] getWords() {
        String[] allWords = new String[otherWords.length + 1];
        allWords[0] = selectWord[0];
        System.arraycopy(otherWords, 0, allWords, 1, otherWords.length);
        return allWords;
    }

    public Point[] getPositions() {
        return wordPositions;
    }

    public void moveWordsDown() {
        for (Point p : wordPositions) {
            p.y += 10;
        }
    }
}
