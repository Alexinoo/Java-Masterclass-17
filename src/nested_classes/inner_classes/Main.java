package nested_classes.inner_classes;

import java.util.ArrayList;
import java.util.List;

public class Main {
    /*
       Inner Classes
       ==============

       - They are non-static classes, declared on an enclosing class at the member level
       - They can have any of the four valid access modifiers
       - They have access to instance members, including private members of the enclosing class
       - Instantiating an inner class from external code is a bit tricky

       - As of JDK 16, static members of all types are supported on inner classes

       - we get an error if we try to access StoreComparator statically from the StoreEmployee class

       - This is because an inner class requires an instance of the enclosing class to be used to
         instantiate an inner class

            new StoreEmployee.StoreComparator<>()
       - Here, we're just calling the class StoreEmployee and not an actual instance of the StoreEmployee
         class and thus it doesn't work

       - Therefore, the solution is to instantiate the StoreEmployee class first and then call .new keyword
         before the StoreComparator inner class
            e.g.
                 var genericEmployee = new StoreEmployee();
                 var comparator = genericEmployee.new StoreComparator<>();

        - We can also chain the instantiations
            e.g.
                new StoreEmployee().new StoreComparator<>()

        - This dot new syntax isn't calling a method, but it will create an instance of an inner class,
          which we've declared on StoreEmployee

        - To create an instance of an inner class, you first must have an instance of the Enclosing class

            e.g.
                EnclosingClass outerClass = new EnclosingClass();
                EnclosingClass.InnerClass innerClass = outerClass.new InnerClass();

        - From that instance you call .new followed by the inner class name and parentheses, taking any
         constructor arguments

        - Many times, an inner class is never accessed or instantiated from outside the enclosing class, but you
          should be still familiar with the syntax
     */

    public static void main(String[] args) {
        List<StoreEmployee> storeEmployees = new ArrayList<>(List.of(
              new StoreEmployee(10015,"Meg",2019,"Target"),
              new StoreEmployee(10515,"Joe",2021,"Walmart"),
              new StoreEmployee(10105,"Tom",2020,"Macys"),
              new StoreEmployee(10215,"Marty",2018,"Walmart"),
              new StoreEmployee(10322,"Bud",2016,"Target")
        ));

        //// BEFORE SORTING
        System.out.println("Before Sorting");
        printList(storeEmployees);

        // Calling EmployeeComparator statically
        // since StoreEmployee class extends Employee - then we can access it
        System.out.println("Sorting via EmployeeComparator by names");
        storeEmployees.sort(new StoreEmployee.EmployeeComparator<>());
        printList(storeEmployees);


        // Calling EmployeeComparator statically
        // since StoreEmployee class extends Employee - then we can access it
        System.out.println("Sorting via StoreComparator by store & year started");
        var genericEmployee = new StoreEmployee();
        var comparator = genericEmployee.new StoreComparator<>();
        storeEmployees.sort(comparator);
        printList(storeEmployees);
    }

    public static <T>void printList(List<T> storeEmployeesList){
        for (T employee:storeEmployeesList) {
            System.out.println(employee);
        }
    }
}
