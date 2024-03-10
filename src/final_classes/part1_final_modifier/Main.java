package final_classes.part1_final_modifier;

import final_classes.part0_consumer_specific.ChildClass;
import final_classes.part2_external.util.Logger;

public class Main {

    public static void main(String[] args) {
        BaseClass parent = new BaseClass();
        ChildClass child = new ChildClass();
        BaseClass childReferredToAsBase = new ChildClass();

        //Calling our recommended method
        parent.recommendedMethod();
        System.out.println("_".repeat(50));

        childReferredToAsBase.recommendedMethod();

        child.recommendedMethod();
        System.out.println("_".repeat(50));



        System.out.println("_".repeat(50));

        //
        //
        //
        // Calling our static recommended method (child class won't hide them)
        parent.recommendedStatic();
        System.out.println("_".repeat(50));

        childReferredToAsBase.recommendedStatic();

        System.out.println("_".repeat(50));
        child.recommendedStatic();

        //
        //
        //
        // Best way to call static methods
        System.out.println("_".repeat(50));
        BaseClass.recommendedStatic();
        ChildClass.recommendedStatic();


        //
        //
        //
        // Local variables declared as static
        System.out.println("_".repeat(50));
        String xArgument = "This is all i've got to say about Section ";
        StringBuilder zArgument = new StringBuilder("Only saying this: Section ");
        doXYZ(xArgument,16,zArgument);
        System.out.println("After Method, xArgument: "+xArgument); // xArgument remains unchanged even after we assign x = c in doXYZ
        System.out.println("After Method, zArgument: "+zArgument); // zArgument changed to "Only saying this: Section 16" after doXYZ is invoked

        //
        //
        //
        //
        // Why change isn't good
        // Created a Logger class on part2_external.util.Logger;
        // Created static method logToConsole that takes 1 parameter of type CharSequence
        StringBuilder tracker = new StringBuilder("Step 1 is abc");
        Logger.logToConsole(tracker.toString()); //03/10/24 12:52:35 : Step 1 is abc
        tracker.append(", Step 2 is xyz");
        Logger.logToConsole(tracker); //03/10/24 12:52:35 : Step 2 is xyz
        System.out.println("After logging, tracker = "+tracker); //After logging, tracker =


        // Passing as tracker.toString
        StringBuilder tracker2 = new StringBuilder("This is another");
        Logger.logToConsole(tracker2.toString()); //03/10/24 13:07:13 : This is another
        tracker2.append(" extra step using toString()");
        Logger.logToConsole(tracker2.toString()); //03/10/24 13:07:13 : This is another extra step using toString()

        System.out.println("After logging, tracker = "+tracker2); //After logging, tracker = This is another extra step using toString()

    }

    public static void doXYZ(String x , int y,final StringBuilder z){
        final String c = x + y;
        System.out.println("c = "+c);
        x = c;
        z.append(y); // append() works regardless of final keyword on parameter z
        // z = new StringBuilder("This is a new reference"); // won't work with z declared as final StringBuilder z
    }
}
