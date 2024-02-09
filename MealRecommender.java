package dining_management;
import java.util.*;
import java.util.stream.Collectors;

public class MealRecommender {
    private static final double RATING_WEIGHT = 0.5;
    private static final double PURCHASE_WEIGHT = 0.5;
    private static final int MIN_PURCHASE_THRESHOLD = 0;
    private static final double POPULARITY_WEIGHT = 0.2;
    private static final double SIMILARITY_WEIGHT = 0.3;
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
        Set<String> preferredTags = extractPreferredTags(user);
        Map<String, Double> preferredNutrition = extractPreferredNutrition(user);

        // Calculate comprehensive scores for each meal
        Map<Meal, Double> mealScores = allMeals.stream().collect(Collectors.toMap(meal -> meal, meal ->
                RATING_WEIGHT * calculatePersonalRatingScore(meal, user.getMealRatings()) +
                        POPULARITY_WEIGHT * meal.getAverageRating() +
                        SIMILARITY_WEIGHT * calculateSimilarityScore(meal, preferredTags, preferredNutrition)
        ));

        // Filter out meals not meeting user's dietary restrictions and sort by calculated score
        return mealScores.entrySet().stream()
                .filter(entry -> !Collections.disjoint(entry.getKey().getTags(), user.getPreferences())) // Assumes user tags include dietary restrictions
                .sorted(Map.Entry.<Meal, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
    private static double calculateScore(Meal meal, StudentAccount user) {
        double userRatingScore = averagePersonalRating(meal, user.getMealRatings());
        double purchasePenalty = meal.getAmountBought() >= MIN_PURCHASE_THRESHOLD ? 1.0 : (double) meal.getAmountBought() / MIN_PURCHASE_THRESHOLD;
        double publicRatingScore = meal.getAverageRating();

        // Calculate weighted score, adjusting for purchase frequency
        double score = (RATING_WEIGHT * publicRatingScore + PURCHASE_WEIGHT * userRatingScore) * purchasePenalty;
        return score;
    }

    private static double averagePersonalRating(Meal meal, Map<Meal, List<Integer>> mealRatings) {
        List<Integer> ratings = mealRatings.get(meal);
        if (ratings == null || ratings.isEmpty()) {
            return 0; // Assumes 0 as default for no personal ratings
        }
        return ratings.stream().mapToInt(Integer::intValue).average().orElse(0);
    }

    private static Set<String> extractPreferredTags(StudentAccount user) {
        List<Meal> preferredMeals = getHighlyRatedMeals(user.getMealRatings(), user.getMealHistory());
        Set<String> preferredTags = new HashSet<>();
        for (Meal meal : preferredMeals) {
            preferredTags.addAll(meal.getTags());
        }
        return preferredTags;
    }

    private static Map<String, Double> extractPreferredNutrition(StudentAccount user) {
        List<Meal> preferredMeals = getHighlyRatedMeals(user.getMealRatings(), user.getMealHistory());
        Map<String, Double> aggregateNutrition = new HashMap<>();

        for (Meal meal : preferredMeals) {
            Map<String, Double> nutrition = meal.getNutritionalInfo();
            for (Map.Entry<String, Double> entry : nutrition.entrySet()) {
                aggregateNutrition.merge(entry.getKey(), entry.getValue(), Double::sum);
            }
        }

        // Calculate average if needed
        aggregateNutrition.forEach((key, value) -> aggregateNutrition.put(key, value / preferredMeals.size()));
        return aggregateNutrition;
    }

    private static double calculateTagScore(Set<String> mealTags, Set<String> preferredTags) {
        long commonTagsCount = mealTags.stream().filter(preferredTags::contains).count();
        return (double) commonTagsCount / preferredTags.size(); // Normalize by the size of preferred tags
    }

    private static double calculateNutritionScore(Map<String, Double> mealNutrition, Map<String, Double> preferredNutrition) {
        double score = 0.0;
        for (String key : preferredNutrition.keySet()) {
            double preferredValue = preferredNutrition.getOrDefault(key, 0.0);
            double mealValue = mealNutrition.getOrDefault(key, 0.0);
            score += Math.pow(preferredValue - mealValue, 2);
        }
        return Math.sqrt(score); // Euclidean distance, lower score means closer match
    }

    private static double calculatePersonalRatingScore(Meal meal, Map<Meal, List<Integer>> mealRatings) {
        List<Integer> ratings = mealRatings.get(meal);
        if (ratings == null || ratings.isEmpty()) {
            return 0;
        }
        double averageRating = ratings.stream().mapToInt(Integer::intValue).average().orElse(0.0);
        return averageRating;
    }

    private static double calculateSimilarityScore(Meal meal, Set<String> preferredTags, Map<String, Double> preferredNutrition) {
        // Calculate and return a score based on how closely the meal's characteristics match the user's preferences
        double tagScore = calculateTagScore(meal.getTags(), preferredTags);
        double nutritionScore = calculateNutritionScore(meal.getNutritionalInfo(), preferredNutrition);
        return tagScore + nutritionScore; // Adjust scoring logic as needed
    }

    public static List<Meal> getHighlyRatedMeals(Map<Meal, List<Integer>> mealRatings, Map<Meal, Integer> mealPurchaseCount) {
        List<Meal> allMeals = new ArrayList<>(mealRatings.keySet());

        // Calculate a composite score for each meal and sort by that score
        List<Meal> sortedMeals = allMeals.stream()
                .sorted((meal1, meal2) -> {
                    double score1 = calculateCompositeScore(meal1, mealRatings, mealPurchaseCount);
                    double score2 = calculateCompositeScore(meal2, mealRatings, mealPurchaseCount);
                    return Double.compare(score2, score1); // Descending order
                })
                .collect(Collectors.toList());

        // Return the top 5 or fewer meals based on their composite scores
        return sortedMeals.subList(0, Math.min(sortedMeals.size(), 5));
    }

    private static double calculateCompositeScore(Meal meal, Map<Meal, List<Integer>> mealRatings, Map<Meal, Integer> mealPurchaseCount) {
        double averageRating = averagePersonalRating(meal, mealRatings);
        int purchases = mealPurchaseCount.getOrDefault(meal, 0);
        // Assuming the balance between rating and purchase count needs to be defined; adjust weights as necessary
        return averageRating * 0.7 + purchases * 0.3; // Example weighting
    }
}






