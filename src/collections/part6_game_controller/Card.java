package collections.part6_game_controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public record Card(Suit suit, String face, int rank) {

    public enum Suit{
        CLUB,DIAMOND,HEART,SPADE;


        // Check Unicode Chart
        // https://www.ssec.wisc.edu/~tomw/java/unicode.html
        public char getImage(){
            char[] myCharacters = new char[]{9827,9830,9829,9824};
            return (myCharacters[ordinal()]);
        }
    }

    public static Comparator<Card> sortRankReversedSuit(){
       return Comparator.comparing(Card::rank).reversed().thenComparing(Card::suit);
    }

    @Override
    public String toString() {
        //Default cases will be 1 unless we get
        // a 10 which is the only case we want 2 digits to be retrieved
        int index = face.equals("10") ? 2:1;
        String faceString = face.substring(0,index);
        return "%s%c(%d)".formatted(faceString,suit.getImage(),rank);
    }

    public static Card getNumericCard(Suit suit,int cardNumber){
        if(cardNumber > 1 && cardNumber < 11){
            return new Card(suit,String.valueOf(cardNumber),cardNumber - 2);
        }
        System.out.println("Invalid Numeric card selected");
        return null;
    }

    public static Card getFaceCard(Suit suit, char abbrev){
        int charIndex = "JKQA".indexOf(abbrev);
        if(charIndex > -1){
            return new Card(suit,"" + abbrev,charIndex + 9);
        }
        System.out.println("Invalid Face card selected");
        return null;
    }

    //Gives a deck of 52 cards in 4 suits
    // First - the cards 2 through 10 [0 - 8]
    // Second - face cards - Jack through Ace ranked from 9 -13

    public static List<Card> getStandardDeck(){
        List<Card> deck = new ArrayList<>(52);
        for (Suit suit: Suit.values()) {
            for (int i = 2; i <= 10 ; i++) {
                deck.add(getNumericCard(suit , i));
            }
            for (char c: new char[]{'J','K','Q','A'}) {
                deck.add(getFaceCard(suit,c));
            }

        }
        return deck;
    }

    public static void printDeck(List<Card> deck){
        printDeck(deck,"Current Deck",4);
    }

    public static void printDeck(List<Card> deck, String description,int rows){
        System.out.println("---------------------------");
        if(description != null){
            System.out.println(description);
        }
        int cardsInRows = deck.size() / rows;

        for (int i = 0; i < rows; i++) {
            int startIndex = i * cardsInRows;
            int endIndex = startIndex + cardsInRows;
            deck.subList(startIndex,endIndex).forEach(c -> System.out.print(c + " "));
            System.out.println();
        }
    }


}
