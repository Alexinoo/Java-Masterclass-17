package linked_lists.part1;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {

        /*  Linked List
            ============
            - We can also use var but need to be more specific on the type
                e.g var placesToVisit = new LinkedList<String>();

            - Adding elements
                - use add()

            - LL implements methods on the deck interface, we have severa other functions to add elements

                - addFirst() - adds element at the beginning of the queue
                - addLast() - adds element at the end of the queue
                - offer () - is a queue language for adding elmnt to the end of the queue
                - offerLast() - does the same as offer()
                - offerFirst() - adds element at the beginning of the queue
                - push() - places elements at the beginning of the list
                - These methods exist on LinkedList class but not on the ArrayList

            - LL implements several methods/functions to remove elements

                - remove(int index) - removes element at the specified index
                - remove(String s) - removes element specified in the String

                - Additional methods to remove elements that not on ArrayList
                =================================================================
                    - remove() - Removes and return the head of the list
                    - removeFirst() - Removes and return the head of the list (Works as remove())
                    - removeLast() - Removes and return the last element of the list

                - Additional Dequeue/Stack methods to remove an element
                ===============================================

                    - offer()
                    - offerFirst()
                    - offerLast()
                    - poll() - removes first element
                    - pollFirst() - removes first element
                    - pollLast() - removes last element
                    - pop() - removes first element

         */
        LinkedList<String> placesToVisit = new LinkedList<>();

        //Adding elements
        placesToVisit.add("Sydney");
        placesToVisit.add(0,"Canberra");

        System.out.println(placesToVisit);

        addMoreElements(placesToVisit);
        System.out.println(placesToVisit);

        //remove elements
        removeElements(placesToVisit);
        System.out.println(placesToVisit);

    }

    private static void addMoreElements(LinkedList<String> list){
        list.addFirst("Darwin");
        list.addLast("Hobart");

        //Queue methods
        list.offer("Melbourne");
        list.offerFirst("Brisbane");
        list.offerLast("Toowomba");

        //Stack Methods
        list.push("Alice Springs");
    }

    private static void removeElements(LinkedList<String> list){
        list.remove(4);
        list.remove("Brisbane");

        System.out.println(list);
        String s1 = list.remove();
        System.out.println(s1 +" was removed");

        String s2 = list.removeFirst();
        System.out.println(s2 +" was removed");

        String s3 = list.removeLast();
        System.out.println(s3 +" was removed");

        //Queue / Dequeue poll methods
        String p1 = list.poll();
        System.out.println(p1 +" was removed");

        String p2 = list.pollFirst();
        System.out.println(p2 +" was removed");

        String p3 = list.pollLast();
        System.out.println(p3 +" was removed");

        //Stack methods
        list.push("Sydney");
        list.push("Brisbane");
        list.push("Canberra");

        String p4 = list.pop();
        System.out.println(p4 +" was removed");

    }
}
