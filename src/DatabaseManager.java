import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * DatabaseManager class for managing SQLite database operations
 * Implements CRUD operations with concurrent access support
 */
public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:gpa_calculator.db";
    private static final String TABLE_COURSES = "courses";
    private ExecutorService executorService;
    
    static {
        // Load SQLite JDBC driver
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("[Database Error] Failed to load SQLite driver: " + e.getMessage());
        }
    }
    
    /**
     * Constructor - Initialize database and executor service
     */
    public DatabaseManager() {
        this.executorService = Executors.newFixedThreadPool(3);
        initializeDatabase();
    }
    
    /**
     * Initialize the database and create tables if not exist
     */
    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            
            String createTableSQL = "CREATE TABLE IF NOT EXISTS " + TABLE_COURSES + " (\n"
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "courseName TEXT NOT NULL,\n"
                    + "courseCode TEXT NOT NULL,\n"
                    + "grade TEXT NOT NULL,\n"
                    + "creditHours REAL NOT NULL,\n"
                    + "teacher1Name TEXT NOT NULL,\n"
                    + "teacher2Name TEXT NOT NULL,\n"
                    + "createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n"
                    + ");";
            
            stmt.execute(createTableSQL);
            System.out.println("[Database] Database initialized successfully!");
            
        } catch (SQLException e) {
            System.err.println("[Database Error] Failed to initialize database: " + e.getMessage());
        }
    }
    
    /**
     * Insert a course into the database (Asynchronous - Concurrent)
     * @param course Course to insert
     * @return CompletableFuture with the inserted course ID
     */
    public CompletableFuture<Integer> insertCourseAsync(Course course) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return insertCourse(course);
            } catch (SQLException e) {
                System.err.println("[Database Error] Failed to insert course: " + e.getMessage());
                return -1;
            }
        }, executorService);
    }
    
    /**
     * Insert a course into the database (Synchronous)
     * @param course Course to insert
     * @return Course ID
     */
    public int insertCourse(Course course) throws SQLException {
        String insertSQL = "INSERT INTO " + TABLE_COURSES 
                + " (courseName, courseCode, grade, creditHours, teacher1Name, teacher2Name) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            
            pstmt.setString(1, course.getCourseName());
            pstmt.setString(2, course.getCourseCode());
            pstmt.setString(3, course.getGrade());
            pstmt.setDouble(4, course.getCreditHours());
            pstmt.setString(5, course.getTeacher1Name());
            pstmt.setString(6, course.getTeacher2Name());
            
            pstmt.executeUpdate();
            
            // Get the last inserted ID
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid() as id")) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    course.setId(id);
                    System.out.println("[Database] Course inserted successfully with ID: " + id);
                    return id;
                }
            }
        }
        return -1;
    }
    
    /**
     * Retrieve all courses from the database (Asynchronous - Concurrent)
     * @return CompletableFuture with list of courses
     */
    public CompletableFuture<List<Course>> getAllCoursesAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getAllCourses();
            } catch (SQLException e) {
                System.err.println("[Database Error] Failed to retrieve courses: " + e.getMessage());
                return new ArrayList<>();
            }
        }, executorService);
    }
    
    /**
     * Retrieve all courses from the database (Synchronous)
     * @return List of courses
     */
    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String selectSQL = "SELECT * FROM " + TABLE_COURSES + " ORDER BY id DESC";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            
            while (rs.next()) {
                Course course = new Course(
                        rs.getInt("id"),
                        rs.getString("courseName"),
                        rs.getString("courseCode"),
                        rs.getString("grade"),
                        rs.getDouble("creditHours"),
                        rs.getString("teacher1Name"),
                        rs.getString("teacher2Name")
                );
                courses.add(course);
            }
            System.out.println("[Database] Retrieved " + courses.size() + " courses from database");
        }
        return courses;
    }
    
    /**
     * Retrieve a course by ID (Synchronous)
     * @param id Course ID
     * @return Course object or null
     */
    public Course getCourseById(int id) throws SQLException {
        String selectSQL = "SELECT * FROM " + TABLE_COURSES + " WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Course(
                            rs.getInt("id"),
                            rs.getString("courseName"),
                            rs.getString("courseCode"),
                            rs.getString("grade"),
                            rs.getDouble("creditHours"),
                            rs.getString("teacher1Name"),
                            rs.getString("teacher2Name")
                    );
                }
            }
        }
        return null;
    }
    
    /**
     * Update a course in the database (Asynchronous - Concurrent)
     * @param course Course to update
     * @return CompletableFuture with boolean result
     */
    public CompletableFuture<Boolean> updateCourseAsync(Course course) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return updateCourse(course);
            } catch (SQLException e) {
                System.err.println("[Database Error] Failed to update course: " + e.getMessage());
                return false;
            }
        }, executorService);
    }
    
    /**
     * Update a course in the database (Synchronous)
     * @param course Course to update
     * @return true if successful, false otherwise
     */
    public boolean updateCourse(Course course) throws SQLException {
        String updateSQL = "UPDATE " + TABLE_COURSES 
                + " SET courseName = ?, courseCode = ?, grade = ?, creditHours = ?, teacher1Name = ?, teacher2Name = ? "
                + "WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            
            pstmt.setString(1, course.getCourseName());
            pstmt.setString(2, course.getCourseCode());
            pstmt.setString(3, course.getGrade());
            pstmt.setDouble(4, course.getCreditHours());
            pstmt.setString(5, course.getTeacher1Name());
            pstmt.setString(6, course.getTeacher2Name());
            pstmt.setInt(7, course.getId());
            
            int affectedRows = pstmt.executeUpdate();
            System.out.println("[Database] Course updated successfully. Affected rows: " + affectedRows);
            return affectedRows > 0;
        }
    }
    
    /**
     * Delete a course from the database (Asynchronous - Concurrent)
     * @param id Course ID to delete
     * @return CompletableFuture with boolean result
     */
    public CompletableFuture<Boolean> deleteCourseAsync(int id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return deleteCourse(id);
            } catch (SQLException e) {
                System.err.println("[Database Error] Failed to delete course: " + e.getMessage());
                return false;
            }
        }, executorService);
    }
    
    /**
     * Delete a course from the database (Synchronous)
     * @param id Course ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteCourse(int id) throws SQLException {
        String deleteSQL = "DELETE FROM " + TABLE_COURSES + " WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            
            pstmt.setInt(1, id);
            
            int affectedRows = pstmt.executeUpdate();
            System.out.println("[Database] Course deleted successfully. Affected rows: " + affectedRows);
            return affectedRows > 0;
        }
    }
    
    /**
     * Delete all courses from the database
     * @return true if successful, false otherwise
     */
    public boolean deleteAllCourses() throws SQLException {
        String deleteSQL = "DELETE FROM " + TABLE_COURSES;
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            
            int affectedRows = stmt.executeUpdate(deleteSQL);
            System.out.println("[Database] All courses deleted. Affected rows: " + affectedRows);
            return true;
        }
    }
    
    /**
     * Get the count of courses in the database
     * @return Number of courses
     */
    public int getCourseCount() throws SQLException {
        String countSQL = "SELECT COUNT(*) FROM " + TABLE_COURSES;
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(countSQL)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    
    /**
     * Close the executor service
     */
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
    
    /**
     * Test database connection
     * @return true if connection successful, false otherwise
     */
    public static boolean testConnection() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            System.out.println("[Database] Connection test successful!");
            return true;
        } catch (SQLException e) {
            System.err.println("[Database Error] Connection test failed: " + e.getMessage());
            return false;
        }
    }
}
