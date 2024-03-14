package streams.part12_term_ops_collect_reduce;


import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static <List> void main(String[] args) {

        //Create 2 courses
        Course pymc = new Course("PYMC","Python Masterclass");
        Course jmc = new Course("JMC","Java Masterclass");

        /*
            Generate random students

            - Instead of setting an Array and populating it with randomly generated students as done previously
            - Let's stream students with generate method into a list using toList()
            - We don't want to modify any first collection of students once we have them -
              so getting back unmodifiable list is fine
            - Returns a Lis of students in order that they were created
         */
        java.util.List<Student> students =
                Stream.generate(()->Student.getRandomStudent(jmc,pymc))
                        .limit(1000)
                        .toList();

         /*
             Get a set of AU students

             - Create a Set of type Student
             - Start by getting a stream from students list
             - Filter with country code "AU"
             - Then collect to a set using Collectors.toSet()
             - And print out the no of studs in my Set
             - similar to toList() but returns a HashSet
         */
        Set<Student> australianStudents = students.stream()
                .filter(s -> s.getCountryCode().equals("AU"))
                .collect(Collectors.toSet());
        System.out.println("# of Australian Students = "+australianStudents.size());

         /*  Get a set of AU students who enrolled when they were under 30 */
        Set<Student> underThirty = students.stream()
                .filter(s -> s.getAgeEnrolled() < 30)
                .collect(Collectors.toSet());
        System.out.println("# of Under 30 Students = "+underThirty.size());

        /*  Get a set of AU students who enrolled when they were under 30
        *   Find out also how many are Australian
        *   We want it to be a TreeSet order by a comparator via studentId
        *   Then add all australianStudents to this set
        *   And retain all students under 30
        *   Remember that this is really an intersect of the 2 sects returning only the students in both sets
        *   Then print each student id in a single line
        *
        */
        Set<Student> youngAussies1 = new TreeSet<>(Comparator.comparing(Student::getStudentId));
        youngAussies1.addAll(australianStudents);
        youngAussies1.retainAll(underThirty);
        youngAussies1.forEach(s -> System.out.print(s.getStudentId() + " "));
        System.out.println();


        /*  Get a set of AU students who enrolled when they were under 30 - Using a Pipeline
         *  Find out also how many are Australian
         *  Use filter with country code
         *  Use filter with getAge enrolled
         *  Returns the same set of student id's and the bad news is that they are not ordered
         *  However, if we add sorted() - we get an intelliJ hint that this is redundant
         *  This means we can either pass this to a TreeSet or a HashSet constructor later
         *  Or we use the overloaded version of collect
         *
         */
        Set<Student> youngAussies2 = students.stream()
                .filter(s -> s.getAgeEnrolled() < 30)
                .filter(s -> s.getCountryCode().equals("AU"))
                .collect(Collectors.toSet());
        youngAussies2.forEach(s -> System.out.print(s.getStudentId() + " "));
        System.out.println();

        /*  Get a set of AU students who enrolled when they were under 30 - Using a Pipeline
         *  Find out also how many are Australian
         *  Using the overloaded version of collect to sort out this
         *  There are 4 params where 3 are either method references or lambda expressions
         *  These are the 3 we would use if we were to do something similar
         *  The 1st method instantiates the HashSet - HashSet:: new - Means the Supplier
         *  The 2nd method adds 1 element at a time to the HashSet - Set::add - is the Accumulator
         *  The 3rd is a lambda expression but it's calling addAll on the set adding 1 collection
            to another collection - That's the Combiner
         *  And that's it, we've set ofr first collection with a Supplier, Accumulator and Combiner
         *  Notice that the sorted() - isn't grayed anymore but it is not needed either so, let's remove it
         *  However, we get an error since the Student does not implement Comparable - i.e the reason
            we had to set the youngAussies1 with my our own Comparator on the constructor
         *  Let's do the same thing here.. but how do we do it..?
         *  Therefore , we need to change the 1st arg from Tree::new to use lambda expression and pass the comparator to the constructor
         *  Running this now works and we get the same results from both sets
         */
        Set<Student> youngAussiesOverloaded = students.stream()
                .filter(s -> s.getAgeEnrolled() < 30)
                .filter(s -> s.getCountryCode().equals("AU"))
                .collect(()-> new TreeSet<>(Comparator.comparing(Student::getStudentId)),TreeSet::add,TreeSet::addAll);
        youngAussiesOverloaded.forEach(s -> System.out.print(s.getStudentId() + " "));
        System.out.println();

         /*  Summary
             ........
          *  The collect() has 2 overloaded versions
                R collect(Collector collector)
                R collect(Supplier supplier, BiConsumer accumulator,BiConsumer combiner)

          *  R collect(Collector collector)
               - can be used by passing the result of any of the many factory methods on the Collectors class
               - i.e asList() and asSet() as 2 examples of static methods of that class

          *  R collect(Supplier supplier, BiConsumer accumulator,BiConsumer combiner)
               - more complex but gives the flexibility

         */

        /* The Reduce() method
         * ...................
         * The reduce() is different from collect, because you are not accumulating elements into a container
         * Instead, you are accumulating elements into a single type
         * Example below
         * Set up a String of country list and start a stream pipeline on students
         * Map the country using getCountry code and get distinct elements only
         * sorts
         * use reduce
         * Prints as a single concatenated string
         */

        String countryList = students.stream()
                .map(Student::getCountryCode)
                .distinct()
                .sorted()
                .reduce("",(r,v) -> r +" "+ v);
        System.out.println("CountryList = "+countryList);

    }
}
