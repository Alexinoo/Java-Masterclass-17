package final_classes.part3_immutable_classes;

public class Main {
    public static void main(String[] args) {

        //Person jane = new Person();
        //jane.setName("Jane");
        //Person jim = new Person();
        //jim.setName("Jim");
        //Person joe = new Person();
        //joe.setName("Joe");
        //Person john = new Person();
        //john.setName("John");
        //john.setDob("05/05/1900");
        //john.setKids(new Person[]{jane,jim,joe});
        //System.out.println(john); //John, dob = 05/05/1900, kids = Jane, Jim, Joe

        ////Mutate
        //john.setName("Jacob");
        //john.setKids(new Person[]{new Person(),new Person()});
        //System.out.println(john); //Jacob, dob = 05/05/1900, kids = null, null


        // Creating with the args constructor
        Person jane = new Person("Jane","01/01/1930");
        Person jim = new Person("Jim","02/02/1932");
        Person joe = new Person("Joe","03/03/1934");

        Person[] johnKids = {jane,jim,joe};
        Person john = new Person("John","05/05/1900",johnKids);

        System.out.println(john); //John, dob = 05/05/1900, kids = Jane, Jim, Joe

        //set the kids
        john.setKids(new Person[]{new Person("Ann","04/04/1930")});
        System.out.println(john); //John, dob = 05/05/1900, kids = Ann

        //Set first kid to Jim instance
        Person[] kids = john.getKids();
        kids[0] = jim;
        System.out.println(john); //John, dob = 05/05/1900, kids = Jim

        //set john's kids to null - No effect
        kids = null;
        System.out.println(john); //John, dob = 05/05/1900, kids = Jim

        // set john's kids to null using Person setKids()
        john.setKids(kids);
        System.out.println(john); //John, dob = 05/05/1900, kids = N/A

    }
}
