package streams.part11_term_ops_processing_transforming_elm;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Student {

    //Static long to help with student id assignments
    private static long lastStudentId = 1;

    //final static Random instance used for randomization - when creating random students
    private final static Random random = new Random();

    /*
        instance fields - private and final
        - student id of type long - final
        - country code of type String - final
        - yearEnrolled of type int - final
        - ageEnrolled of type int - age of the student when they enrolled
        - gender of type String -
        - programming experience for a student - type boolean

        - A Map of course engagement records keyed by the Course Code
            - instantiate as a HashMap- does not need to be ordered
     */
    private final long studentId;
    private final String countryCode;
    private final int yearEnrolled;
    private final int ageEnrolled;
    private final String gender;
    private final boolean programmingExperience;

    private final Map<String, CourseEngagement> engagementMap = new HashMap<>();

    /*
        Constructor
        ...........

        - Generate a constructor with all the fields except lastStudentId
        - Add a variable argument for courses to the constructor
            - so that it's easier to set up a new student with 1 or more courses
        - Initialize studentId to lastStudent id by incrementing that static field after assignment occurs
        - Since Courses is a variable argument
            - use enhanced for loop and call addCourse(course , date)
                - add the course
                - add the date enrolled as the first month / first date of the year they enrolled
                    i.e. if they enrolled in 2024 - set date as 01/01/2024
     */

    public Student(String countryCode, int yearEnrolled, int ageEnrolled,
                   String gender, boolean programmingExperience,
                   Course...courses) {
        studentId = lastStudentId++;
        this.countryCode = countryCode;
        this.yearEnrolled = yearEnrolled;
        this.ageEnrolled = ageEnrolled;
        this.gender = gender;
        this.programmingExperience = programmingExperience;
        for (Course course: courses ) {
            addCourse(course, LocalDate.of(yearEnrolled,1,1));
        }
    }
    /*
        Add an overloaded version of addCourse which does not take any date
        - Pass the current date to the overloaded version with 2 params
     */

    public void addCourse(Course newCourse){
        addCourse(newCourse,LocalDate.now());
    }

    /*
        Define addCourse(course, dateEnrolled)
        - Takes in newCourse of type Course and enrolledDate of type LocalDate
        - Adds to engagementMap with courseCode as Key & map value as engagement instance that
          takes a course , enrollDate, engagementType which will be Enrolled to start
     */
    public void addCourse(Course newCourse, LocalDate enrollDate){
            engagementMap.put(newCourse.courseCode(),
                    new CourseEngagement(newCourse,enrollDate,"Enrollment"));
    }

    /*
        Generate bunch of getters all public -
         - This is the data we will be using for many of the stream code samples coming up
         - Generate getters for all fields except static field - lastStudentId
         - Update getter isProgrammingExperience to hasProgrammingExperience
         - Update getEngagementMap and returns a defensive copy using Map.copyOf()
     */
    public long getStudentId() {
        return studentId;
    }
    public String getCountryCode() {
        return countryCode;
    }
    public int getYearEnrolled() {
        return yearEnrolled;
    }
    public int getAgeEnrolled() {
        return ageEnrolled;
    }
    public String getGender() {
        return gender;
    }
    public boolean hasProgrammingExperience() {
        return programmingExperience;
    }
    public Map<String, CourseEngagement> getEngagementMap() {
        return Map.copyOf(engagementMap);
    }

    /*
        Add Custom Getters for some calculated fields that will be useful

        1. getYearSinceEnrolled
            - Returns the number of years between the current year and the year enrolled
        2. getAge
            - Returns ageEnrolled + the result of calling getYearSinceEnrolled()
        3. getMonthsSinceActive
            - Take 1 param
            - Gives the elapsed months since the last activity as int for the specified courseCode
            - Get the info for that using .get(courseCode) from the engagementMap
            - use a ternary operator to avoid a NullPointerException
                - return 0 months if no such course was found
                - otherwise, call  info.getMonthsSinceActive() from CourseEngagement class
        4. getMonthsSinceActive
            - overloaded version of above that gets overall months since active
            - returns an int
            - inactiveMonths - get diff in years between 2024 and 2014 and convert that to months
            - Any activity for a student should be less than inactiveMonths
            - loop through each key for engagementMap for each course code
            - use Max.min() - returns less of the 2 no's
            - Means we will get back the months elapsed for the most recent activity

        5. getPercentComplete
            - Shares the same name with the one added in CourseEngagement class - with no params
            - This one takes course code as param
     */
    public int getYearSinceEnrolled(){
        return LocalDate.now().getYear() - getYearEnrolled();
    }

    public int getAge(){
        return getAgeEnrolled() + getYearSinceEnrolled();
    }

    public int getMonthsSinceActive(String courseCode){
        CourseEngagement info = engagementMap.get(courseCode);
        return (info == null) ? 0: info.getMonthsSinceActive();
    }

    public int getMonthsSinceActive(){
        int inactiveMonths = (LocalDate.now().getYear() - 2014) * 12;
        for (String key: engagementMap.keySet() ) {
            inactiveMonths = Math.min(inactiveMonths,getMonthsSinceActive(key));
        }
        return inactiveMonths;
    }

    public double getPercentComplete(String courseCode){
        CourseEngagement info = engagementMap.get(courseCode);
        return (info == null) ? 0 : info.getPercentComplete();
    }

    /*
        watchLecture()
            - takes a course code, lecture number, month and a year for the date
            - is public because you can imagine a course management program calling this code
              when a student logs into a course and listens to a lecture
            - passing month and year as int since they will be randomly generated
            - get the activity record from the engagementMap for the specified course code
                - if found - call watchLecture on CourseEngagement class
                            passing lectureNumber
                            and date - construct to be first day of the first month of the year enrolled
     */
    public void watchLecture(String courseCode,int lectureNumber,int month,int year){
        CourseEngagement activity = engagementMap.get(courseCode);
        if (activity != null)
            activity.watchLecture(lectureNumber,LocalDate.of(year,month,1));
    }

    /*  getRandomVal() -
        - Helper method that generates a random item from an array of strings
        - Takes variable argument of strings
        - return an element from the array using the
          index generated randomly up to the upper bound of the array's length
    */
    public static String getRandomVal(String...data){
        return data[random.nextInt(data.length)];
    }

    /* getRandomStudent()
        - Will be public and static
        - generates and return new instance of student populated with a lot of random data
        - Then create some random course activity by looping through the courses passed
            - get a random lecture number i.e. 1 - 40
            - get a random year - not less than year enrolled
            - get a random month
                - check if year we got is the current year
                       - then month shd not be greater than the current month
                       - set to current month if that is the case

        - Then execute watchLecture and pass course code,lecture,year and month
            - will update the engagement record for a particular course with some activity other than enrollment
    */
    public static Student getRandomStudent(Course...courses){
        int maxYear = LocalDate.now().getYear() + 1;
        Student student = new Student(
                getRandomVal("AU","CA","CN","GB","IN","UA","US"), //random country code
                random.nextInt(2015,maxYear), // year between 2015 - 2024 (exclusive of 2025)
                random.nextInt(18,90), // aged between  18-89
                getRandomVal("M","F","U"), //gender M, F, unselected
                random.nextBoolean(), // programming experience
                courses);

        for (Course c:courses ) {
            int lecture = random.nextInt(1, c.lectureCount());
            int year = random.nextInt(student.getYearEnrolled() , maxYear);
            int month = random.nextInt(1,13);
            if(year == (maxYear - 1)){
                if( month > LocalDate.now().getMonthValue())
                    month = LocalDate.now().getMonthValue();
            }
            student.watchLecture(c.courseCode(),lecture,month,year);
        }

        return student;
    }

    /* toString() - generate toString() and select all the fields */

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", countryCode='" + countryCode + '\'' +
                ", yearEnrolled=" + yearEnrolled +
                ", ageEnrolled=" + ageEnrolled +
                ", gender='" + gender + '\'' +
                ", programmingExperience=" + programmingExperience +
                ", engagementMap=" + engagementMap +
                '}';
    }



}
