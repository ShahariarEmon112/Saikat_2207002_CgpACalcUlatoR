import java.util.ArrayList;
import java.util.List;

/**
 * GPACalculator class to manage and calculate GPA from courses
 */
public class GPACalculator {
    private List<Course> courses;
    
    /**
     * Constructor to initialize the calculator
     */
    public GPACalculator() {
        this.courses = new ArrayList<>();
    }
    
    /**
     * Add a course to the calculator
     * @param course Course to add
     * @return true if added successfully, false otherwise
     */
    public boolean addCourse(Course course) {
        if (course.getGradePoint() >= 0) {
            courses.add(course);
            return true;
        }
        return false;
    }
    
    /**
     * Add a course by providing details
     * @param courseName Name of the course
     * @param courseCode Course code
     * @param grade Letter grade
     * @param creditHours Credit hours
     * @param teacher1Name First teacher name
     * @param teacher2Name Second teacher name
     */
    public void addCourse(String courseName, String courseCode, String grade, double creditHours,
                         String teacher1Name, String teacher2Name) {
        Course course = new Course(courseName, courseCode, grade, creditHours, teacher1Name, teacher2Name);
        addCourse(course);
    }
    
    /**
     * Calculate the GPA
     * @return GPA value
     */
    public double calculateGPA() {
        if (courses.isEmpty()) {
            return 0.0;
        }
        
        double totalQualityPoints = 0.0;
        double totalCreditHours = 0.0;
        
        for (Course course : courses) {
            totalQualityPoints += course.getQualityPoints();
            totalCreditHours += course.getCreditHours();
        }
        
        if (totalCreditHours == 0) {
            return 0.0;
        }
        
        return totalQualityPoints / totalCreditHours;
    }
    
    /**
     * Get total credit hours
     * @return Total credit hours
     */
    public double getTotalCreditHours() {
        double total = 0.0;
        for (Course course : courses) {
            total += course.getCreditHours();
        }
        return total;
    }
    
    /**
     * Get all courses
     * @return List of courses
     */
    public List<Course> getCourses() {
        return new ArrayList<>(courses);
    }
    
    /**
     * Clear all courses
     */
    public void clearCourses() {
        courses.clear();
    }
    
    /**
     * Get number of courses
     * @return Number of courses
     */
    public int getCourseCount() {
        return courses.size();
    }
    
    /**
     * Remove a course by index
     * @param index Index of the course to remove (0-based)
     * @return true if removed successfully, false otherwise
     */
    public boolean removeCourse(int index) {
        if (index >= 0 && index < courses.size()) {
            courses.remove(index);
            return true;
        }
        return false;
    }
}
