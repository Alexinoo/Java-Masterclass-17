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

    }
}
