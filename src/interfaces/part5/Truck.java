package interfaces.part5;

public class Truck implements Trackable {
    @Override
    public void track() {
        System.out.println(getClass().getSimpleName()+ " 's coordinates were recorded");
    }
}
