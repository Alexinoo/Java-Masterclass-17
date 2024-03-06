package collections.part16_map_views;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Map<String,Contact> contacts = new HashMap<>();
        ContactData.getData("phone").forEach(c -> contacts.put(c.getName(),c));
        ContactData.getData("email").forEach(c -> contacts.put(c.getName(),c));

        /*
            keySet()
            --------

                - This method is used in Map Interface to return a Set view of the keys contained in this map.

                - The set is backed by the map, so changes to the map are reflected in the set, and vice-versa.
         */

        ///////print separator/////////////
        System.out.println("_".repeat(30));


        Set<String> keysView = contacts.keySet();
        System.out.println(keysView); // [Lucy Van Pelt, Linus Van Pelt, Minnie Mouse, Maid Marion, Charlie Brown, Robin Hood, Daffy Duck, Mickey Mouse]

        ///////print separator/////////////
        System.out.println("_".repeat(30));


        // Using a TreeSet constructor - ordered
        Set<String> copyOfKeys = new TreeSet<>(contacts.keySet());
        System.out.println(copyOfKeys); // [Charlie Brown, Daffy Duck, Linus Van Pelt, Lucy Van Pelt, Maid Marion, Mickey Mouse, Minnie Mouse, Robin Hood]


        /*
            containsKey(Object)
            -------------------

                - This method is used in Map Interface in Java to check whether a particular key is being mapped
                  into the Map or not.

                - It takes the key element as a parameter and returns boolean if that element is mapped in the map.
         */

        ///////print separator/////////////
        System.out.println("_".repeat(30));

        if(contacts.containsKey("Linus Van Pelt")){
            System.out.println("Linus and I go way back, so of course I have info");
        }

         /*
            remove(Object)
            -------------------

                - This method is used in Map Interface to remove the mapping for a key from this map if it is
                  present in the map.
         */
        ///////print separator/////////////
        System.out.println("_".repeat(30));

        //using keysView - removes from the map data (contacts) as well
        keysView.remove("Daffy Duck");
        System.out.println(keysView);
        contacts.forEach((k,v)-> System.out.println(v));

        ///////print separator/////////////
        System.out.println("_".repeat(30));

        //using copyOfKeys - new Treeset<>(contacts.keySet()) Does not remove from the map data (contacts)
        copyOfKeys.remove("Linus Van Pelt");
        System.out.println(copyOfKeys);
        contacts.forEach((k,v)-> System.out.println(v));


         /*
            retainAll(Collection c)
            -----------------------

                - This method is used to retain only the elements in this collection that are contained in the
                  specified collection.

         */
        ///////print separator/////////////
        System.out.println("_".repeat(30));

        keysView.retainAll(List.of("Linus Van Pelt","Charlie Brown","Robin Hood","Mickey Mouse"));
        System.out.println(keysView);
        contacts.forEach((k,v)-> System.out.println(v));

         /*
            clear()
            -----------------------

                - This method is used in Java Map Interface to clear and remove all of the elements or
                   mappings from a specified Map collection.

         */
        ///////print separator/////////////
        System.out.println("_".repeat(30));

        keysView.clear();
        System.out.println(contacts);

        // add() or addAll() won't work on keysView
        // keysView.add("Daisy Duck");
        // System.out.println(contacts);


        // Adding our data back - since we had cleared
        // Our newly added contacts are in the keys view set - we didn't have to execute contacts.keySet() again
        // to get the refreshed data and our keysView was refreshed automatically
        // Tis is pretty powerful & easy way to manipulate elements in the map
        ContactData.getData("phone").forEach(c -> contacts.put(c.getName(),c));
        ContactData.getData("email").forEach(c -> contacts.put(c.getName(),c));
        System.out.println(keysView); //[Lucy Van Pelt, Linus Van Pelt, Minnie Mouse, Maid Marion, Charlie Brown, Robin Hood, Daffy Duck, Mickey Mouse]



         /*
            values()
            -----------------------

                - This method is used in Java Map Interface to create a collection out of the values of the map.
                  It basically returns a Collection view of the values in the HashMap.

         */
        ///////print separator/////////////
        System.out.println("_".repeat(30));
        var values = contacts.values();
        values.forEach(System.out::println);


        ///////print separator/////////////
        System.out.println("_".repeat(30));


        // Using retainAll() but this time using it on the values collection
        values.retainAll(ContactData.getData("email"));
        System.out.println(keysView);
        contacts.forEach((k,v)-> System.out.println(v));


        ///////print separator/////////////
        System.out.println("_".repeat(30));

        List<Contact> list = new ArrayList<>(values);
        list.sort(Comparator.comparing(Contact::getNameLastFirst));
        list.forEach(c -> System.out.println(c.getNameLastFirst() + ": "+c));


        ///////print separator/////////////
        System.out.println("_".repeat(30));

        //Adding a duplicate contact with a different key name
        Contact first = list.get(0);
        contacts.put(first.getNameLastFirst(), first);
        values.forEach(System.out::println);
        keysView.forEach(System.out::println);



        ///////print separator/////////////
        System.out.println("_".repeat(30));


        //Passing my values collection to a HashSet - takes a collection as argument to the constructor
        //Setting up a local variable
        HashSet<Contact> set = new HashSet<>(values);
        set.forEach(System.out::println);
        if(set.size() < contacts.keySet().size()){
            System.out.println("Duplicate values in my Map");
        }




         /*
            entrySet()
            -----------------------

                - This method is used in Map Interface in Java to create a set out of the same elements
                   contained in the map.
                  It basically returns a set view of the map or we can create a new set and store the map elements into them.


         */
        ///////print separator/////////////
        System.out.println("_".repeat(30));

        var nodeSet = contacts.entrySet();
        for (var node: nodeSet) {
            System.out.println(nodeSet.getClass().getName());
            if(!node.getKey().equals(node.getValue().getName())) {
                System.out.println(node.getClass().getName());
                System.out.println("Key doesn't match name: " + node.getKey() + ": " + node.getValue());
            }
        }



    }
}
