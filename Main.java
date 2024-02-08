package dining_management;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.*;

public class Main {
    public static void main(String[] awgs){
        // Initialize Meals
        Map<String, Double> nutritionalInfoMeal1 = new HashMap<>();
        nutritionalInfoMeal1.put("Calories", 500.0);
        nutritionalInfoMeal1.put("Protein", 30.0);

        Map<String, Double> nutritionalInfoMeal2 = new HashMap<>();
        nutritionalInfoMeal2.put("Calories", 700.0);
        nutritionalInfoMeal2.put("Protein", 25.0);

        Map<String, Double> nutritionalInfoMeal3 = new HashMap<>();
        nutritionalInfoMeal3.put("Calories", 900.0);
        nutritionalInfoMeal3.put("Protein", 35.0);

        Set<String> allergensMeal1 = new HashSet<>(Arrays.asList("Nuts"));
        Set<String> tagsMeal1 = new HashSet<>(Arrays.asList("Vegan", "Low-Carb"));

        Set<String> allergensMeal2 = new HashSet<>(Arrays.asList("Dairy"));
        Set<String> tagsMeal2 = new HashSet<>(Arrays.asList("High-Protein", "Gluten-Free"));

        Set<String> allergensMeal3 = new HashSet<>();
        Set<String> tagsMeal3 = new HashSet<>(Arrays.asList("High-Protein", "Vegan"));

        Meal meal1 = new Meal("Vegan Delight", "A vegan meal.",
                nutritionalInfoMeal1, 15.0, allergensMeal1, tagsMeal1);
        Meal meal2 = new Meal("Protein Powerhouse", "High protein meal",
                nutritionalInfoMeal2, 20.0, allergensMeal2, tagsMeal2);
        Meal meal3 = new Meal("Vegan Burger", "A vegan burger.",
                nutritionalInfoMeal3, 25.0, allergensMeal3, tagsMeal3);
        // Initialize StudentAccount with preferences and allergies
        String studentId = "zw-25442";
        String name = "Zongyi Wan";
        String email = "zwan@stu.socsd.org";
        StudentAccount student = new StudentAccount(studentId, name, email);

        student.addPreference("Vegan");
        student.addAllergy("Nuts");
        student.updateBalance(150);
        student.setBudgetLimit(150);
        // Add ratings for meals
        for(int rating : new ArrayList<>(Arrays.asList(7, 8 ,9, 10, 8, 9, 7, 10)))
            meal1.addRating(rating);
        for(int rating : new ArrayList<>(Arrays.asList(1, 4 ,6, 7, 2, 3, 7, 4)))
            meal2.addRating(rating);
        for(int rating : new ArrayList<>(Arrays.asList(7, 8 ,9, 10, 8, 9, 7, 10)))
            meal3.addRating(rating);

        ArrayList<Meal> menu = new ArrayList<>(Arrays.asList(meal2, meal1, meal3));

        // make a new purchase
        MealPurchase newPurchase = new MealPurchase();
        newPurchase.addMeal(meal1, 1);
        newPurchase.addMeal(meal2);
        newPurchase.addMeal(meal3, 2);
        student.addMealPurchase(newPurchase);
        student.addMealPurchase(newPurchase);
        student.printTransactionHistory(2);

        System.out.println(MealRecommender.recommendMeals(student, menu).get(0).getName());
    }
}
