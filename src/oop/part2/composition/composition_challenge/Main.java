package oop.part2.composition.composition_challenge;

public class Main {

    public static void main(String[] args) {

        SmartKitchen kitchen = new SmartKitchen();

        //Do some dirty in the kitchen
        //        kitchen.getDishWasher().setHasWorkToDo(true);
        //        kitchen.getIceBox().setHasWorkToDo(true);
        //        kitchen.getBrewMaster().setHasWorkToDo(true);
        //
        //        kitchen.getDishWasher().doDishes();
        //        kitchen.getIceBox().orderFood();
        //        kitchen.getBrewMaster().brewCoffee();

        kitchen.setKitchenState(true,false,true);

        kitchen.doKitchenWork();
    }
}
