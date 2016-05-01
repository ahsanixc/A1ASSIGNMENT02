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
public class Movement {
    char [][] board;
    Vector plyOne;
    Vector plyTwo;
    Vector plyThree;
    Vector root;
    
    void Move(Board b){
        Vector plyTemp = new Vector();
        root = new Vector();
        plyOne = new Vector();
        plyTwo = new Vector();
        plyThree = new Vector();
        int counter = 0;
        
        root.add(b);
        plyTemp = (Vector) root.clone();
        
        generateChild(plyTemp, root, plyOne, 'X');
        
        plyTemp.removeAllElements();
        plyTemp = (Vector) plyOne.clone();
        
        generateChild(plyTemp, plyOne, plyTwo, 'O');
        plyTemp.removeAllElements();
        plyTemp = (Vector) plyTwo.clone();
        
        generateChild(plyTemp, plyTwo, plyThree, 'X');
        int nodes = plyThree.size();
        
        plyTemp.removeAllElements();
        plyTemp = (Vector) plyThree.clone();
        applyEvaluate(plyTemp, b);
        
        Board [] parent = new Board [plyTwo.size()];
        Board [] child = new Board [plyThree.size()];
        
        Vector plyTWO = (Vector) plyTwo.clone();
        Vector plyTHREE = (Vector) plyThree.clone();
        
        counter = 0;
        
        while(plyTWO.isEmpty() != true){
            parent[counter] = (Board) plyTWO.firstElement();
            plyTWO.remove(0);
            counter++;
        }
        
        counter = 0;
        
        while(plyTHREE.isEmpty() != true){
            child[counter] = (Board) plyTHREE.firstElement();
            plyTHREE.remove(0);
            counter++;
        }
       
        plyTwo = minMax(plyTwo, plyThree, parent, child, false, true);
        
        plyTWO = (Vector) plyTwo.clone();
        Vector plyONE = (Vector) plyOne.clone();
        
        counter = 0;
        
        while(plyTWO.isEmpty() != true){
            child[counter] = (Board) plyTWO.firstElement();
            plyTWO.remove(0);
            counter++;
        }
        
        counter = 0;
        
        while(plyONE.isEmpty() != true){
            parent[counter] = (Board) plyONE.firstElement();
            plyONE.remove(0);
            counter++;
        }
        
        plyOne = minMax(plyOne, plyTwo, parent, child, true, false);
        
        plyONE = (Vector) plyOne.clone();
        Vector ROOT = (Vector) root.clone();
        
        counter = 0;
        
        while(plyONE.isEmpty() != true){
            child[counter] = (Board) plyONE.firstElement();
            plyONE.remove(0);
            counter++;
        }
        
        counter = 0;
        
        while(ROOT.isEmpty() != true){
            parent[counter] = (Board) ROOT.firstElement();
            ROOT.remove(0);
            counter++;
        }
        
        root = minMax(root, plyOne, parent, child, true, false);
        Board x = (Board) root.firstElement();
        
        System.out.println("NODES GENERATED: " + nodes);
        System.out.println("MIN MAX: " +x.getValue());
    }
    
