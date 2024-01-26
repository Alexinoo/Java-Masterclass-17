package oop.part1.car;

public class Car {
    private String make;
    private String model;
    private String color;

    private int doors;
    private boolean convertible;

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public int getDoors() {
        return doors;
    }

    public boolean isConvertible() {
        return convertible;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make){
        if(make == null)
            make = "Unknown";

        String lowerCase = make.toLowerCase();
        switch (lowerCase){
            case "holden","porsche","tesla" -> {this.make = make;}
            default -> {
                this.make = "Unsupported ";
            }
        }
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public void setConvertible(boolean convertible) {
        this.convertible = convertible;
    }

    public void describeCar(){
        System.out.println("Make : "+make);
        System.out.println("Model : "+model);
        System.out.println("Color : "+color);
        System.out.println("Doors : "+doors);
        System.out.println("Convertible : "+((convertible)?"Convertible":""));
    }
}
