package com.csci412.bigbrainvocabbuilder;

import android.content.Context;

import com.csci412.bigbrainvocabbuilder.DatabaseManager;

public class CatchGame {

    private int score = 0;
    private DatabaseManager dbManager;

    private String[] selectWord;

    public CatchGame(Context context) {
        dbManager = new DatabaseManager(context);
    }

    public String[] getOtherWords(int otherWordCount) {
        String[] otherWords = new String[otherWordCount];
        for (int i = 0; i < otherWordCount; i++) {
            String[] otherWord = dbManager.getRandomWord();
            otherWords[i] = otherWord[0];
            if (otherWords[0].equals(selectWord[0])) {
                i--;
            }
        }
        return otherWords;
    }

    public String[] getMainWord() {
        selectWord = dbManager.getRandomWord();
        return selectWord;
    }
}
