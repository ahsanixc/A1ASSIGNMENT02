/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai2;

import java.util.Random;
import java.util.Vector;

/**
 *
 * @author Samama
 */
public class Board {
    private char [][] board;
    private int value;
    private Board parent;
    private Vector plyOne;
    private Vector plyTwo;
    
    Board(){
        board = new char [3][3];
    }
    
    void setCurrentBoard(char [][] b){
            for(int i = 0 ; i < 3 ; i++){
                System.arraycopy(b[i], 0, board[i], 0, 3);
            }
    }
    
    void setValue(int v){
        this.value = v;
    }
    
    int getValue (){
        return this.value;
    }
    
    void setParent(Board p){
        this.parent = p;
    }
    
    Board getParent(){
        return this.parent;
    }
    
    char [][] getBoard(){
        return this.board;
    }
    
    void setInitialBoard(){
        for(int i = 0 ; i < 3 ; i++){
            for(int j = 0 ; j < 3 ; j++){
                board[i][j] = '.';
            }
        }
    }
    
    void randomPlace(char [][] b, char p){
        Random random = new Random();
        int i = Math.abs(random.nextInt() % 3);
        int j = Math.abs(random.nextInt() % 3);
        
        while(b[i][j] != '.'){
            i = Math.abs(random.nextInt() % 3);
            j = Math.abs(random.nextInt() % 3);
        }
        
        b[i][j] = p;
    }
    
     boolean moveUP(int [] pos){
        int i = pos[0];
        int j = pos[1];
        
        if(i-1 < 0 || board[i-1][j] != '.'){
            return false;
        }else{
            char temp = board[i][j];
            board[i][j] = board[i-1][j];
            board[i-1][j] = temp;
            return true;
        }
    }
    
    boolean moveDOWN(int [] pos){
        int i = pos[0];
        int j = pos[1];
        
        if(i+1 >= 3 || board[i+1][j] != '.') {
            return false;
        }else{
            char temp = board[i][j];
            board[i][j] = board[i+1][j];
            board[i+1][j] = temp;
            return true;
        }
    }
    
    boolean moveLEFT(int [] pos){
        int i = pos[0];
        int j = pos[1];
        
        if(j-1 < 0 || board[i][j-1] != '.'){
            return false;
        }else{
            char temp = board[i][j];
            board[i][j] = board[i][j-1];
            board[i][j-1] = temp;
            return true;
        }
    }
    
    boolean moveRIGHT(int [] pos){
        int i = pos[0];
        int j = pos[1];
        
        if(j+1 >= 3 || board[i][j+1] != '.'){
            return false;
        }else{
            char temp = board[i][j];
            board[i][j] = board[i][j+1];
            board[i][j+1] = temp;
            return true;
        }
    }
    
    int [] findXO(int count, char player){
        int[] pos = new int[2];
        char symbol;
        int counter = 0;
        
        if(player == 'X')
            symbol = '#';
        
        else
            symbol = '$';
        
          for (int i = 0 ; i < 3 ; i++)
           for(int j = 0 ; j < 3 ; j++)
         {
            if ( this.board[i][j] == player || this.board[i][j] == symbol)
            {
                 pos[0] = i;
                 pos[1] = j;
                 counter += 1;

                 if(counter == count)
                 return pos;
            }
        } 
        return null;
    }
    
    boolean [] findXO(char[][] b, char p, int row, int col){
        boolean [] flag = new boolean [2];
        flag[0] = true;
        flag[1] = true;
        for(int j = 0 ; j < 3; j++){
            if(b[row][j] == p)
                flag[0] = false;
        }
        for(int i = 0 ; i < 3; i++){
            if(b[i][col] == p)
                flag[1] = false;
        }
        
        return flag;
    }
    
