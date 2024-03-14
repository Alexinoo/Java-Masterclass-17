package streams.part8_code_setup;

import java.time.LocalDate;
import java.time.Period;

/* Mutable to some degree but still uses encapsulation to control the mutations*/
public class CourseEngagement {

    // Course that this activity engagement pertains to
    // Will get set on construction only
    private final Course course;

    // Enrollment date which is also final of type LocalDate
    private final LocalDate enrollmentDate;

    //starts from enrollment then track progress
    private String engagementType;

    //Keeps track of the last lecture the student watched
    private int lastLecture;

    // tracks the last activity date
    private LocalDate lastActivityDate;


    /* Constructor with 3 first fields

        - Select 3 fields - course,enrollmentDate & engagement type
        - Initialize/Set lastActivityDate to date enrolled to start with
        - Added in an assignment chain
            this.enrollmentDate = this.lastActivityDate = enrollmentDate;
     */

    public CourseEngagement(Course course, LocalDate enrollmentDate,
                            String engagementType) {
        this.course = course;
        this.enrollmentDate = this.lastActivityDate = enrollmentDate;
        this.engagementType = engagementType;
    }

    /*
        - Generate all getters for all the fields
            i.e course, enrollmentDate, engagementType,lastLecture,lastActivityDate
        - Update getCourse to getCourseCode
            return just theCourseCode - rather than the whole course

        - Update to getEnrollmentDate to getEnrollmentYear
            return a year as int rather than the LocalDate

        - Update to getLastActivityDate to getLastActivityYear
            return a year as int rather than the LocalDate
     */

    public String getCourseCode() {
        return course.courseCode();
    }

    public int getEnrollmentYear() {
        return enrollmentDate.getYear();
    }

    public String getEngagementType() {
        return engagementType;
    }

    public int getLastLecture() {
        return lastLecture;
    }

    public int getLastActivityYear() {
        return lastActivityDate.getYear();
    }

    /*
        Add Custom Getters

        1. getLastActivityMonth
            - Returns a String which will be the month abbreviation
            - Use a time specifier %t for time
            - %tb is the way to get the 3 character month abbreviation

        2. getPercentComplete
            - Returns a double
            - Divide last lecture by total lecture and multiply by 100
            - Either cast to double - or add 100 as 100.0

        3. getMonthsSinceActive
            - returns an int of how many months has elapsed since the
              student last had an activity for this course
            - Hint, get the number of months btwn 2 dates
                - get current date
                - Use class Period introduced in JDK8 witha static method on it called beteewn
                - between(date,date) - Takes 2 dates
                - Returns a period instance which you can query in diff ways chained with toTotalMonths()
                 to get the number
                - Returns a long which we can cast to an int

       4. watchLecture
            - Make it package private - so that it can only be called through the Student's instance
            - Takes a lectureNumber and currentDate
            - Uss Math.max() - that returns highest of the 2 numbers passed
            - Set LastActivityDate to the date passed
            - Engagement type will reflect the max lecture the student watched
     */

    public String getLastActivityMonth(){
        return "%tb".formatted(lastActivityDate);
    }

    public double getPercentComplete(){
        return (lastLecture * 100.0) / course.lectureCount();
    }

    public int getMonthsSinceActive(){
        LocalDate now = LocalDate.now();
        var months = Period.between(lastActivityDate,now).toTotalMonths();
        return (int)months;
    }

    void watchLecture(int lectureNumber , LocalDate currentDate){
        lastLecture = Math.max(lectureNumber, lastLecture);
        lastActivityDate = currentDate;
        engagementType = "Lecture "+lastLecture;
    }

    /*
        toString()
            - Generate with no fields
            - return a formatted string that prints out the
                courseCode,lastActivity month, lastActivity year,engagementType
                & and the number of elapsed months since last activity
     */

    @Override
    public String toString() {
        return "%s: %s %d %s [%d]".formatted(getCourseCode(),
                getLastActivityMonth(),
                getLastActivityYear(),
                getEngagementType(),
                getMonthsSinceActive());
    }
}
