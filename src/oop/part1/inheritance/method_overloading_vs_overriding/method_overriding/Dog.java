package oop.part1.inheritance.method_overloading_vs_overriding.method_overriding;

public class Dog {

    public void bark(){
        System.out.println("Woof!..");
    }
}

class GermanShepherd extends Dog{
    @Override
    public void bark(){
        System.out.println("wof woof woof");
    }
}
