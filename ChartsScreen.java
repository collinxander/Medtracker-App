import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.time.LocalDate;
import java.util.List;

public class ChartsScreen {
    private MedTrack app;
    private Scene scene;

    public ChartsScreen(MedTrack app) {
        this.app = app;
        this.scene = createScene();
    }

    private Scene createScene() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #f5f5f5;");

        // Header
        Label header = new Label("Health Trends");
        header.setStyle("-fx-font-size: 28; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Charts Container
        VBox chartsContainer = new VBox(20);
        chartsContainer.setPadding(new Insets(20));
        chartsContainer.setStyle("-fx-border-color: #e0e0e0; -fx-border-radius: 10; -fx-background-color: #fff;");

        // Heart Rate Chart
        LineChart<Number, Number> hrChart = createHeartRateChart();
        Label hrLabel = new Label("Heart Rate Trend");
        hrLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        VBox hrBox = new VBox(10);
        hrBox.getChildren().addAll(hrLabel, hrChart);

        // Glucose Chart
        LineChart<Number, Number> glucoseChart = createGlucoseChart();
        Label glucoseLabel = new Label("Glucose Trend");
        glucoseLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        VBox glucoseBox = new VBox(10);
        glucoseBox.getChildren().addAll(glucoseLabel, glucoseChart);

        // Weight Chart
        LineChart<Number, Number> weightChart = createWeightChart();
        Label weightLabel = new Label("Weight Trend");
        weightLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        VBox weightBox = new VBox(10);
        weightBox.getChildren().addAll(weightLabel, weightChart);

        chartsContainer.getChildren().addAll(hrBox, glucoseBox, weightBox);

        // Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button backButton = new Button("Back to Home");
        backButton.setStyle("-fx-font-size: 14; -fx-padding: 10 30; -fx-background-color: #95a5a6; -fx-text-fill: white; -fx-cursor: hand;");
        backButton.setOnAction(e -> app.showHomeScreen());

        buttonBox.getChildren().addAll(backButton);

        // Scroll pane for charts
        javafx.scene.control.ScrollPane scrollPane = new javafx.scene.control.ScrollPane(chartsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-control-inner-background: #f5f5f5;");

        root.getChildren().addAll(header, scrollPane, buttonBox);
        root.setVgrow(scrollPane, javafx.scene.layout.Priority.ALWAYS);

        return new Scene(root);
    }

    private LineChart<Number, Number> createHeartRateChart() {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Days");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Heart Rate (bpm)");

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Heart Rate Over Time");
        chart.setPrefHeight(300);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Heart Rate");

        List<HealthEntry> entries = app.getHealthLogs();
        LocalDate baseDate = entries.isEmpty() ? LocalDate.now() : entries.get(0).getDate();

        for (int i = 0; i < entries.size(); i++) {
            HealthEntry entry = entries.get(i);
            long daysDiff = java.time.temporal.ChronoUnit.DAYS.between(baseDate, entry.getDate());
            series.getData().add(new XYChart.Data<>(daysDiff, entry.getHeartRate()));
        }

        chart.getData().add(series);
        return chart;
    }

    private LineChart<Number, Number> createGlucoseChart() {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Days");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Glucose (mg/dL)");

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Glucose Over Time");
        chart.setPrefHeight(300);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Glucose");

        List<HealthEntry> entries = app.getHealthLogs();
        LocalDate baseDate = entries.isEmpty() ? LocalDate.now() : entries.get(0).getDate();

        for (int i = 0; i < entries.size(); i++) {
            HealthEntry entry = entries.get(i);
            long daysDiff = java.time.temporal.ChronoUnit.DAYS.between(baseDate, entry.getDate());
            series.getData().add(new XYChart.Data<>(daysDiff, entry.getGlucose()));
        }

        chart.getData().add(series);
        return chart;
    }

    private LineChart<Number, Number> createWeightChart() {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Days");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Weight (kg)");

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Weight Over Time");
        chart.setPrefHeight(300);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Weight");

        List<HealthEntry> entries = app.getHealthLogs();
        LocalDate baseDate = entries.isEmpty() ? LocalDate.now() : entries.get(0).getDate();

        for (int i = 0; i < entries.size(); i++) {
            HealthEntry entry = entries.get(i);
            long daysDiff = java.time.temporal.ChronoUnit.DAYS.between(baseDate, entry.getDate());
            series.getData().add(new XYChart.Data<>(daysDiff, entry.getWeight()));
        }

        chart.getData().add(series);
        return chart;
    }

    public void refresh() {
        // Charts are recreated each time, so no explicit refresh needed
        // The scene will be updated with latest data
    }

    public Scene getScene() {
        return scene;
    }
}
