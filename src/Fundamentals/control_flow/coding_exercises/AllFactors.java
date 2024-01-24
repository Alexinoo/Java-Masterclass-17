package Fundamentals.control_flow.coding_exercises;

public class AllFactors {

    public static void main(String[] args) {

        printFactors(10);
        System.out.println();

        printFactors(-1);
        System.out.println();

        printFactors(1);
        System.out.println();
        printFactors(2);

        System.out.println();
        printFactors(120);

        System.out.println();
        printFactors(121);

        System.out.println();
        printFactors(81);

        System.out.println();
        printFactors(25);

        System.out.println();
        printFactors(31);

        System.out.println();
        printFactors(32);

        System.out.println();
        printFactors(33);

        System.out.println();
        printFactors(68);

        System.out.println();
        printFactors(45);

        System.out.println();
        printFactors(12);

        System.out.println();
        printFactors(1024);

        System.out.println();
        printFactors(510);
    }

    public static void printFactors(int number){
        if(number < 1) {
            System.out.println("Invalid Value");
            return;
        }

        int start = 1;
        while(start <= number){
             if(number % start == 0)
                System.out.print(start+" ");
             start++;
        }

    }
}
