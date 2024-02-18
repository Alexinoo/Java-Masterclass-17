package oop.master_challenge_part3;

public class Main {

    public static void main(String[] args) {
        Item coke = new Item("drink","coke",1.50);
        coke.printItem();
        coke.setSize("LARGE");
        coke.printItem();


        Item avocado = new Item("Topping","avocado",1.50);
        avocado.printItem();

        System.out.println("_".repeat(30));

        Burger burger = new Burger("regular",4.00);
        burger.addToppings("BACON","CHEESE","MAYO");
        burger.printItem();


        System.out.println("_".repeat(35));
        MealOrder regularMeal = new MealOrder();
        regularMeal.addBurgerToppings("BACON","CHEESE","MAYO");
        regularMeal.setDrinkSize("LARGE");
        regularMeal.printItemizedList();

        System.out.println("_".repeat(40));
        MealOrder secondMeal = new MealOrder("turkey","7-up","chilli");
        secondMeal.addBurgerToppings("LETTUCE","CHEESE","MAYO");
        secondMeal.setDrinkSize("SMALL");
        secondMeal.printItemizedList();

        System.out.println("_".repeat(45));
        MealOrder deluxeMeal =new MealOrder("deluxe","7-up","chili");
        deluxeMeal.addBurgerToppings("AVOCADO","BACON","LETTUCE","CHEESE","MAYO");
        deluxeMeal.setDrinkSize("SMALL");
        deluxeMeal.printItemizedList();

    }
}
