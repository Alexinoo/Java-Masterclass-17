package abstraction.interfaces.part8_comparable;

public class Student implements Comparable {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Object o) {
        Student other = (Student)o; // Cast method parameter o to a Student type and assign it to a Student variable called other
        return name.compareTo(other.name);
    }
}
