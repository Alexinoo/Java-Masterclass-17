package collections.part15_map_interface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        List<Contact> phones = ContactData.getData("phone");
        List<Contact> emails = ContactData.getData("email");

        List<Contact> fullList = new ArrayList<>(phones);
        fullList.addAll(emails);
        fullList.forEach(System.out::println);
        System.out.println("_".repeat(50));

        //Create a HashMap
        // Specify 2 type arguments i.e. type of the key & type of the value
        Map<String, Contact> contacts = new HashMap<>();

        for (Contact contact: fullList) {
            contacts.put(contact.getName() , contact);
        }

        //Print elements in a Map
        // 1. forEach on the map itself
        contacts.forEach((k,v)-> System.out.println("key = "+k+", value = "+v));

        // Use a key for look up
        // Returns null if key not found
        System.out.println("----get(key) - look up value ---");
        System.out.println(contacts.get("Charlie Brown"));

        //As of JDK 8
        //
        //
        //
        // - getOrDefault() was introduced
        // pass a default value if null is returned to avoid PointerToNullException

        System.out.println(contacts.get("Chuck Brown")); // null

        Contact defaultContact = new Contact("Chuck Brown");
        System.out.println(contacts.getOrDefault("Chuck Brown",defaultContact));

        //Clear the contacts map elements
        contacts.clear();

        //Loop through fullList
        // assign output of the put() to a local variable
        for (Contact contact:fullList ) {
            Contact duplicate = contacts.put(contact.getName(),contact);
            if(duplicate != null){
                // System.out.println("duplicate = "+duplicate);
                // System.out.println("current = "+contact);
                contacts.put(contact.getName(),contact.mergeContactData(duplicate));
            }
        }
        //Print data again
        contacts.forEach((k,v)-> System.out.println("key = "+k+", value = "+v));


        //Clear the contacts map elements
        contacts.clear();
        System.out.println("_".repeat(50));

        //putIfAbsent()
        //Loop contacts and add to HashMap
        //Print using forEach
        for (Contact contact: fullList) {
            contacts.putIfAbsent(contact.getName() , contact);
        }
        contacts.forEach((k,v)-> System.out.println("key = "+k+", value = "+v));


        System.out.println("_".repeat(50));
        //putIfAbsent() -  Using duplicate
        //Loop contacts and add to HashMap
        //Print using forEach
        for (Contact contact: fullList) {
           Contact duplicate =  contacts.putIfAbsent(contact.getName() , contact);
           if(duplicate != null){
               contacts.put(contact.getName(),contact.mergeContactData(duplicate));
           }
        }
        contacts.forEach((k,v)-> System.out.println("key = "+k+", value = "+v));


        //As of JDK 8
        //
        //
        //
        // merge() was introduced
        // takes a key and a value and the 3rd parameter which is a BIFunction interface

        //Clear the contacts map elements
        contacts.clear();
        System.out.println("_".repeat(50));
        fullList.forEach(contact -> contacts.merge(
                contact.getName(),
                contact,
                Contact::mergeContactData
                ));
        contacts.forEach((k,v)-> System.out.println("key = "+k+", value = "+v));



        //As of JDK 8
        //
        //
        //
        // compute() , computeIfAbsent() & computeIfPresent() was introduced
        //
        //
        //




        //
        //
        //
        //
        // compute(K key , BiFunction<Type> remappingFunction)
        // - Attempts to compute a mapping for the specified key
        // and its current mapped value (or null if there is no current mapping)
        // - Acts like put by replacing what's in the map with the result of the BiFunction or the lambda expression

//        System.out.println("_".repeat(50));
//        for (String contactName: new String[]{"Daisy Duck", "Daffy Duck","Scrooge McDuck"}) {
//            contacts.compute(contactName , (k,v)->new Contact(k));
//        }
//        contacts.forEach((k,v)-> System.out.println("key = "+k+", value = "+v));

        //
        //
        //
        //
        // computeIfAbsent(K key ,BiFunction<Type> remappingFunction)
        // - If the specified key is not already associated with a value (or is mapped to null),
        //   attempts to compute its value using the given mapping function and enters it into this map unless null.
        System.out.println("_".repeat(50));
        for (String contactName: new String[]{"Daisy Duck", "Daffy Duck","Scrooge McDuck"}) {
            contacts.computeIfAbsent(contactName , k ->new Contact(k));
        }
        contacts.forEach((k,v)-> System.out.println("key = "+k+", value = "+v));


        //
        //
        //
        //
        // computeIfPresent(K key ,Function<Type> mappingFunction)
        //  - If the value for the specified key is present and non-null,
        // attempts to compute a new mapping given the key and its current mapped value.

        System.out.println("_".repeat(50));
        for (String contactName: new String[]{"Daisy Duck", "Daffy Duck","Scrooge McDuck"}) {
            contacts.computeIfPresent(contactName ,(k,v) -> {
                v.addEmail("Fun place");
                return v;});
        }
        contacts.forEach((k,v)-> System.out.println("key = "+k+", value = "+v));


        //
        //
        //
        //
        // replaceAll(BiFunction<Type> function)
        //  Replaces each entry’s value with the result of invoking the given function
        //  on that entry until all entries have been processed or the function throws an exception.
        System.out.println("_".repeat(50));
        contacts.replaceAll((k,v)->{
            String newEmail = k.replaceAll(" ","") + "@funplace.com";
            v.replaceEmailIfExists("DDuck@funplace.com",newEmail);
            return v;
        });
        contacts.forEach((k,v)-> System.out.println("key = "+k+", value = "+v));


        //
        //
        //
        //
        // replace(K key, V value)
        //  - Replaces the entry for the specified key only if it is currently mapped to some value.
        System.out.println("_".repeat(50));
        Contact daisy = new Contact("Daisy Jane Duck","daisyj@duck.com");
        Contact replacedContact = contacts.replace("Daisy Duck",daisy);
        System.out.println("daisy = "+daisy);
        System.out.println("replacedContact  = "+replacedContact);
        contacts.forEach((k,v)-> System.out.println("key = "+k+", value = "+v));


        //
        //
        //
        //
        // replace() - Overloaded Version
        System.out.println("_".repeat(50));
        Contact updatedDaisy = replacedContact.mergeContactData(daisy);
        System.out.println("updatedDaisy  = "+updatedDaisy);
        boolean success = contacts.replace("Daisy Duck",daisy,updatedDaisy);
        if(success){
            System.out.println("Successfully replaced element");
        }else{
            System.out.printf("Did not match on both key : %s and value : %s %n ".formatted("Daisy Duck",replacedContact));
        }
        contacts.forEach((k,v)-> System.out.println("key = "+k+", value = "+v));

        /*
        - There are 2 overloaded methods of remove() method

        1. remove(Object key)

            - Takes a key, removes and returns the value mapped to that key. Otherwise, returns a null

        2. remove(Object key, Object value)

            - Only removes the element from the map , if the key is in the map and the element to be
              removed equals the value passed
            - This method returns a boolean
        */

        System.out.println("_".repeat(50));
        success = contacts.remove("Daisy Duck",daisy);
        if(success)
            System.out.println("Successfully removed the element");
        else
            System.out.printf("Did not match on both key: %s and value: %s %n".formatted("Daisy Duck",daisy));

        contacts.forEach((k,v)-> System.out.println("key = "+k+", value = "+v));
    }
}