    Vector minMax(Vector plyParent, Vector plyChild, Board [] parent, Board [] child, boolean MIN, boolean MAX){
        Board b1 = new Board();
        Board b2 = new Board();
        int min = 99999;
        int max = -99999;
        int posi = 0;
        int posj = 0;
        
        if(MIN){
            while(plyChild.isEmpty() != true){
                
                if(plyChild.size() <= 2){
                    min = 99999;
                    b1 = (Board) plyChild.lastElement();
                    plyChild.remove(plyChild.size()-1);
                    
                    for(int i = 0 ; i < child.length; i++){
                        if(child[i] == b1)
                            posi = i;
                        
                        if(child[i] == b2)
                            posj = i;
                    }
                    
                    if(child[posi].getValue() < child[posj].getValue())
                        min = child[posi].getValue();
                    
                    else
                        min = child[posj].getValue();
                    
                    for(int i = 0 ; i < parent.length; i++){
                        if(parent[i] == b2.getParent()){
                            parent[i].setValue(min);
                        }
                    }
                }
                
                else{
                    min = 99999;
                    b1 = (Board) plyChild.lastElement();
                    
                    for(int i = 0 ; i < child.length; i++){
                        if(child[i] == b1)
                            posi = i;
                    }
                    
                    if(child[posi].getValue() < min){
                        min = child[posi].getValue();
                        for(int i = 0 ; i < parent.length; i++){
                        if(parent[i] == b1.getParent()){
                            parent[i].setValue(min);
                        }
                    }
                    }
                    plyChild.remove(plyChild.size()-1);
                    b2 = (Board) plyChild.lastElement();
                }
                while(b1.getParent() == b2.getParent() && plyChild.size() >= 2){
                    
                    for(int i = 0 ; i < child.length; i++){
                        if(child[i] == b2)
                            posj = i;
                    }
                    
                    if(child[posj].getValue() < min){
                        min = child[posj].getValue();
                        for(int i = 0 ; i < parent.length; i++){
                        if(parent[i] == b2.getParent()){
                            parent[i].setValue(min);
                        }
                    }
                    }

                    plyChild.remove(plyChild.size()-1);
                    b2 = (Board) plyChild.lastElement();
                }
            }
        }
        
        else if(MAX){
            while(plyChild.isEmpty() != true){
                if(plyChild.size() <= 2){
                    max = -99999;
                    b1 = (Board) plyChild.lastElement();
                    plyChild.remove(plyChild.size()-1);
                    
                    for(int i = 0 ; i < child.length; i++){
                        if(child[i] == b1)
                            posi = i;
                        
                        if(child[i] == b2)
                            posj = i;
                    }
                    
                    
                    if(child[posi].getValue() > child[posj].getValue())
                        max = child[posi].getValue();
                    
                    else
                        max = child[posj].getValue();
                    
                    for(int i = 0 ; i < parent.length; i++){
                        if(parent[i] == b2.getParent()){
                            parent[i].setValue(max);
                        }
                    }
                }
                
                else{
                    max = -99999;
                    b1 = (Board) plyChild.lastElement();
                    
                    for(int i = 0 ; i < child.length; i++){
                        if(child[i] == b1)
                            posi = i;
                    }
                    if(child[posi].getValue() > max){
                        max = child[posi].getValue();
                        for(int i = 0 ; i < parent.length; i++){
                        if(parent[i] == b1.getParent()){
                            parent[i].setValue(max);
                        }
                    }
                    }
                    plyChild.remove(plyChild.size()-1);
                    b2 = (Board) plyChild.lastElement();
                }
                while(b1.getParent() == b2.getParent() && plyChild.size() >= 2){
                    
                    for(int i = 0 ; i < child.length; i++){
                        
                        if(child[i] == b2)
                            posj = i;
                    }
                    
                    if(child[posj].getValue() > max){
                        for(int i = 0 ; i < parent.length; i++){
                        if(parent[i] == b2.getParent()){
                            parent[i].setValue(max);
                        }
                    }
                    }

                    plyChild.remove(plyChild.size()-1);
                    b2 = (Board) plyChild.lastElement();
                }
            }
        }
        plyParent.removeAllElements();
        
        for(int i = 0; i < parent.length; i++){
            plyParent.add(parent[i]);
        }
        
        return plyParent;
    }
    
    void applyEvaluate(Vector plyTemp, Board b){
        Board board = new Board();
        while(plyTemp.isEmpty() != true){
            board = (Board) plyTemp.firstElement();
            board.evaluate();
            plyTemp.remove(0);
        }
    }
    
