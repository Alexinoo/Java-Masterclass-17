package streams.part7_stream_terminal_operations;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {

    /*

        Stream Terminal operations
        ..........................

        1. IntSummaryStatistics summaryStatistics()

            - Returns an IntSummaryStatistics describing various summary data about the elements of this stream.
              This is a special case of a reduction.
     */
    public static void main(String[] args) {

        var result = IntStream
                .iterate(0,i -> i <= 1000, i -> i + 10)
                .summaryStatistics();
        System.out.println("Result = "+result); // Result = IntSummaryStatistics{count=101, sum=50500, min=0, average=500.000000, max=1000}


        var leapYearData = IntStream
                .iterate(2000 , i -> i <= 2025 , i -> i + 1)
                .filter(i -> i % 4 == 0)
                .peek(System.out::println)
                .summaryStatistics();

        System.out.println("Leap Year Data = "+leapYearData);


        //Create an array of seats
        Seat[] seats = new Seat[100];
        Arrays.setAll(seats, i -> new Seat((char)('A' + i / 10), i % 10 + 1));
        // Arrays.asList(seats).forEach(System.out::println);

        //
        //
        //
        //
        // Count how many seats are reserved
        long reservationCount = Arrays
                .stream(seats)
                .filter(Seat::isReserved)
                .count();
        System.out.println("Reserved Seats = "+reservationCount);

        //
        //
        //
        //
        // Demonstrate each of the matching terminal operations
        // No need to filter with any intermediate operation since predicate is built-in


        //
        //
        // Check if there is at least one element that's reserved
        boolean hasBookings = Arrays
                .stream(seats)
                .anyMatch(Seat::isReserved);
        System.out.println("hasBookings = "+ hasBookings); // true

        //
        //
        // Check if theatre is fully booked
        boolean fullyBooked = Arrays
                .stream(seats)
                .allMatch(Seat::isReserved);
        System.out.println("fullyBooked = "+ fullyBooked); // false

        //
        //
        // Check if theatre - Returns true if everything was falsy - no bookings at all
        boolean eventWashedOut = Arrays
                .stream(seats)
                .noneMatch(Seat::isReserved);
        System.out.println("eventWashedOut = "+ eventWashedOut); // false









    }
}
