package abstraction.interfaces.part3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

        ArrayList<FlightEnabled> fliers = new ArrayList<>();
        fliers.add(bird);

        List<FlightEnabled> betterFliers = new ArrayList<>();
        betterFliers.add(bird);

        LinkedList<FlightEnabled> listFliers = new LinkedList<>();
        listFliers.add(bird);

        System.out.println("/".repeat(30));
        triggerFliers(fliers);
        flyFliers(fliers);
        landFliers(fliers);

        System.out.println("/".repeat(30));
        triggerFliers(betterFliers);
        flyFliers(betterFliers);
        landFliers(betterFliers);

        System.out.println("/".repeat(30));
        triggerFliers(listFliers);
        flyFliers(listFliers);
        landFliers(listFliers);
    }

    private static void inFlight(FlightEnabled flier){
        flier.takeOff();
        flier.fly();
        if(flier instanceof Trackable tracked){
            tracked.track();
        }
        flier.land();
    }

    private static void triggerFliers(List<FlightEnabled> fliers){
        for (var flier : fliers){
            flier.takeOff();
        }
    }

    private static void flyFliers(List<FlightEnabled> fliers){
        for (var flier : fliers){
            flier.fly();
        }
    }

    private static void landFliers(List<FlightEnabled> fliers){
        for (var flier : fliers){
            flier.land();
        }
    }
}
