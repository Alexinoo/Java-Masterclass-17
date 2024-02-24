package generics_extra.generics_extra_challenge;

import generics_extra.generics_extra_challenge.util.QueryItem;

import java.util.Random;

public class Student implements QueryItem,Comparable<Student> {
    private static int LAST_ID = 10_000;
    private int studentId;
    private String name;
    private String course;
    private int yearStarted;

    //3 more fields for creating random data for students
    protected static Random random = new Random();
    private static String[] firstNames = {"Ann","Bill","Cathy","John","Tim"};
    private static String[] courses = {"C++","Java","Python"};

    public Student() {
        studentId = LAST_ID++;
        int lastNameIndex = random.nextInt(65,91); // generate int between 65-90 ; 65-A 90 - Z (91 exclusive)
        name = firstNames[random.nextInt(5)] + " "+ (char)lastNameIndex; // pick a random int from 0-4 (5 exclusive of upper bound)
        course = courses[random.nextInt(3)]; // pick a random int from 0-2  (3 exclusive of upper bound)
        yearStarted = random.nextInt(2018,2024); // pick a random year from 2018-2023  (2024 exclusive of upper bound)
    }

    @Override
    public String toString() {
        //returned string is formatted and includes name,course and year started
        //name and course to be left justified with a width of 15
        //-15 indicator for left justify and 15 allotted width
        return "%d %-15s %-15s %d".formatted(studentId,name,course,yearStarted);
    }

    public int getYearStarted() {
        return yearStarted;
    }

    @Override
    public boolean matchFieldValue(String fieldName, String value) {
        String fName = fieldName.toUpperCase();
        return switch(fName){
            case "NAME"->name.equalsIgnoreCase(value);
            case "COURSE"->course.equalsIgnoreCase(value);
            case "YEARSTARTED"->yearStarted == (Integer.parseInt(value));
            default -> false;
        };
    }

    @Override
    public int compareTo(Student o) {
        return Integer.valueOf(studentId).compareTo(o.studentId);
    }
}
