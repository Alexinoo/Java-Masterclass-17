package oop.part1.coding_exercise.wall_area;

public class Main {
    public static void main(String[] args) {

        //Test Cases

        System.out.println(new Wall(-1.25,-1.25).getArea());
        System.out.println(new Wall(1.125,-1.0).getArea());
        System.out.println(new Wall(-1.125,1.0).getArea());
        System.out.println(new Wall(0.0,1.25).getArea());
        System.out.println(new Wall(2.34,0.0).getArea());
        System.out.println(new Wall(4.5,2.0).getArea());
        System.out.println(new Wall(1.75,1.75).getArea());
        System.out.println(new Wall(8.0,7.0).getArea());
        System.out.println(new Wall(6.0,5.0).getArea());

    }
}
