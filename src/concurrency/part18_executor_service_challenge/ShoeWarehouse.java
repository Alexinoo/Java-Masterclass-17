package concurrency.part18_executor_service_challenge;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Need a place to put orders when they're received
 *  Fields:
 *      - List<Order> named "store"
 *      - Array Of Products  - a list of constants
 *  Constructor:
 *      - ShoeWarehouse() no args -
 *      - initialize store List to an ArrayList
 *  Methods:
 *      - receiveOrder(Order order)
 *          - if the store is full - wait for the consumer to request or consume
 *
 *      - consumeOrder()
 *          - if the store is empty - wait for the producer to add
 *          - Once there is at least 1 item in the store, then retrieve the first item with index-0
 *              - the remove will remove() will both retrieve that element and remove it from the list
 *          - Print that the order was consumed with its details
 *
 */
public class ShoeWarehouse {

    private List<Order> store;
    public final static String[] Products = {"Running Shoes","Sandals","Boots","Slippers","High Tops"};

    private final ExecutorService fulfillmentService;

    public ShoeWarehouse() {
        this.store = new ArrayList<>();
        fulfillmentService = Executors.newFixedThreadPool(3);
    }

    public synchronized void receiveOrder(Order item){
        while (store.size() > 20){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        store.add(item);
        System.out.println(Thread.currentThread().getName()+ " Incoming Batch: "+item);
        fulfillmentService.submit(this::consumeOrder);
        notifyAll();
    }

    public synchronized Order consumeOrder(){
        while (store.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Order item = store.remove(0);
        System.out.println(Thread.currentThread().getName() +" Consumed Batch: "+item);
        notifyAll();
        return item;
    }

    public void shutDown() {
        fulfillmentService.shutdown();
    }
}
