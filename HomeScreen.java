import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class HomeScreen {
    private MedTrack app;
    private Scene scene;

    public HomeScreen(MedTrack app) {
        this.app = app;
        this.scene = createScene();
    }

    private Scene createScene() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #f5f5f5;");

        // Header
        Label header = new Label("MedTrack");
        header.setStyle("-fx-font-size: 36; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        Label subtitle = new Label("Your Personal Health Log");
        subtitle.setStyle("-fx-font-size: 14; -fx-text-fill: #7f8c8d;");

        VBox headerBox = new VBox(5);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.getChildren().addAll(header, subtitle);

        // Navigation Cards
        HBox navCards = new HBox(20);
        navCards.setAlignment(Pos.CENTER);

        VBox logCard = createNavCard("📋 Log Vitals", "Record your health data", () -> app.showLogEntryScreen());
        VBox historyCard = createNavCard("📊 View History", "See past entries", () -> app.showHistoryScreen());
        VBox chartsCard = createNavCard("📈 Health Charts", "Visualize trends", () -> app.showChartsScreen());

        navCards.getChildren().addAll(logCard, historyCard, chartsCard);

        // Info Card
        VBox infoCard = new VBox(10);
        infoCard.setPadding(new Insets(20));
        infoCard.setStyle("-fx-border-color: #e0e0e0; -fx-border-radius: 10; -fx-background-color: #fff;");
        Label infoTitle = new Label("💡 Quick Tips");
        infoTitle.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        Label infoText = new Label("Track your vitals regularly to monitor your health. Share your data with your doctor during visits.");
        infoText.setWrapText(true);
        infoCard.getChildren().addAll(infoTitle, infoText);

        root.getChildren().addAll(headerBox, navCards, infoCard);
        return new Scene(root);
    }

    private VBox createNavCard(String title, String desc, Runnable action) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-border-color: #3498db; -fx-border-radius: 10; -fx-background-color: #ecf0f1; -fx-cursor: hand;");
        card.setPrefWidth(200);
        card.setAlignment(Pos.TOP_CENTER);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        Label descLabel = new Label(desc);
        descLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #7f8c8d;");
        descLabel.setWrapText(true);

        card.getChildren().addAll(titleLabel, descLabel);
        card.setOnMouseClicked(e -> action.run());
        return card;
    }

    public Scene getScene() {
        return scene;
    }
}
