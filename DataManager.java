import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataManager {
    
    public static void saveData(String fileName, List<HealthEntry> healthLogs) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("date,bloodPressure,heartRate,glucose,weight,medications,notes");
            for (HealthEntry entry : healthLogs) {
                writer.printf("%s,%s,%d,%d,%d,\"%s\",\"%s\"%n",
                    entry.getDate(),
                    entry.getBloodPressure(),
                    entry.getHeartRate(),
                    entry.getGlucose(),
                    entry.getWeight(),
                    escapeCSV(entry.getMedications()),
                    escapeCSV(entry.getNotes()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<HealthEntry> loadData(String fileName) {
        List<HealthEntry> healthLogs = new ArrayList<>();
        File file = new File(fileName);
        
        if (!file.exists()) {
            return healthLogs;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            reader.readLine(); // Skip header
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (parts.length >= 7) {
                    try {
                        LocalDate date = LocalDate.parse(parts[0].trim());
                        String bloodPressure = parts[1].trim();
                        int heartRate = Integer.parseInt(parts[2].trim());
                        int glucose = Integer.parseInt(parts[3].trim());
                        int weight = Integer.parseInt(parts[4].trim());
                        String medications = removeQuotes(parts[5].trim());
                        String notes = removeQuotes(parts[6].trim());
                        
                        HealthEntry entry = new HealthEntry(date, bloodPressure, heartRate, 
                                                            glucose, weight, medications, notes);
                        healthLogs.add(entry);
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping invalid entry: " + line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return healthLogs;
    }

    private static String escapeCSV(String value) {
        if (value == null) return "";
        return value.replace("\"", "\\\"");
    }

    private static String removeQuotes(String value) {
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }
}
