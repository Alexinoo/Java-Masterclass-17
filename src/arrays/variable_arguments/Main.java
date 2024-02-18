package arrays.variable_arguments;

public class Main {

    public static void main(String... args) {
        System.out.println("Hello world again");

        //String.split() - Returns parts of a string as arrays
        String[] splitStrings = "Hello world again".split(" ");

        printText(splitStrings);

        System.out.println("_".repeat(20));
        printText("Hello");


        System.out.println("_".repeat(20));
        printText("Hello","World","again");

        System.out.println("_".repeat(20));
        printText();

        String[] strArray = {"first","second","third","fourth","fifth"};
        System.out.println(String.join(",",strArray));
    }

    public static void printText(String... textList){
        for (String text: textList ) {
            System.out.println(text);
        }
    }
}
