package collections.part9_sets_hashsets;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        List<Contact> emails = ContactData.getData("email");
        List<Contact> phones = ContactData.getData("phone");

        printData("Phone List", phones);
        printData("Email List", emails);


        // Using Set/HashSet
        Set<Contact> emailContacts = new HashSet<>(emails);
        Set<Contact> phoneContacts = new HashSet<>(phones);

        printData("Phone Contacts", phoneContacts);
        printData("Email Contacts", emailContacts);


        // Get RobinHood from the email contacts
        int index = emails.indexOf(new Contact("Robin Hood"));
        //use get() to return original email contact
        Contact robbinHood = emails.get(index);
        robbinHood.addEmail("Sherwood Forest");
        robbinHood.addEmail("Sherwood Forest");
        robbinHood.replaceEmailIfExists("RHood@sherwoodforest.com","RHood@sherwoodforest.org");


        // Create a Union Set
        Set<Contact> unionAB = new HashSet<>();
        unionAB.addAll(emailContacts);
        unionAB.addAll(phoneContacts);
        printData("(A ∪ B ) Union of emails (A) with phones (B)",unionAB);

        //Getting common dataset btwn 2 sets
        Set<Contact> intersectAB = new HashSet<>(emailContacts);
        intersectAB.retainAll(phoneContacts);
        printData("(A ∩ B ) Intersect emails (A) with phones (B)",intersectAB);

        //Symmetric operation A ∩ B // B ∩ A
        Set<Contact> intersectBA = new HashSet<>(phoneContacts);
        intersectBA.retainAll(emailContacts);
        printData("(B ∩ A ) Intersect phones (B) with emails (A)  ",intersectBA);


        //Assymetric operation A - B
        Set<Contact> AminusB = new HashSet<>(emailContacts);
        AminusB.removeAll(phoneContacts);
        printData("(A - B ) emails (A) - phones (B) ",AminusB);

        //Assymetric operation B - A
        Set<Contact> BminusA = new HashSet<>(phoneContacts);
        BminusA.removeAll(emailContacts);
        printData("(B - A ) phones (B) - emails (A) ",BminusA);

        //Symmetric Diff
        Set<Contact> symmetricDiff = new HashSet<>(AminusB);
        symmetricDiff.addAll(BminusA);
        printData("Symmetric Difference: phones and emails ",symmetricDiff);

    }

    public static void printData(String header, Collection<Contact> contacts){
        System.out.println("-------------------------------------------");
        System.out.println(header);
        System.out.println("-------------------------------------------");
        contacts.forEach(System.out::println);
    }
}
