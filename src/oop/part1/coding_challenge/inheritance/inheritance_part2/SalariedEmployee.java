package oop.part1.coding_challenge.inheritance.inheritance_part2;

public class SalariedEmployee extends Employee{

     double annualSalary;

     boolean isRetired;

    public SalariedEmployee(String name, String birthDate, String hireDate, double annualSalary) {
        super(name, birthDate, hireDate);
        this.annualSalary = annualSalary;
    }

    @Override
    public double collectPay() {
        //return (int)annualSalary/26;
        double paycheck = annualSalary / 26;
        double adjustedPay = isRetired ? 0.9 * paycheck : paycheck;
        return (int)adjustedPay;

    }

    public void retire(){
        terminate("01/02/2024");
        isRetired = true;
    }
}
