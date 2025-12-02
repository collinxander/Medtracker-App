import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.time.LocalDate;

public class LogEntryScreen {
    private MedTrack app;
    private Scene scene;

    // Form fields
    private DatePicker datePicker;
    private TextField bpField;
    private TextField hrField;
    private TextField glucoseField;
    private TextField weightField;
    private TextArea medicationsArea;
    private TextArea notesArea;
    private Label errorLabel;

    public LogEntryScreen(MedTrack app) {
        this.app = app;
        this.scene = createScene();
    }

    private Scene createScene() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #f5f5f5;");

        // Header
        Label header = new Label("Log New Entry");
        header.setStyle("-fx-font-size: 28; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Form Container
        VBox formBox = new VBox(15);
        formBox.setPadding(new Insets(20));
        formBox.setStyle("-fx-border-color: #e0e0e0; -fx-border-radius: 10; -fx-background-color: #fff;");

        // Date field
        VBox dateBox = new VBox(5);
        Label dateLabel = new Label("Date:");
        dateLabel.setStyle("-fx-font-weight: bold;");
        datePicker = new DatePicker(LocalDate.now());
        datePicker.setStyle("-fx-font-size: 12;");
        dateBox.getChildren().addAll(dateLabel, datePicker);

        // Blood Pressure field
        VBox bpBox = new VBox(5);
        Label bpLabel = new Label("Blood Pressure (e.g., 120/80):");
        bpLabel.setStyle("-fx-font-weight: bold;");
        bpField = new TextField();
        bpField.setPromptText("120/80");
        bpField.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        bpBox.getChildren().addAll(bpLabel, bpField);

        // Heart Rate field
        VBox hrBox = new VBox(5);
        Label hrLabel = new Label("Heart Rate (bpm):");
        hrLabel.setStyle("-fx-font-weight: bold;");
        hrField = new TextField();
        hrField.setPromptText("72");
        hrField.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        hrBox.getChildren().addAll(hrLabel, hrField);

        // Glucose field
        VBox glucoseBox = new VBox(5);
        Label glucoseLabel = new Label("Glucose (mg/dL):");
        glucoseLabel.setStyle("-fx-font-weight: bold;");
        glucoseField = new TextField();
        glucoseField.setPromptText("95");
        glucoseField.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        glucoseBox.getChildren().addAll(glucoseLabel, glucoseField);

        // Weight field
        VBox weightBox = new VBox(5);
        Label weightLabel = new Label("Weight (kg):");
        weightLabel.setStyle("-fx-font-weight: bold;");
        weightField = new TextField();
        weightField.setPromptText("70");
        weightField.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        weightBox.getChildren().addAll(weightLabel, weightField);

        // Medications field
        VBox medicationsBox = new VBox(5);
        Label medicationsLabel = new Label("Medications:");
        medicationsLabel.setStyle("-fx-font-weight: bold;");
        medicationsArea = new TextArea();
        medicationsArea.setPrefRowCount(3);
        medicationsArea.setWrapText(true);
        medicationsArea.setStyle("-fx-font-size: 12; -fx-control-inner-background: #fff; -fx-padding: 8;");
        medicationsBox.getChildren().addAll(medicationsLabel, medicationsArea);

        // Notes field
        VBox notesBox = new VBox(5);
        Label notesLabel = new Label("Notes:");
        notesLabel.setStyle("-fx-font-weight: bold;");
        notesArea = new TextArea();
        notesArea.setPrefRowCount(3);
        notesArea.setWrapText(true);
        notesArea.setStyle("-fx-font-size: 12; -fx-control-inner-background: #fff; -fx-padding: 8;");
        notesBox.getChildren().addAll(notesLabel, notesArea);

        // Error label
        errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12;");

        // Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button saveButton = new Button("Save Entry");
        saveButton.setStyle("-fx-font-size: 14; -fx-padding: 10 30; -fx-background-color: #27ae60; -fx-text-fill: white; -fx-cursor: hand;");
        saveButton.setOnAction(e -> saveEntry());

        Button backButton = new Button("Back to Home");
        backButton.setStyle("-fx-font-size: 14; -fx-padding: 10 30; -fx-background-color: #95a5a6; -fx-text-fill: white; -fx-cursor: hand;");
        backButton.setOnAction(e -> app.showHomeScreen());

        buttonBox.getChildren().addAll(saveButton, backButton);

        // Add all form fields to formBox
        formBox.getChildren().addAll(
                dateBox, bpBox, hrBox, glucoseBox, weightBox, medicationsBox, notesBox, errorLabel
        );

        // Wrap in ScrollPane for long content
        ScrollPane scrollPane = new ScrollPane(formBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-control-inner-background: #f5f5f5;");

        root.getChildren().addAll(header, scrollPane, buttonBox);
        return new Scene(root);
    }

    private void saveEntry() {
        errorLabel.setText("");
        
        try {
            // Validate inputs
            LocalDate date = datePicker.getValue();
            String bp = bpField.getText().trim();
            int hr = Integer.parseInt(hrField.getText().trim());
            int glucose = Integer.parseInt(glucoseField.getText().trim());
            int weight = Integer.parseInt(weightField.getText().trim());
            String medications = medicationsArea.getText().trim();
            String notes = notesArea.getText().trim();

            // Validate BP format (basic check)
            if (!bp.matches("\\d{1,3}/\\d{1,3}")) {
                throw new IllegalArgumentException("Blood Pressure must be in format XX/XX");
            }

            // Validate ranges
            if (hr < 30 || hr > 200) {
                throw new IllegalArgumentException("Heart Rate must be between 30 and 200 bpm");
            }
            if (glucose < 20 || glucose > 600) {
                throw new IllegalArgumentException("Glucose must be between 20 and 600 mg/dL");
            }
            if (weight < 20 || weight > 300) {
                throw new IllegalArgumentException("Weight must be between 20 and 300 kg");
            }

            // Create and save entry
            HealthEntry entry = new HealthEntry(date, bp, hr, glucose, weight, medications, notes);
            app.addHealthEntry(entry);

            // Show success and clear form
            errorLabel.setStyle("-fx-text-fill: #27ae60;");
            errorLabel.setText("✓ Entry saved successfully!");
            clearForm();

            // Navigate back after brief delay
            new Thread(() -> {
                try {
                    Thread.sleep(1500);
                    app.showHomeScreen();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();

        } catch (NumberFormatException e) {
            errorLabel.setText("❌ Error: Heart Rate, Glucose, and Weight must be numbers");
            errorLabel.setStyle("-fx-text-fill: #e74c3c;");
        } catch (IllegalArgumentException e) {
            errorLabel.setText("❌ Error: " + e.getMessage());
            errorLabel.setStyle("-fx-text-fill: #e74c3c;");
        }
    }

    private void clearForm() {
        datePicker.setValue(LocalDate.now());
        bpField.clear();
        hrField.clear();
        glucoseField.clear();
        weightField.clear();
        medicationsArea.clear();
        notesArea.clear();
    }

    public void refresh() {
        clearForm();
        errorLabel.setText("");
    }

    public Scene getScene() {
        return scene;
    }
}
