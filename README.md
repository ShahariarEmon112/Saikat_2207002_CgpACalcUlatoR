# CGPA Calculator
**By: Saikat (ID: 2207002)**

A comprehensive JavaFX-based GPA/CGPA calculator with a modern graphical user interface.

## Features

✅ **Home Screen** with "Start GPA Calculator" button
✅ **Course Entry** with all required fields:
   - Course Name
   - Course Code
   - Course Credit
   - Teacher 1 Name
   - Teacher 2 Name
   - Grade selection (A+, A, A-, B+, B, B-, C+, C, C-, D+, D, F)
✅ **Target Credit Setting** - Define total credits required
✅ **Smart GPA Button** - Activates only when target credits are reached
✅ **Award-Style Result Page** - Formatted certificate with all course details
✅ **Scene Navigation** - Home → Course Entry → GPA Result
✅ **Course Management** - Add, remove, clear courses
✅ **Input Validation** - Alerts for missing or invalid data
✅ **Real-time Credit Tracking** - Shows current vs target credits
✅ **GPA Classification** - First Class, Second Class, Pass, Fail

## Grading Scale

| Letter Grade | Grade Point | Numerical Range |
|--------------|-------------|-----------------|
| A+, A        | 4.0         | 90-100          |
| A-           | 3.7         | 90-92           |
| B+           | 3.3         | 87-89           |
| B            | 3.0         | 83-86           |
| B-           | 2.7         | 80-82           |
| C+           | 2.3         | 77-79           |
| C            | 2.0         | 73-76           |
| C-           | 1.7         | 70-72           |
| D+           | 1.3         | 67-69           |
| D            | 1.0         | 60-66           |
| F            | 0.0         | Below 60        |

## GPA Classification

- **3.75 - 4.00**: First Class (Distinction)
- **3.50 - 3.74**: First Class
- **3.00 - 3.49**: Second Class (Upper Division)
- **2.50 - 2.99**: Second Class (Lower Division)
- **2.00 - 2.49**: Pass
- **Below 2.00**: Fail

## Prerequisites

- **Java JDK 21 or higher** (tested with JDK 21)
- **Maven 3.6+** (for dependency management)
- **JavaFX SDK** (automatically downloaded by Maven)

## 🌍 Cross-Platform Compatibility

✅ **Windows** (10/11)
✅ **macOS** (Intel & Apple Silicon)
✅ **Linux** (Ubuntu, Fedora, Debian, etc.)

The application runs on any device with Java 21+ and Maven installed!

## ⚠️ IDE Setup Note

If you see JavaFX import errors in IntelliJ IDEA, this is normal! The project compiles and runs successfully with Maven. To fix IDE errors:

1. **Reload Maven Project**: Right-click on `pom.xml` → Maven → Reload Project
2. **Or**: File → Invalidate Caches → Invalidate and Restart
3. **Or**: Simply run with Maven (errors will disappear after first successful run)

## How to Compile and Run

### Option 1: Using Maven (Recommended) ⭐

1. Navigate to the project directory:
   ```bash
   cd c:\Users\User\Desktop\Saikat_2207002_CgpACalcUlatoR
   ```

2. Run the application (one command):
   ```bash
   mvn javafx:run
   ```

3. Or use the launcher scripts:
   - **Windows**: Double-click `run.bat`
   - **Linux/Mac**: Run `./run.sh`

4. (Optional) Create executable JAR:
   ```bash
   mvn clean package
   ```

### Option 2: Using IntelliJ IDEA

1. Open IntelliJ IDEA
2. Open the project folder: `Saikat_2207002_CgpACalcUlatoR`
3. Wait for Maven to download dependencies
4. Right-click on `Main.java` and select "Run 'Main.main()'"
5. Or use the Maven tool window and run the `javafx:run` goal

### Option 3: Using VS Code

1. Install the "Extension Pack for Java" and "Maven for Java" extensions
2. Open the project folder
3. Open the Maven sidebar
4. Expand the project → Plugins → javafx → javafx:run
5. Click to run the application

## 📦 Distributing to Other Devices

### **Method 1: Share Entire Project**
1. Zip the entire project folder
2. Share with others
3. They need Java 21+ and Maven installed
4. They run: `mvn javafx:run`

### **Method 2: Build Executable JAR**
1. Run `build-jar.bat` (Windows) or `mvn package`
2. Share the JAR file from `target/` folder
3. Others run: `java -jar cgpa-calculator-1.0-SNAPSHOT.jar`
4. They only need Java 21+ (no Maven required)

### **System Requirements for End Users:**
- **Minimum**: Java JDK 21
- **RAM**: 512 MB minimum, 1 GB recommended
- **Storage**: ~100 MB for application + dependencies
- **Display**: 1024x768 minimum resolution

## Usage Guide

### Main Interface Features:

1. **Add Course Panel (Right Side)**:
   - Enter course name in the text field
   - Select letter grade from dropdown (A+, A, A-, B+, B, B-, C+, C, C-, D+, D, F)
   - Enter credit hours
   - Click "Add Course" button

2. **Course Table (Center)**:
   - View all added courses with their details
   - See course name, grade, credit hours, grade point, and quality points
   - Select a course to remove it

3. **Action Buttons**:
   - **Remove Selected**: Delete the selected course from the table
   - **Clear All**: Remove all courses (with confirmation)
   - **View Grade Scale**: Display grading scale and classification system

4. **GPA Dashboard (Bottom)**:
   - **Total Courses**: Number of courses added
   - **Total Credits**: Sum of all credit hours
   - **GPA/CGPA**: Calculated grade point average
   - **Classification**: Your academic standing based on GPA

### How It Works:

The calculator automatically computes your GPA using the formula:
```
GPA = (Sum of Quality Points) / (Total Credit Hours)
Quality Points = Grade Point × Credit Hours
```

GPA updates in real-time as you add or remove courses!

## Project Structure

```
Saikat_2207002_CgpACalcUlatoR/
├── src/
│   ├── Main.java           # JavaFX Main application with GUI
│   ├── Course.java         # Course class representing individual courses
│   ├── GPACalculator.java  # Calculator logic for GPA computation
│   └── module-info.java    # Java module configuration
├── pom.xml                 # Maven project configuration
└── README.md               # This file
```

## Classes Overview

### Course.java
- Represents a single course with name, grade, and credit hours
- Converts letter grades to grade points
- Calculates quality points (grade point × credit hours)

### GPACalculator.java
- Manages a collection of courses
- Adds, removes, and displays courses
- Calculates overall GPA/CGPA
- Tracks total credit hours

### Main.java
- JavaFX Application with modern GUI
- Table view for displaying courses
- Input panel for adding courses
- Real-time GPA calculation and display
- Interactive buttons and alerts

## Author

**Saikat**  
Student ID: 2207002

---

© 2025 CGPA Calculator - All Rights Reserved
