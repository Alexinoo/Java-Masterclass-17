package linked_lists.linkedlist_challenge.part2;

import linked_lists.linkedlist_challenge.part1.Place;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        LinkedList<Place> placesToVisit = new LinkedList<>();

        Place adelaide = new Place("Adelaide",1374);
        addPlace(placesToVisit,adelaide);
        addPlace(placesToVisit,new Place("adelaide",1374));
        addPlace(placesToVisit,new Place("Brisbane",917));
        addPlace(placesToVisit,new Place("Perth",3923));
        addPlace(placesToVisit,new Place("Alice Springs",2171));
        addPlace(placesToVisit,new Place("Darwin",3972));
        addPlace(placesToVisit,new Place("Melbourne",877));

        placesToVisit.addFirst(new Place("Sydney",0));
        System.out.println(placesToVisit);

        var iterator = placesToVisit.listIterator();
        Scanner scanner = new Scanner(System.in);
        boolean quitLoop = false;
        boolean forward = true;

        printMenu();
        while(!quitLoop){
            if(!iterator.hasPrevious()){
                System.out.println("Originating : "+iterator.next());
                forward = true;
            }

            if(!iterator.hasNext()){
                System.out.println("Final : "+iterator.previous());
                forward = false;
            }
            System.out.println("Enter Value: ");
            String menuItem = scanner.nextLine().toUpperCase().substring(0,1);
            switch(menuItem){
                case "F":
                        System.out.println("Users wants to go forward");
                        if(!forward){ //Reversing Direction
                            forward = true;
                            if(iterator.hasNext()){
                                iterator.next(); //Adjust position forward
                            }
                        }
                        if(iterator.hasNext()){
                            System.out.println(iterator.next());
                        }
                        break;
                case "B" :
                        System.out.println("Users wants to go backward");
                        if(forward){ //Reversing Direction
                            forward = false;
                            if(iterator.hasPrevious()){
                                iterator.previous(); //Adjust position forward
                            }
                        }
                        if(iterator.hasPrevious()){
                            System.out.println(iterator.previous());
                        }
                        break;
                case "L" :
                        System.out.println(placesToVisit);
                        break;
                case "M" :
                        printMenu();
                        break;
                default :
                        quitLoop=true;
            }
        }
    }

    private static void addPlace(LinkedList<Place> list, Place place){
        if(list.contains(place)){
            System.out.println("Found duplicate  :"+ place);
            return;
        }
        for (Place p:list ) {
            if(p.name().equalsIgnoreCase(place.name())){
                System.out.println("Found duplicate  :"+ place);
                return;
            }
        }
        int matchedIndex = 0;
        for (var listplace :list) {
            if(place.distance() < listplace.distance()) {
                list.add(matchedIndex,place);
                return;
            }
            matchedIndex++;
        }
        list.add(place);
    }

    private static void printMenu(){
        String textBlock = """
                Available actions (select word or letter):
                (F)orward
                (B)ackwards
                (L)ist Places
                (M)enu
                (Q)uit""";
        System.out.print(textBlock+ " ");
    }
}
