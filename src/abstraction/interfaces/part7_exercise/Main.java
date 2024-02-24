package abstraction.interfaces.part7_exercise;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<ISaveable> list = new ArrayList<>();
        list.add(new Player("Alex",15,20));
        list.add(new Player("Tim",10,15));
        list.add(new Monster("Lion",30,30));

        for (ISaveable l: list ) {
            System.out.println(l);
        }
    }
}
