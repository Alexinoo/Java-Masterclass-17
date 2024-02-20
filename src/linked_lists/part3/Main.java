package linked_lists.part3;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        /*  Linked List
            ============

            - Iterator and the ListIterator
              =============================

            - We've mainly used loops to traverse or step through elements in an array or list
            - Java provides other means to traverse lists
            - Two alternatives are the Iterator and the ListIterator

                - Iterator
                  --------

                    - Can be thought to a database cursor i.e. A kind of object that allows a traversal over
                      records in a collection
                    - When you get an instance of an iterator, you can call the next() to get the next element in the list

                    - You can use the hasNext() to if there are any elements remaining to be processed

                    - In the code, you can see a while loop which uses the iterator hasNext() to determine if it should continue looping

                    - In the loop, the next() is called and it's value assigned to a local variable and the local variable is printed out

                    - This would just print each element in a list , but do it through the iterator obj

                    - use iterator.remove() in case you want to remove an element during iteration instead of list.remove()

                    - iterator.remove() is the only method available for mutating elements in this iterator

                    - There is another iterator, ListIterator that gives us additional functionality

                - ListIterator
                  ------------

                    - Can be used to go both forwards and backwards & in addition to remove(), also supports add()
                       and set()
                    - Again, we are using iterator.add() on th list itself instead of list.add()

                    - Another thing we can do is to call listIterator() and pass a cursor position we want to start at

        */

        LinkedList<String> placesToVisit = new LinkedList<>();

        //Adding elements
        placesToVisit.add("Sydney");
        placesToVisit.add("Brisbane");
        placesToVisit.add("Toowoomba");
        placesToVisit.add("Melbourne");
        placesToVisit.add(0,"Canberra");

        System.out.println(placesToVisit);
        //gettingElements(placesToVisit);
        listIterator(placesToVisit);
    }

//    private static void testIterator(LinkedList<String> list){
//        System.out.println("Trip starts at "+list.getFirst());
//        String previousTown = list.getFirst();
//        ListIterator<String> iterator = list.listIterator(1);
//        while(iterator.hasNext()){
//            var town = iterator.next();
//            System.out.println("--> From: "+previousTown+ " to "+ town);
//            previousTown = town;
//        }
//        System.out.println("Trip ends at "+list.getLast());
//    }
    private static void testIterator(LinkedList<String> list){
        var iterator = list.iterator();
        while(iterator.hasNext()){
            //check for duplicates and remove
            if(iterator.next().equals("Brisbane")){
                iterator.remove();
            }
        }
        System.out.println(list);
    }

    private static void listIterator(LinkedList<String> list){
        var iterator = list.listIterator();
        while(iterator.hasNext()){
            //check for duplicates and remove
            if(iterator.next().equals("Brisbane")){
               // iterator.remove();
                iterator.add("Lake Wivenhoe");
            }
        }
        //Won't run since hasNext() is now false
        //If we wanted it to run again, we can only call a new instance on listIterator or movebackwards
        // Use .hasPrevious() and .previous() on the iterator to move backwards
        while(iterator.hasPrevious()){
            System.out.print(iterator.previous()+ " ");
        }
        System.out.println();
        System.out.println(list);

        var iterator2 = list.listIterator(3);
        System.out.println(iterator2.next());
        System.out.println(iterator2.previous());


    }
}
