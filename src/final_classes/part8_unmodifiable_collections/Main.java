package final_classes.part8_unmodifiable_collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        StringBuilder bobsNotes = new StringBuilder();
        StringBuilder billsNotes = new StringBuilder("Bill struggles with generics");

        Student bob = new Student("Bob",bobsNotes);
        Student bill = new Student("Bill",billsNotes);

        //Create a List of the 2 students
        List<Student> students = new ArrayList<>(List.of(bob,bill));

        //Create a copy of the students list
        List<Student> studentsFirstCopy = new ArrayList<>(students);

        //Create a second copy of the students list - immutable
        List<Student> studentsSecondCopy = List.copyOf(students);

        //Create an unmodifiable list - returns an unmodifiable view - reflects what's in the student's List
        // Does not allow a client to modify it
        // You can pass a reference to a mutating list but prevent changes through that particular reference
        List<Student> studentsThirdCopy = Collections.unmodifiableList(students);

                //Add a new student with empty notes to studentsFirstCopy
        studentsFirstCopy.add(new Student("Bonnie",new StringBuilder()));
        studentsFirstCopy.sort(Comparator.comparing(Student::getName));


        //Add a new student with empty notes to studentsSecondCopy -- Won't work!!!
        //studentsSecondCopy.add(new Student("Bonnie",new StringBuilder()));
        //studentsSecondCopy.set(0,new Student("Bonnie",new StringBuilder()));
        //studentsSecondCopy.sort(Comparator.comparing(Student::getName));

        //Add Bonnie to our original list
        students.add(new Student("Bonnie",new StringBuilder()));


        // Mutating Third copy - Won't work!!!
        //studentsThirdCopy.set(0,new Student("Bonnie",new StringBuilder()));

        //Append Bob's notes
        bobsNotes.append("Bob was one of my first student");

        //Get current Bonnies notes and append his notes
        StringBuilder bonniesNotes = studentsFirstCopy.get(2).getStudentNotes();
        bonniesNotes.append("Bonnie is taking 3 of my courses");



        //Printing
        students.forEach(System.out::println);
        System.out.println("_".repeat(50));

        studentsFirstCopy.forEach(System.out::println);
        System.out.println("_".repeat(50));

        studentsSecondCopy.forEach(System.out::println);
        System.out.println("_".repeat(50));

        studentsThirdCopy.forEach(System.out::println);
        System.out.println("_".repeat(50));




    }
}
