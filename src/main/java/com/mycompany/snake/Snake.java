/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;

import static com.mycompany.snake.Direction.DOWN;
import static com.mycompany.snake.Direction.LEFT;
import static com.mycompany.snake.Direction.RIGHT;
import static com.mycompany.snake.Direction.UP;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alu10701951
 */
public class Snake {
    private List<Node> list;
    private Direction direction;
    private int initialNode;
    private int toGrow;
    
    public Snake(Direction direction, int initialNode) {
        this.direction = direction;
        this.initialNode = initialNode;
        initilSnake(initialNode);
    }

    private void initilSnake(int initialNode) {
        int rows = ConfigData.instance.setBoardRowCol();
        int cols = ConfigData.instance.setBoardRowCol();
        int count = cols/4;
        list = new ArrayList<>();
        for (int i = 0; i < initialNode; i++) {
            Node node = new Node(rows/2, count);
            list.add(node);
            count--;
        }
    }
    
    public void printSnake(Graphics g, int squareWidth, int squareHeight){
        boolean isHead = true;
        Node nodePrint;
        for(int i = 0; i < list.size(); i++){
            nodePrint = list.get(i);
            if(isHead){
                SquareType squareType = SquareType.HEAD;
                Util.drawSquare(g, nodePrint.getRow(), nodePrint.getCol(), squareWidth, squareHeight, squareType);
                isHead = false;
            }else{
                 SquareType squareType = SquareType.BODY;
                Util.drawSquare(g, nodePrint.getRow(), nodePrint.getCol(), squareWidth, squareHeight, squareType);
            }
        }
    }
    
    public boolean containSnake(int row,int col){
        for(int i = 0; i < list.size(); i++){
            Node nodeContains = list.get(i);
            if(row == nodeContains.getRow() && col == nodeContains.getCol() ){
                return true;
            }
        }
        return false;
    }
    
    public void  move(){
        int row, col;
        Node node;
        row = list.get(0).getRow();
        col = list.get(0).getCol();
        switch(direction){
            case UP:
                node =  new Node(row - 1, col);
                list.add(0,node);
                break;
            case DOWN:
                node =  new Node(row + 1, col);
                list.add(0,node);
                break;
            case RIGHT:
                node =  new Node(row, col + 1);
                list.add(0,node);
                break;
            case LEFT:
                node =  new Node(row, col - 1);
                list.add(0,node);
                break;
              
        }
        if(toGrow <= 0){
            list.remove(list.size() -1);
            
        }else{
            toGrow--;
        }
        
    }

    
    public boolean canMove(){
        
        int row = list.get(0).getRow();
        int col = list.get(0).getCol();
        
        switch(direction){
            case UP:
                if(row - 1 < 0 || containSnake(row - 1, col)){
                    return false;
                }
                break;
            case DOWN:
                if(row + 1 >= ConfigData.instance.setBoardRowCol() || containSnake( row + 1, col)){
                    return false;
                }
                break;
            case RIGHT:
                if(col + 1 >= ConfigData.instance.setBoardRowCol() || containSnake(row, col + 1)){
                    return false;
                }
                break;
            case LEFT:
                if(col - 1 < 0 || containSnake(row, col - 1)){
                    return false;
                }
                break;
              
        }
        return true;
    }
 
    
    public boolean eatFood(Food food){
        if(food == null){
            return false;
        }
        int foodRow= food.getRow();
        int foodCol = food.getCol();
        int headNodeRow = list.get(0).getRow();
        int headNodeCol = list.get(0).getCol();
        if(foodRow == headNodeRow && foodCol == headNodeCol){
            toGrow += food.nodesWhenEat();
            return true;
        }
        return false;
    }
    
    public void setToGrow(int toGrow) {
        this.toGrow = toGrow;
    }
    
    
    /*public void openMouth(Food food){
 int foodRow= food.getRow();
        int foodCol = food.getCol();
        int headNodeRow = list.get(0).getRow();
        int headNodeCol = list.get(0).getCol();
        switch(direction){
            case UP:
                if(headNodeRow - 1 == foodRow && headNodeCol == foodCol){
                    return false;
                }
                break;
            case DOWN:
                if(headNodeRow + 1 == foodRow && headNodeCol == foodCol){
                    return false;
                }
                break;
            case RIGHT:
                if(headNodeCol + 1 == foodCol && headNodeRow == foodRow){
                    return false;
                }
                break;
            case LEFT:
                if(headNodeCol - 1 == foodCol && headNodeRow == foodRow){
                    return false;
                }
                break;
              
        }
        return true;
    }*/

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    
}
