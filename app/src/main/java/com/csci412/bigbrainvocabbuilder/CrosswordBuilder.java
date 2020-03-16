/*
 *  Crossword Builder
 *
 *
 *
 *
 *
 *
 *
 *
 */
package com.csci412.bigbrainvocabbuilder;
import java.util.Scanner;

public class CrosswordBuilder{

    private char [][] keyBoard;
    private int difficulty;


    public CrosswordBuilder(int diff){
        difficulty=diff;
        keyBoard=getBoard();
    }

    private char[][] getBoard(){
        char[][] board = new char[20][20];
        for(int i=0; i<board.length;i++){
            for(int j=0; j<board.length;j++){
                board[i][j]=' ';
            }
        }

        int wordsAdded = 0;
        int difficulty = 5;//set by user
        while(wordsAdded<this.difficulty){
            boolean cordsFound = false;
            //Word word =  new Word();
            while(!cordsFound){
                //getarandomword with some criteria
                String w = "";//breakdown of
                if(wordsAdded == 0){
                    if(5+w.length()>=board.length)
                        break;
                    for(int i = 0; i<w.length(); i++){
                        board[4+(wordsAdded*2)][4+i] = w.charAt(i);
                    }
                    wordsAdded++;
                    cordsFound=true;
                }
                else if(wordsAdded%2==1){//vert
                    for(int y =0; y<board.length;y++){
                        for(int x =0; x<board.length;x++){
                            for(int i=0;i<w.length();i++){
                                if(board[y][x]==w.charAt(i)){
                                    boolean cordsValid = true;
                                    for(int temp =i;temp<w.length();temp++){
                                        int newY = y+ temp - i;
                                        if(newY==board.length){
                                            cordsValid=false;
                                            break;
                                        }
                                        if(belowFree(board, x, newY)){
                                            if(temp==i)
                                                continue;

                                            if(belowFree(board, x, newY) && sidesFree(board, x, newY)){
                                                //nothing

                                            }
                                            else{
                                                cordsValid=false;
                                                break;
                                            }
                                        }
                                        else{
                                            cordsValid = false;
                                            break;
                                        }
                                    }
                                    if(cordsValid==false)
                                        continue;
                                    for(int temp = i; temp>=0;temp--){
                                        int newY = y+ temp - i;
                                        if(newY<0){
                                            cordsValid=false;
                                            break;
                                        }
                                        if(aboveFree(board, x, newY)){
                                            if(temp==i){
                                                continue;
                                            }
                                            if(aboveFree(board, x, newY) && sidesFree(board, x, newY)){
                                                //nothing
                                            }
                                            else{
                                                cordsValid=false;
                                                break;
                                            }
                                        }
                                        else{
                                            cordsValid = false;
                                            break;
                                        }
                                    }
                                    if(cordsValid==true){
                                        wordsAdded++;
                                        cordsFound=true;
                                        for(int temp =i;temp<w.length();temp++){
                                            board[temp+y-i][x]=w.charAt(temp);
                                        }
                                        for(int temp=i; temp>=0;temp--){
                                            board[temp+y-i][x]=w.charAt(temp);
                                        }
                                        break;
                                    }
                                }
                                if(cordsFound==true)
                                    break;
                            }
                            if(cordsFound==true)
                                break;
                        }
                        if(cordsFound==true)
                            break;
                    }

                }
                else{// hor
                    for(int y =0; y<board.length;y++){
                        for(int x =0; x<board.length;x++){
                            for(int i=0;i<w.length();i++){
                                if(board[y][x]==w.charAt(i)){
                                    boolean cordsValid = true;
                                    for(int temp =i;temp<w.length();temp++){
                                        int newX = x+ temp - i;
                                        if(newX==board.length){
                                            cordsValid=false;
                                            break;
                                        }
                                        if(rightFree(board, newX, y)){
                                            if(temp==i){
                                                continue;
                                            }
                                            if(rightFree(board, newX, y) && aboveBelowFree(board, newX, y)){
                                                //nothing
                                            }
                                            else{
                                                cordsValid=false;
                                                break;
                                            }
                                        }
                                        else{
                                            cordsValid = false;
                                            break;
                                        }
                                    }
                                    if(cordsValid==false)
                                        continue;
                                    for(int temp = i; temp>=0;temp--){
                                        int newX = x+ temp - i;
                                        if(newX<0){
                                            cordsValid=false;
                                            break;
                                        }
                                        if(leftFree(board,newX, y)){
                                            if(temp==i){
                                                continue;
                                            }
                                            if(leftFree(board, newX, y) && aboveBelowFree(board, newX, y)){
                                                //nothing
                                            }
                                            else{
                                                cordsValid=false;
                                                break;
                                            }
                                        }
                                        else{
                                            cordsValid = false;
                                            break;
                                        }
                                    }
                                    if(cordsValid==true){
                                        wordsAdded++;
                                        cordsFound=true;
                                        for(int temp =i;temp<w.length();temp++){
                                            board[y][temp+x-i]=w.charAt(temp);
                                        }
                                        for(int temp=i; temp>=0;temp--){
                                            board[y][temp+x-i]=w.charAt(temp);
                                        }
                                        break;
                                    }
                                }
                                if(cordsFound==true)
                                    break;
                            }
                            if(cordsFound==true)
                                break;
                        }
                        if(cordsFound==true)
                            break;
                    }
                }
            }


        }
        return board;
    }

    private static boolean sidesFree(char[][] board, int x, int y){
        if(x==0){
            if(board[y][x+1]==' ')
                return true;
        }
        else if(x==board.length-1){
            if(board[y][x-1]==' ')
                return true;
        }
        else if(board[y][x-1]==' ' && board[y][x+1]==' '){
            return true;
        }
        return false;
    }
    private static boolean aboveBelowFree(char[][] board, int x, int y){
        if(y==0){
            if(board[y+1][x]==' ')
                return true;
        }
        else if (y==board.length-1){
            if(board[y-1][x]==' ')
                return true;
        }
        else if(board[y-1][x]==' ' && board[y+1][x]==' '){
            return true;
        }
        return false;
    }
    private static boolean aboveFree(char[][] board, int x, int y){
        if(y==0)
            return true;
        if(board[y-1][x]==' '){
            return true;
        }
        return false;
    }
    private static boolean belowFree(char[][] board, int x, int y){
        if(y==board.length-1)
            return true;
        if(board[y+1][x]==' ')
            return true;
        return false;
    }
    private static boolean leftFree(char[][] board, int x, int y){
        if(x==0)
            return true;
        if(board[y][x-1]==' ')
            return true;
        return false;
    }
    private static boolean rightFree(char[][] board, int x, int y){
        if(x==board.length-1)
            return true;
        if(board[y][x+1]==' '){
            return true;
        }
        return false;
    }
}



