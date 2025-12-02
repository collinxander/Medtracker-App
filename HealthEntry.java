import java.time.LocalDate;

public class HealthEntry {
    private LocalDate date;
    private String bloodPressure;
    private int heartRate;
    private int glucose;
    private int weight;
    private String medications;
    private String notes;

    public HealthEntry(LocalDate date, String bloodPressure, int heartRate, int glucose, 
                       int weight, String medications, String notes) {
        this.date = date;
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
        this.glucose = glucose;
        this.weight = weight;
        this.medications = medications;
        this.notes = notes;
    }

    // Getters
    public LocalDate getDate() {
        return date;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public int getGlucose() {
        return glucose;
    }

    public int getWeight() {
        return weight;
    }

    public String getMedications() {
        return medications;
    }

    public String getNotes() {
        return notes;
    }

    // Setters
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public void setGlucose(int glucose) {
        this.glucose = glucose;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "HealthEntry{" +
                "date=" + date +
                ", bloodPressure='" + bloodPressure + '\'' +
                ", heartRate=" + heartRate +
                ", glucose=" + glucose +
                ", weight=" + weight +
                ", medications='" + medications + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
