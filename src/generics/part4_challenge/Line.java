package generics.part4_challenge;

import java.util.Arrays;

public class Line implements Mappable{
    private double[][] locations;

    public Line(String...locations){
        this.locations = new double[locations.length][];
        int index =0;
        for (var location:locations) {
            this.locations[index++] = Mappable.stringToLatLon(location);
        }
    }

    private String locations(){
        return Arrays.deepToString(locations);
    }
    @Override
    public void render() {
        System.out.println("Render " + this + " as POINT (" + locations()+ ")");
    }
}
