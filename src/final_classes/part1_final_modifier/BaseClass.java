package final_classes.part1_final_modifier;

public class BaseClass {
/*
* final modifier
* ..............
*
* When we use the final modifier,we prevent any further modification to that component
*
* A method declared with the final keyword cannot be overriden by subclasses
*
* A variable declared as final cannot be modified/reassigned / or given a different value
  after initialization which is  done at the moment of declaration
*
* A class declared/marked as final can extend another class,however, a final class cannot be extended
*
* If you ever see the final keyword with a parameter variable, it means that the value of this variable
  cannot be changed anywhere in the method.
*
*/
    public final void recommendedMethod(){
        System.out.println("[BaseClass.recommendedMethod]:Best Way to Do it");
        optionalMethod();
        mandatoryMethod();
    }

    protected void optionalMethod(){
        System.out.println("[BaseClass.optionalMethod]:Customize Optional Method");
    }

    private void mandatoryMethod(){
        System.out.println("[BaseClass.mandatoryMethod]:NON-NEGOTIABLE");
    }


    ////////////Static methods //////
    public static void recommendedStatic(){
        System.out.println("[BaseClass.recommendedStatic]:Best Way to Do it");
        optionalStatic();
        mandatoryStatic();
    }

    protected static void optionalStatic(){
        System.out.println("[BaseClass.optionalStatic]:Customize Optional");
    }

    private static void mandatoryStatic(){
        System.out.println("[BaseClass.mandatoryStatic]:Mandatory");
    }
}
