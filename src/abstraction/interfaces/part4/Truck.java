package abstraction.interfaces.part4;

public class Truck implements Trackable {
    @Override
    public void track() {
        System.out.println(getClass().getSimpleName()+ " 's coordinates were recorded");
    }
}
