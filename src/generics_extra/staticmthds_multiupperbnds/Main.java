package generics_extra.staticmthds_multiupperbnds;

import generics_extra.staticmthds_multiupperbnds.util.QueryList;

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

                - Option 2
                    - Make printList(List<Student> students) a generic method
                    - We can create a generic method on any class not just a generic class
                    - We can set up a type parameter for this method in <T> just before the return type which is void in this case
                    - Then use T where we would have a Type and include them as <T> after List in the method parameter
                        e.g. public static <T> void printList(List<T> students)
                    - That fixed the problem, we can run the code with the same results and no warnings

                    - List<T> students - Here, instead of saying this method will take only a List of Type Student, we are saying, it will take
                     a List of any kind of type

            Generic Method
            ==============
                - For a method, type parameters are placed after any modifiers and before the method's return type

                - The type parameter can be referenced in method parameters, or as the method return type, or in the
                  method code block

                    e.g
                        public <T> String myMethod(T input){
                            return input.toString()
                          }
                - A generic method can be used for static methods on a generic class, because static methods can't use
                  class type parameters

                - A generic method type parameter can be used on a non-generic class to enforce type rules on a specific
                  method
                - In fact , if you have used T for both, the T declared on the method means a different type, than the T
                  for class

                - Like a generic class's type parameter, we can use upper bounds for the type which both restricts the type
                  that we can pass which both restricts the types we can pass but allows us to use that type's methods

                     -  Adding upper bound student

                         e.g. public static <T extends Student> void printList(List<T> students)

                - This means we can pass a list of Students or LPA Students to this method

                - Now we can only use this method for a List of Students, or a subtype of Students

                - We are also able to use Student methods within this method block
                    e.g
                    System.out.println(student.getYearStarted()+ " " +student);

                - Although this solution allowed us to explore generic methods, it might not be the
                  preferred solution

            Use Wildcard (?)
            ================

                - This is what Java calls wild card in the type argument and is represented by a question mark (?)

                - A wild card can only be used in a type argument and not in the type parameter declaration

                    List declaration using a wildcard
                        List<?> unknownList;

                - A wild card means the type is unknown

                - For this reason, a wildcard limits what you can do when you specify a type that way

                - A wild class can't be used in an instantiation of a generic class
                    var myList = new ArrayList<?>();

                - A wildcard can be unbounded, or alternatively, specify either an upper or lower bound

                    Argument            Example                           Desc
                    --------            -------
                    unbounded           List<?>                      A List of any type can be passed/assigned to a List
                                                                     using this wildcard

                    upper bound         List<? extends Student>      A list containing any type that is a Student or a subtype
                                                                     of Student can be assigned/passed to an argument specifying ?

                    lower bound         List<? super LPAStudent>     A list containing any type that is an LPAStudent or a super type
                                                                     of LPAStudent, so in our case that would be Student AND Object

            Type erasure
            ============

                - Generics exists to enforce tighter type checks at compile time

                - The compiler transforms a generic class into a typed class, meaning the byte code, or class file, contains no
                  type parameters

                - Everywhere a type parameter is used in a class, it gets replaced with either the type Object, if no upper bound
                  was specified, or the upper bound type itself

                - This transformation process is called type erasure, because the T parameter (or S , U, V) is erased, or replaced
                  with a true type
                    Demo
                    -----
                    - Created static method called testList(List<String> list) {}
                    - Created static method called testList(List<Integer> list) {}

                    - This overloading won't work because IntelliJ complains that both methods have same type erasure

                    - This also means that A List has no upper bound declared fo it, so it always resolves in byte code,
                      to a List of Object

                    - So how would you code something like this.?

         */
        int studentCount = 10;

        //Student
        List<Student> students = new ArrayList<>();

        for (int i = 0; i < studentCount; i++) {
            students.add(new Student());
        }

        //printList(students);
        printMoreList(students);

        //LPAStudent
        List<LPAStudent> lpaStudents = new ArrayList<>();

        for (int i = 0; i < studentCount; i++) {
            lpaStudents.add(new LPAStudent());
        }
        // printList(lpaStudents);
        printMoreList(lpaStudents);

        //Call testList - Type Erasure
        testList(new ArrayList<String>(List.of("Able","Barry","Charlie")));
        testList(new ArrayList<Integer>(List.of(1,2,3)));

       // Using QueryList - restricts to QueryList types only
        var queryList = new QueryList<>(lpaStudents);
        var matches = queryList.getMatches("Course","Python");
        printMoreList(matches);

        // Static methods and multiple upper bounds (More generic)
        var students2021 = QueryList.getMatches(students,"YearStarted","2021");
        printMoreList(students2021);

        //use QueryList class with the Employee Record
        //QueryList<Employee> employeeList = new QueryList<Employee>();
    }

    public static void printMoreList(List<? extends Student> students){
        for (var student:students){
            System.out.println(student.getYearStarted()+ " : " +student);
        }
        System.out.println();
    }

    public static void testList(List<?> list){
        for (var element:list){
            if(element instanceof String s){
                System.out.println("String :"+s.toUpperCase());
            }else if(element instanceof Integer i){
                System.out.println("Integer :"+i.floatValue());
            }
        }
    }

//    public static void testList(List<String> list){
//        for (var element:list){
//            System.out.println("String :"+element.toUpperCase());
//        }
//    }
//
//    public static void testList(List<Integer> list){
//        for (var element:list){
//            System.out.println("Integer :"+element.floatValue());
//        }
//    }

//    public static <T extends Student> void printList(List<T> students){
//        for (var student:students){
//            System.out.println(student.getYearStarted()+ " : " +student);
//        }
//        System.out.println();
//    }
}
