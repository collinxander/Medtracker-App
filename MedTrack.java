import javafx.application.Application;
import javafx.stage.Stage;
import java.util.*;

public class MedTrack extends Application {
    private Stage primaryStage;
    private List<HealthEntry> healthLogs = new ArrayList<>();
    private final String DATA_FILE = "medtrack_data.csv";
    
    private HomeScreen homeScreen;
    private LogEntryScreen logEntryScreen;
    private HistoryScreen historyScreen;
    private ChartsScreen chartsScreen;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("MedTrack - Your Personal Health Log");
        primaryStage.setWidth(900);
        primaryStage.setHeight(700);
        
        loadData();
        initializeScreens();
        showHomeScreen();
        primaryStage.show();
    }

    private void initializeScreens() {
        homeScreen = new HomeScreen(this);
        logEntryScreen = new LogEntryScreen(this);
        historyScreen = new HistoryScreen(this);
        chartsScreen = new ChartsScreen(this);
    }

    public void showHomeScreen() {
        primaryStage.setScene(homeScreen.getScene());
    }

    public void showLogEntryScreen() {
        logEntryScreen.refresh();
        primaryStage.setScene(logEntryScreen.getScene());
    }

    public void showHistoryScreen() {
        historyScreen.refresh();
        primaryStage.setScene(historyScreen.getScene());
    }

    public void showChartsScreen() {
        chartsScreen.refresh();
        primaryStage.setScene(chartsScreen.getScene());
    }

    public void addHealthEntry(HealthEntry entry) {
        healthLogs.add(entry);
        saveData();
    }

    public List<HealthEntry> getHealthLogs() {
        return healthLogs;
    }

    private void saveData() {
        DataManager.saveData(DATA_FILE, healthLogs);
    }

    private void loadData() {
        healthLogs = DataManager.loadData(DATA_FILE);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
