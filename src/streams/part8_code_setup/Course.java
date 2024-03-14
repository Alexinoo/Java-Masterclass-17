package streams.part8_code_setup;

public record Course(String courseCode, String title,int lectureCount) {

    /*
        Add Custom constructor with only 2 fields
            - courseCode & title
            - Default lectureCount to 40
     */
    public Course(String courseCode, String title) {
        this(courseCode, title, 40);
    }
    /*
        * Add Compact constructor to add extra validation on lectureCount
        * Set course lecture to 1 if not started to avoid division by 0 while
        calculating percent complete
     */

    public Course {
        if(lectureCount <= 0){
            lectureCount = 1;
        }
    }

    /*
        Generate toString() and select none fields
        - Return formatted string of the course code and title of the course
     */

    @Override
    public String toString() {
        return "%s %s".formatted(courseCode,title);
    }
}
