package dining_management;
import java.util.*;

/** @noinspection FieldMayBeFinal, */
public class StudentAccount {
    // Basic Information
    private final String studentId;
    private String name;
    private String email;
    private double balance;
    private String accountStatus;

    // Dietary Preferences and Restrictions
    private Set<String> preferences;
    private Set<String> allergies;

    //History
    private Map<Meal, Integer> mealHistory;
    private List<Transaction> transactionHistory;

    // Meal Ratings and Feedback
    private Map<Meal, List<Integer>> myMealRatings;
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
    public void addMealPurchase(MealPurchase purchase, List<Meal> menu) {
        if ("suspended".equals(this.accountStatus)) {
            System.out.println("This account is suspended.");
            return;
        }
        Set<Meal> meals = purchase.getAllMeals();
        if(meals == null)
            return;
        boolean hasAllergy = false;
        Set<Meal> mealsToRemove = new HashSet<>();
        for (Meal meal : meals) {
            if (!meal.getAllergens().isEmpty() && !this.allergies.isEmpty()) {
                for (String allergen : meal.getAllergens()) {
                    if (this.allergies.contains(allergen)) {
                        hasAllergy = true;
                        System.out.println("Warning: Meal " + meal.getName() + " contains " + allergen +
                                ", which you are allergic to. Removing this meal from your purchase.");
                        mealsToRemove.add(meal);
                        break;
                    }
                }
            }
        }
        mealsToRemove.forEach(meal -> purchase.removeMeal(meal, purchase.getMealQuantity(meal)));

        double amount = purchase.calculateTotalPrice();
        if(NutritionBalancer.balanceNutrition(this, purchase, menu, purchase.getServings()) || hasAllergy){
            System.out.println("Would you like to reconsider your choices? Enter 1 for yes, anything else for no.");
            int choice = Util.getInt();
            if(choice == 1)
                return;
        }
        if(updateBalance(-amount) && (((amount + amountSpent) <= budgetLimit) || budgetLimit <=0)){
            amountSpent += amount;
            System.out.println(purchase.generateReceipt());
            Transaction mealPurchase = new Transaction(amount, purchase.generateReceipt());
            transactionHistory.add(mealPurchase);
            for (Meal meal : meals){
                if(meal != null) {
                    meal.addPurchase(purchase.getMealQuantity(meal));
                    mealHistory.merge(meal, purchase.getMealQuantity(meal), Integer::sum);
                    System.out.println("Please rate " + meal.getName() + " from 1 to 10, or -1 for skip");
                    rateMeal(meal, Util.getInt());
                }
            }
        }
        else{
            System.out.println("Not enough balance or you have reached your budget limit");
        }
    }
    public void rateMeal(Meal meal, int rating) {
        if(0 < rating && rating < 11) {
            meal.addRating(rating);
            if(!myMealRatings.containsKey(meal))
                myMealRatings.put(meal, new ArrayList<>());
            myMealRatings.get(meal).add(rating);
        }
    }

    public Transaction getTransactionHistory(int num){
        if ("suspended".equals(this.accountStatus)) {
            System.out.println("This account is suspended.");
            return null;
        }
        return transactionHistory.get(num);

    }

    public double getBalance(){
        return balance;
    }

    public String getName() {
        if ("suspended".equals(this.accountStatus)) {
            System.out.println("This account is suspended.");
            return "";
        }
        return name;
    }

    public String getStudentId() {
        if ("suspended".equals(this.accountStatus)) {
            System.out.println("This account is suspended.");
            return "";
        }
        return studentId;
    }

    public void setName(String name) {
        if ("suspended".equals(this.accountStatus)) {
            System.out.println("This account is suspended.");
            return;
        }
        this.name = name;
    }

    public String getEmail(){
        if ("suspended".equals(this.accountStatus)) {
            System.out.println("This account is suspended.");
            return "";
        }
        return email;
    }

    public void setEmail(String email) {
        if ("suspended".equals(this.accountStatus)) {
            System.out.println("This account is suspended.");
            return;
        }
        this.email = email;
    }

    public double getAmountSpent() {
        if ("suspended".equals(this.accountStatus)) {
            System.out.println("This account is suspended.");
            return 0;
        }
        return amountSpent;
    }

    public double getBudgetLimit() {
        if ("suspended".equals(this.accountStatus)) {
            System.out.println("This account is suspended.");
            return 0;
        }
        return budgetLimit;
    }

    public void setBudgetLimit(double budgetLimit) {
        if ("suspended".equals(this.accountStatus)) {
            System.out.println("This account is suspended.");
            return;
        }
        this.budgetLimit = budgetLimit;
    }

    public Set<String> getPreferences() {
        if ("suspended".equals(this.accountStatus)) {
            System.out.println("This account is suspended.");
            return null;
        }
        return preferences;
    }

    public Map<Meal, List<Integer>> getMealRatings() {
        if ("suspended".equals(this.accountStatus)) {
            System.out.println("This account is suspended.");
            return null;
        }
        return myMealRatings;
    }

    public Set<String> getAllergies() {
        return allergies;
    }

    public Map<Meal, Integer> getMealHistory() {
        return mealHistory;
    }
}
