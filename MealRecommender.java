package dining_management;
import java.util.*;
import java.util.stream.Collectors;
import java.lang.Math;
import static dining_management.Util.*;


public class MealRecommender {
    private static final double RATING_WEIGHT = 0.6;
    private static final double POPULARITY_WEIGHT = 0.2;
    private static final double SIMILARITY_WEIGHT = 0.3;
    // Recommend meals based on user preferences
    public static List<Meal> recommendMealsNutrition(String nutrient, List<Meal> allMeals, boolean increase) {
        List<Meal> list = new ArrayList<>();
        for (Meal meal : allMeals) {
            if (meal.getNutritionalInfo().containsKey(nutrient)) {
                list.add(meal);
            }
        }
        list.sort((meal1, meal2) -> {
            double nutrientAmount1 = meal1.getNutritionalInfo().get(nutrient);
            double nutrientAmount2 = meal2.getNutritionalInfo().get(nutrient);
            return increase ? Double.compare(nutrientAmount2, nutrientAmount1) : Double.compare(nutrientAmount1, nutrientAmount2);
        });
        return list;
    }
    public static List<Meal> recommendMeals(StudentAccount user, List<Meal> allMeals) {
        Set<String> preferredTags = extractPreferredTags(user);
        Map<String, Double> preferredNutrition = extractPreferredNutrition(user);

        // Calculate scores for each meal
        Map<Meal, Double> mealScores = new HashMap<>();
        for (Meal meal : allMeals) {
            if (mealScores.put(meal, RATING_WEIGHT * calculatePersonalCompositeScore(meal, user.getMealRatings(), user.getMealHistory()) +
                    POPULARITY_WEIGHT * sigmoid(meal.getAverageRating()-5) +
                    SIMILARITY_WEIGHT * calculateSimilarityScore(meal, preferredTags, preferredNutrition)) != null) {
                throw new IllegalStateException("Duplicate key");
            }
        }

        // Filter out meals not meeting user's allergies and sort by calculated score
        List<Map.Entry<Meal, Double>> toSort = new ArrayList<>();
        for (Map.Entry<Meal, Double> entry : mealScores.entrySet()) {
            if (Collections.disjoint(entry.getKey().getTags(), user.getAllergies())) {
                toSort.add(entry);
            }
        }
        toSort.sort(Map.Entry.<Meal, Double>comparingByValue().reversed());
        List<Meal> list = new ArrayList<>();
        for (Map.Entry<Meal, Double> entry : toSort) {
            Meal key = entry.getKey();
            list.add(key);
        }
        return list;
    }

    private static double averagePersonalRating(Meal meal, Map<Meal, List<Integer>> mealRatings) {
        List<Integer> ratings = mealRatings.get(meal);
        if (ratings == null || ratings.isEmpty()) {
            return meal.getAverageRating();
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
        return sigmoid((double) commonTagsCount / preferredTags.size()); // Normalize by the size of preferred tags
    }

    private static double calculateNutritionScore(Map<String, Double> mealNutrition, Map<String, Double> preferredNutrition) {
        double score = 0.0;
        for (String key : preferredNutrition.keySet()) {
            double preferredValue = preferredNutrition.getOrDefault(key, 0.0);
            double mealValue = mealNutrition.getOrDefault(key, 0.0);
            score += Math.pow(preferredValue - mealValue, 2);
        }
        return sigmoid(1 / (Math.sqrt(score)-70)); // Euclidean distance, lower score means closer match
    }


    private static double calculateSimilarityScore(Meal meal, Set<String> preferredTags, Map<String, Double> preferredNutrition) {
        // Calculate and return a score based on how closely the meal's characteristics match the user's preferences
        double tagScore = calculateTagScore(meal.getTags(), preferredTags);
        double nutritionScore = calculateNutritionScore(meal.getNutritionalInfo(), preferredNutrition);
        return sigmoid(tagScore + nutritionScore);
    }

    public static List<Meal> getHighlyRatedMeals(Map<Meal, List<Integer>> mealRatings, Map<Meal, Integer> mealPurchaseCount) {
        List<Meal> allMeals = new ArrayList<>(mealRatings.keySet());

        // Calculate a composite score for each meal and sort by that score
        List<Meal> sortedMeals = allMeals.stream()
                .sorted((meal1, meal2) -> {
                    double score1 = calculatePersonalCompositeScore(meal1, mealRatings, mealPurchaseCount);
                    double score2 = calculatePersonalCompositeScore(meal2, mealRatings, mealPurchaseCount);
                    return Double.compare(score2, score1); // Descending order
                })
                .collect(Collectors.toList());

        // Return the top 10 or fewer meals based on their composite scores
        return sortedMeals.subList(0, Math.min(sortedMeals.size(), 10));
    }

    private static double calculatePersonalCompositeScore(Meal meal, Map<Meal, List<Integer>> mealRatings, Map<Meal, Integer> mealPurchaseCount) {
        double averageRating = averagePersonalRating(meal, mealRatings) - 5;
        int purchases = mealPurchaseCount.getOrDefault(meal, 0);
        return sigmoid(averageRating * 0.8 + purchases * 0.2);
    }
}