    void evaluate(){
        // (3X2 + X1) - (3O2 + O1)
        
        int X1 = 0, X2 = 0, O1 = 0, O2 = 0;
        int X[] = new int [2];
        int O[] = new int [2];
        
//////////////////////////////////////////////////////////////////////// FOR X ///////////////////////////////////////////////////////////////////////////        
        int [] x1pos = findXO(1, 'X');
        boolean [] flagx1 = findXO(this.getBoard(), 'O', x1pos[0], x1pos[1]);
        X = evaluateHelper(flagx1, this, x1pos, 'X', X1, X2);
        X1 = X[0];
        X2 = X[1];
        
        
        int [] x2pos = findXO(2, 'X');
        boolean [] flagx2 = findXO(this.getBoard(), 'O', x2pos[0], x2pos[1]);
        X = evaluateHelper(flagx2, this, x2pos, 'X', X1, X2);
        X1 = X[0];
        X2 = X[1];
        
        int [] x3pos = findXO(3, 'X');
        boolean [] flagx3 = findXO(this.getBoard(), 'O', x3pos[0], x3pos[1]);
        X = evaluateHelper(flagx3, this, x3pos, 'X', X1, X2);
        X1 = X[0];
        X2 = X[1];
        
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   
        this.settleBoard(this.getBoard());
//////////////////////////////////////////////////////////////////////// FOR Y ///////////////////////////////////////////////////////////////////////////

        int [] o1pos = findXO(1, 'O');
        boolean [] flago1 = findXO(this.getBoard(), 'X', o1pos[0], o1pos[1]);
        O = evaluateHelper(flago1, this, o1pos, 'O', O1, O2);
        O1 = O[0];
        O2 = O[1];
        
        int [] o2pos = findXO(2, 'O');
        boolean [] flago2 = findXO(this.getBoard(), 'X', o2pos[0], o2pos[1]);
        O = evaluateHelper(flago2, this, o2pos, 'O', O1, O2);
        O1 = O[0];
        O2 = O[1];
        
        int [] o3pos = findXO(3, 'O');
        boolean [] flago3 = findXO(this.getBoard(), 'X', o3pos[0], o3pos[1]);
        O = evaluateHelper(flago3, this, o3pos, 'O', O1, O2);
        O1 = O[0];
        O2 = O[1];
        this.settleBoard(this.getBoard());
        
        int val = ((3*X2) + X1) - ((3*O2) + O1);
        this.setValue(val);
    }
    
    int [] evaluateHelper(boolean flag[], Board b, int varpos[], char p, int VAR1, int VAR2){
        char symbol;
        boolean rowSymbol = false;
        boolean colSymbol = false;
        
        if(p == 'X')
            symbol = '#';
        else
            symbol = '$';
        
         //No O in row
        if (flag[0] != false){
            boolean check = false;
            int no_of_var = 0;
  
            for(int col = 0 ; col < 3 ; col++){
                if(b.getBoard()[varpos[0]][col] == p)
                    no_of_var++;
                
                if(b.getBoard()[varpos[0]][col] == symbol)
                    check = true;
            }
            
            if(no_of_var == 2){
                    rowSymbol = true;
                    VAR2++;
            }
            
            else if(no_of_var == 1 && check != true){
                VAR1++;
            }
        }
        
        //No O in col
        if(flag[1] != false){
            boolean check = false;
            int no_of_var = 0;
            for(int row = 0 ; row < 3 ; row++){
                if(b.getBoard()[row][varpos[1]] == p)
                    no_of_var++;
                
                if(b.getBoard()[row][varpos[1]] == symbol)
                    check = true;
            }
            
            if(no_of_var == 2){
                    colSymbol = true;
                    VAR2++;
            }
            
            else if(no_of_var == 1 && check != true){
                VAR1++;
            }
        }
        
        if(rowSymbol == true)
            b.getBoard()[varpos[0]][varpos[1]] = symbol;
        
        if(colSymbol == true)
            b.getBoard()[varpos[0]][varpos[1]] = symbol;
                
        int VAR[] = new int [2];
        VAR[0] = VAR1;
        VAR[1] = VAR2;
        return VAR;
    }
    
    void settleBoard(char [][] b){
        for(int i = 0 ; i < 3; i++){
            for(int j = 0; j < 3; j++){
                
                if(board[i][j] == '#')
                    board[i][j] = 'X';
                
                else if(board[i][j] == '$')
                    board[i][j] = 'O';
            }
        }
    }
    
    void printBoard(){
        for(int i = 0 ; i < 3 ; i++){
            System.out.println("");
            for(int j = 0 ; j < 3 ; j++){
                System.out.print(" " + board[i][j]);
            }
        }
        System.out.println("");
        System.out.println("");
    }
}
