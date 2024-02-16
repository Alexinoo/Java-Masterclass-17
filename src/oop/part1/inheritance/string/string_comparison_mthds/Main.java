package oop.part1.inheritance.string.string_comparison_mthds;

public class Main {

    public static void main(String[] args) {

        String helloWorld = "Hello World";

        String helloWorldLower = helloWorld.toLowerCase();

        if(helloWorld.equals(helloWorldLower)){
            System.out.println("Values match exactly");
        }
        if(helloWorld.equalsIgnoreCase(helloWorldLower)){
            System.out.println("Values match ignoring case");
        }

        if(helloWorld.startsWith("Hello")){
            System.out.println("String starts with Hello");
        }

        if(helloWorld.endsWith("World")){
            System.out.println("String starts with World");
        }

        if(helloWorld.contains("World")){
            System.out.println("String contains World");
        }

        if(helloWorld.contentEquals("Hello World")){
            System.out.println("Values match exactly");
        }

    }
}
