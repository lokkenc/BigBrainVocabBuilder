package com.csci412.bigbrainvocabbuilder;

public class WSCoordinate {
    int x;
    int y;
    char letter;

    public WSCoordinate(int x, int y, char letter) {
        this.x = x;
        this.y = y;
        this.letter = letter;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }
}
