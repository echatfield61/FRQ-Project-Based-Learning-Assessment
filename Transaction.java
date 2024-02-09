package dining_management;
import java.time.LocalDateTime;
public class Transaction {
    private final String date;
    private final double amount;
    private final String description;
    public Transaction(double amount){
        this.date = String.valueOf(LocalDateTime.now());
        this.amount = amount;
        this.description = "No description available";
    }
    public Transaction(double amount, String description){
        this.date = String.valueOf(LocalDateTime.now());
        this.amount = amount;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        String output = "";

        output += ("Amount: "  + amount + "\n");
        output += ("Date: " + date + "\n");
        output += ("Description" + description + "\n");
        return output;
    }
}
