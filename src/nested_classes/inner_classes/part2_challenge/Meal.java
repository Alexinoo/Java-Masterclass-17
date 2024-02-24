package nested_classes.inner_classes.part2_challenge;

public class Meal {
    private double basePrice = 5.0;
    private Item burger;
    private Item drink;
    private Item side;

    private double conversionRate;

    public Meal() {
        this(1);
    }

    public Meal(double conversionRate){
        this.conversionRate = conversionRate;
        burger = new Item("regular","burger");
        drink = new Item("coke","drink",1.5);
        side = new Item("fries","side",2.0);
    }

    public double getTotal(){
        double total = burger.price + drink.price + side.price;
        return Item.getPrice(total,conversionRate);
    }

    @Override
    public String toString() {
        return "%s%n%s%n%s%n%26s$%.2f".formatted(burger,drink,side,"Total Due: ",getTotal());
    }

    private class Item{

        //burger , drink and side - Meal items
        private String name;
        private String type;
        private double price;

        public Item(String name, String type) {
            this(name,type,type.equals("burger") ? basePrice:0);
        }

        public Item(String name, String type, double price) {
            this.name = name;
            this.type = type;
            this.price = price;
        }

        @Override
        public String toString() {
            return "%10s%15s $%.2f".formatted(type,name,getPrice(price,conversionRate));
        }

        private static double getPrice(double price,double rate){
            return price * rate;
        }
    }
}
