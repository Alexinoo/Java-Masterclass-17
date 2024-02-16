package oop.part1.inheritance.this_vs_super_calls.super_method;

public class Shape {

    private int x;
    private int y;

    public Shape(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Rectangle extends Shape{

    private  int width;
    private  int height;

    //1st constructor
    public Rectangle(int x, int y) {
        this(x, y,0,0); //calls 2nd constructor
    }

    //2nd constructor
    public Rectangle(int x,int y,int width,int height){
        super(x,y);   //calls constructor from parent (Shape)
        this.width = width;
        this.height = height;

    }
}
