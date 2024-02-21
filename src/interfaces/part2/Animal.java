package interfaces.part2;

interface FlightEnabled{
    double MILE_TO_KM = 1.60934;
    double KM_TO_MILES = 0.621371;
    //public final double KM_TO_MILES= 0.621371; -- same as above
    //public static final double KM_TO_MILES= 0.621371; -- same as above
    void takeOff();
    void land();
    void fly();
}
interface Trackable{
    void track();
}
public abstract class Animal {

    public abstract void move();
}
