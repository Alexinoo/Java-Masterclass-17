package generics_extra.reference_types;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        /*
            Generics Topics to cover
            =========================
            - Using generic references that uses type arguments, declared in method parameters
              and local variables
            - Creating generic methods apart from generic classes
            - Using wild cards in the type argument
            - Understanding static methods with generic types
            - Using multiple upper bounds

            - In this section, we will discuss Generic classes as reference types

            - We have Student class and LPAStudent class in this example.

            - We also have printList(List<Student> students) method that takes in List Type Student and prints their details

            - However, we have a problem, when we pass LPAStudent to this method since it's only accepting List Type Student

            - This is a bit confusing since it's not inheritance

            - We know LPAStudent inherits from Student, and we can pass an instance of LPA Student to any method, or assign
              it to any reference type, declared with the type Student

            - We also know that ArrayList implements List and  we can pass an ArrayList to a method or assign it to a reference
              of the List type

            - But why can't we pass an ArrayList of LPAStudent, to the method parameter that's declared as a List of Student

            - But that's not how it works

            - When used as reference types, a container of one type has no r/ship to the same container of another type, even if
              the contained types do have a relationship

            - When we specify Student as a type argument to a generic class or container, only Student and not one of it's subtyppes
              is valid for this container

            - Therefore we can't pass a List typed as LPAStudent to a reference variable of List typed with Student

            ///SOLUTION /////

                - Option 1
                    - Remove Type Student from the List reference type
                             printList(List<Student> students) - printList(List students)
                    - Seems like a good solution because it works, but we don't want to do this since as IntelliJ
                      is warning us about it

           // SO WHAT ARE THE OTHER ALTERNATIVES  ////

         */
        int studentCount = 10;

        //Student
        List<Student> students = new ArrayList<>();

        for (int i = 0; i < studentCount; i++) {
            students.add(new Student());
        }

        printList(students);

        //LPAStudent
        List<LPAStudent> lpaStudents = new ArrayList<>();

        for (int i = 0; i < studentCount; i++) {
            lpaStudents.add(new LPAStudent());
        }
        printList(lpaStudents);
    }

    public static void printList(List students){
        for (var student:students){
            System.out.println(student);
        }
        System.out.println();
    }
}
