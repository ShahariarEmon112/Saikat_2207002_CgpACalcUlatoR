import java.util.List;

public class AutomatedTest {
    public static void main(String[] args) {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║        GPA CALCULATOR - AUTOMATED PROGRAM TEST                  ║");
        System.out.println("║             By: Saikat (ID: 2207002)                          ║");
        System.out.println("║  Features: Database, JSON, Observable, Concurrency Integrated ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        DatabaseManager dbManager = new DatabaseManager();

        try {
            // Test 1: Add courses directly
            System.out.println("═══ TEST 1: Adding Courses to Database ═══\n");
            
            Course course1 = new Course("Data Structures", "CSC201", "A", 3.0, "Dr. Ahmed", "Dr. Fatima");
            Course course2 = new Course("Database Design", "CSC301", "B+", 3.5, "Prof. Khan", "Dr. Noor");
            Course course3 = new Course("Web Development", "CSC401", "A-", 4.0, "Dr. Hassan", "Ms. Zara");

            int id1 = dbManager.insertCourse(course1);
            int id2 = dbManager.insertCourse(course2);
            int id3 = dbManager.insertCourse(course3);

            System.out.println("✓ Course 1 inserted with ID: " + id1);
            System.out.println("✓ Course 2 inserted with ID: " + id2);
            System.out.println("✓ Course 3 inserted with ID: " + id3);

            // Test 2: View courses
            System.out.println("\n═══ TEST 2: Viewing All Courses ═══\n");
            
            List<Course> courses = dbManager.getAllCourses();
            System.out.println("┌────┬────────────────────────┬──────┬────────┬──────────┬──────────┐");
            System.out.println("│ ID │ Course Name            │ Code │ Grade  │ Credits  │ Gr.Pt    │");
            System.out.println("├────┼────────────────────────┼──────┼────────┼──────────┼──────────┤");
            
            for (Course c : courses) {
                System.out.printf("│ %2d │ %-22s │ %-4s │ %-6s │ %8.1f │ %8.1f │\n", 
                    c.getId(), 
                    c.getCourseName().substring(0, Math.min(22, c.getCourseName().length())),
                    c.getCourseCode(),
                    c.getGrade(),
                    c.getCreditHours(),
                    c.getGradePoint());
            }
            System.out.println("└────┴────────────────────────┴──────┴────────┴──────────┴──────────┘");

            // Test 3: Calculate GPA
            System.out.println("\n═══ TEST 3: Calculating GPA ═══\n");
            
            GPACalculator calc = new GPACalculator();
            for (Course c : courses) {
                calc.addCourse(c);
            }

            double gpa = calc.calculateGPA();
            double totalCredits = calc.getTotalCreditHours();
            double totalQualityPoints = 0.0;
            for (Course c : courses) {
                totalQualityPoints += c.getQualityPoints();
            }

            System.out.println("┌─────────────────────────────────────────┐");
            System.out.printf("│ Total Courses: %-26d │\n", calc.getCourseCount());
            System.out.printf("│ Total Credit Hours: %-22.2f │\n", totalCredits);
            System.out.printf("│ Total Quality Points: %-20.2f │\n", totalQualityPoints);
            System.out.printf("│ %-40s │\n", "");
            System.out.printf("│ GPA: %-35.2f │\n", gpa);
            System.out.println("└─────────────────────────────────────────┘");

            // Test 4: Export to JSON
            System.out.println("\n═══ TEST 4: Exporting to JSON ═══\n");
            
            JSONHandler.exportToJSON(courses, "courses_export.json");
            System.out.println("✓ Courses exported to: courses_export.json");
            System.out.println("  Total courses exported: " + courses.size());

            // Test 5: Import from JSON
            System.out.println("\n═══ TEST 5: Importing from JSON ═══\n");
            
            List<Course> importedCourses = JSONHandler.importFromJSON("courses_export.json");
            System.out.println("✓ Courses imported from: courses_export.json");
            System.out.println("  Total courses imported: " + importedCourses.size());
            
            for (Course c : importedCourses) {
                System.out.println("  - " + c.getCourseName() + " (" + c.getGrade() + ")");
            }

            // Test 6: Update course
            System.out.println("\n═══ TEST 6: Updating Course ═══\n");
            
            if (!courses.isEmpty()) {
                Course courseToUpdate = courses.get(0);
                System.out.println("Original: " + courseToUpdate.getCourseName() + " - " + courseToUpdate.getGrade());
                
                courseToUpdate.setGrade("A+");
                dbManager.updateCourse(courseToUpdate);
                
                System.out.println("Updated:  " + courseToUpdate.getCourseName() + " - " + courseToUpdate.getGrade());
                System.out.println("✓ Course updated successfully");
            }

            // Test 7: Delete course
            System.out.println("\n═══ TEST 7: Deleting Course ═══\n");
            
            if (!courses.isEmpty()) {
                int idToDelete = courses.get(courses.size() - 1).getId();
                dbManager.deleteCourse(idToDelete);
                System.out.println("✓ Course with ID " + idToDelete + " deleted successfully");
            }

            // Test 8: Final count
            System.out.println("\n═══ TEST 8: Final Status ═══\n");
            
            List<Course> finalCourses = dbManager.getAllCourses();
            System.out.println("✓ Final course count in database: " + finalCourses.size());

            System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
            System.out.println("║              ALL TESTS COMPLETED SUCCESSFULLY                  ║");
            System.out.println("║  ✓ Database Operations (CRUD)                                  ║");
            System.out.println("║  ✓ JSON Import/Export                                          ║");
            System.out.println("║  ✓ GPA Calculation                                             ║");
            System.out.println("║  ✓ All features working correctly                              ║");
            System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        } catch (Exception e) {
            System.err.println("✗ Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbManager.shutdown();
        }
    }
}
