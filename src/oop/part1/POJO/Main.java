package oop.part1.POJO;

public class Main {

    public static void main(String[] args) {

        for (int i = 1; i <= 5 ; i++) {

            Student student = new Student("S92300"+i,
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
    }
}
