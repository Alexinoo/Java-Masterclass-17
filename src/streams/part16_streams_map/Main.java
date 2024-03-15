package streams.part16_streams_map;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static java.util.stream.Collectors.*;
public class Main {
    public static void main(String[] args) {

        //Create 3 courses
        Course pymc = new Course("PYMC","Python Masterclass",50);
        Course jmc = new Course("JMC","Java Masterclass",100);
        Course jgames = new Course("JGAME","Creating games in Java");

        //Generate 5000 students
        List<Student> students = IntStream
                .rangeClosed(1,5000)
                .mapToObj(s -> Student.getRandomStudent(jmc,pymc))
                .toList();

        /*
         * Us the above list of students to create a map with countryCode as the key
         * with a list of students for each country(as value)
         * This is easy implementing with the collector's class
         * Use var as type since java can infer the type out and the code will look
         * a lot cleaner without all the generic typing
         *
         * Use .collect(Collectors.groupingBy) and call groupingBy
         * groupingBy() - takes a lambda expression - pass method reference for getCountryCode on
         * students
         *
         * The Collectors.groupingBy() returns a Collector that collects data into a map
         * This map gets keyed by the result of the function lambda we provide - keyed by countryCode
         * Then Loop through the key-value pairs and print them out i.e. key - size
         *
         * This gives us info for each country with total no of students for each country
         */

        var mappedStudents = students.stream()
                .collect(Collectors.groupingBy(Student::getCountryCode));

        mappedStudents.forEach((k,v)-> System.out.println(k + " : "+v.size()));

        /* Count no of students below 25 yrs per country
         * Us the above list of students to create a map with countryCode as the key
         * Limiting above to students age <= 25
         * Use static import so that our code is easier to read
         * static import lets you import 1 or more static members of a class
         * The Collectors class has over 40 static methods on it
         * We can fully qualify each method call with the Collectors name like we have been doing so far
         * Though this is tedious & code will be more readable without it
         * We can use static import statement specifying with a wild card to import all static members
         * Added import static java.util.stream.Collectors.*; above
         * This eliminates the potential errors that we were getting
         * We are using the groupingBy()  overloaded version of the groupingBy() on collectors
         * The groupingBy has a second parameter which is a Collector Type
         * Means we can use any methods on the Collectors that return a Collector Type i.e filtering
         * Filtering takes a predicate as it's first argument and another collector as it's second
         * The predicate takes a lambda expression which filters students with age below or equal to 25
         * We are returning Collectors.toList() as the collector on this
         *
         * Then Loop through the key-value pairs on youngerSet and print them out i.e. key - size
         *
         * This gives us info for each country with total no of students for each country below 25 yrs old
         *
         */
        System.out.println("_".repeat(50));
        int minAge = 25;

        var youngerSet = students.stream()
                .collect(groupingBy(Student::getCountryCode,
                        filtering(s -> s.getAge() <= minAge,toList())
                ));
        youngerSet.forEach((k,v)-> System.out.println(k + " : "+v.size()));

        /* partitionBy
         * Returns a map of boolean values -
         * Means you can split your population into 2 buckets - a bifurcated map based on any condition
         * We can achieve this by using partitioningBy() on Collectors
         * partitioningBy() takes a method reference on student - hasProgrammingExperience
         *
         * Then print out how many experienced students we have by using true as the key to the resulting
         * map which was partitioned with a Boolean Wrapper
         *  i.e.  experienced.get(true).size()
         *
         */
        System.out.println("_".repeat(50));

        var experienced = students.stream()
                .collect(partitioningBy(Student::hasProgrammingExperience));
        System.out.println("Experienced Students = "+experienced.get(true).size());

        /*
         * There is a another method called counting on the Collectors class
         * We can use the overloaded version of partitionBy that takes a collectors as it's 2nd arg
         * Pass counting which is a method on Collectors
         * We have passed counting() as a method reference but we can also pass any expression we want
         * that evaluates to a boolean value
         * Remove .size() which is not needed now
         *
         */
        System.out.println("_".repeat(50));
        var inExperiencedCount = students.stream()
                .collect(partitioningBy(
                        Student::hasProgrammingExperience ,
                        counting()));
        System.out.println("No Experienced Students = "+inExperiencedCount.get(false));

        /*
         * Suppose we want to see students that have programming experience and are active in the current month
         * We can pass a lambda expression this time explicitly by
         * calling hasProgrammingExperience() and check that getMonthsSinceActive equals to 0
         *
         */
        System.out.println("_".repeat(50));
        var experiencedAndActive = students.stream()
                .collect(partitioningBy(
                        s -> s.hasProgrammingExperience() && s.getMonthsSinceActive() == 0 ,
                        counting()));
        System.out.println("Experienced and Active Students = "+experiencedAndActive.get(false));


        /*
         * Multilevel map
         * Use .collect()
         * Pass groupingBy() as the 1st param with a method reference to getCountryCode
         * Pass a nested groupingBy as the 2nd param which returns a Map within each country
         * However, in this case, the Nested Map will be keyed on gender
         *
         * Printing above is however a bit complicated because we need to nest the loops
         * Loop through the multilevel and print the key
         * The value we get back is also another map and we need to repeat this process using
         * forEach on this value
         *
         * Prints a better picture of my student population
         * First by country then by gender and count for each
         *
         */
        System.out.println("_".repeat(50));
        var multiLevel = students.stream()
                .collect(groupingBy(
                        Student::getCountryCode,
                        groupingBy(Student::getGender)));

        multiLevel.forEach((key,value)->{
            System.out.println(key);
            value.forEach((key1,value1)->
                    System.out.println("\t"+ key1 + " "+ value1.size()));
        });

    }
}
