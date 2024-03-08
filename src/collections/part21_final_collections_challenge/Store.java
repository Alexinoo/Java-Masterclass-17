package collections.part21_final_collections_challenge;

import java.time.LocalDate;
import java.util.*;

public class Store {

    /* static fields for a random instance- use it to assign diff prices to my products later */
    private static Random random = new Random();


    /* Make inventory a Map keyed by sku */
    private Map<String,InventoryItem> inventory;

    /* Cart collection can be a set */
    /* - Make it a NavigableSet so that we can use NavigableSet mthods as we look for abandoned carts
       later
       - Instantiate as a TreeSet passing a comparator to the constructor and ordering this TreeSet by cart id
     */

    private NavigableSet<Cart> carts = new TreeSet<>(Comparator.comparing(Cart::getId));


    /* aisle inventory is going to be a Map keyed by product category
    * The type of the element is a nested map with product name(key),InventoryItem(value)
    *  */
    private Map<Category,Map<String,InventoryItem>> aisleInventory;
    public static void main(String[] args) {
        Store myStore = new Store();

        //Stock store and print inventory
        myStore.stockStore();
        myStore.listInventory();

        //Stock aisles and print products by category
        myStore.stockAisles();
        myStore.listProductsByCategory();

        // add items to cart
        myStore.manageStoreCarts();
        myStore.listProductsByCategory(false,true);


        //print cart
        myStore.carts.forEach(System.out::println);


        //Calling abandonedCarts
        //Then list inventory
        //call carts set
        myStore.abandonCarts();
        myStore.listProductsByCategory(false,true);
        myStore.carts.forEach(System.out::println);
    }

    /* Manage storeCarts
    *
    *
    * Let us the customers in and create Cart - cart1
    *
    * Since CartType is a nested enum, it's implicitly static and needs to
    * be referenced using the Cart Class name, ..then CartType..and then the constant
    *
    * Then pass an integer which adjusts the Cart date - means 1 day will be subtracted from today
    * when creating this cart
    *
    * We want some carts with yesterday's date to test abandoning them later
    *
    * Then add cart1 to my carts set on this store
    *
    * Then add items using the aisleInventory map by first selecting the aisle by product
    * category and then the product name
    *
    * Add 6 apples to the cart with quantity as 6
    * Add 5 pears - directly from addItem
    * Add 1 Beverage - coffee
    * Then print the first cart out & invoke this method on the store
    *
    * Remove 2 pears from this cart
    * Then print the removal of 2 pairs
    *
    *
    *
    *
    *
    * PART ////3///
    * Create a virtual Cart cart2 from online shopping using yesterday's date
    * Then add it to the Carts set
    * Add 20 Lemons
    * Add 10 Bananas
    * Add items via inventory using sku instead of aisleInventory
    * Print the second cart
    *
    *
    *
    *
    * Create a virtual Cart cart3 from online shopping using today's date
    * Then add it to the Carts set
    * Add rice - 998kg
    * Execute Low threshold code since low is 5 and 1000-998 = 2
    * So let's add checkOutCart
    *
    *
    *
    * Create a Physical Cart cart4 using today's date
    * */
    private void manageStoreCarts(){
        Cart cart1 = new Cart(Cart.CartType.PHYSICAL,1);
        carts.add(cart1);

        InventoryItem item = aisleInventory.get(Category.PRODUCE).get("apple");
        cart1.addItem(item,6);
        cart1.addItem(aisleInventory.get(Category.PRODUCE).get("pear"),5);
        cart1.addItem(aisleInventory.get(Category.BEVERAGE).get("coffee"),1);
        System.out.println(cart1);

        cart1.removeItem(aisleInventory.get(Category.PRODUCE).get("pear"),2);
        System.out.println(cart1);



        Cart cart2 = new Cart(Cart.CartType.VIRTUAL,1);
        carts.add(cart2);
        cart2.addItem(inventory.get("L103"),20);
        cart2.addItem(inventory.get("B100"),10);
        System.out.println(cart2);


        Cart cart3 = new Cart(Cart.CartType.VIRTUAL,0);
        carts.add(cart3);
        cart3.addItem(inventory.get("R777"),998);
        System.out.println(cart3);

        if(!checkOutCart(cart3))
            System.out.println("Something went wrong..could not check out");

        Cart cart4 = new Cart(Cart.CartType.PHYSICAL,0);
        carts.add(cart4);
        cart4.addItem(aisleInventory.get(Category.BEVERAGE).get("tea"),1);
        System.out.println(cart4);


    }

    /* Check out a cart
    * returns a boolean if that operation was successful
    *
    * Set a for loop that gets the view , the entrySet of the products in the cart
    * and loop through each of these entries
    *
    * Entry is a nested interface type on Map, with each having key & value
    *
    * We can use getKey() to get key which returns the sku
    * and getValue() to get the values which returns the quantity of this products,how many we want to buy
    *
    * Then get quantity via getValue()
    *
    * Invoke sellItem() on each inventory item and if it returns false..
    *  stop processing and return false an quit from this method
    *
    * If it ends successfully , then that means we've successfully sold the item on every
    * inventory item successfuly , which means we can then print the sales slip
    *
    * Then remove carts from the carts set
    * */
    private boolean checkOutCart(Cart cart){
        for (var cartItem: cart.getProducts().entrySet() ) {
            var item = inventory.get(cartItem.getKey());
            int qty = cartItem.getValue();
            if(!item.sellItem(qty)) return false;
        }
        cart.printSalesSlip(inventory);
        carts.remove(cart);
        return true;
    }

