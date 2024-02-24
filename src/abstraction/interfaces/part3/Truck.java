package abstraction.interfaces.part3;

public class Truck implements Trackable {
    @Override
    public void track() {
        System.out.println(getClass().getSimpleName()+ " 's coordinates were recorded");
    }
}
