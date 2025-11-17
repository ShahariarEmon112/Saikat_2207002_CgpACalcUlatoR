import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main extends Application {
    private GPACalculator calculator = new GPACalculator();
    private ObservableList<Course> courseData;
    private Stage primaryStage;
    private double targetTotalCredits = 12.0;
    private TextField targetCreditsField;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Student Grading System - Saikat (2207002)");
        courseData = FXCollections.observableArrayList();
        showHomeScreen();
        primaryStage.show();
    }
    
    private void showHomeScreen() {
        VBox homeLayout = new VBox(30);
        homeLayout.setAlignment(Pos.CENTER);
        homeLayout.setPadding(new Insets(50));
        homeLayout.setStyle("-fx-background: linear-gradient(to bottom right, #667eea, #764ba2);");
        
        Label welcomeLabel = new Label("Welcome to");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 28));
        welcomeLabel.setTextFill(Color.WHITE);
        
        Label titleLabel = new Label("STUDENT GRADING SYSTEM");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 42));
        titleLabel.setTextFill(Color.WHITE);
        
        Label subtitleLabel = new Label("GPA Calculator");
        subtitleLabel.setFont(Font.font("Arial", FontWeight.LIGHT, 32));
        subtitleLabel.setTextFill(Color.rgb(255, 255, 255, 0.9));
        
        Label studentLabel = new Label("By: Saikat (ID: 2207002)");
        studentLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        studentLabel.setTextFill(Color.rgb(255, 255, 255, 0.8));
        
        Button startButton = new Button("Start GPA Calculator");
        startButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        startButton.setStyle("-fx-background-color: white; -fx-text-fill: #667eea; -fx-padding: 20 50; " +
                            "-fx-background-radius: 30; -fx-cursor: hand;");
        startButton.setOnAction(e -> showMainScreen());
        
        Label instructionLabel = new Label("Track your academic performance with ease");
        instructionLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        instructionLabel.setTextFill(Color.rgb(255, 255, 255, 0.7));
        
        homeLayout.getChildren().addAll(welcomeLabel, titleLabel, subtitleLabel, new Region(), 
                                        startButton, new Region(), instructionLabel, studentLabel);
        
        Scene homeScene = new Scene(homeLayout, 1000, 600);
        primaryStage.setScene(homeScene);
    }
    
    private void showMainScreen() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");
        
        VBox header = createHeader();
        root.setTop(header);
        
        GridPane centerContent = createCenterContent();
        root.setCenter(centerContent);
        
        VBox inputPanel = createInputPanel();
        root.setRight(inputPanel);
        
        Scene scene = new Scene(root, 1400, 750);
        primaryStage.setScene(scene);
    }
    
    private VBox createHeader() {
        VBox header = new VBox(5);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: linear-gradient(to right, #667eea, #764ba2);");
        
        Label titleLabel = new Label("STUDENT GRADING SYSTEM");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titleLabel.setTextFill(Color.WHITE);
        
        Label subtitleLabel = new Label("GPA Calculator - Saikat (ID: 2207002)");
        subtitleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        subtitleLabel.setTextFill(Color.WHITE);
        
        Button backButton = new Button("← Back to Home");
        backButton.setStyle("-fx-background-color: rgba(255,255,255,0.3); -fx-text-fill: white; " +
                           "-fx-font-size: 12px; -fx-padding: 8 15; -fx-cursor: hand;");
        backButton.setOnAction(e -> showHomeScreen());
        
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.getChildren().add(backButton);
        
        header.getChildren().addAll(topBar, titleLabel, subtitleLabel);
        return header;
    }
    
    private GridPane createCenterContent() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(15);
        grid.setVgap(15);
        
        VBox leftPanel = new VBox(15);
        leftPanel.setPadding(new Insets(20));
        leftPanel.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        
        Label targetLabel = new Label("Set Total Credits:");
        targetLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        HBox targetBox = new HBox(10);
        targetCreditsField = new TextField(String.valueOf(targetTotalCredits));
        targetCreditsField.setPromptText("Total Credits");
        targetCreditsField.setPrefWidth(100);
        
        Button setButton = new Button("Set");
        setButton.setStyle("-fx-background-color: #667eea; -fx-text-fill: white; -fx-padding: 5 15;");
        setButton.setOnAction(e -> {
            try {
                targetTotalCredits = Double.parseDouble(targetCreditsField.getText());
                showAlert("Success", "Target credits set to " + targetTotalCredits, Alert.AlertType.INFORMATION);
            } catch (Exception ex) {
                showAlert("Error", "Invalid number!", Alert.AlertType.ERROR);
            }
        });
        
        targetBox.getChildren().addAll(targetCreditsField, setButton);
        
        Separator sep1 = new Separator();
        
        Label coursesLabel = new Label("📚 Added Courses");
        coursesLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        TableView<Course> courseTable = new TableView<>();
        courseTable.setItems(courseData);
        courseTable.setPrefHeight(400);
        courseTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        
        TableColumn<Course, String> codeCol = new TableColumn<>("Code");
        codeCol.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        
        TableColumn<Course, String> nameCol = new TableColumn<>("Course Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        
        TableColumn<Course, String> gradeCol = new TableColumn<>("Grade");
        gradeCol.setCellValueFactory(new PropertyValueFactory<>("grade"));
        
        TableColumn<Course, Double> creditCol = new TableColumn<>("Credits");
        creditCol.setCellValueFactory(new PropertyValueFactory<>("creditHours"));
        
        @SuppressWarnings("unchecked")
        TableColumn<Course, ?>[] columns = new TableColumn[] {codeCol, nameCol, gradeCol, creditCol};
        courseTable.getColumns().addAll(columns);
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button removeBtn = new Button("Remove Selected");
        removeBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 10 20;");
        removeBtn.setOnAction(e -> {
            Course selected = courseTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                int index = courseData.indexOf(selected);
                calculator.removeCourse(index);
                courseData.remove(selected);
            }
        });
        
        Button clearBtn = new Button("Clear All");
        clearBtn.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-padding: 10 20;");
        clearBtn.setOnAction(e -> {
            if (!courseData.isEmpty()) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Confirm");
                confirm.setContentText("Clear all courses?");
                if (confirm.showAndWait().get() == ButtonType.OK) {
                    calculator.clearCourses();
                    courseData.clear();
                }
            }
        });
        
        buttonBox.getChildren().addAll(removeBtn, clearBtn);
        
        Label currentLabel = new Label("Current Credits: 0.0 / " + targetTotalCredits);
        currentLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        currentLabel.setStyle("-fx-text-fill: #667eea;");
        
        Button calculateBtn = new Button("Calculate GPA");
        calculateBtn.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        calculateBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-padding: 15 40; " +
                             "-fx-background-radius: 25;");
        calculateBtn.setDisable(true);
        
        courseData.addListener((javafx.collections.ListChangeListener.Change<? extends Course> c) -> {
            double current = calculator.getTotalCreditHours();
            currentLabel.setText(String.format("Current Credits: %.1f / %.1f", current, targetTotalCredits));
            calculateBtn.setDisable(current < targetTotalCredits);
        });
        
        calculateBtn.setOnAction(e -> showGPAResultScreen());
        
        leftPanel.getChildren().addAll(targetLabel, targetBox, sep1, coursesLabel, courseTable, 
                                       buttonBox, new Separator(), currentLabel, calculateBtn);
        
        grid.add(leftPanel, 0, 0);
        return grid;
    }
    
    private VBox createInputPanel() {
        VBox inputPanel = new VBox(15);
        inputPanel.setPadding(new Insets(20));
        inputPanel.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-width: 0 0 0 2;");
        inputPanel.setPrefWidth(400);
        
        Label inputTitle = new Label("➕ Add New Course");
        inputTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        GridPane formGrid = new GridPane();
        formGrid.setVgap(12);
        formGrid.setHgap(10);
        
        Label nameLabel = new Label("Course Name:");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        TextField courseNameField = new TextField();
        courseNameField.setPromptText("e.g., Data Structures");
        courseNameField.setStyle("-fx-font-size: 13px;");
        
        Label codeLabel = new Label("Course Code:");
        codeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        TextField courseCodeField = new TextField();
        courseCodeField.setPromptText("e.g., CSE201");
        courseCodeField.setStyle("-fx-font-size: 13px;");
        
        Label creditLabel = new Label("Course Credit:");
        creditLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        TextField creditField = new TextField();
        creditField.setPromptText("e.g., 3.0");
        creditField.setStyle("-fx-font-size: 13px;");
        
        Label teacher1Label = new Label("Teacher 1 Name:");
        teacher1Label.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        TextField teacher1Field = new TextField();
        teacher1Field.setPromptText("e.g., Dr. Rahman");
        teacher1Field.setStyle("-fx-font-size: 13px;");
        
        Label teacher2Label = new Label("Teacher 2 Name:");
        teacher2Label.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        TextField teacher2Field = new TextField();
        teacher2Field.setPromptText("e.g., Prof. Khan");
        teacher2Field.setStyle("-fx-font-size: 13px;");
        
        Label gradeLabel = new Label("Grade:");
        gradeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        ComboBox<String> gradeComboBox = new ComboBox<>();
        gradeComboBox.setItems(FXCollections.observableArrayList(
            "A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "F"
        ));
        gradeComboBox.setPromptText("Select grade");
        gradeComboBox.setStyle("-fx-font-size: 13px;");
        gradeComboBox.setMaxWidth(Double.MAX_VALUE);
        
        formGrid.add(nameLabel, 0, 0);
        formGrid.add(courseNameField, 0, 1);
        formGrid.add(codeLabel, 0, 2);
        formGrid.add(courseCodeField, 0, 3);
        formGrid.add(creditLabel, 0, 4);
        formGrid.add(creditField, 0, 5);
        formGrid.add(teacher1Label, 0, 6);
        formGrid.add(teacher1Field, 0, 7);
        formGrid.add(teacher2Label, 0, 8);
        formGrid.add(teacher2Field, 0, 9);
        formGrid.add(gradeLabel, 0, 10);
        formGrid.add(gradeComboBox, 0, 11);
        
        Button addButton = new Button("✓ Add Course");
        addButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 15px; " +
                          "-fx-font-weight: bold; -fx-padding: 12 30; -fx-cursor: hand;");
        addButton.setMaxWidth(Double.MAX_VALUE);
        addButton.setOnAction(e -> {
            String courseName = courseNameField.getText().trim();
            String courseCode = courseCodeField.getText().trim();
            String grade = gradeComboBox.getValue();
            String teacher1 = teacher1Field.getText().trim();
            String teacher2 = teacher2Field.getText().trim();
            String creditText = creditField.getText().trim();
            
            if (courseName.isEmpty() || courseCode.isEmpty() || grade == null || 
                teacher1.isEmpty() || teacher2.isEmpty() || creditText.isEmpty()) {
                showAlert("Input Error", "Please fill in all fields!", Alert.AlertType.WARNING);
                return;
            }
            
            try {
                double creditHours = Double.parseDouble(creditText);
                if (creditHours <= 0) {
                    showAlert("Invalid Input", "Credit hours must be positive!", Alert.AlertType.ERROR);
                    return;
                }
                
                Course course = new Course(courseName, courseCode, grade, creditHours, teacher1, teacher2);
                if (calculator.addCourse(course)) {
                    courseData.add(course);
                    courseNameField.clear();
                    courseCodeField.clear();
                    gradeComboBox.setValue(null);
                    creditField.clear();
                    teacher1Field.clear();
                    teacher2Field.clear();
                    showAlert("Success", "Course added successfully!", Alert.AlertType.INFORMATION);
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid number for credit hours!", Alert.AlertType.ERROR);
            }
        });
        
        Button clearFormBtn = new Button("Clear Form");
        clearFormBtn.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 8 20;");
        clearFormBtn.setMaxWidth(Double.MAX_VALUE);
        clearFormBtn.setOnAction(e -> {
            courseNameField.clear();
            courseCodeField.clear();
            gradeComboBox.setValue(null);
            creditField.clear();
            teacher1Field.clear();
            teacher2Field.clear();
        });
        
        inputPanel.getChildren().addAll(inputTitle, new Separator(), formGrid, addButton, clearFormBtn);
        return inputPanel;
    }
    
    private void showGPAResultScreen() {
        VBox resultLayout = new VBox(20);
        resultLayout.setAlignment(Pos.TOP_CENTER);
        resultLayout.setPadding(new Insets(40));
        resultLayout.setStyle("-fx-background-color: white;");
        
        VBox certificate = new VBox(15);
        certificate.setPadding(new Insets(40));
        certificate.setMaxWidth(900);
        certificate.setStyle("-fx-background-color: white; -fx-border-color: #667eea; -fx-border-width: 5; " +
                            "-fx-border-radius: 15; -fx-background-radius: 15; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 20, 0, 0, 5);");
        
        Label awardTitle = new Label("🎓 ACADEMIC PERFORMANCE CERTIFICATE 🎓");
        awardTitle.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        awardTitle.setStyle("-fx-text-fill: #667eea;");
        awardTitle.setTextAlignment(TextAlignment.CENTER);
        
        Label dateLabel = new Label("Date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
        dateLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        
        Separator sep1 = new Separator();
        sep1.setStyle("-fx-background-color: #667eea;");
        
        Label studentInfo = new Label("This certifies that\n\nSaikat (ID: 2207002)\n\n" +
                                     "has successfully completed the following courses:");
        studentInfo.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        studentInfo.setTextAlignment(TextAlignment.CENTER);
        
        GridPane courseGrid = new GridPane();
        courseGrid.setHgap(15);
        courseGrid.setVgap(12);
        courseGrid.setAlignment(Pos.CENTER);
        courseGrid.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 20; -fx-background-radius: 10;");
        
        Label[] headers = {
            new Label("Code"), new Label("Course Name"), new Label("Teacher 1"), 
            new Label("Teacher 2"), new Label("Credits"), new Label("Grade"), new Label("Points")
        };
        
        for (int i = 0; i < headers.length; i++) {
            headers[i].setFont(Font.font("Arial", FontWeight.BOLD, 13));
            headers[i].setStyle("-fx-text-fill: #667eea;");
            courseGrid.add(headers[i], i, 0);
        }
        
        int row = 1;
        for (Course course : courseData) {
            courseGrid.add(new Label(course.getCourseCode()), 0, row);
            courseGrid.add(new Label(course.getCourseName()), 1, row);
            courseGrid.add(new Label(course.getTeacher1Name()), 2, row);
            courseGrid.add(new Label(course.getTeacher2Name()), 3, row);
            courseGrid.add(new Label(String.format("%.1f", course.getCreditHours())), 4, row);
            courseGrid.add(new Label(course.getGrade()), 5, row);
            courseGrid.add(new Label(String.format("%.2f", course.getGradePoint())), 6, row);
            row++;
        }
        
        Separator sep2 = new Separator();
        sep2.setStyle("-fx-background-color: #667eea;");
        
        double gpa = calculator.calculateGPA();
        String classification = getClassification(gpa);
        
        VBox gpaBox = new VBox(10);
        gpaBox.setAlignment(Pos.CENTER);
        gpaBox.setStyle("-fx-background-color: #667eea; " +
                       "-fx-padding: 25; -fx-background-radius: 10;");
        
        Label gpaLabel = new Label(String.format("CGPA: %.2f", gpa));
        gpaLabel.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        gpaLabel.setTextFill(Color.WHITE);
        
        Label totalCreditsLabel = new Label(String.format("Total Credits: %.1f", calculator.getTotalCreditHours()));
        totalCreditsLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        totalCreditsLabel.setTextFill(Color.WHITE);
        
        Label classLabel = new Label("Classification: " + classification);
        classLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        classLabel.setTextFill(Color.WHITE);
        
        gpaBox.getChildren().addAll(gpaLabel, totalCreditsLabel, classLabel);
        
        Label congratsLabel = new Label("🌟 Congratulations! 🌟");
        congratsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        congratsLabel.setStyle("-fx-text-fill: #27ae60;");
        
        certificate.getChildren().addAll(awardTitle, dateLabel, sep1, studentInfo, courseGrid, 
                                        sep2, gpaBox, congratsLabel);
        
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button backButton = new Button("← Back to Entry");
        backButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 12 30;");
        backButton.setOnAction(e -> showMainScreen());
        
        Button homeButton = new Button("🏠 Home");
        homeButton.setStyle("-fx-background-color: #667eea; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 12 30;");
        homeButton.setOnAction(e -> showHomeScreen());
        
        buttonBox.getChildren().addAll(backButton, homeButton);
        
        resultLayout.getChildren().addAll(certificate, buttonBox);
        
        ScrollPane scrollPane = new ScrollPane(resultLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #f5f5f5;");
        
        Scene resultScene = new Scene(scrollPane, 1200, 800);
        primaryStage.setScene(resultScene);
    }
    
    private String getClassification(double gpa) {
        if (gpa >= 3.75) return "First Class (Distinction)";
        if (gpa >= 3.5) return "First Class";
        if (gpa >= 3.0) return "Second Class (Upper Division)";
        if (gpa >= 2.5) return "Second Class (Lower Division)";
        if (gpa >= 2.0) return "Pass";
        return "Fail";
    }
    
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
