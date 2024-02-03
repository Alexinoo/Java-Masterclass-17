package oop.part1.static_variables;

public class Dog {

    static String name;

    public Dog(String name){
        Dog.name = name;
    }

    public void printName(){
        System.out.println("name = "+name);
    }
}
