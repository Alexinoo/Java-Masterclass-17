package lambda_expressions.part10_convenience_mthds;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) {
        String name = "Alex";

        Function<String,String> uCase = String::toUpperCase;
        System.out.println(uCase.apply(name));

        Function<String,String> lastName = s -> s.concat(" Mwangi");


        Function<String,String> uCaseLastName = uCase.andThen(lastName);
        System.out.println(uCaseLastName.apply(name));

        uCaseLastName = uCase.compose(lastName);
        System.out.println(uCaseLastName.apply(name));

        Function<String,String[]> f0 = uCase
                .andThen(s -> s.concat(" Mwangi"))
                .andThen(s -> s.split(" "));
        System.out.println(Arrays.toString(f0.apply(name)));

        Function<String,String> f1 = uCase
                                    .andThen(s->s.concat(" Wakanyi"))
                                    .andThen(s -> s.split(" "))
                                    .andThen(s -> s[1].toUpperCase() +", "+ s[0]);

        System.out.println(f1.apply(name));

        Function<String,Integer> f2 = uCase
                                      .andThen(s ->s.concat(" Wakanyi"))
                                      .andThen(s -> s.split(" "))
                                      .andThen(s -> String.join(", ",s))
                                      .andThen(String::length);

        System.out.println(f2.apply(name));

        // Consumer Functions
        String[] names = {"Ann","Bob","Carol"};
        Consumer<String> s0 = s -> System.out.print(s.charAt(0));
        Consumer<String> s1 = System.out::println;

        Arrays.asList(names).forEach(s0
                .andThen(s -> System.out.print(" - "))
                .andThen(s1));

        // Predicate Functions
        Predicate<String> p1 = s -> s.equals("ALEX");
        Predicate<String> p2 = s -> s.equalsIgnoreCase("Alex");
        Predicate<String> p3 = s -> s.startsWith("A");
        Predicate<String> p4 = s -> s.endsWith("e");

        Predicate<String> combined1 = p1.or(p2);
        System.out.println("combined1 = "+combined1.test(name));

        Predicate<String> combined2 = p3.and(p4);
        System.out.println("combined2 = "+combined2.test(name));

        Predicate<String> combined3 = p3.and(p4).negate();
        System.out.println("combined3 = "+combined3.test(name));






    }
}
