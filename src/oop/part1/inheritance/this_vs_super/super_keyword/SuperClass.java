package oop.part1.inheritance.this_vs_super.super_keyword;

public class SuperClass {
    public void printMethod(){
        System.out.println("Printed in SuperClass");
    }
}

class SubClass extends SuperClass{
    @Override
    public void printMethod() {
        super.printMethod();
        System.out.println("Printed in SubClass");
    }
}

class MainClass {
    public static void main(String[] args) {
        SubClass subclass = new SubClass();
        subclass.printMethod();
    }
}
