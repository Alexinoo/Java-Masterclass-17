package files_input_output.part16_writing_file_challenge.student;

import java.util.StringJoiner;

public record Course(String courseCode, String title) {

    public int getLectureCount() {
        return 15;
    }

    @Override
    public String toString() {
        return "%s %s".formatted(courseCode, title);
    }

    public String toJSON() {
        return new StringJoiner(", ", "{", "}")
                .add("\"courseCode\":\"" + courseCode + "\"")
                .add("\"title\":\"" + title + "\"")
                .toString();
    }
}
