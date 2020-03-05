package com.csci412.bigbrainvocabbuilder;

import java.util.ArrayList;

public class WordSearch {
    int numRows;
    int numColumns;
    int difficulty;
    WSCoordinate[][] grid;
    //DatabaseManager databaseManager;

    public WordSearch(int numRows, int numColumns, int difficulty, ArrayList<String> words) {
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.difficulty = difficulty;
        grid = new WSCoordinate[numRows][numColumns];
        populateGrid(words);
    }

    public void populateGrid(ArrayList<String> words) {
        
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

}
