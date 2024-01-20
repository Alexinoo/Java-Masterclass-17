package Fundamentals.basics.coding_challenges;

public class MethodOverloadingChallenge {
    public static void main(String[] args) {
        System.out.println("5ft,8inc = "+convertToCentimetres(5,8)+"cm");
        System.out.println("68inc = "+convertToCentimetres(68)+"cm");
    }

    public static double convertToCentimetres(int inches){
        // 1 inch = 2.54cm
        return inches * 2.54;
    }

    public static double convertToCentimetres(int feet ,int inches){
        // 1 foot = 12 inches
        int feetToInches = feet * 12;
        int totalInches = feetToInches + inches;
//     //   double result =  convertToCentimetres(totalInches);
        return  convertToCentimetres(totalInches);
    }
}
