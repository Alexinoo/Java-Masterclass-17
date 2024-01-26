package oop.part1.car;

public class Main {
    public static void main(String[] args) {


        Car car = new Car();

        //Private - make,model,color - Not accessible
        // car.make = "Porsche";
        // car.model = "Tesla";
        // car.color = "Red";

        System.out.println();
        System.out.println("=======First Object===============");
        System.out.println();
        System.out.println();

        // setters

        car.setDoors(2);
        car.setMake("Maserati");
        car.setModel("Tesla");
        car.setColor("Red");
        car.setConvertible(true);

        // getters
        System.out.println("==== Getters ======");
        System.out.println("Make = "+car.getMake());
        System.out.println("Model = "+car.getModel());
        System.out.println("Color = "+car.getColor());
        System.out.println("Convertible = "+car.isConvertible());

        //carDescribe
        System.out.println("==== describeCar() ======");
        car.describeCar();

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("=======Another Object===============");

        Car targa = new Car();
        targa.setDoors(2);
        targa.setMake("Porsche");
        targa.setModel("Tesla");
        targa.setColor("Red");
        targa.setConvertible(true);

        targa.describeCar();

    }
}
