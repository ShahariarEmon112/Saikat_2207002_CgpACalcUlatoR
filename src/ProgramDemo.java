import java.util.List;
import java.util.Scanner;

public class ProgramDemo {
    private DatabaseManager dbManager;

    public ProgramDemo() {
        this.dbManager = new DatabaseManager();
    }

    public static void main(String[] args) {
        ProgramDemo demo = new ProgramDemo();
        demo.runDemo();
    }

    private void runDemo() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║        GPA CALCULATOR - INTERACTIVE PROGRAM DEMO               ║");
        System.out.println("║             By: Saikat (ID: 2207002)                          ║");
        System.out.println("║  Features: Database, JSON, Observable, Concurrency Integrated ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        while (running) {
            displayMenu();
            System.out.print("Select option (1-8): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addCourse(scanner);
                    break;
                case "2":
                    viewAllCourses();
                    break;
                case "3":
                    calculateGPA();
                    break;
                case "4":
                    exportToJSON();
                    break;
                case "5":
                    importFromJSON();
                    break;
                case "6":
                    updateCourse(scanner);
                    break;
                case "7":
                    deleteCourse(scanner);
                    break;
                case "8":
                    System.out.println("\n✓ Shutting down. Thank you for using GPA Calculator!");
                    running = false;
                    break;
                default:
                    System.out.println("✗ Invalid option. Please try again.\n");
            }
        }

