/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;

import java.awt.Graphics;
import java.util.Calendar;
import java.util.Random;
import javax.swing.Timer;

/**
 *
 * @author alu10701951
 */
public class SpecialFood extends Food{
    public static final int POINT = 15;
    
    public SpecialFood(Snake snake) {
        super(snake);
        Random r = new Random();
        int row = r.nextInt(ConfigData.instance.setBoardRowCol());
        int col = r.nextInt(ConfigData.instance.setBoardRowCol());
        while(snake.containSnake(row, col)){
            row = r.nextInt(ConfigData.instance.setBoardRowCol());
            col = r.nextInt(ConfigData.instance.setBoardRowCol());
        }
        setRow(row);
        setCol(col);
    }
 
    @Override
    public void printFood(Graphics g, int squareWidth, int squareHeight){
        FoodType foodType = FoodType.SPECIALFOOD;
        Util.drawImage(g, getRow(), getCol(), squareWidth, squareHeight, foodType);
    }
    
    @Override
    public void remove(){
       
    }
    
    @Override
    public int getPoints(){
        return POINT;
    }
    
    @Override
    public int nodesWhenEat(){
        return 3;
    }
}
