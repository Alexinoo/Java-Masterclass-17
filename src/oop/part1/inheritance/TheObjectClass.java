package oop.part1.inheritance;

public class TheObjectClass extends Object {

    public static void main(String[] args) {

        Student student = new Student("Anthony",43);
        System.out.println(student.toString()); //works same
        System.out.println(student); //works same

        PrimarySchoolStudent nancy = new PrimarySchoolStudent("Alex",33,"Nancy");
        System.out.println(nancy);
    }
}

class Student {
    private String name;
    private int age;

    Student(String name, int age){
        this.name = name;
        this.age = age;
    }

//    @Override
//    public String toString() {
//        return super.toString();
//    }


//    @Override
//    public String toString() {
//        return "Student{" +
//                "name='" + name + '\'' +
//                ", age=" + age +
//                '}';
//    }
    public String toString(){
        return name + " is " +age;
    }

}

class PrimarySchoolStudent extends Student{

    private String parentName;

    PrimarySchoolStudent(String name, int age , String parentName){
        super(name,age);
        this.parentName = parentName;
    }

    @Override
    public String toString() {
        return parentName + "'s kid, " +super.toString();
    }
}