package linked_lists.linkedlist_challenge;

import java.util.LinkedList;

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
    }

    private static void addPlace(LinkedList<Place> list,Place place){
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
}
