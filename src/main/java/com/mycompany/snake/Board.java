/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author alu1070195prints1
 */
public class Board extends javax.swing.JPanel implements InitGamer{

    //public static final int NUM_ROWS = 20;
    //public static final int NUM_COLS = 20;

    private Timer timer;
    private Snake snake;
    private Food food;
    private FoodFactory foodFactory;
    private MyKeyAdapter keyAdapter;
    private int deltaTime;
    private List<Direction> movements;
    private int highScore;
    //private boolean gameOver;
    private Incrementer incrementer;

    class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (snake.getDirection() != Direction.RIGHT) {

                        snake.setDirection(Direction.LEFT);
                        movements.add(Direction.LEFT);
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (snake.getDirection() != Direction.LEFT) {
                        snake.setDirection(Direction.RIGHT);
                        movements.add(Direction.RIGHT);
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (snake.getDirection() != Direction.DOWN) {
                        snake.setDirection(Direction.UP);
                        movements.add(Direction.UP);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (snake.getDirection() != Direction.UP) {
                        snake.setDirection(Direction.DOWN);
                        movements.add(Direction.DOWN);

                    }
                    break;
                default:
                    break;
            }
            repaint();
        }
    }

    public Board() {
        initComponents();
        myInit();
    }

    public void initGame(){
        removeKeyListener(keyAdapter);
        snake = new Snake(Direction.RIGHT, 4);
        movements = new Vector<>(2);
        food = generateFood();
        addKeyListener(keyAdapter);
        setDeltaTime();
        incrementer.resetScore();
        timer.start();
        repaint();
    }
    
    private void myInit() {
        foodFactory = new FoodFactory();      
        //gameOver = false;
        keyAdapter = new MyKeyAdapter();      
        setFocusable(true);
        timer = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                tick();
            }
        });
        highScore = 0;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int squareWidth = getWidth() / ConfigData.instance.setBoardRowCol();
        int squareHeight = getHeight()/ ConfigData.instance.setBoardRowCol();
        
        for(int row = 0; row < ConfigData.instance.setBoardRowCol(); row++){
            for(int col = 0; col < ConfigData.instance.setBoardRowCol(); col++){
                if((row + col) % 2 == 0){
                    g.setColor(new Color(210,210,210));
                }else{
                    g.setColor(new Color(180,180,180));
                }
                g.fillRect(col*squareWidth, row*squareHeight, squareWidth, squareHeight);
            }
        }
        if (snake != null) {
            snake.printSnake(g, squareWidth(), squareHeight());

        }
        if (food != null) {
            food.printFood(g, squareWidth(), squareHeight());
        }
       
        /*if(gameOver){
            paintGameOver(g);
        }*/
        //Toolkit.getDefaultToolkit().sync();
    }

    private void tick() {
        if(movements.size() != 0){
            Direction dir = movements.get(0);
            snake.setDirection(dir);
            movements.remove(0);
        }
        if (snake.canMove()) {
            snake.move();
            if (snake.eatFood(food)) {
                incrementer.incrementScore(food.getPoints());
                food = generateFood();
            }
        } else {
            processGameOver();
            //gameOver = true;
        }
        repaint();
        Toolkit.getDefaultToolkit().sync();
    }

    private Food generateFood() {
        return foodFactory.getFood(snake);
    }
    
    public void setDeltaTime() {
        switch (ConfigData.instance.getlevel()) {
            case 0:
                deltaTime = 350;
                break;
            case 1:
                deltaTime = 250;
                break;
            case 2:
                deltaTime = 150;
                break;
            default:
                throw new AssertionError();
        }
        timer.setDelay(deltaTime);
    }

    public int squareWidth() {
        return getWidth() / ConfigData.instance.setBoardRowCol();
    }

    public int squareHeight() {
        return getHeight() / ConfigData.instance.setBoardRowCol();
    }
    
    public void setIncrementer(Incrementer incrementer) {
        this.incrementer = incrementer;
    }
    
    public void pausedGame(){
        timer.stop();
    }
    
    public void continueGame(){
        timer.start();
    }
    
    public void processGameOver() {
        timer.stop();
        removeKeyListener(keyAdapter);
       JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
       GameOverDialog gameOverDialog = new GameOverDialog(topFrame, true);
       gameOverDialog.setInitGamer(this);
       gameOverDialog.setScore(incrementer.getScore());
       if(incrementer.getScore()> highScore){
           highScore = incrementer.getScore();
       }
       gameOverDialog.setName();
       gameOverDialog.setHighScore(highScore);
       gameOverDialog.setVisible(true);
       
   }
    
    /*private void paintGameOver(Graphics g){
        //G
        Util.drawSquare(g, 3, 3, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 3, 4, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 3, 5, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 3, 6, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 4, 3, squareWidth(), squareHeight(), SquareType.GAMEOVER);
         Util.drawSquare(g, 5, 3, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 6, 3, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 7, 3, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 8, 3, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 8, 4, squareWidth(), squareHeight(), SquareType.GAMEOVER);
         Util.drawSquare(g, 8, 5, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 8, 6, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 7, 6, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 6, 6, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 6, 5, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        //A
        Util.drawSquare(g, 4, 7, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 5, 7, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 6, 7, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 7, 7, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 8, 7, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 4, 10, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 5, 10, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 6, 10, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 7, 10, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 8, 10, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 3, 8, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 3, 9, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 6, 8, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 6, 9, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        //M
        Util.drawSquare(g, 3, 11, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 3, 14, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 4, 11, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 5, 11, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 6, 11, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 7, 11, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 8, 11, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 4, 14, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 5, 14, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 6, 14, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 7, 14, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 8, 14, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 4, 12, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 4, 13, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        //E
        Util.drawSquare(g, 3, 15, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 3, 16, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 3, 17, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 3, 18, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 4, 15, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 5, 15, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 6, 15, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 7, 15, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 8, 15, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 8, 16, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 8, 17, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 8, 18, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 6, 16, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        //O
        Util.drawSquare(g, 11, 2, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 12, 2, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 13, 2, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 14, 2, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 15, 3, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 15, 4, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 10, 3, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 10, 4, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 11, 5, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 12, 5, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 13, 5, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 14, 5, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        //V
        Util.drawSquare(g, 10, 6, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 11, 6, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 12, 6, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 13, 6, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 10, 9, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 11, 9, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 12, 9, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 13, 9, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 14, 7, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 15, 7, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 14, 8, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 15, 8, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        //E
        Util.drawSquare(g, 10, 10, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 10, 11, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 10, 12, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 11, 10, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 12, 10, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 13, 10, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 14, 10, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 15, 10, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 13, 11, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 15, 11, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 15, 12, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 15, 13, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        Util.drawSquare(g, 10, 13, squareWidth(), squareHeight(), SquareType.GAMEOVER);
        //R
        Util.drawSquare(g, 10, 14, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 10, 15, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 10, 16, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 10, 17, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 11, 14, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 11, 17, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 12, 14, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 12, 17, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 13, 14, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 13, 15, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 13, 16, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 13, 17, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 14, 14, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 14, 16, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
         Util.drawSquare(g, 15, 14, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        Util.drawSquare(g, 15, 17, squareWidth(), squareHeight(), SquareType.GAMEOVER2);
        
    }*/

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(180, 180, 180));
        setPreferredSize(new java.awt.Dimension(400, 300));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
