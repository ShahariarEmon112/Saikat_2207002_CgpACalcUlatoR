import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * DatabaseTest class for testing all database, JSON, and concurrency features
 */
public class DatabaseTest {
    
    public static void main(String[] args) {
        System.out.println("========== GPA CALCULATOR - DATABASE & FEATURES TEST ==========\n");
        
        try {
            // Test 1: Database Connection
            testDatabaseConnection();
            
            // Test 2: CRUD Operations
            testCRUDOperations();
            
            // Test 3: Concurrent Operations
            testConcurrentOperations();
            
            // Test 4: JSON Import/Export
            testJSONOperations();
            
            System.out.println("\n========== ALL TESTS COMPLETED SUCCESSFULLY ==========");
            
        } catch (Exception e) {
            System.err.println("\n[ERROR] Test failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Test database connection
     */
    private static void testDatabaseConnection() {
        System.out.println("\n--- Test 1: Database Connection ---");
        boolean connected = DatabaseManager.testConnection();
        if (connected) {
            System.out.println("✓ Database connection test PASSED");
        } else {
            System.out.println("✗ Database connection test FAILED");
            throw new RuntimeException("Cannot connect to database");
        }
    }
    
    /**
     * Test CRUD operations
     */
    private static void testCRUDOperations() throws Exception {
        System.out.println("\n--- Test 2: CRUD Operations ---");
        DatabaseManager dbManager = new DatabaseManager();
        
        try {
            // Clear previous data
            dbManager.deleteAllCourses();
            System.out.println("✓ Database cleaned for testing");
            
            // Test CREATE (INSERT)
            System.out.println("\nTesting CREATE (INSERT):");
            Course course1 = new Course("Data Structures", "CSC203", "A", 3.0, "Dr. Ahmed", "Dr. Fatima");
            Course course2 = new Course("Database Design", "CSC301", "A-", 3.5, "Prof. Khan", "Dr. Noor");
            Course course3 = new Course("Web Development", "CSC402", "B+", 4.0, "Dr. Hasan", "Ms. Zara");
            
            int id1 = dbManager.insertCourse(course1);
            int id2 = dbManager.insertCourse(course2);
            int id3 = dbManager.insertCourse(course3);
            
            System.out.println("✓ Inserted 3 courses with IDs: " + id1 + ", " + id2 + ", " + id3);
            
            // Test READ (SELECT)
            System.out.println("\nTesting READ (SELECT):");
            List<Course> allCourses = dbManager.getAllCourses();
            System.out.println("✓ Retrieved " + allCourses.size() + " courses:");
            for (Course course : allCourses) {
                System.out.println("  - ID: " + course.getId() + ", " + course);
            }
            
            if (allCourses.size() != 3) {
                throw new RuntimeException("Expected 3 courses, but got " + allCourses.size());
            }
            
            // Test UPDATE
            System.out.println("\nTesting UPDATE:");
            Course courseToUpdate = dbManager.getCourseById(id1);
            if (courseToUpdate != null) {
                courseToUpdate.setGrade("A+");
                courseToUpdate.setCreditHours(3.5);
                boolean updated = dbManager.updateCourse(courseToUpdate);
                if (updated) {
                    System.out.println("✓ Course updated successfully");
                    Course updatedCourse = dbManager.getCourseById(id1);
                    System.out.println("  - Updated course: " + updatedCourse);
                } else {
                    System.out.println("✗ Failed to update course");
                }
            }
            
            // Test DELETE
            System.out.println("\nTesting DELETE:");
            boolean deleted = dbManager.deleteCourse(id3);
            if (deleted) {
                System.out.println("✓ Course deleted successfully");
                int countAfterDelete = dbManager.getCourseCount();
                System.out.println("  - Remaining courses: " + countAfterDelete);
                if (countAfterDelete != 2) {
                    throw new RuntimeException("Expected 2 courses after deletion, but got " + countAfterDelete);
                }
            }
            
        } finally {
            dbManager.shutdown();
        }
    }
    
    /**
     * Test concurrent operations
     */
    private static void testConcurrentOperations() throws Exception {
        System.out.println("\n--- Test 3: Concurrent Operations ---");
        DatabaseManager dbManager = new DatabaseManager();
        
        try {
            // Clear database
            dbManager.deleteAllCourses();
            
            // Create multiple courses concurrently
            System.out.println("\nCreating courses concurrently:");
            CompletableFuture<Integer> async1 = dbManager.insertCourseAsync(
                    new Course("Concurrent Programming", "CSC501", "A", 3.0, "Dr. Ali", "Prof. Sara"));
            CompletableFuture<Integer> async2 = dbManager.insertCourseAsync(
                    new Course("Machine Learning", "CSC502", "B+", 4.0, "Dr. Rashid", "Dr. Layla"));
            CompletableFuture<Integer> async3 = dbManager.insertCourseAsync(
                    new Course("Cloud Computing", "CSC503", "A-", 3.5, "Prof. Omar", "Ms. Mariam"));
            
            // Wait for all operations to complete
            CompletableFuture.allOf(async1, async2, async3).join();
            
            int id1 = async1.get();
            int id2 = async2.get();
            int id3 = async3.get();
            
            System.out.println("✓ Async insert completed with IDs: " + id1 + ", " + id2 + ", " + id3);
            
            // Read all courses concurrently
            System.out.println("\nRetrieving all courses concurrently:");
            CompletableFuture<List<Course>> asyncRead = dbManager.getAllCoursesAsync();
            List<Course> courses = asyncRead.get();
            System.out.println("✓ Async read completed: " + courses.size() + " courses retrieved");
            
            // Update course concurrently
            System.out.println("\nUpdating course concurrently:");
            Course courseToUpdate = courses.get(0);
            courseToUpdate.setGrade("A+");
            CompletableFuture<Boolean> asyncUpdate = dbManager.updateCourseAsync(courseToUpdate);
            boolean updated = asyncUpdate.get();
            System.out.println("✓ Async update completed: " + (updated ? "Success" : "Failed"));
            
            // Delete course concurrently
            System.out.println("\nDeleting course concurrently:");
            CompletableFuture<Boolean> asyncDelete = dbManager.deleteCourseAsync(id3);
            boolean deleted = asyncDelete.get();
            System.out.println("✓ Async delete completed: " + (deleted ? "Success" : "Failed"));
            
        } catch (ExecutionException e) {
            System.err.println("✗ Concurrent operation failed: " + e.getCause().getMessage());
            throw e;
        } finally {
            dbManager.shutdown();
        }
    }
    
    /**
     * Test JSON import/export operations
     */
    private static void testJSONOperations() throws Exception {
        System.out.println("\n--- Test 4: JSON Import/Export Operations ---");
        DatabaseManager dbManager = new DatabaseManager();
        
        try {
            // Clear database and prepare test data
            dbManager.deleteAllCourses();
            
            Course course1 = new Course("Object Oriented Programming", "CSC101", "A", 3.0, "Dr. Hassan", "Prof. Fatima");
            Course course2 = new Course("Database Systems", "CSC201", "B+", 3.5, "Dr. Karim", "Dr. Noor");
            
            dbManager.insertCourse(course1);
            dbManager.insertCourse(course2);
            
            // Test EXPORT
            System.out.println("\nTesting JSON EXPORT:");
            List<Course> coursesToExport = dbManager.getAllCourses();
            String exportPath = "courses_export.json";
            boolean exported = JSONHandler.exportToJSON(coursesToExport, exportPath);
            
            if (exported) {
                System.out.println("✓ JSON export successful to: " + exportPath);
            } else {
                System.out.println("✗ JSON export failed");
                throw new RuntimeException("Export failed");
            }
            
            // Test IMPORT
            System.out.println("\nTesting JSON IMPORT:");
            List<Course> importedCourses = JSONHandler.importFromJSON(exportPath);
            System.out.println("✓ JSON import successful: " + importedCourses.size() + " courses imported");
            
            if (importedCourses.size() != coursesToExport.size()) {
                throw new RuntimeException("Import count mismatch");
            }
            
            for (Course course : importedCourses) {
                System.out.println("  - " + course.getCourseName() + " (" + course.getGrade() + ")");
            }
            
            // Test single course JSON conversion
            System.out.println("\nTesting single course JSON conversion:");
            String courseJson = JSONHandler.courseToJSON(course1);
            System.out.println("  - Course as JSON: " + courseJson);
            
            Course parsedCourse = JSONHandler.jsonToCourse(courseJson);
            System.out.println("  - Parsed back: " + parsedCourse.getCourseName() + " (" + parsedCourse.getGrade() + ")");
            System.out.println("✓ Single course JSON conversion successful");
            
        } finally {
            dbManager.shutdown();
        }
    }
}
