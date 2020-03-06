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
import java.util.Scanner;

public class CrosswordBuilder{

    public static char [][] board;
    public static void main(String[] argv){
        board = new char[20][20];
        for(int i=0; i<board.length;i++){
            for(int j=0; j<board.length;j++){
                board[i][j]=' ';
            }
        }
        Grid grid = new Grid(20);
        boolean stillBuilding = true;
        Scanner sc = new Scanner(System.in);
        int wordsAdded = 0;
        while(wordsAdded<10){
            boolean cordsFound = false;
            //Word word =  new Word();
            while(!cordsFound){
            //getarandomword with some criteria
            String w = sc.nextLine();
                if(wordsAdded == 0){
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
                                    for(int temp =i;temp<w.length()-1;){
                                        int newY = y+ temp - i;
                                        if(newY==board.length){
                                            cordsValid=false;
                                            break;
                                        }
                                        if(belowFree(x, newY)){
                                            if(temp==i){
                                                temp++;
                                                continue;
                                            }
                                            temp++;
                                            if(belowFree(x, newY) && sidesFree(x, newY)){
                                                if(newY==board.length-1){
                                                    cordsValid=false;
                                                    break;
                                                }
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
                                    for(int temp = i; temp>0;){
                                        int newY = y+ temp - i;
                                        if(newY<0){
                                            cordsValid=false;
                                            break;
                                        }
                                        if(aboveFree(x, newY)){
                                            if(temp==i){
                                                temp--;
                                                continue;
                                            }
                                            temp--;
                                            if(aboveFree(x, newY) && sidesFree(x, newY)){
                                                if(newY==0){
                                                    cordsValid=false;
                                                    break;
                                                }
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
                                    for(int temp =i;temp<w.length()-1;){
                                        int newX = x+ temp - i;
                                        if(newX==board.length){
                                            cordsValid=false;
                                            break;
                                        }
                                        if(rightFree(newX, y)){
                                            if(temp==i){
                                                temp++;
                                                continue;
                                            }
                                            temp++;
                                            if(rightFree(newX, y) && aboveBelowFree(newX, y)){
                                                if(newX==board.length-1){
                                                    cordsValid=false;
                                                    break;
                                                }
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
                                    for(int temp = i; temp>0;){
                                        int newX = x+ temp - i;
                                        if(newX<0){
                                            cordsValid=false;
                                            break;
                                        }
                                        if(leftFree(newX, y)){
                                            if(temp==i){
                                                temp--;
                                                continue;
                                            }
                                            temp--;
                                            if(leftFree(newX, y) && aboveBelowFree(newX, y)){
                                                if(newX==0){
                                                    cordsValid=false;
                                                    break;
                                                }
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
        for(int i =0; i<board.length;i++){
            for(int j =0; j<board.length; j++){
                if(board[i][j]==' ')
                    System.out.print("* ");
                else
                    System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static boolean sidesFree(int x, int y){
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
    public static boolean aboveBelowFree(int x, int y){
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
    public static boolean aboveFree(int x, int y){
        if(y==0)
            return true;
        if(board[y-1][x]==' '){
            return true;
        }
        return false;
    }
    public static boolean belowFree(int x, int y){
        if(y==board.length-1)
            return true;
        if(board[y+1][x]==' ')
            return true;
        return false;
    }
    public static boolean leftFree(int x, int y){
        if(x==0)
            return true;
        if(board[y][x-1]==' ')
            return true;
        return false;
    }
    public static boolean rightFree(int x, int y){
        if(x==board.length-1)
            return true;
        if(board[y][x+1]==' '){
            return true;
        }
        return false;
    }
}



