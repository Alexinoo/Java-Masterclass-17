package final_classes.part10_constructors_review;

import final_classes.part2_external.util.Child;

public class Main {

    public static void main(String[] args) {
        Parent parent =  new Parent("Jane Doe","01/01/1900",4);
        Child child =  new Child();

        System.out.println("Parent: "+parent);
        System.out.println("Child: "+child);
    }
}
