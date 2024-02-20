package linked_lists.part2;

import java.util.LinkedList;
import java.util.ListIterator;

public class Main {

    public static void main(String[] args) {

        /*  Linked List
            ============
            - We can also use var but need to be more specific on the type
                e.g var placesToVisit = new LinkedList<String>();

            - Adding elements
              =====================
                - use add()

            - LL implements methods on the deck interface, we have severa other functions to add elements

                - addFirst() - adds element at the beginning of the queue
                - addLast() - adds element at the end of the queue
                - offer () - is a queue language for adding elmnt to the end of the queue
                - offerLast() - does the same as offer()
                - offerFirst() - adds element at the beginning of the queue
                - push() - places elements at the beginning of the list
                - These methods exist on LinkedList class but not on the ArrayList

            - Remove elements
              =====================

            - LL implements several methods/functions to remove elements

                - remove(int index) - removes element at the specified index
                - remove(String s) - removes element specified in the String

                - Additional methods to remove elements that not on ArrayList
                  -----------------------------------------------------------
                    - remove() - Removes and return the head of the list
                    - removeFirst() - Removes and return the head of the list (Works as remove())
                    - removeLast() - Removes and return the last element of the list

                - Additional Dequeue/Stack methods to remove an element
                  ------------------------------------------------------

                    - offer()
                    - offerFirst()
                    - offerLast()
                    - poll() - removes first element
                    - pollFirst() - removes first element
                    - pollLast() - removes last element
                    - pop() - removes first element

              - Fetching elements
                =================
                - get(int index) - returns the element specified by the index
                - getFirst() - returns the first element
                - getLast() - returns the last element
                - indexOf() - return the index of the elemnt specified , -1 if not found
                - lastIndexOf() - return the index of the elemnt specified

               - Additional Dequeue methods to retrieve list elements
                -----------------------------------------------------

                    - element() - gets the first element out of the list

               - Additional stack methods to retrieve list elements
                ----------------------------------------------------

                    - peek() - Retrieves, but does not remove, the first element of this list, or returns null if this list is empty.
                    - peekFirst() - Retrieves, but does not remove, the first element of this list, or returns null if this list is empty.
                    - peekLast() - Retrieves, but does not remove, the last element of this list, or returns null if this list is empty.

               - Traversing & Manipulating List elements
                =========================================

                    - using traditional for loop
                    - using enhanced loop
                    - using ListIterator<String> iterator = list.listIterator(1)
                        - then we call the hasNext variable which loops until the last element
                        - list.listIterator(1) takes in an index that specifies the index to start with
         */

    }

    private static void gettingElements(LinkedList<String> list){
        System.out.println("Retrieved element = "+list.get(1));
        System.out.println("First element = "+list.getFirst());
        System.out.println("Last element = "+list.getLast());
        System.out.println("Toowoomba is at position element = "+list.indexOf("Toowoomba"));
        System.out.println("Melbourne is at position element = "+list.lastIndexOf("Melbourne"));
        System.out.println("Element from element() = "+list.element());
        System.out.println("Element from peek() = "+list.peek());
        System.out.println("Element from peekFirst() = "+list.peekFirst());
        System.out.println("Element from peekLast() = "+list.peekLast());

    }

    public static void printItinerary(LinkedList<String> list){
            System.out.println("Tr LinkedList<String> placesToVisit = new LinkedList<>();\n" +
                    "\n" +
                    "        //Adding elements\n" +
                    "        placesToVisit.add(\"Sydney\");\n" +
                    "        placesToVisit.add(\"Brisbane\");\n" +
                    "        placesToVisit.add(\"Toowoomba\");\n" +
                    "        placesToVisit.add(\"Melbourne\");\n" +
                    "        placesToVisit.add(0,\"Canberra\");\n" +
                    "\n" +
                    "        System.out.println(placesToVisit);\n" +
                    "        //gettingElements(placesToVisit);\n" +
                    "        printItinerary3(placesToVisit);ip starts at "+list.getFirst());
            for (int i = 1; i < list.size(); i++) {
                System.out.println("--> From: "+list.get(i-1)+ " to "+ list.get(i));
            }
            System.out.println("Trip ends at "+list.getLast());
    }

    public static void printItinerary2(LinkedList<String> list){
        System.out.println("Trip starts at "+list.getFirst());
        String previousTown = list.getFirst();
        for (String town : list) {
            System.out.println("--> From: "+previousTown+ " to "+ town);
            previousTown = town;
        }
        System.out.println("Trip ends at "+list.getLast());
    }

    public static void printItinerary3(LinkedList<String> list){
        System.out.println("Trip starts at "+list.getFirst());
        String previousTown = list.getFirst();
        ListIterator<String> iterator = list.listIterator(1);
        while(iterator.hasNext()){
            var town = iterator.next();
            System.out.println("--> From: "+previousTown+ " to "+ town);
            previousTown = town;
        }
        System.out.println("Trip ends at "+list.getLast());
    }
}
