package dining_management;

import java.util.*;

public class NutritionBalancer {
    private static final double AVERAGE_CALORIES_PER_SERVING = 700.0;
    private static final double threshold = 0.1;
    private static final Map<String, Double> RECOMMENDED_INTAKES_PER_SERVING = Map.of(
            "Protein", 50.0,  // grams per serving
            "Carbs", 100.0,
            "Fats", 40.0
    );

    public static boolean balanceNutrition(StudentAccount user, MealPurchase purchase, List<Meal> allMeals, int servings) {
        Map<String, Double> totalNutrition = aggregateNutrition(purchase);
        double totalCalories = totalNutrition.getOrDefault("Calories", 0.0);
        int estimatedServings = (servings > 0) ? servings :
                (int) Math.round(totalCalories / AVERAGE_CALORIES_PER_SERVING);

        Map<String, Double> adjustedTargets = new HashMap<>();
        boolean needsChange = false;
        RECOMMENDED_INTAKES_PER_SERVING.forEach((nutrient, amountPerServing) -> {
            adjustedTargets.put(nutrient, amountPerServing * estimatedServings);
        });
        for (Map.Entry<String, Double> entry : totalNutrition.entrySet()) {
            String nutrient = entry.getKey();
            Double totalAmount = entry.getValue();
            if (!"Calories".equals(nutrient)) { // Skip calories since it was used for estimation
                double adjustedAmount = adjustedTargets.getOrDefault(nutrient, 0.0);
                if (totalAmount < adjustedAmount * (1 - threshold)) {
                    needsChange = true;
                    System.out.println("Increase " + nutrient + " to meet the nutritional needs of " + estimatedServings + " servings.");
                    List<Meal> substitutes = MealRecommender.recommendMealsBasedOnNutrition(nutrient, allMeals, true).subList(0, 2);
                    System.out.print("for a healthier diet, consider replacing meals that are low in " + nutrient + " with ");
                    // substitutes = MealRecommender.recommendMeals(user, substitutes);
                    for (Meal meal : substitutes)
                        System.out.print(meal.getName() + ", ");
                } else if (totalAmount > adjustedAmount * (1 + threshold)) {
                    needsChange = true;
                    System.out.println("Decrease " + nutrient + " to avoid exceeding the nutritional needs of " + estimatedServings + " servings.");

                    List<Meal> substitutes = MealRecommender.recommendMealsBasedOnNutrition(nutrient, allMeals, false).subList(0, 2);
                    System.out.print("for a healthier diet, consider replacing meals that are high in " + nutrient + " with ");
                    // substitutes = MealRecommender.recommendMeals(user, substitutes);
                    for (Meal meal : substitutes)
                        System.out.print(meal.getName() + ", ");
                }
                System.out.println("\n");
            }
        }
        return needsChange;
    }

    private static Map<String, Double> aggregateNutrition(MealPurchase purchase) {
        Map<String, Double> totalNutrition = new HashMap<>();
        for (Meal meal : purchase.getAllMeals()) {
            for (Map.Entry<String, Double> entry : meal.getNutritionalInfo().entrySet()) {
                totalNutrition.merge(entry.getKey(), entry.getValue(), Double::sum);
            }
        }
        return totalNutrition;
    }

}
