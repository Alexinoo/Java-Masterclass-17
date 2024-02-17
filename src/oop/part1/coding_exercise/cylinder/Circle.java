package oop.part1.coding_exercise.cylinder;

public class Circle {
    private static final double PI = Math.PI;

    private double radius;

    public Circle(double radius) {
        this.radius = radius < 0 ? 0 : radius;
    }

    public double getRadius() {
        return radius;
    }

    public double getArea(){
        return (radius * radius * PI);
    }
}
