package collections.part21_final_collections_challenge;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Cart {

    enum CartType  { PHYSICAL,VIRTUAL};

    /*Generate unique cart id for each cart*/
    private static int lastId = 1;
    private int id;
    private LocalDate cartDate;
    private CartType type;

    //Map of products -
    //  keyed by String sku
    //  Integer rep qty ordered
    Map<String,Integer> products;

    public Cart(CartType type,int days) {
        this.type = type;
        id = lastId++;
        cartDate = LocalDate.now().minusDays(days);
        products = new HashMap<>();
    }

    public Cart(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public LocalDate getCartDate() {
        return cartDate;
    }

    public Map<String, Integer> getProducts() {
        return products;
    }

    /*
        addItem()
        - Adds item to map (products) and adds qty to the curr qty
        - If not in the map, it will insert a new entry using the qty
        - If for some reason.. there are not enough items/qty in the stock - print
          something went wrong
     */

    public void addItem(InventoryItem item, int qty){
        products.merge(item.getProduct().sku(),qty,Integer::sum);

        if(!item.reserveItem(qty))
            System.out.println("Ouch, something went wrong, could not add item");

    }

    /*
        removeItem()
        - get the current quantity from the products Map on this cart
        - If current qty  from Map[] is <= qty passed .. we don't want to subtract more than we have
         so set the quantity to whatever it is on the map
        - remove the product from products map altogether
     */

    public void removeItem(InventoryItem item, int qty){

        int currentQty = products.get(item.getProduct().sku()); //5

        if(currentQty <= qty){
            qty = currentQty;
            products.remove(item.getProduct().sku());
            System.out.printf("Item [%s] was removed from basket%n",item.getProduct().name());
        }else{
            products.merge(item.getProduct().sku(), qty, (oldVal , newVal)->oldVal - newVal);
            System.out.printf("%d [%s]s removed%n",qty,item.getProduct().name());
        }
        item.releaseItem(qty);
    }

    /*
        printSalesSlip(products)
        - Pass Map products and print every item plus sales price
        - Print every product in the sales slip plus the price
        - Calculate total
     */
    public void printSalesSlip(Map<String,InventoryItem> inventory){
        double total = 0;
        System.out.println("_".repeat(50));
        System.out.println("Thank you for your sale: ");
        for (var cartItem: products.entrySet() ) {
            var item = inventory.get(cartItem.getKey());
            int qty = cartItem.getValue();
            double itemizedPrice = (item.getPrice() * qty);
            total += itemizedPrice;
            System.out.printf("\t%s %-10s (%d)@ $%.2f = $%.2f%n",
                    cartItem.getKey(),
                    item.getProduct().name(),
                    qty,
                    item.getPrice(),itemizedPrice
                    );
        }
        System.out.printf("Total sale: $%.2f",total);
        System.out.println("_".repeat(50));
    }
    /*
        toString()

     */

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", cartDate=" + cartDate +
                ", products=" + products +
                '}';
    }
}
