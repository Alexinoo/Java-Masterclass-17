package nested_classes.inner_classes.part1;

import java.util.Comparator;

public class StoreEmployee extends Employee{

    private String store;

    public StoreEmployee() {
    }

    public StoreEmployee(int employeeId, String name, int yearStarted, String store) {
        super(employeeId, name, yearStarted);
        this.store = store;
    }

    @Override
    public String toString() {
        return "%-8s%s".formatted(store,super.toString());
    }

    public class StoreComparator<T extends StoreEmployee> implements Comparator<StoreEmployee>{

        @Override
        public int compare(StoreEmployee o1, StoreEmployee o2) {
            int result = o1.store.compareTo(o2.store);

            //if result is 0
            //  - then the employees work in the same store
            //  - add another comparison level by calling EmployeeComparator to sort by (yearStarted)
            //     - then sort by store as done by chaining .compare()
            if(result == 0)
                return new Employee.EmployeeComparator<>("yearStarted").compare(o1,o2);
            return result;
        }
    }
}
