package final_classes.part11_record_constructors;

public class Main {

    public static void main(String[] args) {

        // Calling Canonical constructor
        Person joe = new Person("Joe","01-01-1950");
        System.out.println(joe);

        // Calling copy constructor
        Person joeCopy = new Person(joe);
        System.out.println(joeCopy);
    }
}
