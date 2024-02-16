package oop.part1.inheritance.inheritance_part_3;

public class Dog extends Animal {
    private String earShape;
    private String tailShape;

    public Dog(){
        // super(); -- calls parent's class default constructor
        super("German Shepherd","Big",50);
    }

    public Dog(String type , double weight){
        //constructor chaining
        this(type,weight,"perky","Curled");
    }

    public Dog(String type,double weight, String earShape, String tailShape) {
        super(type, weight < 15 ? "small":(weight < 35 ? "medium":"large"), weight);
        this.earShape = earShape;
        this.tailShape = tailShape;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "earShape='" + earShape + '\'' +
                ", tailShape='" + tailShape + '\'' +
                "} " + super.toString();
    }

    /* Overriding makeNoise() from the super class*/
    public void makeNoise(){
        if(type == "Wolf"){
            System.out.print("Ow Woooo! ");
        }
        bark();
        System.out.println();

    }

    @Override
    public void move(String speed) {
        super.move(speed);
       // System.out.println("Dogs walk, run and wag their tail"); //replace with specific methods
        if(speed == "slow"){
            walk();
            wagTail();
        }else{
            run();
            bark();
        }
        System.out.println();
    }

    private void bark(){
        System.out.print("Woof! ");
    }

    private void run(){
        System.out.print("Dog running ");
    }

    private void walk(){
        System.out.print("Dog walking ");
    }

    private void wagTail(){
        System.out.print("Tail wagging ");
    }
}
