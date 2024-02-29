package collections.part1_overview;


import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) {

        // All below works
        // List<String> list = new ArrayList<>();
        // Collection<String> list = new ArrayList<>();
       //  Collection<String> list = new TreeSet<>(); //Works the same
        Collection<String> list = new HashSet<>(); //Works the same - elements are not ordered

        String[] names = {"Anna","Bob","Carol","Edna"};
        list.addAll(Arrays.asList(names));

        //Add 1 more
        list.add("Fred");

        ///Add several
        list.addAll(Arrays.asList("George","Gary","Grace"));
        printList(list);

        System.out.println("Gary is in the list ?" +list.contains("Gary"));

        //Remove names that starts with letter G
        //list.removeIf(s -> s.startsWith("G"));
        list.removeIf(s -> s.charAt(0) == 'G');
        printList(list);
        System.out.println("Gary is in the list ?" +list.contains("Gary"));

        //list.sort() - does not work on HashSet
    }

    private static void printList(Collection<String> myList){
        System.out.println(myList);
    }
}
