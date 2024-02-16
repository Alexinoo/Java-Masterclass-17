package oop.part1.inheritance.this_vs_super_calls.this_method;

public class GoodConstructorExample {
    private int x;
    private int y;
    private int width;
    private int height;

    //1st constructor
    public GoodConstructorExample(){
        this(0,0);          //calls 2nd constructor
    }

    //2nd constructor
    public GoodConstructorExample(int width , int height){
       this(0,0,width,height);      //calls 3rd constructor
    }

    //3rd constructor
    public GoodConstructorExample(int x, int y, int width, int height){
        //initialize variables
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
