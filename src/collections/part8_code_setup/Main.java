package collections.part8_code_setup;

import java.util.Collection;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Contact> phones = ContactData.getData("phone");
        List<Contact> emails = ContactData.getData("email");

        printData("Phone List", phones);
        printData("Email List", emails);
    }

    public static void printData(String header, Collection<Contact> contacts){
        System.out.println("-------------------------------------------");
        System.out.println(header);
        System.out.println("-------------------------------------------");
        contacts.forEach(System.out::println);
    }
}
