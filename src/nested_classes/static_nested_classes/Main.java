package nested_classes.static_nested_classes;

import java.util.ArrayList;
import java.util.List;

public class Main {
     /*
        Static Nested Class
        ====================

        - is a class enclosed in the structure of another class and declared as static

        - This means this class, if accessed externally, requires the outer class name as
          part of the qualifying name

        - This class has the advantage of being able to access private attributes on the
          outer class

        - The enclosing class can access any attributes on the static nested class, also including
          private attributes

     */

    public static void main(String[] args) {

        //Set up initial ArrayList with 5 employees not in particular order
        List<Employee> employees = new ArrayList<>(List.of(
                new Employee(10001,"Ralph",2015),
                new Employee(10005,"Carole",2021),
                new Employee(10022,"Jane",2013),
                new Employee(13151,"Laura",2020),
                new Employee(10050,"Jim",2018)
        ));

        //// BEFORE NESTING EmployeeComparator class ////

        //sort with local EmployeeComparator variable
        // var comparator = new EmployeeComparator<>();  //same as below
        //EmployeeComparator<Employee> comparator = new EmployeeComparator<>();
        //employees.sort(comparator);

        //// AFTER NESTING ////
        //instantiate and pass the Comparator to the sort method
        //only diff is to specify that this comparator is accessed through the Employee class
        //sorts by name by default
        System.out.println("Sorted By Name");
        employees.sort(new Employee.EmployeeComparator<>());
        printList(employees);

        // sorts by yearStarted
        System.out.println("Sorted By Year Hired , ASC");
        employees.sort(new Employee.EmployeeComparator<>("yearStarted"));
        printList(employees);

        //comparator has default method called reverse which we can use
        // by chaining to the current comparator

        System.out.println("Sorted By Year Hired , DESC");
        employees.sort(new Employee.EmployeeComparator<>("yearStarted").reversed());
        printList(employees);

    }

    public static void printList(List<Employee> employeesList){
        for (var employee:employeesList) {
            System.out.println(employee);
        }
    }

}
