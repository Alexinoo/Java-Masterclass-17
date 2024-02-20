package array_lists.array_list_exercise;

public class Main {

    public static void main(String[] args) {
        Contact myContact1 = new Contact("Alex","+254717316925");
        Contact myContact2 = new Contact("Ann","+254707294300");
        Contact myContact3 = new Contact("Nancy","+254723219477");
        Contact myContact4 = new Contact("Anthony","+254721623819");

        MobilePhone phone = new MobilePhone(null);

        //Adding Contacts
        phone.addNewContact(myContact1);
        phone.addNewContact(myContact2);
        phone.addNewContact(myContact3);
        phone.addNewContact(myContact4);
        phone.printContacts();

        System.out.println("_".repeat(30));

        //Update Contacts
        phone.updateContact(myContact1,new Contact("Alexis","+254755543918"));
        phone.printContacts();

        System.out.println("_".repeat(30));

        // Remove Contacts
        phone.removeContact(myContact4);
        phone.printContacts();

        //Query Contacts
        System.out.println(phone.queryContact("Nancy").getPhoneNumber());
    }
}
