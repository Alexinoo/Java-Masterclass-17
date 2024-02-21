package abstraction.abstract_classes.part4_challenge;

import java.util.ArrayList;

record OrderItem(int quantity, ProductForSale product){

}

public class Store {

    private static ArrayList<ProductForSale> storeProducts = new ArrayList<>();
    public static void main(String[] args) {
        storeProducts.add(new ArtObject("Oil Painting",1350,"Impressionistic work by ABF painted in 2010"));
        storeProducts.add(new ArtObject("Sculpture",2000,"Bronze work by JKF, produced in 1950"));
        listProducts();

        storeProducts.add(new Furniture("Desk",500,"Mahogany Desk"));
        storeProducts.add(new Furniture("Lamp",200,"Tiffany Lamp with Hummingbirds"));

        System.out.println("\n Order 1");
        var order1 = new ArrayList<OrderItem>();
        addItemToOrder(order1,1,2);
        addItemToOrder(order1,0,1);
        printOrder(order1);

        System.out.println("\n Order 2");
        var order2 = new ArrayList<OrderItem>();
        addItemToOrder(order2,3,5);
        addItemToOrder(order2,0,1);
        addItemToOrder(order2,2,1);
        printOrder(order2);
     }

    public static void listProducts(){
        for (var product: storeProducts) {
            System.out.println("_".repeat(30));
            product.showDetails();
        }
    }

    public static void addItemToOrder(ArrayList<OrderItem> order, int orderIndex,int qty){
        order.add(new OrderItem(qty,storeProducts.get(orderIndex)));
    }

    public static void printOrder(ArrayList<OrderItem> order){
        double salesTotal = 0.0;
        for (var item:order) {
            item.product().printPricedItem(item.quantity());
            salesTotal += item.product().getSalesPrice(item.quantity());
        }
        System.out.printf("Sales Total = $%6.2f %n",salesTotal);
    }
}
