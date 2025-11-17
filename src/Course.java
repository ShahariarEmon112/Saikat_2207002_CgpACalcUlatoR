/**
 * Course class to represent a course with its name, grade, and credit hours
 */
public class Course {
    private String courseName;
    private String courseCode;
    private String grade;
    private double creditHours;
    private String teacher1Name;
    private String teacher2Name;
    
    /**
     * Constructor to initialize a course
     * @param courseName Name of the course
     * @param courseCode Course code
     * @param grade Letter grade (A+, A, A-, B+, B, B-, C+, C, C-, D+, D, F)
     * @param creditHours Credit hours for the course
     * @param teacher1Name First teacher's name
     * @param teacher2Name Second teacher's name
     */
    public Course(String courseName, String courseCode, String grade, double creditHours, 
                  String teacher1Name, String teacher2Name) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.grade = grade.toUpperCase();
        this.creditHours = creditHours;
        this.teacher1Name = teacher1Name;
        this.teacher2Name = teacher2Name;
    }
    
    /**
     * Get the course name
     * @return Course name
     */
    public String getCourseName() {
        return courseName;
    }
    
    /**
     * Get the course code
     * @return Course code
     */
    public String getCourseCode() {
        return courseCode;
    }
    
    /**
     * Get the letter grade
     * @return Letter grade
     */
    public String getGrade() {
        return grade;
    }
    
    /**
     * Get the credit hours
     * @return Credit hours
     */
    public double getCreditHours() {
        return creditHours;
    }
    
    /**
     * Get teacher 1 name
     * @return Teacher 1 name
     */
    public String getTeacher1Name() {
        return teacher1Name;
    }
    
    /**
     * Get teacher 2 name
     * @return Teacher 2 name
     */
    public String getTeacher2Name() {
        return teacher2Name;
    }
    
    /**
     * Get the grade point based on the letter grade
     * @return Grade point value (0.0 - 4.0)
     */
    public double getGradePoint() {
        switch (grade) {
            case "A+":
                return 4.0;
            case "A":
                return 3.75;
            case "A-":
                return 3.5;
            case "B+":
                return 3.25;
            case "B":
                return 3.0;
            case "B-":
                return 2.75;
            case "C+":
                return 2.50;
            case "C":
                return 2.25;
            case "C-":
                return 1.7;
            case "D+":
                return 1.3;
            case "D":
                return 1.0;
            case "F":
                return 0.0;
            default:
                return -1.0; // Invalid grade
        }
    }
    
    /**
     * Get the quality points (grade point * credit hours)
     * @return Quality points
     */
    public double getQualityPoints() {
        return getGradePoint() * creditHours;
    }
    
    @Override
    public String toString() {
        return String.format("%-25s [%s] Grade: %-3s Credits: %.1f GradePoint: %.1f Teachers: %s, %s", 
                           courseName, courseCode, grade, creditHours, getGradePoint(), 
                           teacher1Name, teacher2Name);
    }
}
