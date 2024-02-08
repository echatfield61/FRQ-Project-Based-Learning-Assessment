package dining_management;
import java.util.*;
import java.util.stream.Collectors;

public class MealRecommender {
    // Recommend meals based on user preferences
    public List<Meal> recommendMeals(StudentAccount user, List<Meal> allMeals) {
        List<Meal> filteredMeals = allMeals.stream()
                .filter(meal -> !Collections.disjoint(meal.getTags(), user.getPreferences()) && !hasLowPersonalRatings(meal, user.getMealRatings()))
                .collect(Collectors.toList());

        // Sort by a combination of factors: least frequently purchased by the user,
        // highest public average rating, and if applicable, highest personal average rating
        filteredMeals.sort((meal1, meal2) -> {
            int frequencyComparison = compareByPurchaseFrequency(meal1, meal2, user.getMealRatings());
            if (frequencyComparison != 0) {
                return frequencyComparison; // Less frequently purchased first
            }
            int publicRatingComparison = -Double.compare(meal1.getAverageRating(), meal2.getAverageRating());
            if (publicRatingComparison != 0) {
                return publicRatingComparison; // Higher public average rating first
            }
            return -Double.compare(averagePersonalRating(meal1, user.getMealRatings()), averagePersonalRating(meal2, user.getMealRatings()));
        });

        return filteredMeals;
    }

    private boolean hasLowPersonalRatings(Meal meal, Map<Meal, List<Integer>> mealRatings) {
        List<Integer> ratings = mealRatings.get(meal);
        if (ratings == null || ratings.isEmpty()) {
            return false; // No personal ratings means not low
        }
        return ratings.stream().mapToInt(Integer::intValue).average().orElse(0) < 3;
    }

    private int compareByPurchaseFrequency(Meal meal1, Meal meal2, Map<Meal, List<Integer>> mealRatings) {
        List<Integer> ratings1 = mealRatings.getOrDefault(meal1, Collections.emptyList());
        List<Integer> ratings2 = mealRatings.getOrDefault(meal2, Collections.emptyList());
        return Integer.compare(ratings1.size(), ratings2.size());
    }

    private double averagePersonalRating(Meal meal, Map<Meal, List<Integer>> mealRatings) {
        List<Integer> ratings = mealRatings.get(meal);
        if (ratings == null || ratings.isEmpty()) {
            return 0; // Assumes 0 as default for no personal ratings
        }
        return ratings.stream().mapToInt(Integer::intValue).average().orElse(0);
    }
}

