package lambda_expressions.part6_lambda_expression_challenge;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {

    private static Random random = new Random();
    public static void main(String[] args) {

        String[] names = {"Anna","Bob","Carole","David","Ed","Fred","Gary"};

        Arrays.setAll(names, i -> names[i].toUpperCase());

        System.out.println("--> Transform to Uppercase (Using Arrays.setAll(arr[])");
        System.out.println(Arrays.toString(names));


        System.out.println("--> Add second name initials (Using List.replaceAll()");
        List<String> backedArray = Arrays.asList(names);
        backedArray.replaceAll(s -> s += " "+ getRandomChar('A','D')+".");
        System.out.println(Arrays.toString(names));

        System.out.println("--> Add reversed name as last name");
        backedArray.replaceAll(s -> s += " "+getReversedName(s.split(" ")[0]));

        //Instead of toString() on Arrays to print the array out - use forEach
        Arrays.asList(names).forEach(s -> System.out.println(s));


        System.out.println("--> Remove names where first name = last name");
        List<String> newList = new ArrayList<>(List.of(names));

        //newList.removeIf(s ->s.substring(0,s.indexOf(" ")).equals(s.substring(s.lastIndexOf(" ")+1)));

        // Variations of the commented above
        newList.removeIf(s ->{
            String first = s.substring(0,s.indexOf(" "));
            String last = s.substring(s.lastIndexOf(" ")+1);
            return first.equals(last);
            });

        newList.forEach(s -> System.out.println(s));


    }

    public static char getRandomChar(char startChar,char endChar){
        return (char) random.nextInt((int)startChar , (int)endChar + 1);
    }
    private static String getReversedName(String firstName){
        return new StringBuilder(firstName).reverse().toString();
    }
}
