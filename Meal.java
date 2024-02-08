package dining_management;
import java.util.*;

public class Meal {
    private String name;
    private String description;
    private Map<String, Double> nutritionalInfo;
    private Set<String> tags;
    private double price;
    private Set<String> allergens;
    private List<Integer> ratings;
    private int amountBought;

    public Meal(String name, double price) {
        this.name = name;
        this.description = "No description available";
        this.nutritionalInfo = new HashMap<>();
        this.price = price;
        this.allergens = new HashSet<>();
        this.ratings = new ArrayList<>();
        this.tags = new HashSet<>();
        this.amountBought = 0;
    }
    public Meal(String name, String description, Map<String, Double> nutritionalInfo,
                double price, Set<String> allergens, Set<String> tags) {
        this.name = name;
        this.description = description;
        this.nutritionalInfo = nutritionalInfo;
        this.price = price;
        this.allergens = allergens;
        this.ratings = new ArrayList<>();
        this.tags = tags;
        this.amountBought = 0;
    }

    public double getAverageRating(){
        if(ratings.size()<1)
            return 0;
        double sum = 0;
        for(int rating : ratings){
            sum += rating;
        }
        return sum / ratings.size();
    }
    public void addPurchase(int amount){
        amountBought += amount;
    }
    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void addRating(int rating) {
        this.ratings.add(rating);
    }

    public Set<String> getTags() {
        return tags;
    }

    public int getAmountBought() {
        return amountBought;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Double> getNutritionalInfo() {
        return nutritionalInfo;
    }

    public Set<String> getAllergens() {
        return allergens;
    }

    public void addTag(String tag){
        tags.add(tag);
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addAllergen(String allergen) {
        this.allergens.add(allergen);
    }

    public void setNutritionalInfo(Map<String, Double> nutritionalInfo) {
        this.nutritionalInfo = nutritionalInfo;
    }
}
