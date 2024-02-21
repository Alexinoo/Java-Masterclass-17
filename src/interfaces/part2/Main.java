package interfaces.part2;

public class Main {

    public static void main(String[] args) {
        Bird bird = new Bird();

        Animal animal = bird;
        FlightEnabled flier = bird;
        Trackable tracked = bird;

        inFlight(flier);

        System.out.println("_".repeat(30));

       // Jet jet = new Jet();
        inFlight(new Jet());

        System.out.println("_".repeat(30));

        Trackable truck = new Truck();
        truck.track();

        double kmsTravelled = 100;
        double milesTravelled = kmsTravelled * FlightEnabled.KM_TO_MILES;
        System.out.printf("The truck travelled %.2f km or %.2f miles%n",kmsTravelled,milesTravelled);

    }

    private static void inFlight(FlightEnabled flier){
        flier.takeOff();
        flier.fly();
        if(flier instanceof Trackable tracked){
            tracked.track();
        }
        flier.land();
    }
}
