package interfaces.part9_comparable_vs_comparator;

import java.util.Random;

public class Student implements Comparable<Student> {

    String name;

    /// ADDED instance fields///
    private int id;
    protected double gpa; //Grade Point Average - how well a student is doing overall

    /// ADDED Static fields///
    private static int LAST_ID =1000;
    private static Random random = new Random();

    public Student(String name) {
        this.name = name;
        id = LAST_ID++; //static - only one copy in memory
        gpa = random.nextDouble(1.0,4.0);
    }

    @Override
    public String toString() {

        //return name;
        return "%d - %s (%.2f)".formatted(id,name,gpa); //prints id, name & gpa in 2dp
    }

    @Override
    public int compareTo(Student o) {
        // return name.compareTo(o.name);
        return Integer.valueOf(id).compareTo(o.id);
    }

    //    @Override
//    public int compareTo(Object o) {
//        Student other = (Student)o; // Cast method parameter o to a Student type and assign it to a Student variable called other
//        return name.compareTo(other.name);
//    }
}
