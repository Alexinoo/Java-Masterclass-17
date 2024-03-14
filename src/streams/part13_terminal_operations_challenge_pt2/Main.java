package streams.part13_terminal_operations_challenge_pt2;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        /* Copy 2 courses pymc and jmc from the code setup
         * Add lecture count param as 50 for pymc
         * Add lecture count param as 100 for jmc
         * Add another course - Create games in Java - no need for lecture count
         */
        Course pymc = new Course("PYMC","Python Masterclass",50);
        Course jmc = new Course("JMC","Java Masterclass",100);
        Course jgames = new Course("JGAME","Creating games in Java");

        /* Using Stream.generate or Stream.iterate to generate 5000 random students
         * Create a list of these
         * Returns an unmodifiable list
         *
         */
        List<Student> students0 = Stream
                .iterate(1, s -> s <= 5000, s-> s + 1)
                .map(s -> Student.getRandomStudent(jmc,pymc))
                .toList();

        /* Using IntStream
         * chain with rangeClosed which takes in lower(inc) and upper bound(inc) params
         * map the stream to an obj - need to use mapToObj with IntStream,DoubleStream , LongStream
         *
         */
        List<Student> students = IntStream
                .rangeClosed(1,5000)
                .mapToObj(s -> Student.getRandomStudent(jmc,pymc))
                .toList();

        /* Task 2 - use reduce()
         * Use your getPercentComplete() to Calculate the average percentage completed for all students
          for JMC course
         * use mapToDouble on each student to get a double and getPercentComplete for "JMC" course
         * use reduce that takes 2 params
         *  - takes a seed first i.e 0 - total variable you might create if you were doing this with a for loop
         *  - accumulator - A function going to add the averages to that seed value
         * Divide the totalPercent with students.size()
         *
         */

        double totalPercent = students.stream()
                .mapToDouble(s -> s.getPercentComplete("JMC"))
                .reduce(0, Double::sum);
        double averagePercent = totalPercent / students.size();
        System.out.printf("Average Percent complete = %.2f%% %n", averagePercent);

        /* use sum()
         * Then divide the totalPercent with students.size()
         * Works the same as above

        double totalPercent = students.stream()
                .mapToDouble(s -> s.getPercentComplete("JMC"))
                .sum();
        double averagePercent = totalPercent / students.size();
        System.out.printf("Average Percent complete = %.2f%% %n", averagePercent);
        */

         /* Task 3 - Multiply above result by 1.25
         * Collect a group of students (either as a list, or a set)
         * Students who have completed more than 3/4 of that average percent
         * Did not specify how to define active and was left for us to decide
         * Multiply average percent with 1.25 and cast the result to an int - fraction part is truncated
         * Then print it
         * Create a collection of students that exceed topPercent threshold and who are active.
         * Active means student who had an activity this month,so getMonthsSinceActive shd be 0 for JMC course
         * Create a list of hard workers and filter the ones whose getMonthsSinceActive for JMC is 0
         * And filter again where their percentComplete  >= the topPercent
         * Use toList() to create and return the list
         * Then print how many students fall ino that category
         */

        int topPercent = (int) (1.25 * averagePercent);
        System.out.printf("Best Percentage Complete = %d%% %n",topPercent);

        List<Student> hardWorkers = students.stream()
                .filter(s -> s.getMonthsSinceActive("JMC") == 0)
                .filter(s -> s.getPercentComplete("JMC") >= topPercent)
                .toList();
        System.out.println("hardWorkers = "+hardWorkers.size());

        /* Task 4 - Sort by longest enrolled students who are still active
         * Offer your new course to 10 of these students for a trial run as a criteria for getting selected
         * Use hardWorkers List above
         * chain sorted() and pass the comparator that sorts via the yearEnrolled
         * chain limit() to a max of 10 students
         *
         * Then loop through the selected students and add the new course
         * Then print the student data to confirm
         * Comes back as unmodifiable list
         * However, it did not prevent us from modifying elements in the list as we just saw
         */
        Comparator<Student> longTermStudent = Comparator.comparing(Student::getYearEnrolled);
        List<Student> rewardedStudents = students.stream()
                .filter(s -> s.getMonthsSinceActive("JMC") == 0)
                .filter(s -> s.getPercentComplete("JMC") >= topPercent)
                .sorted(longTermStudent)
                .limit(10)
                .toList();
        rewardedStudents.forEach(s -> {
            s.addCourse(jgames);
            System.out.println(s);
        });

        /* Task 4 - Sort by longest enrolled students who are still active
         * Using .toList()
         * Offer your new course to 10 of these students for a trial run as a criteria for getting selected
         * Using a one-liner and printing only student id in one line
         */
        System.out.println("____ toList() __");
        students.stream()
                .filter(s -> s.getMonthsSinceActive("JMC") == 0)
                .filter(s -> s.getPercentComplete("JMC") >= topPercent)
                .sorted(longTermStudent)
                .limit(10)
                .toList()
                .forEach(s -> {
                s.addCourse(jgames);
                System.out.print(s.getStudentId() + " ");
        });

        /* Task 4 - Sort by longest enrolled students who are still active
         * Using .collect(Collectors.toList())
         * Offer your new course to 10 of these students for a trial run as a criteria for getting selected
         * Using a one-liner and printing only student id in one line
         * We get a warning to replace toList() but we still get the same result
         * However, the only diff btwn this & the previous version is that the collection returned is modifiable
         */
        System.out.println("\n_____ collect(Collectors.toList()) ____");
        students.stream()
                .filter(s -> s.getMonthsSinceActive("JMC") == 0)
                .filter(s -> s.getPercentComplete("JMC") >= topPercent)
                .sorted(longTermStudent)
                .limit(10)
                .collect(Collectors.toList())
                .forEach(s -> {
                    s.addCourse(jgames);
                    System.out.print(s.getStudentId() + " ");
                });

        /* Task 4 - Sort by longest enrolled students who are still active
         * Using .collect(Collectors.toSet())
         * Offer your new course to 10 of these students for a trial run as a criteria for getting selected
         * Using a one-liner and printing only student id in one line
         * We get the same students but not sorted
         * Remember, this gives us back a HashSet - so it has no order
         * sorted intermediate operation is still needed to pick the right set of students
         * Ones who have been enrolled the longest that meet the other criteria
         */
        System.out.println("\n_____ collect(Collectors.toSet()) ____");
        students.stream()
                .filter(s -> s.getMonthsSinceActive("JMC") == 0)
                .filter(s -> s.getPercentComplete("JMC") >= topPercent)
                .sorted(longTermStudent)
                .limit(10)
                .collect(Collectors.toSet())
                .forEach(s -> {
                    s.addCourse(jgames);
                    System.out.print(s.getStudentId() + " ");
                });

        /* Task 4 - Sort by longest enrolled students who are still active
         * Using .collect( Supplier , Accumulator,Combiner)
         * Offer your new course to 10 of these students for a trial run as a criteria for getting selected
         * Using a one-liner and printing only student id in one line
         * Using .collect() - overloaded version with TreeSet
         * Now there is a big difference -
         * We get only one result - is because the Comparator that we are using is not a good one to use
         * with a TreeSet
         * The TreeSet will use it to sort but also to determine the uniqueness of the student
         * Therefore, students are not unique by the enrollment age,that's why we don't get 10 of them
         */
        System.out.println("\n_____ .collect( Supplier,Accumulator,Combiner) - TreeSet ____");
        students.stream()
                .filter(s -> s.getMonthsSinceActive("JMC") == 0)
                .filter(s -> s.getPercentComplete("JMC") >= topPercent)
                .sorted(longTermStudent)
                .limit(10)
                .collect(()->new TreeSet<>(longTermStudent), TreeSet::add,TreeSet::addAll)
                .forEach(s -> {
                    s.addCourse(jgames);
                    System.out.print(s.getStudentId() + " ");
                });

        /* Task 4 - Sort by longest enrolled students who are still active
         * Using .collect( Supplier , Accumulator,Combiner)
         * Offer your new course to 10 of these students for a trial run as a criteria for getting selected
         * Using with previous comparator then chain with another one that compares with student id
         */
        System.out.println("\n_____ .collect( Supplier,Accumulator,Combiner) - TreeSet ____");
        System.out.println("\n_____ Chain previous comparator with student id ____");
        Comparator<Student> uniqueSorted = longTermStudent.thenComparing(Student::getStudentId);
        students.stream()
                .filter(s -> s.getMonthsSinceActive("JMC") == 0)
                .filter(s -> s.getPercentComplete("JMC") >= topPercent)
                .sorted(longTermStudent)
                .limit(10)
                .collect(()->new TreeSet<>(uniqueSorted), TreeSet::add,TreeSet::addAll)
                .forEach(s -> {
                    s.addCourse(jgames);
                    System.out.print(s.getStudentId() + " ");
                });

        /* Task 4 - Sort by longest enrolled students who are still active
         * Using no collect at all
         * Offer your new course to 10 of these students for a trial run as a criteria for getting selected
         * Still works..
         */
        System.out.println("\n_____ Using no collection at all ____");
        students.stream()
                .filter(s -> s.getMonthsSinceActive("JMC") == 0)
                .filter(s -> s.getPercentComplete("JMC") >= topPercent)
                .sorted(longTermStudent)
                .limit(10)
                .forEach(s -> {
                    s.addCourse(jgames);
                    System.out.print(s.getStudentId() + " ");
                });


    }
}
