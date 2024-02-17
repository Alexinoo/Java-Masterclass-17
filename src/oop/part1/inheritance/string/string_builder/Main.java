package oop.part1.inheritance.string.string_builder;

public class Main {
    public static void main(String[] args) {

        String helloWorld = "Hello" + " World";
        helloWorld.concat(" and Goodbye");

        // StringBuilder helloWorldBuilder = "Hello" + "World"; -- WONT WORK!
        StringBuilder helloWorldBuilder =  new StringBuilder("Hello" + " World");
        helloWorldBuilder.append(" and Goodbye");

        printInformation(helloWorld);
        printInformation(helloWorldBuilder);

        StringBuilder emptyStart = new StringBuilder();
        emptyStart.append("a".repeat(17));

        StringBuilder emptyStart32 = new StringBuilder(32);
        emptyStart32.append("a".repeat(17));

        printInformation(emptyStart);
        printInformation(emptyStart32);


        //deleteCharAt()
        StringBuilder builderPlus =  new StringBuilder("Hello" + " World");
        builderPlus.append(" and Goodbye");
        builderPlus.deleteCharAt(16).insert(16,'g');
        System.out.println(builderPlus);

        //replace()
        builderPlus.replace(16,17,"G");
        System.out.println(builderPlus);

        //reverse() chain setLength()
        builderPlus.reverse().setLength(7);
        System.out.println(builderPlus);
    }

    public static void printInformation(String someString){
        System.out.println("String = "+someString);
        System.out.println("length = "+someString.length());
    }

    public static void printInformation(StringBuilder builder){
        System.out.println("StringBuilder = "+builder);
        System.out.println("length = "+builder.length());
        System.out.println("capacity = "+builder.capacity());
    }
}
