package lambda_expressions.part1_lambdaIntro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Person> people = new ArrayList<>(Arrays.asList(
                new Person("John","Anderson"),
                new Person("Cyrus","Mwangi"),
                new Person("Alex","Mwangi"),
                new Person("Evans","Koech"),
                new Person("Loice","Mbugua")
        ));


        //sort People using Anonymous comparator class
        people.sort((o1, o2) -> o1.lastName().compareTo(o2.lastName()));

        //print people
        printList(people);


        // Local interface method local to this method that extends Comparator interface
        interface EnhancedComparator<T> extends Comparator<T>{
            int secondLevel(T o1, T o2);
        }

        //Anonymous expression using Enhanced Comparator above
        var comparatorMixed = new EnhancedComparator<Person>(){
            @Override
            public int compare(Person o1, Person o2) {
                int result = o1.lastName().compareTo(o2.lastName());
                return (result == 0)? secondLevel(o1,o2):result;
            }

            @Override
            public int secondLevel(Person o1, Person o2) {
                return o1.firstName().compareTo(o2.firstName());
            }
        };

        //Sort people List with comparatorMixed
        people.sort(comparatorMixed);

        //Print persons
        System.out.println("/".repeat(30));
        printList(people);

    }

    public static void printList(List<Person> personList){
        for (Person person :personList ) {
            System.out.println(person);
        }
    }
}
