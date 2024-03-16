package streams.part18_streams_challenge;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        //Create 3 courses
        Course pymc = new Course("PYMC","Python Masterclass",50);
        Course jmc = new Course("JMC","Java Masterclass",100);
        Course jgames = new Course("JGAME","Creating games in Java");

        /*
         * Generate a list of 10,000 students
         * Must have enrolled no later than 4 years ago
         * Get the current year from LocalDate
         * Set a local variable for a list of students and generate 10000 students by calling getRandomStudent()
         * filter the only ones who have enrolled from 4 yrs ago .i.e >= 2020
         * Collect as toList()
         */

        int currentYear = LocalDate.now().getYear();
        List<Student> students = Stream
                .generate(()->Student.getRandomStudent(jmc,pymc,jgames))
                .filter(s -> s.getYearEnrolled() >= (currentYear - 4))
                .limit(10000)
                .toList();

        /*
         * Test the result using the summaryStatistics
         * pass the stream pipeline directly to sout and use mapToInt and use getYearEnrolled()
         * to get an int from every stream value
         * Then call summaryStatistics
         */

        System.out.println(students
                .stream()
                .mapToInt(Student::getYearEnrolled)
                .summaryStatistics()
        );


        /*
         * Check if the randomization of the number of classes is working
         * Print 10 out and examine them
         * You may be tempted to use a stream for this though it's not of the preferred approach in
         * this scenario
         * Use a sub list on my students and print them out
         *
         * Result - we can see a variety of courses listed
         * Some students only have 1 class, others 2 and a couple with 3 classes
         */
        System.out.println("_".repeat(50));
        students.subList(0,10).forEach(System.out::println);

        /*
         * Check for summary statistics for the above as well
         * Use lambda expression that returns the size of the engagementMap
         */
        System.out.println("_".repeat(50));
        System.out.println(students
                .stream()
                .mapToInt(s -> s.getEngagementMap().size())
                .summaryStatistics()
        );

        /*
         * Find out how many students are in each class
         * Look at the course engagement records as whole set & use it to figure out my counts
         * Set a local variable called mappedActivity
         * use flatMap to get all the engagement records for each student and get the engagement values from this map
         * Then call . stream on that collection
         * Once we have a stream of engagement records, create a Map of all that data using Collectors.groupingBy
         * Then pass the course code as the key to the map  as a method reference
         * Then call Collectors counting()
         * used counting() with the qualifying name on these methods rather than use a static import
         * Then printing above
         */
        System.out.println("_".repeat(50));
        var mappedActivity = students.stream()
                .flatMap(s -> s.getEngagementMap().values().stream())
                .collect(Collectors.groupingBy(
                        CourseEngagement::getCourseCode,
                        Collectors.counting()
                ));
        mappedActivity.forEach((k,v)-> System.out.println(k + " : "+v));


        /*
         * Get the count of students taking 1,2 or 3 classes next
         * Remove the flatMap operation
         * This is because we will group by the engagementMap()
         * And count the student in each via .size() by passing it as a lambda expression
         *
         */
        System.out.println("_".repeat(50));
        var classCounts = students.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getEngagementMap().size(),
                        Collectors.counting()
                ));
        classCounts.forEach((k,v)-> System.out.println(k + " : "+v));

        /*
         * Get the averagePercent complete for each class
         * Replace counting() with averagingDouble()
         * which then takes method reference getPercentComplete from CourseEngagement
         *
         */
        System.out.println("_".repeat(50));
        var percentages = students.stream()
                .flatMap(s -> s.getEngagementMap().values().stream())
                .collect(Collectors.groupingBy(
                        CourseEngagement::getCourseCode,
                        Collectors.averagingDouble(CourseEngagement::getPercentComplete)
                ));
        percentages.forEach((k,v)-> System.out.printf("%s : %.2f%% %n",k,v));


        /*
         * Get a map of statistics on the above stats
         * Replace averagingDouble() with summarizingDouble()
         * This gives us more data for each class
         * i.e. minimum and maximum % complete for each class
         *
         */
        System.out.println("_".repeat(50));
        var percentagesStats = students.stream()
                .flatMap(s -> s.getEngagementMap().values().stream())
                .collect(Collectors.groupingBy(
                        CourseEngagement::getCourseCode,
                        Collectors.summarizingDouble(CourseEngagement::getPercentComplete)
                ));
        percentagesStats.forEach((k,v)-> System.out.println(k + " "+v));

        /*
         * Final challenge - create a nested map using the course code as the key to the top level map
         * With last activity year as the key, for the nested map
         * Nest a call to the Collectors.groupingBy() as the additional parameter keyed by ..or grouped by last
         * activity year on course engagement
         * Then get a list of counts and not a list or set of students
         *
         * Prints each course - for the past 4 years - what kind of activity we have each year
         * current year count is higher because its cumulative of some of the previous years and will include
         * all the students enrolled this year
         *
         */
        System.out.println("_".repeat(50));
        var yearMap = students.stream()
                .flatMap(s -> s.getEngagementMap().values().stream())
                .collect(Collectors.groupingBy(
                        CourseEngagement::getCourseCode,
                        Collectors.groupingBy(CourseEngagement::getLastActivityYear,
                                Collectors.counting())
                ));
        yearMap.forEach((k,v)-> System.out.println(k + " : "+v));


        /*
         * The above no's looks off and maybe knowing how many enrolled each year would be kind of useful
         * as you're studying these counts
         * Won't be assigning below to a variable
         *
         * Call flatMap and stream on engagementMap() values
         * Then group by enrollment year which is on the CourseEngagement class
         * Then count that group
         * Then print these out by chaining forEach
         */

        System.out.println("_".repeat(50));
        students.stream()
                .flatMap(s -> s.getEngagementMap().values().stream())
                .collect(Collectors.groupingBy(
                        CourseEngagement::getEnrollmentYear ,
                        Collectors.counting()))
                .forEach((k,v) -> System.out.println(k + " : "+v));

        /*
         * Adding grouping by Course for the above
         * Add a collector to group by course code
         *
         * Running this, we get enrollments by course in each year
         */
        System.out.println("_".repeat(50));
        students.stream()
                .flatMap(s -> s.getEngagementMap().values().stream())
                .collect(Collectors.groupingBy(
                        CourseEngagement::getEnrollmentYear ,
                        Collectors.groupingBy(CourseEngagement::getCourseCode,
                        Collectors.counting())))
                .forEach((k,v) -> System.out.println(k + " : "+v));

    }
}