    void generateChild(Vector plyTemp, Vector plyPrev, Vector plyNext, char p){
        Board b;
        while(plyTemp.isEmpty() != true){
            b = (Board) plyTemp.firstElement();
            int [] c1 = b.findXO(1, p);
            int [] c2 = b.findXO(2, p);
            int [] c3 = b.findXO(3, p);

            Board upc1 = new Board();
            upc1.setCurrentBoard(b.getBoard());
            if(upc1.moveUP(c1)){
                upc1.setParent(b);
                plyNext.add(upc1);
                System.out.println("X1 UP TRUE:");
                
                upc1.printBoard();
            }

            Board downc1 = new Board();
            downc1.setCurrentBoard(b.getBoard());

            if(downc1.moveDOWN(c1)){
                if(!plyPrev.contains(downc1)){
                    downc1.setParent(b);
                    plyNext.add(downc1);
                    System.out.println("X1 DOWN TRUE");
                    
                    downc1.printBoard();
                }
            }

            Board leftc1 = new Board();
            leftc1.setCurrentBoard(b.getBoard());

            if(leftc1.moveLEFT(c1)){
                leftc1.setParent(b);
                plyNext.add(leftc1);
                System.out.println("X1 LEFT TRUE:");
                
                leftc1.printBoard();
            }

            Board rightc1 = new Board();
            rightc1.setCurrentBoard(b.getBoard());

            if(rightc1.moveRIGHT(c1)){
                rightc1.setParent(b);
                plyNext.add(rightc1);
                System.out.println("X1 RIGHT TRUE:");
                
                rightc1.printBoard();
            }

            Board upc2 = new Board();
            upc2.setCurrentBoard(b.getBoard());

            if(upc2.moveUP(c2)){
                upc2.setParent(b);
                plyNext.add(upc2);
                System.out.println("X2 UP TRUE:");
                
                upc2.printBoard();
            }

            Board downc2 = new Board();
            downc2.setCurrentBoard(b.getBoard());

            if(downc2.moveDOWN(c2)){
                downc2.setParent(b);
                plyNext.add(downc2);
                System.out.println("X2 DOWN TRUE:");
                
                downc2.printBoard();
            }

            Board leftc2 = new Board();
            leftc2.setCurrentBoard(b.getBoard());

            if(leftc2.moveLEFT(c2)){
                leftc2.setParent(b);
                plyNext.add(leftc2);
                System.out.println("X2 LEFT TRUE:");
                
                leftc2.printBoard();
            }

            Board rightc2 = new Board();
            rightc2.setCurrentBoard(b.getBoard());

            if(rightc2.moveRIGHT(c2)){
                rightc2.setParent(b);
                plyNext.add(rightc2);
                System.out.println("X2 RIGHT TRUE:");
                
                rightc2.printBoard();
            }

            Board upc3 = new Board();
            upc3.setCurrentBoard(b.getBoard());

            if(upc3.moveUP(c3)){
                upc3.setParent(b);
                plyNext.add(upc3);
                System.out.println("X3 UP TRUE:");
                
                upc3.printBoard();
            }

            Board downc3 = new Board();
            downc3.setCurrentBoard(b.getBoard());

            if(downc3.moveDOWN(c3)){
                downc3.setParent(b);
                plyNext.add(downc3);
                System.out.println("X3 DOWN TRUE:");
                
                downc3.printBoard();
            }

            Board leftc3 = new Board();
            leftc3.setCurrentBoard(b.getBoard());

            if(leftc3.moveLEFT(c3)){
                leftc3.setParent(b);
                plyNext.add(leftc3);
                System.out.println("X3 LEFT TRUE:");
                
                leftc3.printBoard();
            }

            Board rightc3 = new Board();
            rightc3.setCurrentBoard(b.getBoard());

            if(rightc3.moveRIGHT(c3)){
                rightc3.setParent(b);
                plyNext.add(rightc3);
                System.out.println("X3 RIGHT TRUE:");
                
                rightc3.printBoard();
            }
            
            plyTemp.remove(0);
        }
    }
}
