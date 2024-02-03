package oop.part1.POJO_record;

public class Main {

    public static void main(String[] args) {

        for (int i = 1; i <= 5 ; i++) {

            LPAStudent student = new LPAStudent("S92300"+i,
                    switch(i){
                        case 1 -> "Mary";
                        case 2 -> "Carol";
                        case 3 -> "Alex";
                        case 4 -> "Lisa";
                        case 5 -> "Bob";
                        default -> "Anonymous";
                    },"05/11/1985","Java MasterClass");

            System.out.println(student.toString());

        }
        Student pojoStudent = new Student("S923006","Ann","05/11/1985","Java MasterClass");
        LPAStudent recordStudent = new LPAStudent("S923007","Bill","05/11/1985","Java MasterClass");

        System.out.println(pojoStudent);
        System.out.println(recordStudent);

        pojoStudent.setClassList(pojoStudent.getClassList() + ", Java OCP Exam 829");
//        recordStudent.setClassList(recordStudent.classList() + "Java OCP Exam 829");

        System.out.println(pojoStudent.getName() + " is taking "+pojoStudent.getClassList());
        System.out.println(recordStudent.name() + " is taking "+recordStudent.classList());
    }
}
