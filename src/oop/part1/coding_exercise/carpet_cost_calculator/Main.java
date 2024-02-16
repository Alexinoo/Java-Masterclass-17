package oop.part1.coding_exercise.carpet_cost_calculator;

public class Main {

    public static void main(String[] args) {

        Carpet carpet = new Carpet(3.5);
        Floor floor = new Floor(2.75,4.0);
        Calculator calculator = new Calculator(floor , carpet);

        System.out.println("total = "+calculator.getTotalCost());

        carpet = new Carpet(1.5);
        floor = new Floor(5.4 , 4.5);
        calculator = new Calculator(floor,carpet);

        System.out.println("total = "+calculator.getTotalCost());


        carpet = new Carpet(1.5);
        floor = new Floor(5.5 , 3.5);
        calculator = new Calculator(floor,carpet);

        System.out.println("total = "+calculator.getTotalCost());

        carpet = new Carpet(3.5);
        floor = new Floor(2.5 , 6.0);
        calculator = new Calculator(floor,carpet);

        System.out.println("total = "+calculator.getTotalCost());
    }
}
