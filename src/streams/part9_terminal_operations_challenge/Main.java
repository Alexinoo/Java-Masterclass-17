package streams.part9_terminal_operations_challenge;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

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
    }
}
