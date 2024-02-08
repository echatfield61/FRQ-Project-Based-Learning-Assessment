package dining_management;
import java.util.*;
import java.util.stream.Collectors;

public class MealRecommender {
    private static final double RATING_WEIGHT = 0.7;
    private static final double PURCHASE_WEIGHT = 0.3;
    private static final int MIN_PURCHASE_THRESHOLD = 0;
    // Recommend meals based on user preferences
    public static List<Meal> recommendMealsBasedOnNutrition(String nutrient, List<Meal> allMeals, boolean increase) {
        return allMeals.stream()
                .filter(meal -> meal.getNutritionalInfo().containsKey(nutrient))
                .sorted((meal1, meal2) -> {
                    double nutrientAmount1 = meal1.getNutritionalInfo().get(nutrient);
                    double nutrientAmount2 = meal2.getNutritionalInfo().get(nutrient);
                    return increase ? Double.compare(nutrientAmount2, nutrientAmount1) : Double.compare(nutrientAmount1, nutrientAmount2);
                })
                .collect(Collectors.toList());
    }
    public static List<Meal> recommendMeals(StudentAccount user, List<Meal> allMeals) {
        Set<String> userAllergies = user.getAllergies(); // Assume getAllergies() method exists in StudentAccount

        List<Meal> filteredMeals = allMeals.stream()
                .filter(meal -> !Collections.disjoint(meal.getTags(), user.getPreferences()) // Matches user preferences
                        && Collections.disjoint(meal.getAllergens(), userAllergies) // No matching allergens
                        && !hasLowPersonalRatings(meal, user.getMealRatings())) // Filter out low-rated meals
                .sorted((meal1, meal2) -> {
                    // Prioritize by least frequently purchased, then by highest average rating
                    int frequencyComparison = compareByPurchaseFrequency(meal1, meal2, user.getMealRatings());
                    if (frequencyComparison != 0) return frequencyComparison;

                    double publicRatingComparison = Double.compare(meal2.getAverageRating(), meal1.getAverageRating());
                    if (publicRatingComparison != 0) return (int)publicRatingComparison;

                    return compareByPersonalRating(meal1, meal2, user.getMealRatings());
                })
                .collect(Collectors.toList());

        return filteredMeals;
    }

    public static Map<Meal, Double> generateRecommendationScores(StudentAccount user, List<Meal> allMeals) {
        Map<Meal, Double> recommendationScores = new HashMap<>();

        for (Meal meal : allMeals) {
            if (!Collections.disjoint(meal.getAllergens(), user.getAllergies())) {
                // Skip meals containing allergens the user is allergic to
                continue;
            }

            double score = calculateScore(meal, user);
            recommendationScores.put(meal, score);
        }

        return recommendationScores;
    }

    private static double calculateScore(Meal meal, StudentAccount user) {
        double userRatingScore = averagePersonalRating(meal, user.getMealRatings());
        double purchasePenalty = meal.getAmountBought() >= MIN_PURCHASE_THRESHOLD ? 1.0 : (double) meal.getAmountBought() / MIN_PURCHASE_THRESHOLD;
        double publicRatingScore = meal.getAverageRating();

        // Calculate weighted score, adjusting for purchase frequency
        double score = (RATING_WEIGHT * publicRatingScore + PURCHASE_WEIGHT * userRatingScore) * purchasePenalty;
        return score;
    }

    public static boolean hasLowPersonalRatings(Meal meal, Map<Meal, List<Integer>> mealRatings) {
        List<Integer> ratings = mealRatings.get(meal);
        if (ratings == null || ratings.isEmpty()) {
            return false; // No personal ratings means not low
        }
        return ratings.stream().mapToInt(Integer::intValue).average().orElse(0) < 3;
    }

    public static int compareByPurchaseFrequency(Meal meal1, Meal meal2, Map<Meal, List<Integer>> mealRatings) {
        List<Integer> ratings1 = mealRatings.getOrDefault(meal1, Collections.emptyList());
        List<Integer> ratings2 = mealRatings.getOrDefault(meal2, Collections.emptyList());
        return Integer.compare(ratings1.size(), ratings2.size());
    }
    private static int compareByPersonalRating(Meal meal1, Meal meal2, Map<Meal, List<Integer>> mealRatings) {
        double avgRatingMeal1 = averagePersonalRating(meal1, mealRatings);
        double avgRatingMeal2 = averagePersonalRating(meal2, mealRatings);

        // Higher personal ratings come first
        return Double.compare(avgRatingMeal2, avgRatingMeal1);
    }
    public static double averagePersonalRating(Meal meal, Map<Meal, List<Integer>> mealRatings) {
        List<Integer> ratings = mealRatings.get(meal);
        if (ratings == null || ratings.isEmpty()) {
            return 0; // Assumes 0 as default for no personal ratings
        }
        return ratings.stream().mapToInt(Integer::intValue).average().orElse(0);
    }
}

