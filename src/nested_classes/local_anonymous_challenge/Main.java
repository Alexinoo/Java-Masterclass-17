package nested_classes.local_anonymous_challenge;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Alex","Mwangi","01/04/2017"));
        employees.add(new Employee("Cyrus","Mwangi","01/12/2019"));
        employees.add(new Employee("Loice","Wakonyo","22/09/2014"));
        employees.add(new Employee("Evans","Koech","01/06/2022"));

        //Call printOrderedList method - Sort by name
        printOrderedList(employees,"name");

        System.out.println("_".repeat(45));

        //Call printOrderedList method - Sort by year by default in case name is not provided
        printOrderedList(employees,"");
    }

    public static void printOrderedList(List<Employee> eList , String sortField){
        int currentYear = LocalDate.now().getYear();

        class MyEmployee{
            Employee containedEmployee;
            int yearsWorked;
            String fullName;

            public MyEmployee(Employee containedEmployee) {
                this.containedEmployee = containedEmployee;
                yearsWorked = currentYear - Integer.parseInt(containedEmployee.hireDate().split("/")[2]);
                fullName = String.join(" ",containedEmployee.firstName(), containedEmployee.lastName());
            }

            @Override
            public String toString() {
                return "%s has been an employee for %d years".formatted(fullName,yearsWorked);
            }
        }

        List<MyEmployee> list = new ArrayList<>(); // Add an empty list of MyEmployees

        //loop through Employees list and add to MyEmployee
        for (var employee :eList) {
            list.add(new MyEmployee(employee));
        }

        //sort by sortField functionality using anonymous Class
        var comparator = new Comparator<MyEmployee>(){
            @Override
            public int compare(MyEmployee o1, MyEmployee o2) {
                if(sortField.equals("name")){
                    return o1.fullName.compareTo(o2.fullName);
                }
                return o1.yearsWorked - o2.yearsWorked;
            }
        };
        
        //sort list using anonymous class that implements Comparator interface
        list.sort(comparator);
        
        //print employees 
        for (MyEmployee employee: list ) {
            System.out.println(employee);
        }
    }
}
