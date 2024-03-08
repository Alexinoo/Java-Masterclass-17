package collections.part21_final_collections_challenge;

public class InventoryItem {

    private Product product;
    private double price;
    private int qtyTotal;
    private int qtyReserved;
    private int qtyReorder;
    private int qtyLow;

    public InventoryItem(Product product, double price, int qtyTotal, int qtyLow) {
        this.product = product;
        this.price = price;
        this.qtyTotal = qtyTotal;
        this.qtyLow = qtyLow;
        this.qtyReorder = qtyTotal;
    }

    public Product getProduct() {
        return product;
    }

    public double getPrice() {
        return price;
    }

    /* reserveItem() - called when an item get's added to the cart
    * Takes quantity - no. of products added to the cart
    * Check if quantity in stock is more than the quantity ordered
    * */

    public boolean reserveItem(int qty){
        if((qtyTotal - qtyReserved) >= qty){
            qtyReserved += qty;
            return true;
        }
        return false;
    }

    /* releaseItem() -
     * Takes quantity - no. of products added to the cart
     * Decreases the quantity left of a certain product after purchase
     * */

    public void releaseItem(int qty){
        qtyReserved -= qty;
    }

    /* sellItem() -
     * Takes quantity - no. of products added to the cart
     * Called during the check out cart process
     * */
    public boolean  sellItem(int qty){
        if(qtyTotal >= qty){
            qtyTotal -= qty;
            qtyReserved -= qty;
            if(qtyTotal <= qtyLow){
                placeInventoryOrder();
            }
            return true;
        }
        return false;
    }

    /* placeInventoryOrder() -
     * Triggered by a condition that happens on the inventory item
     * */

    private void placeInventoryOrder(){
        System.out.printf("Ordering qty %d : %s%n",qtyReorder,product);
    }

    /* toString() -
     * Return a formatted string with product, price, quantity total, and quantity reserved
     * */

    @Override
    public String toString() {
        return "%s $%.2f : [%04d,% 2d]".formatted(product,price,qtyTotal,qtyReserved);
    }
}