        scanner.close();
        dbManager.shutdown();
    }

    private void displayMenu() {
        System.out.println("\n┌─────────────────────────────────────────────────┐");
        System.out.println("│  MENU - Select an operation:                    │");
        System.out.println("├─────────────────────────────────────────────────┤");
        System.out.println("│ 1. Add a new course                             │");
        System.out.println("│ 2. View all courses (from Database)             │");
        System.out.println("│ 3. Calculate GPA                                │");
        System.out.println("│ 4. Export courses to JSON                       │");
        System.out.println("│ 5. Import courses from JSON                     │");
        System.out.println("│ 6. Update a course                              │");
        System.out.println("│ 7. Delete a course                              │");
        System.out.println("│ 8. Exit                                         │");
        System.out.println("└─────────────────────────────────────────────────┘");
    }

    private void addCourse(Scanner scanner) {
        System.out.println("\n═══ ADD NEW COURSE ═══");
        
        System.out.print("Course Name: ");
        String courseName = scanner.nextLine().trim();
        
        System.out.print("Course Code: ");
        String courseCode = scanner.nextLine().trim();
        
        System.out.print("Credit Hours: ");
        double creditHours = Double.parseDouble(scanner.nextLine().trim());
        
        System.out.print("Grade (A/A-/B+/B/B-/C+/C/D): ");
        String grade = scanner.nextLine().trim().toUpperCase();
        
        System.out.print("Teacher 1 Name: ");
        String teacher1 = scanner.nextLine().trim();
        
        System.out.print("Teacher 2 Name: ");
        String teacher2 = scanner.nextLine().trim();

        Course course = new Course(courseName, courseCode, grade, creditHours, teacher1, teacher2);
        
        try {
            int id = dbManager.insertCourse(course);
            System.out.println("✓ Course added successfully with ID: " + id);
        } catch (Exception e) {
            System.out.println("✗ Error adding course: " + e.getMessage());
        }
    }

    private void viewAllCourses() {
        System.out.println("\n═══ ALL COURSES (FROM DATABASE) ═══");
        try {
            List<Course> courses = dbManager.getAllCourses();
            
            if (courses.isEmpty()) {
                System.out.println("No courses found in database.");
                return;
            }

            System.out.println("\n┌────┬────────────────────────┬──────┬────────┬──────────┐");
            System.out.println("│ ID │ Course Name            │ Code │ Grade  │ Credits  │");
            System.out.println("├────┼────────────────────────┼──────┼────────┼──────────┤");
            
            for (Course course : courses) {
                System.out.printf("│ %2d │ %-22s │ %-4s │ %-6s │ %8.1f │\n", 
                    course.getId(), 
                    course.getCourseName().substring(0, Math.min(22, course.getCourseName().length())),
                    course.getCourseCode(),
                    course.getGrade(),
                    course.getCreditHours());
            }
            System.out.println("└────┴────────────────────────┴──────┴────────┴──────────┘");
            System.out.println("\nTotal Courses: " + courses.size());
        } catch (Exception e) {
            System.out.println("✗ Error retrieving courses: " + e.getMessage());
        }
    }

    private void calculateGPA() {
        System.out.println("\n═══ GPA CALCULATION ═══");
        try {
            List<Course> courses = dbManager.getAllCourses();
            
            if (courses.isEmpty()) {
                System.out.println("No courses found. Please add courses first.");
                return;
            }

            // Create a fresh calculator with loaded courses
            GPACalculator localCalc = new GPACalculator();
            for (Course course : courses) {
                localCalc.addCourse(course);
            }

            double gpa = localCalc.calculateGPA();
            double totalCredits = localCalc.getTotalCreditHours();
            double totalQualityPoints = 0.0;
            for (Course c : localCalc.getCourses()) {
                totalQualityPoints += c.getQualityPoints();
            }

            System.out.println("\n┌─────────────────────────────────────────┐");
            System.out.printf("│ Total Courses: %-26d │\n", localCalc.getCourseCount());
            System.out.printf("│ Total Credit Hours: %-22.2f │\n", totalCredits);
            System.out.printf("│ Total Quality Points: %-20.2f │\n", totalQualityPoints);
            System.out.printf("│ %-40s │\n", "");
            System.out.printf("│ GPA: %-35.2f │\n", gpa);
            System.out.println("└─────────────────────────────────────────┘");
        } catch (Exception e) {
            System.out.println("✗ Error calculating GPA: " + e.getMessage());
        }
    }

    private void exportToJSON() {
        System.out.println("\n═══ EXPORT TO JSON ═══");
        try {
            List<Course> courses = dbManager.getAllCourses();
            JSONHandler.exportToJSON(courses, "courses_export.json");
            System.out.println("✓ Courses exported to: courses_export.json");
            System.out.println("  Total courses exported: " + courses.size());
        } catch (Exception e) {
            System.out.println("✗ Error exporting to JSON: " + e.getMessage());
        }
    }

    private void importFromJSON() {
        System.out.println("\n═══ IMPORT FROM JSON ═══");
        System.out.print("Enter JSON file path (default: courses_export.json): ");
        String filePath = "courses_export.json";
        
        try {
            List<Course> courses = JSONHandler.importFromJSON(filePath);
            System.out.println("✓ Courses imported from: " + filePath);
            System.out.println("  Total courses imported: " + courses.size());
            
            for (Course course : courses) {
                System.out.println("  - " + course.getCourseName() + " (" + course.getGrade() + ")");
            }
        } catch (Exception e) {
            System.out.println("✗ Error importing from JSON: " + e.getMessage());
        }
    }

    private void updateCourse(Scanner scanner) {
        System.out.println("\n═══ UPDATE COURSE ═══");
        System.out.print("Enter Course ID to update: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        
        try {
            List<Course> courses = dbManager.getAllCourses();
            Course courseToUpdate = null;
            
            for (Course c : courses) {
                if (c.getId() == id) {
                    courseToUpdate = c;
                    break;
                }
            }

            if (courseToUpdate == null) {
                System.out.println("✗ Course with ID " + id + " not found.");
                return;
            }

            System.out.println("Current Course: " + courseToUpdate.getCourseName() + " (" + courseToUpdate.getGrade() + ")");
            System.out.print("New Grade (leave empty to keep current): ");
            String newGrade = scanner.nextLine().trim();
            
            if (!newGrade.isEmpty()) {
                courseToUpdate.setGrade(newGrade);
            }

            dbManager.updateCourse(courseToUpdate);
            System.out.println("✓ Course updated successfully.");
        } catch (Exception e) {
            System.out.println("✗ Error updating course: " + e.getMessage());
        }
    }

    private void deleteCourse(Scanner scanner) {
        System.out.println("\n═══ DELETE COURSE ═══");
        System.out.print("Enter Course ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        
        try {
            dbManager.deleteCourse(id);
            System.out.println("✓ Course with ID " + id + " deleted successfully.");
        } catch (Exception e) {
            System.out.println("✗ Error deleting course: " + e.getMessage());
        }
    }
}
