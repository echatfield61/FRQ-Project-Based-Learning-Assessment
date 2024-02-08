package dining_management;
import java.util.*;

public class StudentAccount {
    // Basic Information
    private String studentId;
    private String name;
    private String email;

    // Account Details
    private double balance;
    private String accountStatus;

    // Dietary Preferences and Restrictions
    private Set<String> dietaryPreferences;
    private Set<String> allergies;

    // Meal History
    private List<MealPurchase> mealHistory;

    // Meal Ratings and Feedback
    private Map<String, Integer> myMealRatings; // Mapping mealId to rating


    // Constructors
    public StudentAccount(String studentId, String name, String email) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.balance = 0.0; // Assuming new accounts start with a zero balance
        this.accountStatus = "active"; // Default status
        this.dietaryPreferences = new HashSet<>();
        this.allergies = new HashSet<>();
        this.mealHistory = new ArrayList<>();
        this.myMealRatings = new HashMap<>();
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
    public void addDietaryPreference(String preference) {
        if ("suspended".equals(this.accountStatus)) {
            System.out.println("This account is suspended.");
            return;
        }
        this.dietaryPreferences.add(preference);
    }

    // Remove dietary preference
    public void removeDietaryPreference(String preference) {
        if ("suspended".equals(this.accountStatus)) {
            System.out.println("This account is suspended.");
            return;
        }
        this.dietaryPreferences.remove(preference);
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

    // Getters and setters for accountStatus, balance, etc., as needed


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
                        purchase.removeMeal(meal, purchase.getMealQuantity(meal)); 
                        break;
                    }
                }
            }
        }
        double amount = purchase.calculateTotalPrice();
        if(updateBalance(-amount)){
            mealHistory.add(purchase);
            System.out.println(purchase.generateReceipt());
            for (Meal meal : meals){
                if(meal != null) {
                    System.out.println("Please rate " + meal.getName() + " from 1 to 10");
                    rateMeal(meal, Util.getInt());
                }
            }

        }
        else{
            System.out.println("Not enough balance");
        }
    }
    public void rateMeal(Meal meal, int rating) {
        meal.addRating(rating);
    }

    public void printMealHistory(int num){
        if ("suspended".equals(this.accountStatus)) {
            System.out.println("This account is suspended.");
            return;
        }
        for(int i = 1; i < num+1; i++){
            mealHistory.get(mealHistory.size()-i).generateReceipt();
        }
    }

    public double getBalance(){
        return balance;
    }

}
