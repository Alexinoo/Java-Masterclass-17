package oop.part3.encapsulation.encapsulation_challenge;

public class Main {

    public static void main(String[] args) {

        //Printer printer = new Printer(50 , false);
        Printer printer = new Printer(50 , true);
        System.out.println("Initial page count "+printer.getPagesPrinted());

        int pagesPrinted = printer.printPages(5);
        System.out.printf("Current job pages: %d, Total pages printed: %d %n ",pagesPrinted,printer.getPagesPrinted());

        pagesPrinted = printer.printPages(10);
        System.out.printf("Current job pages: %d, Total pages printed: %d %n ",pagesPrinted,printer.getPagesPrinted());


    }
}
