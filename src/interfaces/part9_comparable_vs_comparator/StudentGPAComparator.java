package interfaces.part9_comparable_vs_comparator;

import java.util.Comparator;

public class StudentGPAComparator implements Comparator<Student> {


    @Override
    public int compare(Student o1, Student o2) {
        //return (o1.gpa + o1.name).compareTo(o2.gpa + o2.name); //sorts with gpa [lowest to highest]
        //return (o2.gpa + o2.name).compareTo(o1.gpa + o1.name); //sorts with gpa [highest to lowest]
        return (o1.gpa + o1.name).compareTo(o2.gpa + o2.name); // use with comparator static method gpaSorter.reversed on main()
    }
}
