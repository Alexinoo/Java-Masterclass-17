package final_classes.part7_copying_classes;

import java.util.Arrays;

record Person (String name, String dob, Person[] kids){

    public Person(Person p) {
        this(p.name,p.dob,p.kids == null ? null:Arrays.copyOf(p.kids,p.kids.length));
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", kids=" + Arrays.toString(kids) +
                '}';
    }
}
public class Main {

    public static void main(String[] args) {
        Person joe = new Person("Joe","01/01/1961",null);

        Person jim = new Person("Jim","02/02/1962",null);

        Person jack = new Person("Jack","03/03/1963",new Person[]{joe,jim});

        Person jane = new Person("Jane","04/04/1964",null);

        Person jill = new Person("Jill","05/05/1965",new Person[]{joe,jim});

        //Create an array of these persons using array initializer
        Person[] persons = {joe,jim,jack,jane,jill};

        //Making a shallow copy
       // Person[] personsCopy = Arrays.copyOf(persons,persons.length);

        //Making a deep copy
        //Person[] personsCopy = new Person[5];


        //Using clone
        Person[] personsCopy = persons.clone();

        //Use Arrays.setALl() or for-loop works the same
        //Arrays.setAll(personsCopy,i -> new Person(persons[i]));

        for (int i = 0; i < 5; i++) {
            // Person current = persons[i];
            // var kids = current.kids() == null ? null : Arrays.copyOf(current.kids(),current.kids().length);
            // personsCopy[i] = new Person(current.name(), current.dob(), kids);

            // Using the copy constructor above
            personsCopy[i] = new Person(persons[i]);
        }

                //Get Jill's kids and change the second child for Jill to jane
        var jillsKids = personsCopy[4].kids();
        jillsKids[1] = jane;

        // to confirm that the 2 are referencing the same record
        // loop and compare whether the references are equal and not values

        for (int i = 0; i < 5; i++) {
            if(persons[i] == personsCopy[i])
                System.out.println("Equal References "+persons[i]  );
        }

        System.out.println(persons[4]);
        System.out.println(personsCopy[4]);


    }
}
