/**
 * Course class to represent a course with its name, grade, and credit hours
 */
public class Course {
    private int id;
    private String courseName;
    private String courseCode;
    private String grade;
    private double creditHours;
    private String teacher1Name;
    private String teacher2Name;
    
    /**
     * Default constructor for Course (for JSON deserialization)
     */
    public Course() {
        this.id = -1; // Default ID for new records
    }
    
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
        this.id = -1; // Default ID for new records
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.grade = grade.toUpperCase();
        this.creditHours = creditHours;
        this.teacher1Name = teacher1Name;
        this.teacher2Name = teacher2Name;
    }
    
    /**
     * Constructor with ID (used for database retrieval)
     */
    public Course(int id, String courseName, String courseCode, String grade, double creditHours, 
                  String teacher1Name, String teacher2Name) {
        this.id = id;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.grade = grade.toUpperCase();
        this.creditHours = creditHours;
        this.teacher1Name = teacher1Name;
        this.teacher2Name = teacher2Name;
    }
    
    /**
     * Get the course ID
     * @return Course ID
     */
    public int getId() {
        return id;
    }
    
    /**
     * Set the course ID
     * @param id Course ID
     */
    public void setId(int id) {
        this.id = id;
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
            case "A":
                return 4.0;
            case "A-":
                return 3.7;
            case "B+":
                return 3.3;
            case "B":
                return 3.0;
            case "B-":
                return 2.7;
            case "C+":
                return 2.3;
            case "C":
                return 2.0;
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
    
    /**
     * Set course name
     * @param courseName Course name
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    /**
     * Set course code
     * @param courseCode Course code
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    
    /**
     * Set grade
     * @param grade Letter grade
     */
    public void setGrade(String grade) {
        this.grade = grade.toUpperCase();
    }
    
    /**
     * Set credit hours
     * @param creditHours Credit hours
     */
    public void setCreditHours(double creditHours) {
        this.creditHours = creditHours;
    }
    
    /**
     * Set teacher 1 name
     * @param teacher1Name Teacher 1 name
     */
    public void setTeacher1Name(String teacher1Name) {
        this.teacher1Name = teacher1Name;
    }
    
    /**
     * Set teacher 2 name
     * @param teacher2Name Teacher 2 name
     */
    public void setTeacher2Name(String teacher2Name) {
        this.teacher2Name = teacher2Name;
    }
    
    @Override
    public String toString() {
        return String.format("%-25s [%s] Grade: %-3s Credits: %.1f GradePoint: %.1f Teachers: %s, %s", 
                           courseName, courseCode, grade, creditHours, getGradePoint(), 
                           teacher1Name, teacher2Name);
    }
}
