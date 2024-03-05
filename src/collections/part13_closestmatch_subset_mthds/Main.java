package collections.part13_closestmatch_subset_mthds;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        List<Contact> phones = ContactData.getData("phone");
        List<Contact> emails = ContactData.getData("email");

        //NavigableSet<Contact> sorted = new TreeSet<>(phones);
        Comparator<Contact> mySort = Comparator.comparing(Contact::getName);
        NavigableSet<Contact> sorted = new TreeSet<>(mySort);
        sorted.addAll(phones);
        sorted.forEach(System.out::println);

        //Add Just Names
        NavigableSet<String> justNames = new TreeSet<>();
        phones.forEach(c -> justNames.add(c.getName()));
        System.out.println(justNames);


        NavigableSet<Contact> fullSet = new TreeSet<>(sorted);
        fullSet.addAll(emails);
        fullSet.forEach(System.out::println);

        List<Contact> fullList = new ArrayList<>(phones);
        fullList.addAll(emails);
        fullList.sort(sorted.comparator());
        System.out.println("___________________");
        fullList.forEach(System.out::println);

        ///// min , max , first , last , pollFirst , pollLast methods //
        // Contact class must implement Comparable
        // There are overloaded methods hat let's us pass comparator
        Contact min = Collections.min(fullSet, fullSet.comparator());
        Contact max = Collections.max(fullSet, fullSet.comparator());
        Contact first = fullSet.first();
        Contact last = fullSet.last();

        System.out.println("______________________");
        System.out.printf("min = %s, first = %s %n",min.getName(),first.getName());
        System.out.printf("max = %s, last = %s %n",max.getName(),last.getName());
        System.out.println("______________________");


        //pollFirst() & pollLast()
        NavigableSet<Contact> copiedSet = new TreeSet<>(fullSet);
        System.out.println("First element = "+copiedSet.pollFirst());
        System.out.println("Last element = "+copiedSet.pollLast());
        copiedSet.forEach(System.out::println);
        System.out.println("--------------------------");

        //////
        //
        //
        //
        //
        //  TreeSet , Closest match and subset methods

        //New Contacts

        Contact daffy = new Contact("Daffy Duck");
        Contact daisy = new Contact("Daisy Duck");
        Contact snoopy = new Contact("Snoopy");
        Contact archie = new Contact("Archie");

        // Higher , ceiling methods
        for (Contact c: List.of(daffy,daisy,last,snoopy)) {
            System.out.printf("ceiling(%s)=%s%n",c.getName(),fullSet.ceiling(c));
            System.out.printf("higher(%s)=%s%n",c.getName(),fullSet.higher(c));
        }
        System.out.println("__________________________");

        // lower , floor methods
        for (Contact c: List.of(daffy,daisy,first,snoopy)) {
            System.out.printf("floor(%s)=%s%n",c.getName(),fullSet.floor(c));
            System.out.printf("lower(%s)=%s%n",c.getName(),fullSet.lower(c));
        }
        System.out.println("__________________________");


        // Print set in descending order
        NavigableSet<Contact> descendingSet = fullSet.descendingSet();
        descendingSet.forEach(System.out::println);
        System.out.println("__________________________");

        //
        Contact lastContact = descendingSet.pollLast();
        System.out.println("Removed "+lastContact);
        descendingSet.forEach(System.out::println);
        System.out.println("__________________________");
        fullSet.forEach(System.out::println);
        System.out.println("__________________________");

        /*
            headSet(Object toElement)
            - return contacts before Maid Marion(exclusive) in the contact list

            - Also has an overloaded version that takes a 2nd parameter which is a
            boolean value - to determine if the elemnt passed should be inclusive
         */

        Contact marion = new Contact("Maid Marion");
        var headSet = fullSet.headSet(marion,true);
        headSet.forEach(System.out::println);
        System.out.println("_____________________________");

       /*
            tailSet(Object fromElement)
            - return contacts after / greater than Maid Marion(exclusive) in the contact list

            - Also has an overloaded version that takes a 2nd parameter which is a
            boolean value - to determine if the elemnt passed should be inclusive
         */
        var tailSet = fullSet.tailSet(marion,false);
        tailSet.forEach(System.out::println);
        System.out.println("_____________________________");


         /*
            subSet(Object fromElement , Object toElement)

            - This method will return elements ranging from fromElement to toElement.

            - fromElement is inclusive and toElement is exclusive.

            - Also has an overloaded version that takes a 2nd and 4th parameter which is a
            boolean value - to determine if the fromElement & toElement should be inclusive
         */

        Contact linus = new Contact("Linus Van Pelt");
        var subset = fullSet.subSet(linus,marion);
        subset.forEach(System.out::println);
    }
}
