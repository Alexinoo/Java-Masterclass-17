package oop.part1.instance_variables;

public class Dog {

    private String name;

    public Dog(String name){
        this.name = name;
    }

    public void printName(){
        System.out.println("name = "+name);
    }
}
