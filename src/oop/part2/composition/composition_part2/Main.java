package oop.part2.composition.composition_part2;

public class Main {

    public static void main(String[] args) {
        ComputerCase theCase = new ComputerCase("2208","Dell","240");

        Monitor theMonitor = new Monitor("27inch Beast","Acer",27,"2540 * 1440");

        MotherBoard theMotherBoard = new MotherBoard("BJ-200","Asus",4,6,"v2.44");

        PersonalComputer thePC = new PersonalComputer("2208","Dell",theCase,theMonitor,theMotherBoard);

        //thePC.getMonitor().drawPixelAt(10,10,"red");
        //thePC.getMotherBoard().loadProgram("Window OS");
        //thePC.getComputerCase().pressPowerButton();

        thePC.powerUp();
    }
}
