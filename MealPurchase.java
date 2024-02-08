package dining_management;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MealPurchase {
    private final LocalDateTime purchaseDateTime;
    private Map<Meal, Integer> mealsOrdered;

    // Constructor
    public MealPurchase() {
        this.purchaseDateTime = LocalDateTime.now();
        this.mealsOrdered = new HashMap<>();
    }

    // Add a meal to the order
    public void addMeal(Meal meal, int quantity) {
        mealsOrdered.merge(meal, quantity, Integer::sum);
    }
    public void removeMeal(Meal meal, int quantity) {
        if (mealsOrdered.containsKey(meal)) {
            int currentQuantity = mealsOrdered.get(meal);
            int newQuantity = currentQuantity - quantity;

            if (newQuantity > 0) {
                mealsOrdered.put(meal, newQuantity); // Update with new quantity
            } else {
                mealsOrdered.remove(meal); // Remove meal entirely if quantity is 0 or negative
            }
        } else {
            System.out.println("Meal not found in purchase.");
        }
    }

    public Set<Meal> getAllMeals() {
        return mealsOrdered.keySet();
    }
    // Calculate total price of the purchase
    public double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (Entry<Meal, Integer> entry : mealsOrdered.entrySet()) {
            Meal meal = entry.getKey();
            Integer quantity = entry.getValue();
            totalPrice += meal.getPrice() * quantity;
        }
        return totalPrice;
    }

    // Generate a detailed receipt for the purchase
    public String generateReceipt() {
        StringBuilder receipt = new StringBuilder("Purchase Receipt\n");
        receipt.append("Purchase Date: ").append(purchaseDateTime.toString()).append("\n");
        for (Entry<Meal, Integer> entry : mealsOrdered.entrySet()) {
            Meal meal = entry.getKey();
            Integer quantity = entry.getValue();
            receipt.append("Meal: ").append(meal.getName())
                    .append(", Quantity: ").append(quantity)
                    .append(", Price per unit: $").append(meal.getPrice())
                    .append(", Subtotal: $").append(meal.getPrice() * quantity).append("\n");
        }
        receipt.append("Total Price: $").append(calculateTotalPrice()).append("\n");
        return receipt.toString();
    }

    public int getMealQuantity(Meal meal){
        return mealsOrdered.get(meal);
    }
}
