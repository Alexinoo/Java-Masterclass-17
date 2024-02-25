package nested_classes.anonymous_classes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    /*
        Anonymous Classes
        =================
        - An anonymous class is a local class that does not have a name
        - All the nested classes we've looked at so far have been created with a class declaration.
        - The anonymous class is never created with a class declaration, but it's always instantiated as
          part of an expression
        - Anonymous classes are used a lot less since the introduction of Lambda Expressions in JDK 8
        - But there are still some use cases where an anonymous class might be a good solution
        - Understanding anonymous classes, leads to a better understanding of lambda expression
        - An anonymous class is instantiated and assigned in a single element

            e.g.
                var c4 = new Comparator<StoreEmployee>(){};
        - The new keyword is used followed by any type.
        - This is NOT the type of the class being instantiated
        - It's the super class of the anonymous class, or it's the interface this anonymous class will implement
        - On the example above, the anonymous unnamed class will implement the Comparator interface

        - In the second example below, the anonymous class extends the Employee class meaning it's a sub class of
          Employee
            e.g.
                var e1 = new Employee {};
        - In both cases, it's important to remember the semi-colon after the closing bracket, because this is an expression
          and not a declaration

        - We can as well pass the anonymous class as method argument and not necessarily assign first to a variable

            e.g
                var c4 = new Comparator<StoreEmployee>(){
                        @Override
                        public int compare(StoreEmployee o1, StoreEmployee o2) {
                            return o1.getName().compareTo(o2.getName()); //compares with name
                        }
                    };
                sortIt(storeEmployees, c4);

              /// SAME AS BELOW ////
                sortIt(storeEmployees, new Comparator<StoreEmployee>(){
                        @Override
                        public int compare(StoreEmployee o1, StoreEmployee o2) {
                            return o1.getName().compareTo(o2.getName()); //compares with name
                        }
                 });

           - However, IntelliJ , grays out the new Comparator part of this expression and after hovering,
             it tells us that the Anonymous new Comparator can be replaced with a lambda expression and gives
             us the option to replace

                e.g.
                      sortIt(storeEmployees, (o1, o2) -> {
                        return o1.getName().compareTo(o2.getName()); //compares with name
                    });



     */

    public static void main(String[] args) {

        List<StoreEmployee> storeEmployees = new ArrayList<>(List.of(
                new StoreEmployee(10015,"Meg",2019,"Target"),
                new StoreEmployee(10515,"Joe",2021,"Walmart"),
                new StoreEmployee(10105,"Tom",2020,"Macys"),
                new StoreEmployee(10215,"Marty",2018,"Walmart"),
                new StoreEmployee(10322,"Bud",2016,"Target")
        ));

        //Different comparators that we have created so far
        // c0- Stand-alone
        // c1 - nested static comparator
        // c2 - inner class comparator

        var c0 = new EmployeeComparator<StoreEmployee>();
        var c1 = new Employee.EmployeeComparator<StoreEmployee>();
        var c2 = new StoreEmployee().new StoreComparator<StoreEmployee>();

        //local class that acts as a Comparator
        class NameSort<T> implements Comparator<StoreEmployee> {
            @Override
            public int compare(StoreEmployee o1, StoreEmployee o2) {
                return o1.getName().compareTo(o2.getName()); //compares with names
            }
        }

        //Add another variable which is an instance of the local class above
        var c3 = new NameSort<StoreEmployee>();


        //Using anonymous class
        var c4 = new Comparator<StoreEmployee>(){
            @Override
            public int compare(StoreEmployee o1, StoreEmployee o2) {
                return o1.getName().compareTo(o2.getName()); //compares with name
            }
        };

        //Call sortIt on StoreEmployees and pass comparators above
        sortIt(storeEmployees, c0);  //sorts by name
        sortIt(storeEmployees, c1);  // sorts by name
        sortIt(storeEmployees, c2); // sorts by store & yearHired
        sortIt(storeEmployees, c3); // sorts by name using NameSort class
        sortIt(storeEmployees, c4); // sorts by name using anonymous Comparator class

        //// Passing anonymous class as an argument directly - Replaced Later with lambda expression ///
        sortIt(storeEmployees, (o1, o2) -> {
            return o1.getName().compareTo(o2.getName()); //compares with name
        });


    }

    public static <T> void sortIt(List<T> list, Comparator<? super T> comparator){
        System.out.println("Sorting with comparator: "+comparator.toString());
        list.sort(comparator);
        for (var employee:list ) {
            System.out.println(employee);
        }
    }
}
