package final_classes.part0_consumer_specific;

import final_classes.part1_final_modifier.BaseClass;

public class ChildClass extends BaseClass {

    @Override
    protected void optionalMethod() {
        System.out.println("Child.optionalMethod]:Extra stuff here");
        super.optionalMethod();
    }

//    @Override
//    public void recommendedMethod() {
//        System.out.println("["+this.getClass().getSimpleName()+".recommendedMethod]:I'll do things my own way");
//        optionalMethod();
//    }

    private void mandatoryMethod(){
        System.out.println("Child.mandatoryMethod]:My own important stuff");
    }

    public static void recommendedStatic(){
        System.out.println("[Child.recommendedStatic]:Best Way to Do it");
        optionalStatic();
        //mandatoryStatic(); -declared as private on BaseClass
    }
}
