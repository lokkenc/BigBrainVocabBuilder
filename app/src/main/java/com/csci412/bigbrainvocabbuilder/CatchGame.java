package com.csci412.bigbrainvocabbuilder;

import android.content.Context;
import android.graphics.Rect;

import com.csci412.bigbrainvocabbuilder.DatabaseManager;

public class CatchGame {

    private int score = 0;
    private DatabaseManager dbManager;

    private String[] selectWord;
    private String[] otherWords;
    private Rect[] wordRects;
    private Rect screenRect;

    public CatchGame(Context context) {
        dbManager = new DatabaseManager(context);
        startWordPositions(3);
    }

    private String[] getOtherWords(int otherWordCount) {
        otherWords = new String[otherWordCount];
        for (int i = 0; i < otherWordCount; i++) {
            String[] otherWord = dbManager.getRandomWord();
            otherWords[i] = otherWord[0];
            if (otherWords[0].equals(selectWord[0])) {
                i--;
            }
        }
        return otherWords;
    }

    private String[] getMainWord() {
        selectWord = dbManager.getRandomWord();
        return selectWord;
    }

    public void startWordPositions(int wordCount) {
        getMainWord();
        getOtherWords(wordCount-1);

    }
}
