package lambda_expressions.part11_convenience_mthds_comparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        record Person(String firstName,String lastName) {}

        List<Person> list = new ArrayList<>(Arrays.asList(
                new Person("Peter","Pan"),
                new Person("Alex","PumpkinEater"),
                new Person("Mickie","Mouse"),
                new Person("Minnie","Mouse")
        ));

        System.out.println("--> Using sort on Comparator:");
        list.sort( (o1,o2) ->o1.lastName.compareTo(o2.lastName));
        list.forEach(System.out::println);

        //Using static method on Comparator called comparing
        System.out.println("--> Using Comparator static:");
        list.sort(Comparator.comparing(Person::firstName));
        list.forEach(System.out::println);

        //Chain another convenient method called .thenComparing()
        // then pass a second method reference or lambda
        System.out.println("--> Using thenComparing()");
        list.sort(Comparator.comparing(Person::lastName).thenComparing(Person::firstName));
        list.forEach(System.out::println);


        //Chain another convenient method called .thenComparing()
        // then pass a second method reference or lambda
        System.out.println("--> Using .reversed() on thenComparing()");
        list.sort(Comparator.comparing(Person::lastName).thenComparing(Person::firstName).reversed());
        list.forEach(System.out::println);
    }
}
