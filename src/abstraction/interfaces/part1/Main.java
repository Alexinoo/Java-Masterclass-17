package abstraction.interfaces.part1;

public class Main {

    public static void main(String[] args) {
        Bird bird = new Bird();

        Animal animal = bird;
        FlightEnabled flier = bird;
        Trackable tracked = bird;

        animal.move();
        flier.takeOff();
        flier.fly();
        tracked.track();
        flier.land();

    }
}
