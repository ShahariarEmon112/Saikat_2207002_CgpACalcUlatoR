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
    private DatabaseManager dbManager;
    private ObservableList<Course> courseData;
    private Stage primaryStage;
    private double targetTotalCredits = 12.0;
    private TextField targetCreditsField;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.dbManager = new DatabaseManager(); // Initialize database
        primaryStage.setTitle("Student Grading System - Saikat (2207002)");
        courseData = FXCollections.observableArrayList();
        showHomeScreen();
        primaryStage.show();
        
        // Setup shutdown hook for database
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down database...");
            if (dbManager != null) {
                dbManager.shutdown();
            }
        }));
    }
    
    private void showHomeScreen() {
        VBox homeLayout = new VBox(25);
        homeLayout.setAlignment(Pos.CENTER);
        homeLayout.setPadding(new Insets(60, 100, 60, 100));
        homeLayout.setStyle("-fx-background: linear-gradient(180deg, #667eea 0%, #764ba2 50%, #5d3f7a 100%); " +
                           "-fx-padding: 60px 100px;");
        
        // Main Content Container with shadow effect
        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(50));
        contentBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.08); " +
                           "-fx-border-color: rgba(255, 255, 255, 0.2); " +
                           "-fx-border-width: 2; " +
                           "-fx-border-radius: 20; " +
                           "-fx-background-radius: 20; " +
                           "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 8);");
        
        Label welcomeLabel = new Label("✨ Welcome to ✨");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 26));
        welcomeLabel.setTextFill(Color.rgb(255, 255, 255, 0.95));
        welcomeLabel.setStyle("-fx-text-fill: #ffd700;");
        
        Label titleLabel = new Label("STUDENT GRADING SYSTEM");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setStyle("-fx-font-size: 48px; " +
                           "-fx-font-weight: bold; " +
                           "-fx-text-fill: white; " +
                           "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 5, 0, 0, 2);");
        
        Label subtitleLabel = new Label("GPA Calculator");
        subtitleLabel.setFont(Font.font("Arial", FontWeight.LIGHT, 36));
        subtitleLabel.setStyle("-fx-font-size: 36px; " +
                              "-fx-font-weight: 300; " +
                              "-fx-text-fill: #b3d9ff;");
        
        // Decorative separator
        Separator sep1 = new Separator();
        sep1.setStyle("-fx-border-color: rgba(255, 255, 255, 0.3);");
        
        Label featureLabel = new Label("📊 Track Your Academic Performance with Ease 📊");
        featureLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        featureLabel.setStyle("-fx-font-size: 16px; " +
                             "-fx-text-fill: rgba(255, 255, 255, 0.85);");
        
        // Features List
        VBox featuresBox = new VBox(10);
        featuresBox.setStyle("-fx-padding: 15px 0px;");
        
        Label feature1 = new Label("✓ Add multiple courses with grades and credits");
        feature1.setStyle("-fx-font-size: 13px; -fx-text-fill: rgba(255, 255, 255, 0.8);");
        
        Label feature2 = new Label("✓ Calculate your GPA automatically");
        feature2.setStyle("-fx-font-size: 13px; -fx-text-fill: rgba(255, 255, 255, 0.8);");
        
        Label feature3 = new Label("✓ Save and load courses from database");
        feature3.setStyle("-fx-font-size: 13px; -fx-text-fill: rgba(255, 255, 255, 0.8);");
        
        Label feature4 = new Label("✓ Export and import courses as JSON");
        feature4.setStyle("-fx-font-size: 13px; -fx-text-fill: rgba(255, 255, 255, 0.8);");
        
        featuresBox.getChildren().addAll(feature1, feature2, feature3, feature4);
        
        // Decorative separator
        Separator sep2 = new Separator();
        sep2.setStyle("-fx-border-color: rgba(255, 255, 255, 0.3);");
        
        // Start Button with enhanced styling
        Button startButton = new Button("🚀 START GPA CALCULATOR 🚀");
        startButton.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        startButton.setPrefWidth(300);
        startButton.setPrefHeight(55);
        startButton.setStyle("-fx-background-color: linear-gradient(180deg, #ffd700 0%, #ffed4e 100%); " +
                            "-fx-text-fill: #333333; " +
                            "-fx-font-size: 18px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 15 50; " +
                            "-fx-background-radius: 35; " +
                            "-fx-cursor: hand; " +
                            "-fx-border-color: rgba(255, 255, 255, 0.4); " +
                            "-fx-border-width: 2; " +
                            "-fx-border-radius: 35; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 10, 0, 0, 4);");
        startButton.setOnMouseEntered(e -> 
            startButton.setStyle("-fx-background-color: linear-gradient(180deg, #ffed4e 0%, #ffd700 100%); " +
                                "-fx-text-fill: #1a1a1a; " +
                                "-fx-font-size: 18px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 15 50; " +
                                "-fx-background-radius: 35; " +
                                "-fx-cursor: hand; " +
                                "-fx-border-color: rgba(255, 255, 255, 0.6); " +
                                "-fx-border-width: 2; " +
                                "-fx-border-radius: 35; " +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 15, 0, 0, 6);"));
        startButton.setOnMouseExited(e -> 
            startButton.setStyle("-fx-background-color: linear-gradient(180deg, #ffd700 0%, #ffed4e 100%); " +
                                "-fx-text-fill: #333333; " +
                                "-fx-font-size: 18px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 15 50; " +
                                "-fx-background-radius: 35; " +
                                "-fx-cursor: hand; " +
                                "-fx-border-color: rgba(255, 255, 255, 0.4); " +
                                "-fx-border-width: 2; " +
                                "-fx-border-radius: 35; " +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 10, 0, 0, 4);"));
        startButton.setOnAction(e -> showMainScreen());
        
        contentBox.getChildren().addAll(welcomeLabel, titleLabel, subtitleLabel, sep1, 
                                       featureLabel, featuresBox, sep2, startButton);
        
        // Student Info at bottom
        Label studentLabel = new Label("Developed by: Saikat (ID: 2207002) | 2025");
        studentLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        studentLabel.setStyle("-fx-font-size: 12px; " +
                             "-fx-text-fill: rgba(255, 255, 255, 0.6); " +
                             "-fx-padding: 20 0 0 0;");
        
        homeLayout.getChildren().addAll(contentBox, studentLabel);
        
        ScrollPane homeScrollPane = new ScrollPane(homeLayout);
        homeScrollPane.setFitToWidth(true);
        homeScrollPane.setStyle("-fx-background-color: #667eea;");
        
        Scene homeScene = new Scene(homeScrollPane, 1000, 600);
        primaryStage.setScene(homeScene);
    }
    
    private void showMainScreen() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");
        
        VBox header = createHeader();
        root.setTop(header);
        
        GridPane centerContent = createCenterContent();
        ScrollPane centerScroll = new ScrollPane(centerContent);
        centerScroll.setFitToWidth(true);
        centerScroll.setStyle("-fx-background-color: #f5f5f5;");
        root.setCenter(centerScroll);
        
        VBox inputPanel = createInputPanel();
        ScrollPane rightScroll = new ScrollPane(inputPanel);
        rightScroll.setFitToWidth(true);
        rightScroll.setPrefWidth(420);
        rightScroll.setStyle("-fx-background-color: white;");
        root.setRight(rightScroll);
        
        Scene scene = new Scene(root, 1400, 750);
        primaryStage.setScene(scene);
    }
    
    private VBox createHeader() {
        VBox header = new VBox(5);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: linear-gradient(to right, #667eea, #764ba2); " +
                       "-fx-padding: 20px;");
        
        Label titleLabel = new Label("STUDENT GRADING SYSTEM");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titleLabel.setTextFill(Color.WHITE);
        
        Label subtitleLabel = new Label("GPA Calculator - Saikat (ID: 2207002)");
        subtitleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        subtitleLabel.setTextFill(Color.WHITE);
        
        Button backButton = new Button("← Back to Home");
        backButton.setPrefWidth(150);
        backButton.setPrefHeight(45);
        backButton.setTextFill(Color.WHITE);
        backButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        backButton.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(255, 107, 107), rgb(231, 76, 60)); " +
                           "-fx-text-fill: white; " +
                           "-fx-padding: 10 20; " +
                           "-fx-cursor: hand; " +
                           "-fx-background-radius: 25; " +
                           "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 3);");
        backButton.setOnMouseEntered(e -> 
            backButton.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(255, 82, 82), rgb(192, 57, 43)); -fx-text-fill: white; " +
                               "-fx-padding: 10 20; -fx-cursor: hand; -fx-background-radius: 25; " +
                               "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 12, 0, 0, 4);"));
        backButton.setOnMouseExited(e -> 
            backButton.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(255, 107, 107), rgb(231, 76, 60)); -fx-text-fill: white; " +
                               "-fx-padding: 10 20; -fx-cursor: hand; -fx-background-radius: 25; " +
                               "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 3);"));
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
        grid.setStyle("-fx-background-color: #ecf0f1;");
        
        VBox leftPanel = new VBox(15);
        leftPanel.setPadding(new Insets(25));
        leftPanel.setStyle("-fx-background-color: white; " +
                          "-fx-background-radius: 15; " +
                          "-fx-border-color: rgb(102, 126, 234); " +
                          "-fx-border-width: 3; " +
                          "-fx-border-radius: 15; " +
                          "-fx-effect: dropshadow(gaussian, rgba(102,126,234,0.35), 15, 0, 0, 5);");
        
        Label targetLabel = new Label("📊 Set Total Credits:");
        targetLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        targetLabel.setTextFill(Color.web("rgb(102, 126, 234)"));
        
        HBox targetBox = new HBox(10);
        targetCreditsField = new TextField(String.valueOf(targetTotalCredits));
        targetCreditsField.setPromptText("Total Credits");
        targetCreditsField.setPrefWidth(100);
        targetCreditsField.setStyle("-fx-font-size: 14px; -fx-padding: 8; -fx-control-inner-background: rgb(240, 244, 255); -fx-border-color: rgb(102, 126, 234); -fx-border-width: 2; -fx-text-fill: rgb(44, 62, 80); -fx-font-weight: bold;");
        
        Button setButton = new Button("Set");
        setButton.setPrefWidth(80);
        setButton.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(102, 126, 234), rgb(85, 104, 211)); " +
                          "-fx-text-fill: white; " +
                          "-fx-padding: 8 20; " +
                          "-fx-font-weight: bold; " +
                          "-fx-font-size: 12px; " +
                          "-fx-background-radius: 15; " +
                          "-fx-cursor: hand; " +
                          "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0, 0, 2);");
        setButton.setOnMouseEntered(e -> setButton.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(85, 104, 211), rgb(68, 75, 163)); -fx-text-fill: white; -fx-padding: 8 20; -fx-font-weight: bold; -fx-font-size: 12px; -fx-background-radius: 15; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 3);"));
        setButton.setOnMouseExited(e -> setButton.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(102, 126, 234), rgb(85, 104, 211)); -fx-text-fill: white; -fx-padding: 8 20; -fx-font-weight: bold; -fx-font-size: 12px; -fx-background-radius: 15; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0, 0, 2);"));
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
        sep1.setStyle("-fx-border-color: rgb(224, 224, 224); -fx-padding: 10 0; -fx-border-width: 1 0 0 0;");
        
        Label coursesLabel = new Label("📚 Added Courses");
        coursesLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        coursesLabel.setTextFill(Color.web("rgb(39, 174, 96)"));
        
        TableView<Course> courseTable = new TableView<>();
        courseTable.setItems(courseData);
        courseTable.setPrefHeight(400);
        courseTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        courseTable.setStyle("-fx-font-size: 12px; -fx-control-inner-background: rgb(249, 249, 249); -fx-table-cell-border-color: rgb(224, 224, 224); -fx-text-fill: rgb(44, 62, 80);");
        
        TableColumn<Course, String> codeCol = new TableColumn<>("Code");
        codeCol.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        codeCol.setStyle("-fx-alignment: CENTER;");
        
        TableColumn<Course, String> nameCol = new TableColumn<>("Course Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        
        TableColumn<Course, String> gradeCol = new TableColumn<>("Grade");
        gradeCol.setCellValueFactory(new PropertyValueFactory<>("grade"));
        gradeCol.setStyle("-fx-alignment: CENTER;");
        
        TableColumn<Course, Double> creditCol = new TableColumn<>("Credits");
        creditCol.setCellValueFactory(new PropertyValueFactory<>("creditHours"));
        creditCol.setStyle("-fx-alignment: CENTER;");
        
        courseTable.getColumns().addAll(codeCol, nameCol, gradeCol, creditCol);
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button removeBtn = new Button("❌ Remove Selected");
        removeBtn.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(231, 76, 60), rgb(192, 57, 43)); " +
                          "-fx-text-fill: white; " +
                          "-fx-font-weight: bold; " +
                          "-fx-font-size: 12px; " +
                          "-fx-padding: 10 20; " +
                          "-fx-background-radius: 15; " +
                          "-fx-cursor: hand; " +
                          "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0, 0, 2);");
        removeBtn.setOnMouseEntered(e -> removeBtn.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(192, 57, 43), rgb(169, 50, 38)); -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 10 20; -fx-background-radius: 15; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 3);"));
        removeBtn.setOnMouseExited(e -> removeBtn.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(231, 76, 60), rgb(192, 57, 43)); -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 10 20; -fx-background-radius: 15; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0, 0, 2);"));
        removeBtn.setOnAction(e -> {
            Course selected = courseTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                int index = courseData.indexOf(selected);
                calculator.removeCourse(index);
                courseData.remove(selected);
            }
        });
        
        Button clearBtn = new Button("🗑️ Clear All");
        clearBtn.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(243, 156, 18), rgb(214, 137, 16)); " +
                         "-fx-text-fill: white; " +
                         "-fx-font-weight: bold; " +
                         "-fx-font-size: 12px; " +
                         "-fx-padding: 10 20; " +
                         "-fx-background-radius: 15; " +
                         "-fx-cursor: hand; " +
                         "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0, 0, 2);");
        clearBtn.setOnMouseEntered(e -> clearBtn.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(214, 137, 16), rgb(184, 103, 12)); -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 10 20; -fx-background-radius: 15; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 3);"));
        clearBtn.setOnMouseExited(e -> clearBtn.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(243, 156, 18), rgb(214, 137, 16)); -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 10 20; -fx-background-radius: 15; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0, 0, 2);"));
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
        currentLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        currentLabel.setTextFill(Color.web("rgb(22, 160, 133)"));
        currentLabel.setStyle("-fx-padding: 10;");
        
        Button calculateBtn = new Button("✓ Calculate GPA");
        calculateBtn.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        calculateBtn.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(39, 174, 96), rgb(34, 153, 84)); " +
                             "-fx-text-fill: white; " +
                             "-fx-padding: 15 40; " +
                             "-fx-background-radius: 25; " +
                             "-fx-font-weight: bold; " +
                             "-fx-cursor: hand; " +
                             "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 2);");
        calculateBtn.setDisable(true);
        calculateBtn.setOnMouseEntered(e -> {
            if (!calculateBtn.isDisable()) {
                calculateBtn.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(34, 153, 84), rgb(28, 129, 71)); -fx-text-fill: white; -fx-padding: 15 40; -fx-background-radius: 25; -fx-font-weight: bold; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 12, 0, 0, 4);");
            }
        });
        calculateBtn.setOnMouseExited(e -> {
            if (!calculateBtn.isDisable()) {
                calculateBtn.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(39, 174, 96), rgb(34, 153, 84)); -fx-text-fill: white; -fx-padding: 15 40; -fx-background-radius: 25; -fx-font-weight: bold; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 2);");
            }
        });
        
        courseData.addListener((javafx.collections.ListChangeListener.Change<? extends Course> c) -> {
            double current = calculator.getTotalCreditHours();
            currentLabel.setText(String.format("📈 Current Credits: %.1f / %.1f", current, targetTotalCredits));
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
        inputPanel.setPadding(new Insets(25));
        inputPanel.setStyle("-fx-background-color: white; " +
                           "-fx-border-color: rgb(52, 152, 219); " +
                           "-fx-border-width: 3 0 0 0; " +
                           "-fx-border-radius: 0;");
        inputPanel.setPrefWidth(400);
        
        Label inputTitle = new Label("➕ Add New Course");
        inputTitle.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        inputTitle.setTextFill(Color.web("rgb(52, 152, 219)"));
        
        GridPane formGrid = new GridPane();
        formGrid.setVgap(12);
        formGrid.setHgap(10);
        formGrid.setStyle("-fx-padding: 15px 0;");
        
        Label nameLabel = new Label("Course Name:");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        nameLabel.setTextFill(Color.web("rgb(44, 62, 80)"));
        TextField courseNameField = new TextField();
        courseNameField.setPromptText("e.g., Data Structures");
        courseNameField.setStyle("-fx-font-size: 13px; -fx-padding: 8; -fx-control-inner-background: rgb(236, 240, 241); -fx-border-color: rgb(52, 152, 219); -fx-border-width: 2; -fx-text-fill: rgb(44, 62, 80);");
        
        Label codeLabel = new Label("Course Code:");
        codeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        codeLabel.setTextFill(Color.web("rgb(44, 62, 80)"));
        TextField courseCodeField = new TextField();
        courseCodeField.setPromptText("e.g., CSE201");
        courseCodeField.setStyle("-fx-font-size: 13px; -fx-padding: 8; -fx-control-inner-background: rgb(236, 240, 241); -fx-border-color: rgb(52, 152, 219); -fx-border-width: 2; -fx-text-fill: rgb(44, 62, 80);");
        
        Label creditLabel = new Label("Course Credit:");
        creditLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        creditLabel.setTextFill(Color.web("rgb(44, 62, 80)"));
        TextField creditField = new TextField();
        creditField.setPromptText("e.g., 3.0");
        creditField.setStyle("-fx-font-size: 13px; -fx-padding: 8; -fx-control-inner-background: rgb(236, 240, 241); -fx-border-color: rgb(52, 152, 219); -fx-border-width: 2; -fx-text-fill: rgb(44, 62, 80);");
        
        Label teacher1Label = new Label("Teacher 1 Name:");
        teacher1Label.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        teacher1Label.setTextFill(Color.web("rgb(44, 62, 80)"));
        TextField teacher1Field = new TextField();
        teacher1Field.setPromptText("e.g., Dr. Rahman");
        teacher1Field.setStyle("-fx-font-size: 13px; -fx-padding: 8; -fx-control-inner-background: rgb(236, 240, 241); -fx-border-color: rgb(52, 152, 219); -fx-border-width: 2; -fx-text-fill: rgb(44, 62, 80);");
        
        Label teacher2Label = new Label("Teacher 2 Name:");
        teacher2Label.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        teacher2Label.setTextFill(Color.web("rgb(44, 62, 80)"));
        TextField teacher2Field = new TextField();
        teacher2Field.setPromptText("e.g., Prof. Khan");
        teacher2Field.setStyle("-fx-font-size: 13px; -fx-padding: 8; -fx-control-inner-background: rgb(236, 240, 241); -fx-border-color: rgb(52, 152, 219); -fx-border-width: 2; -fx-text-fill: rgb(44, 62, 80);");
        
        Label gradeLabel = new Label("Grade:");
        gradeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        gradeLabel.setTextFill(Color.web("rgb(44, 62, 80)"));
        ComboBox<String> gradeComboBox = new ComboBox<>();
        gradeComboBox.setItems(FXCollections.observableArrayList(
            "A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "F"
        ));
        gradeComboBox.setPromptText("Select grade");
        gradeComboBox.setStyle("-fx-font-size: 13px; -fx-padding: 8; -fx-control-inner-background: rgb(236, 240, 241); -fx-border-color: rgb(52, 152, 219); -fx-border-width: 2; -fx-text-fill: rgb(44, 62, 80);");
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
        addButton.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(39, 174, 96), rgb(30, 132, 73)); " +
                          "-fx-text-fill: white; " +
                          "-fx-font-size: 15px; " +
                          "-fx-font-weight: bold; " +
                          "-fx-padding: 12 30; " +
                          "-fx-background-radius: 20; " +
                          "-fx-cursor: hand; " +
                          "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 2);");
        addButton.setMaxWidth(Double.MAX_VALUE);
        addButton.setOnMouseEntered(e -> addButton.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(30, 132, 73), rgb(25, 111, 60)); -fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; -fx-padding: 12 30; -fx-background-radius: 20; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 12, 0, 0, 4);"));
        addButton.setOnMouseExited(e -> addButton.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(39, 174, 96), rgb(30, 132, 73)); -fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; -fx-padding: 12 30; -fx-background-radius: 20; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 2);"));
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
        clearFormBtn.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(149, 165, 166), rgb(127, 140, 141)); " +
                             "-fx-text-fill: white; " +
                             "-fx-font-size: 13px; " +
                             "-fx-font-weight: bold; " +
                             "-fx-padding: 10 20; " +
                             "-fx-background-radius: 15; " +
                             "-fx-cursor: hand; " +
                             "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 4, 0, 0, 1);");
        clearFormBtn.setMaxWidth(Double.MAX_VALUE);
        clearFormBtn.setOnMouseEntered(e -> clearFormBtn.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(127, 140, 141), rgb(108, 117, 125)); -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 15; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0, 0, 2);"));
        clearFormBtn.setOnMouseExited(e -> clearFormBtn.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(149, 165, 166), rgb(127, 140, 141)); -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 15; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 4, 0, 0, 1);"));
        clearFormBtn.setOnAction(e -> {
            courseNameField.clear();
            courseCodeField.clear();
            gradeComboBox.setValue(null);
            creditField.clear();
            teacher1Field.clear();
            teacher2Field.clear();
        });
        
        // ===== DATABASE INTEGRATION SECTION =====
        Separator dbSep1 = new Separator();
        dbSep1.setStyle("-fx-border-color: rgb(52, 152, 219); -fx-border-width: 2 0 0 0; -fx-padding: 10 0;");
        
        Label dbTitle = new Label("💾 Database Operations");
        dbTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        dbTitle.setTextFill(Color.web("rgb(52, 152, 219)"));
        
        Button saveToDBBtn = new Button("💾 Save All to Database");
        saveToDBBtn.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(52, 152, 219), rgb(41, 128, 185)); " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 13px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 10 20; " +
                            "-fx-background-radius: 15; " +
                            "-fx-cursor: hand; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0, 0, 2);");
        saveToDBBtn.setMaxWidth(Double.MAX_VALUE);
        saveToDBBtn.setOnMouseEntered(e -> saveToDBBtn.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(41, 128, 185), rgb(35, 110, 160)); -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 15; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 3);"));
        saveToDBBtn.setOnMouseExited(e -> saveToDBBtn.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(52, 152, 219), rgb(41, 128, 185)); -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 15; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0, 0, 2);"));
        saveToDBBtn.setOnAction(e -> saveCoursesToDatabase());
        
        Button loadFromDBBtn = new Button("📂 Load from Database");
        loadFromDBBtn.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(22, 160, 133), rgb(19, 141, 117)); " +
                              "-fx-text-fill: white; " +
                              "-fx-font-size: 13px; " +
                              "-fx-font-weight: bold; " +
                              "-fx-padding: 10 20; " +
                              "-fx-background-radius: 15; " +
                              "-fx-cursor: hand; " +
                              "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0, 0, 2);");
        loadFromDBBtn.setMaxWidth(Double.MAX_VALUE);
        loadFromDBBtn.setOnMouseEntered(e -> loadFromDBBtn.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(19, 141, 117), rgb(16, 120, 99)); -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 15; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 3);"));
        loadFromDBBtn.setOnMouseExited(e -> loadFromDBBtn.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(22, 160, 133), rgb(19, 141, 117)); -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 15; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0, 0, 2);"));
        loadFromDBBtn.setOnAction(e -> loadCoursesFromDatabase());
        
        Button exportJSONBtn = new Button("📄 Export to JSON");
        exportJSONBtn.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(243, 156, 18), rgb(214, 137, 16)); " +
                              "-fx-text-fill: white; " +
                              "-fx-font-size: 13px; " +
                              "-fx-font-weight: bold; " +
                              "-fx-padding: 10 20; " +
                              "-fx-background-radius: 15; " +
                              "-fx-cursor: hand; " +
                              "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0, 0, 2);");
        exportJSONBtn.setMaxWidth(Double.MAX_VALUE);
        exportJSONBtn.setOnMouseEntered(e -> exportJSONBtn.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(214, 137, 16), rgb(184, 103, 12)); -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 15; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 3);"));
        exportJSONBtn.setOnMouseExited(e -> exportJSONBtn.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(243, 156, 18), rgb(214, 137, 16)); -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 15; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0, 0, 2);"));
        exportJSONBtn.setOnAction(e -> exportCoursesAsJSON());
        
        Button importJSONBtn = new Button("📋 Import from JSON");
        importJSONBtn.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(230, 126, 34), rgb(211, 84, 0)); " +
                              "-fx-text-fill: white; " +
                              "-fx-font-size: 13px; " +
                              "-fx-font-weight: bold; " +
                              "-fx-padding: 10 20; " +
                              "-fx-background-radius: 15; " +
                              "-fx-cursor: hand; " +
                              "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0, 0, 2);");
        importJSONBtn.setMaxWidth(Double.MAX_VALUE);
        importJSONBtn.setOnMouseEntered(e -> importJSONBtn.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(211, 84, 0), rgb(180, 71, 0)); -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 15; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 3);"));
        importJSONBtn.setOnMouseExited(e -> importJSONBtn.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(230, 126, 34), rgb(211, 84, 0)); -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 15; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0, 0, 2);"));
        importJSONBtn.setOnAction(e -> importCoursesFromJSON());
        
        Separator dbSep2 = new Separator();
        dbSep2.setStyle("-fx-border-color: rgb(52, 152, 219); -fx-border-width: 2 0 0 0; -fx-padding: 10 0;");
        
        inputPanel.getChildren().addAll(inputTitle, formGrid, addButton, clearFormBtn, 
                                        dbSep1, dbTitle, saveToDBBtn, loadFromDBBtn, 
                                        exportJSONBtn, importJSONBtn, dbSep2);
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
        certificate.setStyle("-fx-background-color: white; -fx-border-color: rgb(102, 126, 234); -fx-border-width: 5; " +
                            "-fx-border-radius: 15; -fx-background-radius: 15; " +
                            "-fx-effect: dropshadow(gaussian, rgba(102, 126, 234, 0.3), 20, 0, 0, 5);");
        
        Label awardTitle = new Label("🎓 ACADEMIC PERFORMANCE CERTIFICATE 🎓");
        awardTitle.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        awardTitle.setTextFill(Color.web("rgb(102, 126, 234)"));
        awardTitle.setTextAlignment(TextAlignment.CENTER);
        
        Label dateLabel = new Label("Date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
        dateLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        
        Separator sep1 = new Separator();
        sep1.setStyle("-fx-background-color: rgb(102, 126, 234);");
        
        Label studentInfo = new Label("This certifies that\n\nSaikat (ID: 2207002)\n\n" +
                                     "has successfully completed the following courses:");
        studentInfo.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        studentInfo.setTextAlignment(TextAlignment.CENTER);
        
        GridPane courseGrid = new GridPane();
        courseGrid.setHgap(15);
        courseGrid.setVgap(12);
        courseGrid.setAlignment(Pos.CENTER);
        courseGrid.setStyle("-fx-background-color: rgb(248, 249, 250); -fx-padding: 20; -fx-background-radius: 10;");
        
        Label[] headers = {
            new Label("Code"), new Label("Course Name"), new Label("Teacher 1"), 
            new Label("Teacher 2"), new Label("Credits"), new Label("Grade"), new Label("Points")
        };
        
        for (int i = 0; i < headers.length; i++) {
            headers[i].setFont(Font.font("Arial", FontWeight.BOLD, 13));
            headers[i].setTextFill(Color.web("rgb(102, 126, 234)"));
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
        sep2.setStyle("-fx-background-color: rgb(102, 126, 234);");
        
        double gpa = calculator.calculateGPA();
        String classification = getClassification(gpa);
        
        VBox gpaBox = new VBox(10);
        gpaBox.setAlignment(Pos.CENTER);
        gpaBox.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(102, 126, 234), rgb(85, 104, 211)); " +
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
        congratsLabel.setTextFill(Color.web("rgb(39, 174, 96)"));
        
        certificate.getChildren().addAll(awardTitle, dateLabel, sep1, studentInfo, courseGrid, 
                                        sep2, gpaBox, congratsLabel);
        
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button backButton = new Button("← Back to Entry");
        backButton.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(52, 152, 219), rgb(41, 128, 185)); -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12 30; -fx-background-radius: 15; -fx-cursor: hand;");
        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(41, 128, 185), rgb(35, 110, 160)); -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12 30; -fx-background-radius: 15; -fx-cursor: hand;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(52, 152, 219), rgb(41, 128, 185)); -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12 30; -fx-background-radius: 15; -fx-cursor: hand;"));
        backButton.setOnAction(e -> showMainScreen());
        
        Button homeButton = new Button("🏠 Home");
        homeButton.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(102, 126, 234), rgb(85, 104, 211)); -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12 30; -fx-background-radius: 15; -fx-cursor: hand;");
        homeButton.setOnMouseEntered(e -> homeButton.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(85, 104, 211), rgb(68, 75, 163)); -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12 30; -fx-background-radius: 15; -fx-cursor: hand;"));
        homeButton.setOnMouseExited(e -> homeButton.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(102, 126, 234), rgb(85, 104, 211)); -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12 30; -fx-background-radius: 15; -fx-cursor: hand;"));
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
    
    /**
     * Save all current courses to the database
     */
    private void saveCoursesToDatabase() {
        if (courseData.isEmpty()) {
            showAlert("Empty List", "No courses to save!", Alert.AlertType.WARNING);
            return;
        }
        
        try {
            // Save each course to database asynchronously
            int saveCount = 0;
            for (Course course : courseData) {
                if (course.getId() == -1) { // New course, not yet in DB
                    dbManager.insertCourse(course);
                    saveCount++;
                }
            }
            showAlert("Success", "Saved " + saveCount + " new courses to database!", Alert.AlertType.INFORMATION);
            System.out.println("[Main] Saved " + saveCount + " courses to database");
        } catch (Exception e) {
            showAlert("Error", "Failed to save to database: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    /**
     * Load all courses from the database
     */
    private void loadCoursesFromDatabase() {
        try {
            java.util.List<Course> dbCourses = dbManager.getAllCourses();
            
            if (dbCourses.isEmpty()) {
                showAlert("Empty Database", "No courses found in database!", Alert.AlertType.INFORMATION);
                return;
            }
            
            // Clear current data
            courseData.clear();
            calculator.clearCourses();
            
            // Load courses from database
            for (Course course : dbCourses) {
                courseData.add(course);
                calculator.addCourse(course);
            }
            
            showAlert("Success", "Loaded " + dbCourses.size() + " courses from database!", Alert.AlertType.INFORMATION);
            System.out.println("[Main] Loaded " + dbCourses.size() + " courses from database");
        } catch (Exception e) {
            showAlert("Error", "Failed to load from database: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    /**
     * Export courses to JSON file
     */
    private void exportCoursesAsJSON() {
        if (courseData.isEmpty()) {
            showAlert("Empty List", "No courses to export!", Alert.AlertType.WARNING);
            return;
        }
        
        try {
            String fileName = "courses_" + java.time.LocalDate.now() + ".json";
            boolean success = JSONHandler.exportToJSON(new java.util.ArrayList<>(courseData), fileName);
            
            if (success) {
                showAlert("Success", "Exported " + courseData.size() + " courses to\n" + fileName, Alert.AlertType.INFORMATION);
                System.out.println("[Main] Exported courses to " + fileName);
            } else {
                showAlert("Error", "Failed to export courses to JSON", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Error", "Export failed: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    /**
     * Import courses from JSON file
     */
    private void importCoursesFromJSON() {
        try {
            // Try to load from default or most recent JSON file
            String fileName = "courses_export.json";
            java.util.List<Course> importedCourses = JSONHandler.importFromJSON(fileName);
            
            if (importedCourses.isEmpty()) {
                showAlert("No Data", "Could not import courses from JSON file: " + fileName, Alert.AlertType.WARNING);
                return;
            }
            
            // Clear current data
            courseData.clear();
            calculator.clearCourses();
            
            // Load courses from JSON
            for (Course course : importedCourses) {
                courseData.add(course);
                calculator.addCourse(course);
            }
            
            showAlert("Success", "Imported " + importedCourses.size() + " courses from JSON!", Alert.AlertType.INFORMATION);
            System.out.println("[Main] Imported " + importedCourses.size() + " courses from JSON");
        } catch (Exception e) {
            showAlert("Error", "Import failed: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
