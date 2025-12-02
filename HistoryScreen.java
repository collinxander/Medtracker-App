import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.List;

public class HistoryScreen {
    private MedTrack app;
    private Scene scene;
    private TableView<HealthEntry> tableView;

    public HistoryScreen(MedTrack app) {
        this.app = app;
        this.scene = createScene();
    }

    private Scene createScene() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #f5f5f5;");

        // Header
        Label header = new Label("Health History");
        header.setStyle("-fx-font-size: 28; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Statistics Panel
        HBox statsPanel = createStatsPanel();

        // Table
        tableView = new TableView<>();
        tableView.setStyle("-fx-font-size: 12;");
        setupTable();

        // Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button backButton = new Button("Back to Home");
        backButton.setStyle("-fx-font-size: 14; -fx-padding: 10 30; -fx-background-color: #95a5a6; -fx-text-fill: white; -fx-cursor: hand;");
        backButton.setOnAction(e -> app.showHomeScreen());

        Button deleteButton = new Button("Delete Selected");
        deleteButton.setStyle("-fx-font-size: 14; -fx-padding: 10 30; -fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand;");
        deleteButton.setOnAction(e -> deleteSelectedEntry());

        buttonBox.getChildren().addAll(deleteButton, backButton);

        root.getChildren().addAll(header, statsPanel, new Label("Recent Entries:"), tableView, buttonBox);
        root.setVgrow(tableView, javafx.scene.layout.Priority.ALWAYS);

        return new Scene(root);
    }

    private HBox createStatsPanel() {
        HBox panel = new HBox(20);
        panel.setPadding(new Insets(15));
        panel.setStyle("-fx-border-color: #e0e0e0; -fx-border-radius: 10; -fx-background-color: #ecf0f1;");
        panel.setAlignment(Pos.CENTER_LEFT);

        Label totalLabel = new Label("Total Entries: ");
        totalLabel.setStyle("-fx-font-size: 13; -fx-font-weight: bold;");
        Label totalValue = new Label("0");
        totalValue.setStyle("-fx-font-size: 13;");

        Label avgHRLabel = new Label("Avg Heart Rate: ");
        avgHRLabel.setStyle("-fx-font-size: 13; -fx-font-weight: bold;");
        Label avgHRValue = new Label("--");
        avgHRValue.setStyle("-fx-font-size: 13;");

        Label avgGlucoseLabel = new Label("Avg Glucose: ");
        avgGlucoseLabel.setStyle("-fx-font-size: 13; -fx-font-weight: bold;");
        Label avgGlucoseValue = new Label("--");
        avgGlucoseValue.setStyle("-fx-font-size: 13;");

        // Update stats when refresh is called
        List<HealthEntry> entries = app.getHealthLogs();
        if (!entries.isEmpty()) {
            totalValue.setText(String.valueOf(entries.size()));
            int avgHR = (int) entries.stream().mapToInt(HealthEntry::getHeartRate).average().orElse(0);
            int avgGlucose = (int) entries.stream().mapToInt(HealthEntry::getGlucose).average().orElse(0);
            avgHRValue.setText(avgHR + " bpm");
            avgGlucoseValue.setText(avgGlucose + " mg/dL");
        } else {
            totalValue.setText("0");
        }

        panel.getChildren().addAll(
                totalLabel, totalValue,
                new Separator(javafx.geometry.Orientation.VERTICAL),
                avgHRLabel, avgHRValue,
                new Separator(javafx.geometry.Orientation.VERTICAL),
                avgGlucoseLabel, avgGlucoseValue
        );

        return panel;
    }

    private void setupTable() {
        // Date Column
        TableColumn<HealthEntry, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDate().toString()));
        dateCol.setPrefWidth(100);

        // Blood Pressure Column
        TableColumn<HealthEntry, String> bpCol = new TableColumn<>("BP");
        bpCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getBloodPressure()));
        bpCol.setPrefWidth(80);

        // Heart Rate Column
        TableColumn<HealthEntry, Integer> hrCol = new TableColumn<>("Heart Rate (bpm)");
        hrCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getHeartRate()));
        hrCol.setPrefWidth(100);

        // Glucose Column
        TableColumn<HealthEntry, Integer> glucoseCol = new TableColumn<>("Glucose (mg/dL)");
        glucoseCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getGlucose()));
        glucoseCol.setPrefWidth(120);

        // Weight Column
        TableColumn<HealthEntry, Integer> weightCol = new TableColumn<>("Weight (kg)");
        weightCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getWeight()));
        weightCol.setPrefWidth(100);

        // Medications Column
        TableColumn<HealthEntry, String> medicationsCol = new TableColumn<>("Medications");
        medicationsCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMedications()));
        medicationsCol.setPrefWidth(150);

        // Notes Column
        TableColumn<HealthEntry, String> notesCol = new TableColumn<>("Notes");
        notesCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNotes()));
        notesCol.setPrefWidth(150);

        tableView.getColumns().addAll(dateCol, bpCol, hrCol, glucoseCol, weightCol, medicationsCol, notesCol);
    }

    private void deleteSelectedEntry() {
        HealthEntry selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Entry");
            alert.setHeaderText("Delete this health entry?");
            alert.setContentText("This action cannot be undone.");
            
            if (alert.showAndWait().get() == ButtonType.OK) {
                app.getHealthLogs().remove(selected);
                // Manually save after deletion
                DataManager.saveData("medtrack_data.csv", app.getHealthLogs());
                refresh();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("Please select an entry to delete");
            alert.showAndWait();
        }
    }

    public void refresh() {
        List<HealthEntry> entries = app.getHealthLogs();
        ObservableList<HealthEntry> observableEntries = FXCollections.observableArrayList(entries);
        tableView.setItems(observableEntries);
    }

    public Scene getScene() {
        return scene;
    }
}
