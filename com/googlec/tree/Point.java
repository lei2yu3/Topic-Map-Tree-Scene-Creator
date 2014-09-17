package com.googlec.tree;

public class Point {
    private int x;
    private int y;

    public Point(){
        this(0, 0);
    }
    
    public Point(int a, int b){
        this.x = a;
        this.y = b;
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }

    public void setX(int n){
        this.x = n;
    }
    
    public void setY(int n){
        this.y = n;
    }
}
