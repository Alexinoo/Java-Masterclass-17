package collections.part7_hashing;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        String aText = "Hello";
        String bText = "Hello";
        String cText = String.join("l", "He", "lo");
        String dText = "He".concat("llo");
        String eText = "hello";

        List<String> hellos = Arrays.asList(aText, bText, cText, dText, eText);

        hellos.forEach(s -> System.out.println(s + ": " + s.hashCode()));


        //Creating a Set for the above strings
        Set<String> mySet = new HashSet<>(hellos);

        System.out.println("mySet = " + mySet);
        System.out.println("# no of elements = " + mySet.size());

        //loop through and see which elements are in this set
        for (String setValue : mySet) {
            System.out.print(setValue + ": ");
            for (int i = 0; i < hellos.size(); i++) {
                if (setValue == hellos.get(i)) {
                    System.out.print(i + ", ");
                }
            }
            System.out.println(" ");
        }

        PlayingCard aceHearts = new PlayingCard("Hearts","Ace");
        PlayingCard kingClubs = new PlayingCard("Clubs","King");
        PlayingCard queenSpades = new PlayingCard("Spades","Queen");

        List<PlayingCard> cards = Arrays.asList(aceHearts,kingClubs,queenSpades);

        cards.forEach(s -> System.out.println(s + ": " +s.hashCode()));


        //Create a set of cards and add the cards one at a time
        //Make it a hashset

        Set<PlayingCard> deck = new HashSet<>();
        for (PlayingCard card: cards) {
            if(!deck.add(card))
                System.out.println("Found a duplicate for "+ card);
        }
        System.out.println(deck);
    }
}
