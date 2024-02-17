package oop.part4.polymorphism.polymorphism_part1;

public class Main {

    public static void main(String[] args) {

        Movie movie = new Movie("Star Wars");
        movie.watchMovie();

        movie = new Adventure("Star Wars");
        movie.watchMovie();
    }
}
