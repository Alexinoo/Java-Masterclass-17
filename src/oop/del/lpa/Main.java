package oop.del.lpa;

//import oop.com.abc.first.Item; // Specific class
import oop.com.abc.first.*;     //All classes

public class Main {

    public static void main(String[] args) {

        Item firstItem = new Item("Burger");
        //oop.com.abc.first.Item firstItem = new oop.com.abc.first.Item("Burger");

        System.out.println(firstItem);
    }
}
