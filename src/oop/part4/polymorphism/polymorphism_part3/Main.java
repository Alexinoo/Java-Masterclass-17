package oop.part4.polymorphism.polymorphism_part3;

public class Main {

    public static void main(String[] args) {

        Movie movie = Movie.getMovie("A","Jaws");
        movie.watchMovie();

        // Use cas
        // Adventure jaws = Movie.getMovie("A","Jaws");

        // Use casting
        Adventure jaws = (Adventure)Movie.getMovie("A","Jaws");
        jaws.watchMovie();

        //Object reference - Need to do a lot of casting
         Object comedy = Movie.getMovie("C","Jaws");
        // Movie comedyMovie = (Movie)comedy; - Does not work
         Comedy comedyMovie = (Comedy)comedy; // Need to cast to a more specific type
         comedyMovie.watchComedy();

        // Use var - Local Variable Type Inference
        var airplane = Movie.getMovie("C","Airplane");
        airplane.watchMovie();

        //Use var - with instances
        var plane = new Comedy("Airplane");
        plane.watchComedy();



    }
}
