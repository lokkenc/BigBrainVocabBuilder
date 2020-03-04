package com.csci412.bigbrainvocabbuilder;

import java.util.ArrayList;
import java.util.Random;

public class WordSearch {
    int numRows;
    int numColumns;
    WSCoordinate[][] grid;
    ArrayList<String> words;

    public WordSearch(int numRows, int numColumns, ArrayList<String> words) {
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.words = words;
        grid = new WSCoordinate[numRows][numColumns];
        initGrid();
        populateGrid();
    }

    public void initGrid() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                grid[i][j] = new WSCoordinate(i, j);
            }
        }
    }

    public void populateGrid() {
        int wordsInserted = 0; // also used as index of word array
        int tries = 0;
        int wordCount = words.size();

        Random rand = new Random();

        while (wordsInserted < wordCount && tries < 1000) {
            Boolean horizontal = (rand.nextInt()%2 == 1);

            int reverse = rand.nextInt()%2;
            if (reverse == 1) {
                //Reverse current word
                words.set(wordsInserted, new StringBuilder(words.get(wordsInserted)).reverse().toString());
            }

            //Word is to be entered horizontally
            if (horizontal) {
                int possibleColumn = numColumns - words.get(wordsInserted).length();
                int rowTarget = rand.nextInt(numRows);
                int columnTarget = rand.nextInt(possibleColumn);
                if (isRoom(rowTarget, columnTarget, words.get(wordsInserted).length(), horizontal)) {
                    insertWord(rowTarget, columnTarget, words.get(wordsInserted), horizontal);
                    wordsInserted++;
                } else {
                    tries++;
                }
            } else {
                int possibleRow = numRows - words.get(wordsInserted).length();
                int rowTarget = rand.nextInt(possibleRow);
                int columnTarget = rand.nextInt(numColumns);
                if (isRoom(rowTarget, columnTarget, words.get(wordsInserted).length(), horizontal)) {
                    insertWord(rowTarget, columnTarget, words.get(wordsInserted), horizontal);
                    wordsInserted++;
                } else {
                    tries++;
                }
            }
        }

        fillEmptySpace();
    }

    private Boolean isRoom(int row, int column, int length, Boolean horizontal) {
        if (horizontal) {
            for (int i = 0; i < length; i++) {
                if (grid[row][column+i].getLetter() != ' ') {
                    return false;
                }
            }
            return true;
        } else {
            for (int i = 0; i < length; i++) {
                if (grid[row+i][column].getLetter() != ' ') {
                    return false;
                }
            }
            return true;
        }
    }

    private void insertWord(int row, int column, String word, Boolean horizontal) {
        if (horizontal) {
            for (int i = 0; i < word.length(); i++) {
                grid[row][column+i].setLetter(word.charAt(i));
            }
        } else {
            for (int i = 0; i < word.length(); i++) {
                grid[row+i][column].setLetter(word.charAt(i));
            }
        }
    }
    private void fillEmptySpace() {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        Random rand = new Random();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (grid[i][j].getLetter() == ' ') {
                    char randomLetter = alphabet.charAt(rand.nextInt(alphabet.length()));
                    grid[i][j].setLetter(randomLetter);
                }
            }
        }
    }

    public String getWord(WSCoordinate endCoor1, WSCoordinate endCoor2) {
        String ret = "";


        if (endCoor1.getX() == endCoor2.getX()) {
            //Word is horizontal
            int x = endCoor1.getX();
            int i = Math.min(endCoor1.getY(), endCoor2.getY());
            while (i <= Math.max(endCoor1.getY(), endCoor2.getY())) {
                ret += grid[x][i].getLetter();
                i++;
            }

        } else if (endCoor1.getY() == endCoor2.getY()) {
            //Word is vertical
            int y = endCoor1.getY();
            int i = Math.min(endCoor1.getX(), endCoor2.getX());
            while (i <= Math.max(endCoor1.getX(), endCoor2.getX())) {
                ret += grid[i][y].getLetter();
                i++;
            }

        } else {
            //Word is diagonal
            Boolean isUpwards;
            int i;
            int j;
            WSCoordinate start;
            WSCoordinate end;
            if (endCoor1.getX() < endCoor2.getX()) {
                i = endCoor1.getX();
                j = endCoor1.getY();
                start = endCoor1;
                end = endCoor2;
            } else {
                i = endCoor2.getX();
                j = endCoor2.getY();
                start = endCoor2;
                end = endCoor1;
            }
            if (start.getY() < end.getY())
                isUpwards = true;
            else
                isUpwards = false;

            while (i != end.getX() && j != end.getY()) {
                ret += grid[i][j].getLetter();
                j++;
                if (isUpwards)
                    i++;
                else
                    i--;
            }
        }

        return ret;

    }

    public Boolean isWordInList(String word) {
        if (words.contains(word)) {
            return true;
        } else if (words.contains(new StringBuilder(word).reverse().toString())) {
            return true;
        }
        return false;
    }

//    public void printGrid() {
//        for (int i = 0; i < numRows; i++) {
//            for (int j = 0; j < numColumns; j++) {
//                System.out.print(grid[i][j].getLetter() + " ");
//            }
//            System.out.println();
//        }
//    }

}
