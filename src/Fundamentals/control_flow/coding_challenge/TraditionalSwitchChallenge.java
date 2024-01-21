package Fundamentals.control_flow.coding_challenge;

public class TraditionalSwitchChallenge {

    public static void main(String[] args) {

        char charValue = 'F';

        switch(charValue){
            case 'A':
                System.out.println(charValue +" is Able");
                break;
            case 'B':
                System.out.println(charValue +" is Baker");
                break;
            case 'C':
                System.out.println(charValue +" is Charlie");
                break;
            case 'D':
                System.out.println(charValue +" is Dog");
                break;
            case 'E':
                System.out.println(charValue +" is Easy");
                break;
            default:
                System.out.println("Letter " +charValue+ " was not found in the switch");
        }
    }
}
