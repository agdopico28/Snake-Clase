/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Singleton.java to edit this template
 */
package com.mycompany.snake;

/**
 *
 * @author alu10701951
 */
public class ConfigData {
    private int size;
    private int level;
    private String name;
    
    public static ConfigData instance = new ConfigData();
    
    private ConfigData() {
        level = 0;
        size = 0;
        name = "NoName";
    }
    
    public void setBoardSize(int size){
        if (size < 0) {
            this.size = 0;
        } else if (size > 2) {
            this.size = 2;
        } else{
            this.size = size;
        }
    }
    
    public int setBoardRowCol(){
        switch(ConfigData.instance.getBoardSize()){
            case 0: return 20;
            case 1: return 25;
            case 2: return 30;
            default: return 20;
        }
    }
    
    public int getBoardSize(){
        return size;
    }
    public void setLevel (int level){
        if (level < 0) {
            this.level = 0;
        } else if (level > 2) {
            this.level = 2;
        } else{
            this.level = level;
        }
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public int getlevel(){
        return level;
    }
    
    public String getName(){
        return name;
    }
}
