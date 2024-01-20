package Fundamentals.basics.coding_exercises;

public class AreaCalculator {
    private static final int INVALID_VALUE = -1;
    private static final double PI  = Math.PI;

    public static void main(String[] args) {

        System.out.println(area(0.0,0.0));
        System.out.println(area(10.0,5.0));
        System.out.println(area(2.0,6.25));
        System.out.println(area(1.75,5.5));
        System.out.println(area(-1.0,5.0));
        System.out.println(area(1.0,-5.0));
        System.out.println(area(4.265,8.387));

        System.out.println("===================");
        System.out.println(area(0.0));
        System.out.println(area(1.0));
        System.out.println(area(2.0));
        System.out.println(area(8.5));
        System.out.println(area(100.25));
        System.out.println(area(63.354));
        System.out.println(area(11.325));
        System.out.println(area(18.671));
        System.out.println(area(-2.0));
        System.out.println(area(-8.5));
        System.out.println(area(-20.75));
        System.out.println(area(-100.25));
        System.out.println(area(-443.6223));


    }

    public static double area(double radius){
        if( radius < 0)
            return INVALID_VALUE;
        return PI * radius *radius;
    }

    public static double area(double x , double y){
        if( x < 0 || y < 0)
            return INVALID_VALUE;
        return x * y;
    }
}
