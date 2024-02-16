package oop.part1.coding_challenge.inheritance.inheritance_part1;

public class Main {

    public static void main(String[] args) {

        Employee employeeOne = new Employee("Alex","22/01/1991","01/04/2017");
        printDetails(employeeOne);

        Employee employeeTwo = new Employee("Anne","22/01/1994","01/04/2019");
        printDetails(employeeTwo);
    }

    public static void printDetails(Worker worker){
        System.out.println(worker);
        System.out.println("Age = "+worker.getAge());
        System.out.println("Pay = "+worker.collectPay());
        System.out.println("_____");
    }
}
