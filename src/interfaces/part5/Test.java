package interfaces.part5;

public class Test {

    public static void main(String[] args) {
        inFlight(new Jet());

        //OrbitEarth.log("Testing " +new Satellite());
        System.out.println("/".repeat(30));
        orbit(new Satellite());
    }
    private static void inFlight(FlightEnabled flier){
        flier.takeOff();
        flier.transition(FlightStages.LAUNCH);
        flier.fly();
        if(flier instanceof Trackable tracked){
            tracked.track();
        }
        flier.land();
    }

    private static void orbit(OrbitEarth flier){
        flier.takeOff();
        flier.fly();
        flier.land();
    }
}
