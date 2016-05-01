/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai2;

/**
 *
 * @author Samama
 */
public class AI2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Board board = new Board();
        board.setInitialBoard();
        
        board.randomPlace(board.getBoard(), 'X');
        board.randomPlace(board.getBoard(), 'O');
        board.randomPlace(board.getBoard(), 'X');
        board.randomPlace(board.getBoard(), 'O');
        board.randomPlace(board.getBoard(), 'X');
        board.randomPlace(board.getBoard(), 'O');
        
        board.printBoard();
        
        Movement move = new Movement();
        move.Move(board);
    }
    
}
