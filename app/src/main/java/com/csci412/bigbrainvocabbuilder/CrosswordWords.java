package com.csci412.bigbrainvocabbuilder;

public class CrosswordWords {

    int id;
    private int startingX;
    private int startingY;
    private String word;
    private char orientation;//v for vert h for hori

    public CrosswordWords(int id, String s, int x, int y, char orientation){
        startingX=x;
        startingY=y;
        word=s;
        this.orientation=orientation;
        this.id=id;
    }

    public char getOrientation() {
        return orientation;
    }

    public int getStartingX() {
        return startingX;
    }

    public int getStartingY() {
        return startingY;
    }

    public String getWord() {
        return word;
    }
}
