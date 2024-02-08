package dining_management;
import java.util.*;

public class StudentAccount {
    // Basic Information
    private final String studentId;
    private String name;
    private String email;

    // Account Details
    private double balance;
    private String accountStatus;

    // Dietary Preferences and Restrictions
    private Set<String> preferences;
    private Set<String> allergies;

    // Meal History
    private Map<Meal, Integer> mealHistory;
    private List<Transaction> transactionHistory;

    // Meal Ratings and Feedback
    private Map<Meal, List<Integer>> myMealRatings; // Mapping mealId to rating
    // budget management
    private double budgetLimit;
    private double amountSpent;

    // Constructors
    public StudentAccount(String studentId, String name, String email) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.balance = 0.0; // Assuming new accounts start with a zero balance
        this.accountStatus = "active"; // Default status
        this.preferences = new HashSet<>();
        this.allergies = new HashSet<>();
        this.mealHistory = new HashMap<>();
        this.myMealRatings = new HashMap<>();
        this.transactionHistory = new ArrayList<>();
        this.budgetLimit = -1; // default no limit
        this.amountSpent = 0;
    }

    public boolean updateBalance(double amount) {
        if ("suspended".equals(this.accountStatus)) {
            System.out.println("This account is suspended.");
            return false;
        }
        if(balance+amount>0) {
            this.balance += amount;
            return true;
        }
        else{
            return false;
        }
    }

    // Change account status
    public void changeAccountStatus(String newStatus) {
        this.accountStatus = newStatus;
    }

    // Add dietary preference
    public void addPreference(String preference) {
        if ("suspended".equals(this.accountStatus)) {
            System.out.println("This account is suspended.");
            return;
        }
        this.preferences.add(preference);
    }

    // Remove dietary preference
    public void removePreference(String preference) {
        if ("suspended".equals(this.accountStatus)) {
            System.out.println("This account is suspended.");
            return;
        }
        this.preferences.remove(preference);
    }

    // Add an allergy
    public void addAllergy(String allergy) {
        if ("suspended".equals(this.accountStatus)) {
            System.out.println("This account is suspended.");
            return;
        }
        this.allergies.add(allergy);
    }

    // Remove an allergy
    public void removeAllergy(String allergy) {
        if ("suspended".equals(this.accountStatus)) {
            System.out.println("This account is suspended.");
            return;
        }
        this.allergies.remove(allergy);
    }

    // Meal history and feedback
    public void addMealPurchase(MealPurchase purchase) {
        if ("suspended".equals(this.accountStatus)) {
            System.out.println("This account is suspended.");
            return;
        }
        Set<Meal> meals = purchase.getAllMeals();
        for (Meal meal : meals) {
            // Proceed with allergen check only if the meal has allergens and the user has allergies
            if (!meal.getAllergens().isEmpty() && !this.allergies.isEmpty()) {
                for (String allergen : meal.getAllergens()) {
                    if (this.allergies.contains(allergen)) {
                        System.out.println("Warning: Meal " + meal.getName() + " contains " + allergen + ", which you are allergic to. Removing this meal from your purchase.");
                        purchase.removeMeal(meal, purchase.getMealQuantity(meal)); // Adjust the method call as necessary
                        break; // Exit the allergen loop early since one match is enough to remove the meal
                    }
                }
            }
        }
        double amount = purchase.calculateTotalPrice();
        if(updateBalance(-amount) && ((amount + amountSpent) < budgetLimit)){
            amountSpent += amount;
            System.out.println(purchase.generateReceipt());
            Transaction mealPurchase = new Transaction(amount, purchase.generateReceipt());
            transactionHistory.add(mealPurchase);
            for (Meal meal : meals){
                if(meal != null) {
                    mealHistory.merge(meal, purchase.getMealQuantity(meal), Integer::sum);
                    System.out.println("Please rate " + meal.getName() + " from 1 to 10");
                    rateMeal(meal, Util.getInt());
                }
            }

        }
        else{
            System.out.println("Not enough balance or you have reached your budget limit");
        }
    }
    public void rateMeal(Meal meal, int rating) {
        if(0 < rating && rating < 11)
            meal.addRating(rating);
    }

    public void printTransactionHistory(int num){
        if ("suspended".equals(this.accountStatus)) {
            System.out.println("This account is suspended.");
            return;
        }
        for(int i = 1; i < num+1; i++){
            System.out.println("Amount: " +
                    transactionHistory.get(transactionHistory.size()-i).getAmount());
            System.out.println("Date: " +
                    transactionHistory.get(transactionHistory.size()-i).getDate());
            System.out.println("Description: " +
                    transactionHistory.get(transactionHistory.size()-i).getDescription());
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getAmountSpent() {
        return amountSpent;
    }

    public double getBudgetLimit() {
        return budgetLimit;
    }

    public void setBudgetLimit(double budgetLimit) {
        this.budgetLimit = budgetLimit;
    }

    public Set<String> getPreferences() {
        return preferences;
    }

    public Map<Meal, List<Integer>> getMealRatings() {
        return myMealRatings;
    }
}
