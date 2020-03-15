package com.csci412.bigbrainvocabbuilder;

public class Statistics {
    private int wordsLearned;
    private int wordsLeft;
    private int testsTaken;
    private int gamesCompleted;

    public int getWordsLearned() {
        return wordsLearned;
    }

    public void setWordsLearned(int wordsLearned) {
        this.wordsLearned = wordsLearned;
    }

    public int getWordsLeft() {
        return wordsLeft;
    }

    public void setWordsLeft(int wordsLeft) {
        this.wordsLeft = wordsLeft;
    }

    public int getTestsTaken() {
        return testsTaken;
    }

    public void setTestsTaken(int testsTaken) {
        this.testsTaken = testsTaken;
    }

    public int getGamesCompleted() {
        return gamesCompleted;
    }

    public void setGamesCompleted(int gamesCompleted) {
        this.gamesCompleted = gamesCompleted;
    }
}
