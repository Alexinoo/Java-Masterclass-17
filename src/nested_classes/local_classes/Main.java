package nested_classes.local_classes;

import java.util.ArrayList;
import java.util.List;

public class Main {

    /*
        Local classes
        =============
         - Local classes are inner classes, but declared directly in a code block, usually a method body
         - Because of that, they don't have access modifiers and are only available to that method body
           while it's executing
         - Like an inner class, they have access to all fields and methods on the enclosing class
         - They can also access local variables and method arguments, that are final or effectively final
         - When you create an instance of a local class, referenced variables used in the class, from the
           enclosing code are 'captured'.
         - This means a copy is made of them , and the copy is stored with the instance
         - This is done because the instance is stored in a different memory area than the local variables
           in the method
         - Therefore, if a local class uses local variables, or method arguments, from the enclosing code,
           these must be final or effectively final


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


        ////// WITH PIG LATIN NAMES ///////////
        System.out.println("////// WITH PIG LATIN NAMES ///////////");
        addPigLatinName(storeEmployees);

    }

        public static <T>void printList(List<T> storeEmployeesList){
            for (T employee:storeEmployeesList) {
                System.out.println(employee);
            }
        }

        public static void addPigLatinName(List<? extends StoreEmployee> list){

            String lastName = "Piggy";
            class DecoratedEmployee extends StoreEmployee implements Comparable<DecoratedEmployee>{
                private String pigLatinName;
                private Employee originalInstance;

                public DecoratedEmployee(String pigLatinName, Employee originalInstance) {
                    this.pigLatinName = pigLatinName+ " "+ lastName ;
                    this.originalInstance = originalInstance;
                }
                @Override
                public String toString() {
                    return originalInstance.toString() + " "+ pigLatinName;
                }


                @Override
                public int compareTo(DecoratedEmployee o) {
                    return pigLatinName.compareTo(o.pigLatinName);
                }
            }
            List<DecoratedEmployee> newList = new ArrayList<>(list.size());

            for (var employee: list) {
                String name = employee.getName();
                String pigLatin = name.substring(1) + name.charAt(0) + "ay";
                newList.add(new DecoratedEmployee(pigLatin,employee));
            }
            newList.sort(null);
            for (var dEmployee: newList) {
                System.out.println(dEmployee.originalInstance.getName() +" "+dEmployee.pigLatinName);
            }
        }
}
