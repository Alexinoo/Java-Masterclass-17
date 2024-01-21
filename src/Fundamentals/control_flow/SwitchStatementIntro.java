package Fundamentals.control_flow;

public class SwitchStatementIntro {

    public static void main(String[] args) {

        /*
        int value = 3;
        if(value == 1){
            System.out.println("Value is 1");
        }else if (value == 2){
            System.out.println("Value is 2");
        }else{
            System.out.println("Values is neither 1 or 2");
        }
        */

        int switchValue = 6;

        switch(switchValue){

            case 1:
                System.out.println("Value is 1");
                break;

            case 2:
                System.out.println("Value is 2");
                break;
            case 3: case 4: case 5:
                System.out.println("Value is   3 , 4 or 5");
                System.out.println("Actually it's a "+switchValue);
                break;
            default:
                System.out.println("Values is neither 1,2,3,4 or 5");

        }
    }
}
