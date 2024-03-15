package streams.part17_streams_flatmaps;

import java.util.List;
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

        var experienced = students.stream()
                .collect(partitioningBy(Student::hasProgrammingExperience));
        System.out.println("Experienced Students = "+experienced.get(true).size());


        /*
         * flatMap operation
         * performs one-to-many transformations on elements in a stream pipeline
         * It's called flatMap because it flattens results from a hierarchical collection
         * into on of uniformly typed elements
               flatMap(Function<T , Stream> mapper)
               map(Function<T, R> mapper
         * For map, you return a different instance of an object - exchanging one type for
         * another, for each element on the stream
         * For flatMap, you return a Stream - means you are exchanging one element, for a stream
         * of elements back
         * See why this is a big convenience by examining some code
         */

        /*
         * Get the count of students from experienced
         *
         */

        long studentBodyCount = 0;
        for(var list : experienced.values()){
            studentBodyCount += list.size();
        }
        System.out.println("studentBodyCount = "+studentBodyCount); // 5000

        /*
         * Alternatively, we could do the following to get the same results as above
         * Let's use sum operation on IntStream to achieve this
         */

         studentBodyCount = experienced.values().stream()
                 .mapToInt( l -> l.size())
                 .sum();
        System.out.println("studentBodyCount = "+studentBodyCount); // 5000


        /*
         * Get the count of students who have been active in the last 3 months using streams
         * use map and return a stream -
         * filter the students who have been active for the last 3 months
         * chain count to return how many they are
         * Since we are getting a Stream<Long> from the map operation
         * We can use mapToLong in the consequent operation and just return the value
         * of the string which is a long
         * However this code below seems convoluted and ugly
         * Imagine, if we wanted same info with our multi-level map
         * This is where flatMap() operation comes into picture
         *
         */

        studentBodyCount = experienced.values().stream()
                .map(l -> l.stream()
                        .filter(s -> s.getMonthsSinceActive() <= 3)
                        .count()
                )
                .mapToLong( l -> l)
                .sum();
        System.out.println("studentBodyCount = "+studentBodyCount); // 2162

        /*
         * flatMap()
         * use a map and get a stream from each of list values
         * Then add a filter for students who have been active for the last 3 months
         * Then apply terminal operation count
         *
         * Printing the value that we get back
         *
         * Our list  experienced.values().stream() which contained
         *  a bunch of lists i.e. Stream<List<Student>>
         * Now contains Stream<Student> instead and not a stream of a stream of students
         * This operation is called flatMap since we've flattened the Tree structure into a
         * simple list of Students -
         * Think of it as flattening the hierarchy of the source of data
         * And we get the same result as above
         */
        long count = experienced.values().stream()
                .flatMap(l -> l.stream())
                .filter(s -> s.getMonthsSinceActive() <= 3)
                .count();
        System.out.println("Active Students = "+ count);

        /*
         * flatMap()
         * using flatMap with multi-level
         * We can either chain further
         *  e.g.  .flatMap(l -> l.values().stream().flatMap(l -> l.stream()))
         * or add flatMap as another operation
         * Work with the latter
         * And still get the same results and the below is easier to read
         *
         * flatMap allows us to use a single pipeline stream to our processing
         * instead of nesting stream pipelines as we saw earlier
         */
        System.out.println("_".repeat(50));
        var multiLevel = students.stream()
                .collect(groupingBy(
                        Student::getCountryCode,
                        groupingBy(Student::getGender)));

        count = multiLevel.values().stream()
                .flatMap(l -> l.values().stream())
                .flatMap(l -> l.stream())
                .filter(s -> s.getMonthsSinceActive() <= 3)
                .count();
        System.out.println("Active Students in multi level = "+ count);










    }
}
