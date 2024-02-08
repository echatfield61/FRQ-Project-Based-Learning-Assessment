package dining_management;
import java.util.*;
import java.util.stream.Collectors;

public class MealRecommender {
    // Recommend meals based on user preferences
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

