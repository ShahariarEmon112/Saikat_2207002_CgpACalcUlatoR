# SETUP GUIDE FOR OTHER DEVICES
# CGPA Calculator - Saikat (2207002)

## Quick Start Checklist

### Step 1: Verify Java Installation
Open terminal/command prompt and run:
```bash
java -version
```
**Expected output:** Java version 21 or higher

If not installed:
- Windows: Download from https://adoptium.net/ or https://www.oracle.com/java/technologies/downloads/
- Install JDK 21 or higher
- After installation, restart your terminal

### Step 2: Verify Maven Installation
```bash
mvn -version
```
**Expected output:** Apache Maven 3.6 or higher

If not installed:
- Windows: Download from https://maven.apache.org/download.cgi
- Extract to C:\maven
- Add to System Environment Variables:
  - MAVEN_HOME = C:\maven\apache-maven-x.x.x
  - Add to Path: %MAVEN_HOME%\bin
- Restart terminal

### Step 3: Navigate to Project Directory
```bash
cd path\to\Saikat_2207002_CgpACalcUlatoR
```

### Step 4: First-Time Setup (Important!)
Run this command FIRST to download all dependencies:
```bash
mvn clean install
```
This will download JavaFX and all required libraries (requires internet connection)

### Step 5: Run the Application
```bash
mvn javafx:run
```

## Common Issues & Solutions

### Issue 1: "mvn: command not found" or "mvn is not recognized"
**Solution:** Maven is not installed or not in PATH
- Install Maven from https://maven.apache.org/download.cgi
- Add Maven's bin folder to system PATH
- Restart terminal/command prompt

### Issue 2: "JAVA_HOME is not defined correctly"
**Solution:** Set JAVA_HOME environment variable
- Windows: 
  - System Properties → Environment Variables
  - New System Variable: JAVA_HOME = C:\Program Files\Eclipse Adoptium\jdk-21.x.x
  - Restart terminal

### Issue 3: "javafx cannot be resolved"
**Solution:** Run Maven install first
```bash
mvn clean install
```
This downloads JavaFX dependencies from Maven Central

### Issue 4: Build fails with compilation errors
**Solution:** Ensure Java 21 is being used
```bash
mvn clean compile -X
```
Check the output for Java version

### Issue 5: "No JavaFX runtime found"
**Solution:** JavaFX dependencies not downloaded
```bash
mvn dependency:resolve
mvn javafx:run
```

### Issue 6: Port already in use or display issues
**Solution:** Close any running instances
- Check Task Manager (Windows) or Activity Monitor (Mac)
- Kill any java.exe processes
- Try again

## Alternative: Run Without Maven (Using JAR)

If Maven installation is problematic, use the JAR file:

1. On your working device, run:
   ```bash
   mvn clean package
   ```

2. Copy the entire `target` folder to the other device

3. On the other device (only needs Java 21):
   ```bash
   cd target
   java --module-path . --add-modules javafx.controls,javafx.graphics -jar cgpa-calculator-1.0-SNAPSHOT.jar
   ```

## System Requirements

### Minimum:
- Java JDK 21+
- Maven 3.6+
- 2 GB RAM
- 500 MB free disk space
- Internet connection (for first-time Maven dependency download)

### Supported OS:
- Windows 10/11
- macOS 10.14+
- Linux (Ubuntu 20.04+, Fedora, etc.)

## Still Not Working?

### Collect Information:
1. Run these commands and note the output:
   ```bash
   java -version
   mvn -version
   echo %JAVA_HOME%
   ```

2. Try verbose mode:
   ```bash
   mvn clean compile -X
   ```

3. Check for error messages in the output

### Common Error Messages:

**"The import javafx cannot be resolved"**
→ IDE issue only. Run `mvn clean compile` - if it succeeds, ignore IDE errors

**"Failed to execute goal org.openjfx:javafx-maven-plugin"**
→ JavaFX dependencies not downloaded. Run `mvn clean install` first

**"Unsupported class file major version"**
→ Java version mismatch. Ensure Java 21+ is installed and active

## Contact
If still facing issues, share:
1. Output of `java -version`
2. Output of `mvn -version`
3. Error message from `mvn clean compile`
