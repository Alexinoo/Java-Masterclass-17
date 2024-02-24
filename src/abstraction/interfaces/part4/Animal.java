package abstraction.interfaces.part4;

enum FlightStages implements Trackable{
    GROUNDED,LAUNCH,CRUISE,DATA_COLLECTION;

    @Override
    public void track() {
        if (this != GROUNDED){
            System.out.println("Monitoring "+this);
        }
    }
    public FlightStages getNextStage(){
        FlightStages[] allStages = values();
        return allStages[(ordinal()+1)% allStages.length];
    }
}

record DragonFly(String name,String type) implements FlightEnabled{
    @Override
    public void takeOff() {

    }

    @Override
    public void land() {

    }

    @Override
    public void fly() {

    }
}

class Satellite implements OrbitEarth{

    @Override
    public void achieveOrbit() {

    }

    @Override
    public void takeOff() {

    }

    @Override
    public void land() {

    }

    @Override
    public void fly() {

    }
}

interface OrbitEarth extends FlightEnabled{
    void achieveOrbit();
}

interface FlightEnabled{
    double MILE_TO_KM = 1.60934;
    double KM_TO_MILES = 0.621371;
    //public final double KM_TO_MILES= 0.621371; -- same as above
    //public static final double KM_TO_MILES= 0.621371; -- same as above
    void takeOff();
    void land();
    void fly();

//    FlightStages transition(FlightStages stage);
    default FlightStages transition(FlightStages stage){
//        System.out.println("Transition not implemented on "+this.getClass().getName());
//        return null;
        FlightStages nextStage = stage.getNextStage();
        System.out.println("Transitioning from "+stage+" to "+ nextStage);
        return nextStage;
    }


}
interface Trackable{
    void track();
}
public abstract class Animal {

    public abstract void move();
}
