package concurrency.part12_synchronization_challenge;

import java.util.Random;

public class Main {

    /*
     * A Shoe Warehouse Fulfillment Center
     *
     * The Producer code should generate orders and send them to the soe warehouse to be processed
     * The consumer code should fulfill, or process the orders in a FIFO, or First In , First Out
     * You'll be creating at a minimum, 3 types of this , an Order, a Shoe Warehouse,and a Main executable class
     *
     * Order
     * .....
     * An order should include an order id , a shoe type and the quantity ordered
     * A record might be a good fit for this type
     *
     * Shoe Warehouse Class
     * ....................
     * Should maintain product list, as a public static field
     * Should maintain a private list of orders
     * Should have 2 methods:
     *  - receiveOrder()
     *      - called by Producer
     *      - poll or loop indefinitely checking the size of the list
     *      - call wait() if the the list has reached some max capacity
     *
     *  - consumeOrder()
     *      - gets called by a Consumer thread
     *      - should also poll the list, needs to check if the list is empty and wait in the loop until an order is added
     * Both methods should invoke the wait() and notifyAll() appropriately
     *
     *
     * Main Class
     * ...........
     * Create and start a single Producer thread
     *  - generate 10 sales orders and call receiveOrder on the Shoe Warehouse for each
     *
     * Create and start 2 consumer threads.
     *  - Each thread needs to process 5 fulfillment orders, calling consumeOrder() on the shoe warehouse for each item
     *
     * You'll test your Producer-Consumer application and confirm you application consumes all the 10 orders it receives
     *
     *
     *  Main Class
     * ...........
     * Fields :
     *  - A Random number generator to create randomized data for a set of orders
     *
     * Instance
     *  - Create an instance of a ShoeWarehouse Class (shared resource)
     *
     * Create threads
     *  - Producer thread
     *      - Create a thread (producer) and pass a lambda expression
     *          - loop for 10 iterations
     *              - make a call to receiveOrder passing a new order (instance of the order record)
     *  - Start producer thread
     *
     *  - Create a for loop for 2 iterations
     *      - Create a thread (consumer) and pass a runnable lambda expression to a new Thread instance
     *          - loop 5 times
     *              - Each iteration make a call to consumerOrder on the warehouse instance
     *                  - consumeOrder returns an order the one that got fulfilled (will not do anything with it)
     *
     *      - Start consumer thread inside the loop
     *
     *
     * Running this:-
     *  - Examining the output
     *      - confirmed 10 orders were submitted by the Producer thread
     *      - confirmed 10 orders were consumed by the 2 Consumer thread - 5 each
     *
     * Let's add thread-name to the receiveOrder() to confirm which thread consumed which order
     *
     *
     *
     */
    private static final Random random = new Random();

    public static void main(String[] args) {
        ShoeWarehouse shoeWarehouse = new ShoeWarehouse();

        Thread producerThread = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shoeWarehouse.receiveOrder( new Order(
                        random.nextLong(10000,99999),
                        shoeWarehouse.Products[random.nextInt(0, 5)],
                        random.nextInt(1,4)
                ));
            }
        });

        producerThread.start();

        for (int i = 0; i < 2; i++) {
            Thread consumerThread = new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                   Order item = shoeWarehouse.consumeOrder();
                }
            });
            consumerThread.start();
        }

    }
}
