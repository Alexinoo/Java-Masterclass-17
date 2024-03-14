package streams.part8_code_setup;

import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        //Create 2 courses
        Course pymc = new Course("PYMC","Python Masterclass");
        Course jmc = new Course("JMC","Java Masterclass");

         /*
          Add a Student
        Student tim = new Student("AU",2019,30,"M",
                true,jmc,pymc);
        //System.out.println(tim);

        Call Watch lecture on Tim
                - Course - JMC      - PYMC
                - LectureNo - 10    - 7
                - Month - 5 (May)   - 7
                - Year - 2019       - 2020
           Print Tim instance again

        tim.watchLecture("JMC",10,5,2019);
        tim.watchLecture("PYMC",7,7,2020);
        System.out.println(tim);
        */

        /*
            Use stream to generate as many random students as we would want
            - generate takes a Supplier with no params and we can then invoke Student.getRandomStudent()
              and pass jmc,pymc which are effectively final
            - limit to 10 random students
            - Then print each student to see how each student looks
            - Comment out above
         */
        Stream.generate(()->Student.getRandomStudent(jmc,pymc))
                .limit(10)
                .forEach(System.out::println);

    }
}
