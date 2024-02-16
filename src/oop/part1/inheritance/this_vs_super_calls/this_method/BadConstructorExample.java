package oop.part1.inheritance.this_vs_super_calls.this_method;

public class BadConstructorExample {
    private int x;
    private int y;
    private int width;
    private int height;

    public BadConstructorExample(){
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
    }

    public BadConstructorExample(int width , int height){
        this.x = 0;
        this.y = 0;
        this.width = width;
        this.height = height;
    }

    public BadConstructorExample(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
