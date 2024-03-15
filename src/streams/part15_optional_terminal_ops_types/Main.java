package streams.part15_optional_terminal_ops_types;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        //Create 2 courses
        Course pymc = new Course("PYMC","Python Masterclass");
        Course jmc = new Course("JMC","Java Masterclass");

       // Generate 1000 random students
        List<Student> students = Stream
                .generate(()-> Student.getRandomStudent(pymc,jmc))
                .limit(1000)
                .toList();

        /* Operation Termination Methods
         * Filter students whose age <= minAge
         * Use findAny() - does no take any arguments and
         * We have an optional result, means we can chain ifPresentOrElse
         * ifPresentOrElse() takes 2 parameters
         *  1st - is what gets executed if the resulting stream had atleast 1 value
         *  2nd - get's executed if the stream is empty
         * This means we don't have to assign to local variables or check for Nulls
         * The findAny() returns any student on this stream, but it's not guaranteed to be the first
         *  student found in the stream's processing order
         */
        int minAge = 21;
        //int minAge = 15;
        students.stream()
                .filter(s -> s.getAge() <= minAge)
                .findAny()
                .ifPresentOrElse(
                        s -> System.out.printf("Student %d from %s is %d%n",
                                s.getStudentId(),s.getCountryCode(),s.getAge()),
                        () -> System.out.println("Didn't find any one under "+minAge));

        /* Operation Termination Methods
         * Filter students whose age <= minAge
         * Use findFirst() - to return the first student in the stream processing order
         * Though it's highly likely to get the same value as with findAny()
         * Never count on findAny() to return the first instance - use findFirst() instead
         */
        System.out.println("_".repeat(50));
        students.stream()
                .filter(s -> s.getAge() <= minAge)
                .findFirst()
                .ifPresentOrElse(
                        s -> System.out.printf("Student %d from %s is %d%n",
                                s.getStudentId(),s.getCountryCode(),s.getAge()),
                        () -> System.out.println("Didn't find any one under "+minAge));

        /* Operation Termination Methods - min()
         * Filter students whose age <= minAge
         * Suppose we want the youngest student
         * We can add the sorted() intermediate operation b4 the findFirst()
         * However, since our Student class does not implement comparable  we need to pass a comparator - sorting with age
         * intelliJ gives us a hint that we can replace all that with .min()
         * This eliminates/removes the findFirst() and replaces sorted with .min()
         * The min & max also always require a comparator
         */
        System.out.println("_".repeat(50));
        students.stream()
                .filter(s -> s.getAge() <= minAge)
                .min(Comparator.comparing(Student::getAge))
                .ifPresentOrElse(
                        s -> System.out.printf("Student %d from %s is %d%n",
                                s.getStudentId(),s.getCountryCode(),s.getAge()),
                        () -> System.out.println("Didn't find any one under "+minAge));

        /* Operation Termination Methods - max()
         * Filter students whose age <= minAge
         * Suppose we want the oldest student
         * We can use max() and pass the comparator
         * We are most likely, to get the oldest student with the same age closer to 21 or exactly 21
         */
        System.out.println("_".repeat(50));
        students.stream()
                .filter(s -> s.getAge() <= minAge)
                .max(Comparator.comparing(Student::getAge))
                .ifPresentOrElse(
                        s -> System.out.printf("Student %d from %s is %d%n",
                                s.getStudentId(),s.getCountryCode(),s.getAge()),
                        () -> System.out.println("Didn't find any one under "+minAge));

        /* Operation Termination Methods - average()
         * Filter students whose age <= minAge
         * get the average age of students under 21
         * use mapToInt using getAge
         * Use the average terminal operation which returns OptionalDouble
         * Chain ifPresentOrElse on that result i.e the average returned from OptionalDouble
         * print the average value or a message for that matter
         *
         */
        System.out.println("_".repeat(50));
        students.stream()
                .filter(s -> s.getAge() <= minAge)
                .mapToInt(Student::getAge)
                .average()
                .ifPresentOrElse(
                        a -> System.out.printf("Avg age under 21: %.2f%n", a),
                        ()-> System.out.println("Did not find any one under " + minAge));

        /* Operation Termination Methods - reduce()
         * This is a single parameter version, that returns an Optional
         * Get the countries of our under 21 population in a single comma delimited list
         * Use distinct() to get unique values only
         * Execute reduce that takes 1 parameter, a BinaryOperator
         * A Binary operator takes 2 params of the same type- in tis case the type from the stream so a String
         * It accumulates a value in memory using the function specified here
         * USe String.join to get countries by comma
         * USe ifPresentOrElse() to print whatever comes back, otherwise print none
         *
         * Adjust minAge to get diff output
         */
        System.out.println("_".repeat(50));
        students.stream()
                .filter(s -> s.getAge() <= minAge)
                .map(Student::getCountryCode)
                .distinct()
                .reduce((a,b)-> String.join(",",a,b))
                .ifPresentOrElse(
                        System.out::println,
                        ()-> System.out.println("None Found"));
    }
}