    /* Abandon carts
    *
    *  This code is going to remove items from abandoned carts and remove the carts from the carts set
    *
    * An abandoned cart is any cart that doesn't have today's date
    *
    * To set this up
    *
    * Get the current day of the year
    *
    * Set up a local variable lastCart and initialize it to null
    * Then loop through the carts and set the last cart in the carts set with a date != current date
    *
    * The next for loop is simply going to adjust the reserved qty in my inventory
    * subtracting the abandoned products qty from the reserved qty
    *  */
    private void abandonCarts(){
        int dayOfYear = LocalDate.now().getDayOfYear();
        Cart lastCart = null;
        for (Cart cart: carts ) {
            if(cart.getCartDate().getDayOfYear() == dayOfYear)
                break;
            lastCart = cart;
        }
        var oldCarts = carts.headSet(lastCart,true);
        Cart abandonedCart = null;
        while( (abandonedCart = oldCarts.pollFirst()) != null){
            for (String sku: abandonedCart.getProducts().keySet() ) {
                InventoryItem item = inventory.get(sku);
                item.releaseItem(abandonedCart.getProducts().get(sku));
            }
        }
    }

    /* List Products  by aisle
    *
    * Loop through the aisleInventory
    *
    * Loop through first with the Key which is the product Category [PRODUCE,DAIRY,..etc] in btwn dashed lines
    *
    * Then get the Map by the key and loop through the nested map's key
    *  */
    private void listProductsByCategory(){
         // Commented after adding the overloaded version
         //aisleInventory.keySet().forEach(key -> {
         //    System.out.println("----------\n"+key+ "\n----------");
         //    aisleInventory.get(key).keySet().forEach(System.out::println);
        // });

        //Calling the overloaded version
        listProductsByCategory(true,false);
    }



    /*  (Overloaded version) of listProductsByCategory()
     *
     * Printed out by category,..then product but with the inventory details
     *
     * 2 boolean parameters - includeHeader & includeDetails
     *
     * Only print key in the dashed part only if includeHeader is true
     *
     * print just keys if includeDetails is false
     *
     * Otherwise, print the value
     *
     * Then call this from the overloaded version with no params
     *
     * Again call this from the main with false and true values listProductsByCategory(false,true)
     *  */
    private void listProductsByCategory(boolean includeHeader,boolean includeDetails){
        aisleInventory.keySet().forEach(key -> {
           if(includeHeader)
               System.out.println("----------\n"+key+ "\n----------");
           if(!includeDetails)
                aisleInventory.get(key).keySet().forEach(System.out::println);
           else
               aisleInventory.get(key).values().forEach(System.out::println);

        });
    }

    /* 2 Helper methods to stockStore() / stockAisles() */

    /* stockStore()
    *
    *  Allows us to stock some products first - lets us stock the store and aisles
    *  Use productData file
    *
    * */
    private void stockStore(){
        /*
          - HashMap is used on inventory to easily retrieve product info via the sku field
          - Loop through products and add to inventory HashMap
                - use a lambda expression to add a new instance of a new inventory item,into inventory map using put()
                    WHERE sku is the (key) & inventoryItem as (value)
         */
        inventory = new HashMap<>();
        List<Product> products = new ArrayList<>(List.of(
                new Product("A100","apple","local",Category.PRODUCE),
                new Product("B100","banana","local",Category.PRODUCE),
                new Product("P100","pear","local",Category.PRODUCE),
                new Product("L103","lemon","local",Category.PRODUCE),
                new Product("M201","milk","farm",Category.DAIRY),
                new Product("Y001","yogurt","farm",Category.DAIRY),
                new Product("C333","cheese","farm",Category.DAIRY),
                new Product("R777","rice chex","Nabisco",Category.CEREAL),
                new Product("G111","granola","Nat Valley",Category.CEREAL),
                new Product("BB11","ground beef","butcher",Category.MEAT),
                new Product("CC11","chicken","butcher",Category.MEAT),
                new Product("BC11","bacon","butcher",Category.MEAT),
                new Product("BC77","coke","coca cola",Category.BEVERAGE),
                new Product("BC88","coffee","value",Category.BEVERAGE),
                new Product("BC99","tea","herbal",Category.BEVERAGE)
        ));

        products.forEach( product -> inventory.put(
                                    product.sku(),
                                    new InventoryItem(product,random.nextDouble(0,1.25),1000,5))
                                     );


    }

    /* stockAisles()
     *
     *  Keyed by product category
     *  so what should the collection element be ?
     *
     * A shopper first find the right aisle,
     * then search the shelves for the product/product name they want
     *
     * That is why we wanted aisleInventory map to contain a nested map
     *
     * set this up as a new instance of an enumMap and pass it my category class
     *
     * Then loop through the inventory
     *
     * Get category from the item through getProduct().category() // PRODUCE,DAIRY,CEREAL,MEAT,BEVERAGE
     *
     * Check if it's already in my aisle inventory using aisle/category key
     *
     * If null, then it's not in my map .. so set up a new TreeMap()
     *
     * Otherwise, add the inventory item, with product name as (key) item as (value)
     *
     * Then add the category[DAIRY,CEREAL,PRODUCE..etc] plus items in each category to aisleInventory using putIfAbsent
     *  */
    private void stockAisles(){
        aisleInventory = new EnumMap<>(Category.class);
        for(var item : inventory.values()){
            Category aisle = item.getProduct().category();
            Map<String,InventoryItem> productMap = aisleInventory.get(aisle);
            if(productMap == null){
                productMap = new TreeMap<>();
            }
            productMap.put(item.getProduct().name(),item);
            aisleInventory.putIfAbsent(aisle,productMap);
        }
    }

    /*
        List Products on the inventory
     */
    private void listInventory(){
        /*
            Loop through the values in the inventory and print them out
         */
        System.out.println("_".repeat(50));
        inventory.values().forEach(System.out::println);
    }
}
