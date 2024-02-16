package oop.part1.inheritance.string.string_inspection_mthds;

public class Main {

    public static void main(String[] args) {
        printInformation("Hello World");
        printInformation("");
        printInformation("\t   \n");

        String helloWorld = "Hello World";
        System.out.printf("index of r = %d %n",helloWorld.indexOf('r'));
        System.out.printf("index of World = %d %n",helloWorld.indexOf("World"));

        System.out.printf("index of l = %d %n",helloWorld.indexOf('l'));
        System.out.printf("index of l = %d %n",helloWorld.lastIndexOf('l'));

        System.out.printf("index of l = %d %n",helloWorld.indexOf('l',3));
        System.out.printf("index of l = %d %n",helloWorld.lastIndexOf('l',8));
    }

    public static void printInformation( String someString){
        int length = someString.length();
        System.out.printf("Length = %d %n",length);

        if(someString.isEmpty()){
            System.out.println("String is empty");
            return;
        }

        if(someString.isBlank()){
            System.out.println("String is blank");
        }
        System.out.printf("First char = %c %n",someString.charAt(0));
        System.out.printf("Last char = %c %n",someString.charAt(length - 1));


    }
}
