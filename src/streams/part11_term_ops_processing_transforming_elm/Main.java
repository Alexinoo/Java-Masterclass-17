package streams.part11_term_ops_processing_transforming_elm;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        //Create 2 courses
        Course pymc = new Course("PYMC","Python Masterclass");
        Course jmc = new Course("JMC","Java Masterclass");

        /*
            Generate 10 random students and give some activities in the course they are taking

            Stream.generate(()-> Student.getRandomStudent(jmc,pymc))
                .limit(10)
                .forEach(System.out::println);

         */



        /*
            - Generate 1000 random students and assign to a variable - Using Streams
                - use Stream.generate() and invoke static method getRandomStudent() passing 2 courses
            - Add Intermediate operation and add filter for gender

            - Count the no of male students

        var maleStudents = Stream.generate(()-> Student.getRandomStudent(jmc,pymc))
                .limit(1000);
        maleStudents = maleStudents.filter(s -> s.getGender().equals("M"));

        System.out.println("# of male students " +maleStudents.count());
        */

        /*
            Find out Female students
            - Create an array of 1000 students - initialized to null
            - Use Arrays.setAll to initialize students[] with random generated student data
            - Task 1 - Get male students
                - Use Arrays.stream() helper class
                - Chain filter() - intermediate method with the predicate condition for filtering Male studs

            - Task 2 - Query all the gender groups and return gender counts for each
                - Set up a loop to process a pipeline for each gender
                - Loop through the possible options using List.of() to create an iterable collection
                    - Create an array of stream (myStudents) and return the count that matches the current gender iteration

            - Task 3 - Group students by age , but my ages are ranges
                 - similar to iterating through the gender groups over a series of string values
                 - but instead, iterate over several predicates
                 - Means setting up a list of several lambda expression variable
                 - start by creating a list of predicate variables/conditions that will operate on Student
                    - condition1 - filters students below 30 yrs
                    - condition2 - lambda expression can also take type which sometime can help with readability
                 - Use 2 conditions because we can calculate the third without iterating through the entire set of
                   students again
         */
        Student[] students = new Student[1000];
        Arrays.setAll(students, i -> Student.getRandomStudent(jmc,pymc));

        var maleStudents = Arrays.stream(students)
                .filter(s -> s.getGender().equals("M"));
        System.out.println("# of male students " +maleStudents.count());

        System.out.println("_".repeat(50));

        for (String gender :List.of("M","F","U")) {
            var myStudents = Arrays.stream(students)
                    .filter(s -> s.getGender().equals(gender));
            System.out.println("# of "+ gender+" students " +myStudents.count());

        }




        System.out.println("_".repeat(50));
        List<Predicate<Student>> list = List.of(
                s -> s.getAge() < 30 ,
                (Student s) -> s.getAge() >= 30 && s.getAge() < 60
        );
        long total = 0;
        for (int i = 0; i < list.size(); i++) {
            var myStudents = Arrays.stream(students).filter(list.get(i));
            long cnt = myStudents.count();
            total += cnt;
            System.out.printf("# of students (%s) = %d%n",i == 0 ? " < 30" : ">= 30 & < 60",cnt);
        }

        System.out.println("# of students >= 60 " + (students.length - total));



        ///////////////// PART 2 TERMINAL OPERATIONS ///////////////////////
        /*
            Count from previous did not seem like they were evenly distributed

            - Let's use summaryStatistics terminal operations to understand why whi
              always give more info

            - Let's use the enrollment age first to figure out what age statistics
              we can get with the summaryStatistics operation

            - We can only use summaryStatistics with IntStream,DoubleStream or LongStream

            - Therefore the need to use mapToInt with Student::getAgeEnrolled which returns an int

            - Print back what we get by calling terminal operation, on summaryStatistics

            - Use the same to print stats for current Age - using Student::getAge
         */
        System.out.println("_".repeat(50));

        var ageStream = Arrays.stream(students)
                .mapToInt(Student::getAgeEnrolled);

        System.out.println("Stats for Enrollment Age = "+ ageStream.summaryStatistics());



        System.out.println("_".repeat(50));

        var currentAgeStream = Arrays.stream(students)
                .mapToInt(Student::getAge);
        System.out.println("Stats for current Age = "+ currentAgeStream.summaryStatistics());


        /*
            Task 4 -  What countries are my students from

                - Use Map with the return of getCountry code getter
                - Eliminate duplicates by using distinct intermediate operation
                - Sort (though that was optional)
        */
        System.out.println("_".repeat(50));
        Arrays.stream(students)
                .map(Student::getCountryCode)
                .distinct()
                .sorted()
                .forEach(s -> System.out.print(s + " "));

          /*
            Task 5 - Are there students that are still active that have been enrolled
               for more than 7 years

               - Decide what does recent activity means ?
               - We can say that a student is active if they've had some activity in the
                last 12 months meaning the getMonthsSinceActive <= 12
        */
        System.out.println();
        System.out.println("_".repeat(50));
        boolean longTerm = Arrays.stream(students)
                .anyMatch(s -> (s.getAge() - s.getAgeEnrolled() >= 7) &&
                            (s.getMonthsSinceActive() < 12));
        System.out.println("longTerm students? "+longTerm);

         /*
            Task 6 - Are there students that are still active that have been enrolled
               for more than 7 years (Find out how many they are using the count operation)
        */
        System.out.println("_".repeat(50));
        long longTermCount = Arrays.stream(students)
                .filter(s -> (s.getAge() - s.getAgeEnrolled() >= 7) &&
                        (s.getMonthsSinceActive() < 12))
                .count();
        System.out.println("longTerm students count =  "+longTermCount);

         /*
            Task 7 - Are there students that are still active that have been enrolled
               for more than 7 years
                - Find out how many they are using the count operation
                - Find out how many of these students who have never had any programming experience
                - limit to only 5 students
        */
        System.out.println("_".repeat(50));
        Arrays.stream(students)
                .filter(s -> (s.getAge() - s.getAgeEnrolled() >= 7) &&
                        (s.getMonthsSinceActive() < 12))
                .filter(s -> !s.hasProgrammingExperience())
                .limit(5)
                .forEach(System.out::println);

        ///////////////////////////////////////////////////////////////////////////////////////
        // TERMINAL OPS FOR PROCESSING AND TRANSFORMING STREAM ELEMENTS /////////////////

    /*
        toList()

        - Get the above 5 students into some container type
        - Inserted after the limit() operation in our last pipeline
        - This means we are converting the Stream<Student> to List<Student> via toList() operation
        - Therefore the forEach() is a method on that list and not a terminal operation on the stream
        - In this scenario, the List() method provides unmodifiable list, restricting your ability
          to perform further operations or chain additional methods
        - Instead, it's more likely you want to assign the result of your list to some variable
            - let's do this next
     */

        System.out.println("_".repeat(50));
        Arrays.stream(students)
                .filter(s -> (s.getAge() - s.getAgeEnrolled() >= 7) &&
                        (s.getMonthsSinceActive() < 12))
                .filter(s -> !s.hasProgrammingExperience())
                .limit(5)
                .toList()
                .forEach(System.out::println);

        /*
           - Instead, it's more likely you want to assign the result of your list to some variable
            - let's do this next
           - This means we have a concrete collection of elements that we could pass to other methods
             as long as they don't attempt to modify the original list

           - We can always pass this list to the constructor of a new ArrayList or LinkedList if we
             want our own modifiable list

        */

        System.out.println("_".repeat(50));
        List<Student> longTimeLearners =  Arrays.stream(students)
                .filter(s -> (s.getAge() - s.getAgeEnrolled() >= 7) &&
                        (s.getMonthsSinceActive() < 12))
                .filter(s -> !s.hasProgrammingExperience())
                .limit(5)
                .toList();

        /*
            toArray()
            ..........
           - Returns an array of objects which is basically an untyped array - might need to cast to use it
           - There is an overloaded version of this operation that's let's us specify the
             type of array we want back
                - it takes an IntFunction type, means it takes int as parameter & returns
                  an instance of something else
                - means we can use the parameter, to create a new array of Student & use that argument value
                  as the size of the array
        */
        System.out.println("_".repeat(50));
        var longTimeLearnersAsArray =  Arrays.stream(students)
                .filter(s -> (s.getAge() - s.getAgeEnrolled() >= 7) &&
                        (s.getMonthsSinceActive() < 12))
                .filter(s -> !s.hasProgrammingExperience())
                .limit(5)
                .toArray();

        /*
            toArray(IntFunction<A[]> generator)
            ..........
           - There is an overloaded version of this operation that's let's us specify the
             type of array we want back
                - it takes an IntFunction type, means it takes int as parameter & returns
                  an instance of something else
                - means we can use the parameter, to create a new array of Student & use that argument value
                  as the size of the array
           - Returns an array containing the elements of this stream, using the generator function to
              allocate the returned array,as well as any additional arrays that might be required
              for a partitioned execution or for resizing.
        */
        System.out.println("_".repeat(50));
        var longTimeLearnersAsArrayOverloaded =  Arrays.stream(students)
                .filter(s -> (s.getAge() - s.getAgeEnrolled() >= 7) &&
                        (s.getMonthsSinceActive() < 12))
                .filter(s -> !s.hasProgrammingExperience())
                .limit(5)
                .toArray(Student[]::new);

        /*
            collect(Collector<? super T,A,R> collector)
            ...........................................

           - Performs/returns a mutable reduction operation on the elements of this stream using a Collector.
           - A Collector encapsulates the functions used as arguments to collect(Supplier, BiConsumer, BiConsumer),
             allowing for reuse of collection strategies and composition of collect operations such as
              multiple-level grouping or partitioning.
           - If the stream is parallel, and the Collector is concurrent, and either the stream is unordered or the
             collector is unordered,then a concurrent reduction will be performed.

        */
        System.out.println("_".repeat(50));
        var learners =  Arrays.stream(students)
                .filter(s -> (s.getAge() - s.getAgeEnrolled() >= 7) &&
                        (s.getMonthsSinceActive() < 12))
                .filter(s -> !s.hasProgrammingExperience())
                .limit(5)
                .collect(Collectors.toList());
        Collections.shuffle(learners);

    }
}
