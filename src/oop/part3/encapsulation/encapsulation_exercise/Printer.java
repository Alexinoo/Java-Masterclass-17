package oop.part3.encapsulation.encapsulation_exercise;

public class Printer {

    private int tonerLevel;
    private int pagesPrinted;
    private boolean duplex;

    public Printer(int tonerLevel, boolean duplex) {
        this.pagesPrinted = 0;
        this.tonerLevel = (tonerLevel > -1 && tonerLevel < 101) ?tonerLevel : -1;
        this.duplex = duplex;
    }

    public int addToner(int tonerAmount){
        if( tonerAmount > 0 && tonerAmount <= 100){
            int tempAmount = tonerAmount + tonerLevel;
            if( tempAmount > 100) {
                return -1;
            }
            tonerLevel += tonerAmount;
            return tonerLevel;
        }
        return -1;
    }

    public int printPages(int pages){

        int pagesToPrint = pages;

        if(duplex) {
            pagesToPrint = (pages / 2) + (pages % 2);
            System.out.println("printing in duplex");
        }
        pagesPrinted += pagesToPrint;
        return pagesToPrint;
    }

    public int getPagesPrinted() {
        return pagesPrinted;
    }
}
