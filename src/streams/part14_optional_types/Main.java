package streams.part14_optional_types;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        Course pymc = new Course("PYMC","Python Masterclass");
        Course jmc = new Course("JMC","Java Masterclass");

        /* Generate 1000 random students
         * Use .collect(Collectors.toList()) since we plan to modify the list
         */

        List<Student> students = Stream
                .generate(()-> Student.getRandomStudent(pymc,jmc))
                .limit(1000)
                .collect(Collectors.toList());

        /* Calling getStudent()
         * Passing null and type "first
         * Execute isEmpty() and isPresent on the result
         * We get a NullPointerException even though we are still using Optional
         * First rule for developers who use Optional is any method that returns an optional
         * shd never return null but return an empty optional
         * Update getStudent() to return Optional.empty() instead of null
         *
         * Prints true for empty and false for present
         * Returns Optional.empty
         * Calling get() on Optional.empty() instance gives an error
         * Need to evaluate ifPresent() first and call get()
         */
        Optional<Student> o1 = getStudent(null,"first");
        System.out.println("Empty = "+ o1.isEmpty()+ ", Present = "+o1.isPresent());
        System.out.println(o1);
        //System.out.println(o1.get());

        /* Calling getStudent()
         * Passing empty ArrayList  and type "first
         * Prints true for empty and false for present
         * Returns Optional.empty
         * Calling get() on Optional.empty() instance gives an error
         * Need to evaluate ifPresent() first and call get() - o2.ifPresent(System.out::println);
         * The line above will print nothing since ifPresent returns false
         * We also have ifPresentOrElse()
         * This takes consumer function and a 2nd parameter,a Runnable called emptyAction
         * Runnable is a functional interface - a target for lambda expressions
         * Does not take any parameters or return any
         * Add a lambda that prints some text  --> Empty
         */
        System.out.println("_".repeat(50));
        Optional<Student> o2 = getStudent(new ArrayList<>(),"first");
        System.out.println("Empty = "+ o2.isEmpty()+ ", Present = "+o2.isPresent());
        System.out.println(o2);
        o2.ifPresent(System.out::println);
        o2.ifPresentOrElse(System.out::println,()-> System.out.println("--> Empty"));

        /* Calling getStudent()
         * Passing the students list
         * Prints false for empty and true for present
         * Returns an instance of the first student
         *
         * But what happens if one of our list elmnt is null
         * Let's add null to the beginning of our List  -with index - 0
         * We get NullPointerException error
         * Means we can't pass/return a null value to Optional.of()
         *
         * Solution is to use ofNullable in each case - resolves the error
         * Commenting  students.add(0, null);
         * Runs but how do we access the value - a student
         *
         * Optional type has a get()
         * Add .get on o1,o2 and o3
         * Fails on o1 and o2 since both are returning Optional.empty
         * Works on o3 by returning a Student - however need to evaluate
         * o3.ifPresent() then call o3.get()
         * intelliJ flags o3.ifPresent() and it says it can be replaced with single
         * expression functional style
         * This converts it to o3.ifPresent(System.out::println);
         * ifPresent() takes a consumer,meaning we can pass the usual println method reference
         * ifPresentOrElse() takes consumer function and a 2nd parameter,a Runnable called emptyAction
         * Runnable is a functional interface - a target for lambda expressions
         * Does not take any parameters or return any
         *
         * ifPresent() & ifPresentOrElse() can be used to retrieve value from Optional or assign
         * to another local variable like the simple get() does
         *
         * We also have isPresent() - does not take any parameters
         * Using isPresent with a ternary operator
         * Can be replaced as single expression functional style as o3.orElse(null);
         * This is a special kind of get that will get the value but if the value isn't present
         * we can specify another default value, in this case , null
         *
         * Suppose, we don't want to return a null Student as the other value but rather some
         * some dummy variable
         *
         * Changing our orElse(null) - to return instead a dummy instance - o3.orElse(getDummyStudent(pymc,jmc));
         *
         * However, If we pass method invocation as an argument, that method gets called whether you need that value or not
         * We see Getting dummy data printed yet we don't expect that call
         *
         * The solution is to use orElseGet() whose parameter is a target for lambda expression
         * Commenting orElse
         * Using orElseGet() instead which takes a supplier function which then calls getDummyStudent(pymc,jmc)
         *
         *
         * The Optional() also has methods that seems to mirror some of the stream operations
         *
         */
        System.out.println("_".repeat(50));
        //students.add(0, null);
        Optional<Student> o3 = getStudent(students,"first");
        System.out.println("Empty = "+ o3.isEmpty()+ ", Present = "+o3.isPresent());
        System.out.println(o3);
        o3.ifPresent(System.out::println);

        //Student firstStudent = o3.orElse(getDummyStudent(pymc,jmc));
        Student firstStudent = o3.orElseGet(()->getDummyStudent(pymc,jmc));
        long id = firstStudent.getStudentId();
        System.out.println("first student id is "+id);


        /*
         * The Optional() also has methods that seems to mirror some of the stream operations
         * Stream students and get unique values for country code
         * Create an Optional.of countries by passing it the list of strings
         * The Optional.of() has a map() - we can transform the values into something else
         * Like a single comma delimited string
         * The Optional().of() has a filter() too - so we can check to see if the values contain country code FR
         * Means if above is false, the value is going to be empty
         * We can then chain ifPresentOrElse to print the country code list or print "Missing FR"
         */
        System.out.println("_".repeat(50));
        List<String> countries = students.stream()
                .map(Student::getCountryCode)
                .distinct()
                .toList();

        Optional.of(countries)
                .map(l -> String.join(",",l))
                .filter(l -> l.contains("FR"))
                .ifPresentOrElse(System.out::println,()-> System.out.println("Missing FR"));
    }
     /*
      * Add a private static method
      * Returns an Optional with a type argument of Student
      * Will take a List of Students and a String for the type
      * of retrieval which can be first/last or any
      * Return null if list is null or empty
      * If type is "first" return Optional.of the 1st element
      * If type is "last" return Optional.of the last element
      * pick a random element and pass it back in an Optional container
      *
      * Update Optional.of() to Optional.ofNullable
      *
      */

    private static Optional<Student> getStudent(List<Student> list , String type){
        if(list == null || list.size() == 0){
            return Optional.empty();
        }else if(type.equals("first")){
            return Optional.ofNullable(list.get(0));
        }else if(type.equals("last")){
            return Optional.ofNullable(list.get(list.size()- 1));
        }
        return Optional.of(list.get(new Random().nextInt(list.size())));
    }

    /*
     * A private static function that returns some a dummy or fake Student instance
     * Take variable courses as variable args
     *
     */

    private static Student getDummyStudent(Course...courses){
        System.out.println("Getting the dummy student");
        return new Student("NO",1,1,"U",false,courses);

    }

}


















