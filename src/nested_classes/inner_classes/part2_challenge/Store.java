package nested_classes.inner_classes.part2_challenge;

public class Store {
    public static void main(String[] args) {

        //Australian dollars
        Meal regularMeal = new Meal();
        System.out.println(regularMeal);

        System.out.println("_".repeat(30));

        //US meal
        Meal USRegularMeal = new Meal(0.68);
        System.out.println(USRegularMeal);
    }
}
