import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * JSONHandler class for handling JSON serialization and deserialization
 * Supports importing and exporting Course data to/from JSON files
 */
public class JSONHandler {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    
    /**
     * Export courses to a JSON file
     * @param courses List of courses to export
     * @param filePath Path to the JSON file to create
     * @return true if successful, false otherwise
     */
    public static boolean exportToJSON(List<Course> courses, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(courses, writer);
            System.out.println("[JSON] Exported " + courses.size() + " courses to: " + filePath);
            return true;
        } catch (IOException e) {
            System.err.println("[JSON Error] Failed to export to JSON: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Import courses from a JSON file
     * @param filePath Path to the JSON file to read
     * @return List of imported courses, or empty list if error
     */
    public static List<Course> importFromJSON(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            Type courseListType = new TypeToken<ArrayList<Course>>(){}.getType();
            List<Course> courses = gson.fromJson(reader, courseListType);
            if (courses != null) {
                System.out.println("[JSON] Imported " + courses.size() + " courses from: " + filePath);
                return courses;
            }
        } catch (IOException e) {
            System.err.println("[JSON Error] Failed to import from JSON: " + e.getMessage());
        }
        return new ArrayList<>();
    }
    
    /**
     * Convert a single course to JSON string
     * @param course Course to convert
     * @return JSON string representation
     */
    public static String courseToJSON(Course course) {
        return gson.toJson(course);
    }
    
    /**
     * Convert a JSON string to a Course object
     * @param json JSON string
     * @return Course object or null
     */
    public static Course jsonToCourse(String json) {
        try {
            return gson.fromJson(json, Course.class);
        } catch (Exception e) {
            System.err.println("[JSON Error] Failed to parse course from JSON: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Convert courses list to JSON string
     * @param courses List of courses
     * @return JSON string representation
     */
    public static String coursesListToJSON(List<Course> courses) {
        return gson.toJson(courses);
    }
    
    /**
     * Convert JSON string to courses list
     * @param json JSON string
     * @return List of courses or empty list
     */
    public static List<Course> jsonToCourseLis(String json) {
        try {
            Type courseListType = new TypeToken<ArrayList<Course>>(){}.getType();
            List<Course> courses = gson.fromJson(json, courseListType);
            return courses != null ? courses : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("[JSON Error] Failed to parse courses list from JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
