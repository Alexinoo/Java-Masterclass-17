package collections.part4_collection_methds;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        // Create an Array of cards for 1 suit. Ace of Hearts
        Card[] cardArray = new Card[13];

        // Generate AceOfHearts card using static getFaceCard()
        // Pass Heart from Enum and the abbrev
        // Call Arrays.fill() and pass
        Card aceOfHearts = Card.getFaceCard(Card.Suit.HEART , 'A');
        Arrays.fill(cardArray,aceOfHearts);
        Card.printDeck(Arrays.asList(cardArray),"Aces of Hearts",1);

        // Collections.fill()
        List<Card> cards = new ArrayList<>(52);
        Collections.fill(cards ,aceOfHearts);
        System.out.println(cards); // []
        System.out.println("cards.size() = "+cards.size()); // 0

        // Collections.nCopies()
        // Creates a new list with the no. of elmnt(s) you specify as the 1st arg filling it with the elmnt you pass as 2nd arg
        List<Card> acesOfHearts = Collections.nCopies(13,aceOfHearts);
        Card.printDeck(acesOfHearts,"Aces of Hearts",1);

        // Create KingOfClub cards
        Card kingOfClubs = Card.getFaceCard(Card.Suit.CLUB , 'K');
        List<Card> kingsOfClubs = Collections.nCopies(13,kingOfClubs);
        Card.printDeck(kingsOfClubs,"Kings Of Clubs" , 1);

        // Collections.addAll()
        // Takes 2 arg
        // 1st arg - the list that you want to add elmnt(s) to
        // 2nd arg - elmnt(s) to be added
        Collections.addAll(cards,cardArray); //aces of hearts
        Card.printDeck(cards,"Card Collection with Aces added",1);


        // Collections.copy()
        // Takes 2 arg
        // 1st arg - destination of the copied elements
        // 2nd arg - elmnt(s) to be copied
        // Can't use this mthd if the no. of elmnt(s) in your curr list is < than the no. of elmnts in the source list
        List<Card>  cardsEmpty = new ArrayList<>(52);
        Collections.addAll(cardsEmpty,cardArray); // populate some elements first
        Collections.copy(cardsEmpty,kingsOfClubs); //i.e (0 , 13)
        Card.printDeck(cardsEmpty,"Card Collection with Kings copied",1);

        //What happens if the no. of elements > than the source
        //copies 1 list to another , but doesn't return the copy of your list
        Collections.addAll(cardsEmpty,cardArray);
        Card.printDeck(cardsEmpty,"Card Collection with Kings copied",2);

        // Use List.copyOf - if you want a full list copy
        cardsEmpty = List.copyOf(kingsOfClubs);
        Card.printDeck(cardsEmpty,"List Copy of Kings",1);

        List<Card> deck = Card.getStandardDeck();
        Card.printDeck(deck);


        // Collections.shuffle()
        // This method randomly permutes the specified list using a default source of randomness.
        Collections.shuffle(deck);
        Card.printDeck(deck,"Shuffled deck",4);

        // Collections.reverse()
        // This method reverses the order of the elements in the specified list
        Collections.reverse(deck);
        Card.printDeck(deck,"Reversed Deck of Cards",4);


        //Collections.sort() - We have 2 flavours
            // 1. Collections.sort(List<T> list, Comparator<? super T> c)
            // 2.  Collections.sort(List<T> list)

        // 1. Collections.sort(List<T> list, Comparator<? super T> c)

        var sortingAlgorithm = Comparator.comparing(Card::rank)
                .thenComparing(Card::suit);

        Collections.sort(deck,sortingAlgorithm);
        Card.printDeck(deck,"Standard Deck sorted by rank, suit",13);

        Collections.reverse(deck);
        Card.printDeck(deck,"Sorted by rank, suit reversed:",13);

        //SubLists

        List<Card> kings = new ArrayList<>(deck.subList(8,12));
        Card.printDeck(kings,"Kings in deck",1);

        List<Card> tens = new ArrayList<>(deck.subList(16,20));
        Card.printDeck(tens,"Tens in deck",1);



        /*
         *Collections.indexOfSubList(List<?> source, List<?> target)

          - This method returns the starting position of the first occurrence of the
          specified target list within the specified source list,or -1 if there is no
           such occurrence.
        */
        //Collections.shuffle(deck);
        int subListIndex = Collections.indexOfSubList(deck,tens);
        System.out.println("sub list index for tens = "+subListIndex);
        System.out.println("Contains = "+deck.containsAll(tens));

         /*

          Collections.disjoint(Collection<?> c1, Collection<?> c2)

          - This method returns true if the two specified collections have no elements in common.
        */
        boolean disjoint = Collections.disjoint(deck,tens);
        System.out.println("disjoint = "+disjoint);

        boolean disjoint2 = Collections.disjoint(kings,tens);
        System.out.println("disjoint = "+disjoint2);

    }
}
