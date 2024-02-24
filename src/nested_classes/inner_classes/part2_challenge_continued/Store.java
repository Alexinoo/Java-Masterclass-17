package nested_classes.inner_classes.part2_challenge_continued;

public class Store {
    public static void main(String[] args) {

        //Australian dollars
        Meal regularMeal = new Meal();
        regularMeal.addToppings("Ketchup","Mayo","Bacon","Cheddar");
        System.out.println(regularMeal);

        System.out.println("_".repeat(30));

        //US meal
        Meal USRegularMeal = new Meal(0.68);
        System.out.println(USRegularMeal);
    }
}
