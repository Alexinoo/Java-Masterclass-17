package collections.part14_Treeset_challenge;

public class Main {
    public static void main(String[] args) {

        int rows = 10;
        int totalSeats = 100;

        Theatre rodgersNYC = new Theatre("Richard Rodgers",rows,totalSeats);

        rodgersNYC.printSeatMap();

        bookSeat(rodgersNYC,'A',3);
        bookSeat(rodgersNYC,'A',3);

        bookSeat(rodgersNYC,'B',1);
        bookSeat(rodgersNYC,'B',11);
        bookSeat(rodgersNYC,'M',1);


        ///Book multiple seats - Contiguosly [B003,B004,B005,B006]
        bookSeats(rodgersNYC,4,'B',3,10);

        ///Book multiple seats - Contiguosly [C003,C004,C005,C006,C007,C008]
        // -- No enough seats in row B ... so book in row C
        bookSeats(rodgersNYC,6,'B','C',3,10);

        //Book 4 contiguous seats in row B anywhere between seat 1 to seat 10
        // --[ Congratulations! Your reserved seats  are [B007, B008, B009, B010]
        // & ignored b002 since there were no contiguous seats after it
        bookSeats(rodgersNYC,4,'B',1,10);

        // Book 4 contiguous seats in either row B or row C between seat 1 to seat 10
        // -- Request Failed [Sorry! No matching contiguous seats in rows : B - C]
        bookSeats(rodgersNYC,4,'B','C',1,10);

        //Ask for 1 seat in either row B or row C between seat 1 to seat 10
        // -- [ Congratulations! You reserved seats are [B002] ]
        bookSeats(rodgersNYC,1,'B','C',1,10);


        // Bad data
        //Invalid! 4 seats between M[3-10]-J[3-10] Try again: Seat must be between A001 and J010
        //Sorry! No matching contiguous seats in rows : M - Z
        bookSeats(rodgersNYC,4,'M','Z',3,10);

        //Book 10 contiguous seats in either row A through E
        // Congratulations! You reserved seats are [D001, D002, D003, D004, D005, D006, D007, D008, D009, D010]
        bookSeats(rodgersNYC,10,'A','E',1,10);


    }

    private static void bookSeat(Theatre theatre , char row, int seatNo){
        String seat = theatre.reserveSeat(row, seatNo);
        if(seat != null){
            System.out.println("Congratulations! Your reserved seat is "+seat);
            theatre.printSeatMap();
        }else{
            System.out.println("Sorry, unable to reserve "+ row + seatNo);
        }
    }

    private static void bookSeats(Theatre theatre , int tickets,char minRow,
                                  int minSeat,int maxSeat){
        bookSeats(theatre,tickets,minRow,minRow,minSeat,maxSeat);
    }

    private static void bookSeats(Theatre theatre , int tickets,
                                  char minRow,char maxRow,
                                  int minSeat,int maxSeat){
        var seats = theatre.reserveSeats(tickets,minRow,maxRow,minSeat,maxSeat);

        if(seats != null){
            System.out.println("Congratulations! You reserved seats are "+ seats);
            theatre.printSeatMap();
        }else{
            System.out.println("Sorry! No matching contiguous seats in rows : "+minRow+ " - " + maxRow);
        }
    }


}
