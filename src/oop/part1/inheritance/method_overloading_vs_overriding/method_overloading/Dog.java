package oop.part1.inheritance.method_overloading_vs_overriding.method_overloading;

public class Dog {

    public void bark(){
        System.out.println("woof");
    }

    public void bark(int number){
        for (int i = 0; i < number ; i++) {
            System.out.println("woof");
        }
    }
}
