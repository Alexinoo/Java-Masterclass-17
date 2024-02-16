package oop.part1.coding_challenge.inheritance.inheritance_part2;

public class Main {

    public static void main(String[] args) {

        Employee employeeOne = new Employee("Alex","22/01/1991","01/04/2017");
        printDetails(employeeOne);

        SalariedEmployee salEmp = new SalariedEmployee("Anne","22/01/1994","01/04/2019",35000);
        printDetails(salEmp);

        salEmp.retire();
        System.out.println(salEmp.name +"'s Pension cheque = $"+ salEmp.collectPay());

        HourlyEmployee casualEmp = new HourlyEmployee("Musakhulu","01/01/1985","01/01/2021",15);
        printDetails(casualEmp);
        System.out.println(casualEmp.name+"'s Holiday Pay = $"+casualEmp.getDoublePay());

    }

    public static void printDetails(Worker worker){
        System.out.println(worker);
        System.out.println(worker.name +"'s Age = "+worker.getAge());
        System.out.println(worker.name +"'s Paycheck = $"+worker.collectPay());
        System.out.println("_____");
    }
}
