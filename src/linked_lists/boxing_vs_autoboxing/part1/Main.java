package linked_lists.boxing_vs_autoboxing.part1;

public class Main {

    public static void main(String[] args) {
        /*
            Boxing and Autoboxing
            =======================
            - The first 2 statements are trying to manually box the primitive int 15 to an instance
              Integer wrapper class
            - IntelliJ will try to attract your attention with some warnings
            - We can also use WrapperClass.valueOf() which is a static factory method that
              returns an object of type double
         */
        Integer boxedInt = Integer.valueOf(15); //preferred but unnecessary
        Integer deprecatedBoxing = new Integer(15); //deprecated since JDK 9
        int unboxedInt = boxedInt.intValue(); //unnecessary

        //Automatic
        Integer autoBoxed = 15;
        int autoUnboxed = autoBoxed;
        System.out.println(autoBoxed.getClass().getName());
        //  System.out.println(autoUnboxed.getClass().getName()); - Can't use getClass().getName() on primitive types


        //Calling Below methods
        Double resultBoxed = getLiteralDoublePrimitive();
        double resultUnBoxed = getDoubleObject();
    }

    private static Double getDoubleObject(){
        return Double.valueOf(100.00);
    }

    private static double getLiteralDoublePrimitive(){
        return 100.0;
    }
}
