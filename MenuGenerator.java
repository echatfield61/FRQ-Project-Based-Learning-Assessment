package dining_management;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MenuGenerator {
    // a series of dishes generated by ChatGPT
    private List<Meal> menu;
    public MenuGenerator(){
        this.menu = new ArrayList<>();
        // Dish 1: Classic Cheeseburger
        Map<String, Double> nutritionalInfoClassicCheeseburger = new HashMap<>();
        nutritionalInfoClassicCheeseburger.put("Calories", 530.0);
        nutritionalInfoClassicCheeseburger.put("Protein", 30.0);
        nutritionalInfoClassicCheeseburger.put("Carbs", 40.0);
        nutritionalInfoClassicCheeseburger.put("Fats", 25.0);

        Set<String> allergensClassicCheeseburger = new HashSet<>(Arrays.asList("Gluten", "Dairy"));
        Set<String> tagsClassicCheeseburger = new HashSet<>(Arrays.asList("FastFood", "American", "Beef"));
        ArrayList<Integer> ratingsClassicCheeseburger = new ArrayList<>(Arrays.asList(8, 9, 7, 8, 9, 6, 5, 7, 8, 9));

        Meal classicCheeseburger = new Meal("Classic Cheeseburger",
                "Juicy beef patty with cheddar cheese, lettuce, tomato, and onions on a sesame bun.",
                nutritionalInfoClassicCheeseburger, 8.99, allergensClassicCheeseburger, tagsClassicCheeseburger);

        for(int rating : ratingsClassicCheeseburger){
            classicCheeseburger.addRating(rating);
        }

// Dish 2: Vegan Quinoa Salad
        Map<String, Double> nutritionalInfoVeganQuinoaSalad = new HashMap<>();
        nutritionalInfoVeganQuinoaSalad.put("Calories", 350.0);
        nutritionalInfoVeganQuinoaSalad.put("Protein", 12.0);
        nutritionalInfoVeganQuinoaSalad.put("Carbs", 50.0);
        nutritionalInfoVeganQuinoaSalad.put("Fats", 10.0);

        Set<String> allergensVeganQuinoaSalad = new HashSet<>();
        Set<String> tagsVeganQuinoaSalad = new HashSet<>(Arrays.asList("Vegan", "Salad", "Healthy"));
        ArrayList<Integer> ratingsVeganQuinoaSalad = new ArrayList<>(Arrays.asList(10, 9, 10, 8, 9, 10, 9, 10, 8, 9));

        Meal veganQuinoaSalad = new Meal("Vegan Quinoa Salad",
                "A refreshing mix of quinoa, avocado, black beans, corn, cherry tomatoes, and a lime dressing.",
                nutritionalInfoVeganQuinoaSalad, 10.50, allergensVeganQuinoaSalad, tagsVeganQuinoaSalad);

        for(int rating : ratingsVeganQuinoaSalad){
            veganQuinoaSalad.addRating(rating);
        }


// Dish 3: Spicy Ramen Bowl
        Map<String, Double> nutritionalInfoSpicyRamenBowl = new HashMap<>();
        nutritionalInfoSpicyRamenBowl.put("Calories", 600.0);
        nutritionalInfoSpicyRamenBowl.put("Protein", 25.0);
        nutritionalInfoSpicyRamenBowl.put("Carbs", 85.0);
        nutritionalInfoSpicyRamenBowl.put("Fats", 20.0);

        Set<String> allergensSpicyRamenBowl = new HashSet<>(Arrays.asList("Gluten", "Eggs", "Soy"));
        Set<String> tagsSpicyRamenBowl = new HashSet<>(Arrays.asList("Asian", "Spicy", "Pork"));
        ArrayList<Integer> ratingsSpicyRamenBowl = new ArrayList<>(Arrays.asList(7, 8, 6, 5, 4, 7, 8, 5, 6, 7));

        Meal spicyRamenBowl = new Meal("Spicy Ramen Bowl",
                "Rich pork broth with tender noodles, topped with spicy ground pork, boiled egg, and green onions.",
                nutritionalInfoSpicyRamenBowl, 12.00, allergensSpicyRamenBowl, tagsSpicyRamenBowl);

        for(int rating : ratingsSpicyRamenBowl){
            spicyRamenBowl.addRating(rating);
        }

// Dish 4: Margherita Pizza
        Map<String, Double> nutritionalInfoMargheritaPizza = new HashMap<>();
        nutritionalInfoMargheritaPizza.put("Calories", 400.0);
        nutritionalInfoMargheritaPizza.put("Protein", 18.0);
        nutritionalInfoMargheritaPizza.put("Carbs", 45.0);
        nutritionalInfoMargheritaPizza.put("Fats", 18.0);

        Set<String> allergensMargheritaPizza = new HashSet<>(Arrays.asList("Gluten", "Dairy"));
        Set<String> tagsMargheritaPizza = new HashSet<>(Arrays.asList("Italian", "Vegetarian", "Pizza"));
        ArrayList<Integer> ratingsMargheritaPizza = new ArrayList<>(Arrays.asList(4, 5, 3, 2, 6, 4, 5, 3, 4, 5));

        Meal margheritaPizza = new Meal("Margherita Pizza",
                "Classic Italian pizza with fresh mozzarella, tomatoes, basil, and a drizzle of olive oil on a thin crust.",
                nutritionalInfoMargheritaPizza, 11.25, allergensMargheritaPizza, tagsMargheritaPizza);

        for(int rating : ratingsMargheritaPizza){
            margheritaPizza.addRating(rating);
        }

// Dish 5: Chicken Caesar Salad
        Map<String, Double> nutritionalInfoChickenCaesarSalad = new HashMap<>();
        nutritionalInfoChickenCaesarSalad.put("Calories", 470.0);
        nutritionalInfoChickenCaesarSalad.put("Protein", 32.0);
        nutritionalInfoChickenCaesarSalad.put("Carbs", 22.0);
        nutritionalInfoChickenCaesarSalad.put("Fats", 28.0);

        Set<String> allergensChickenCaesarSalad = new HashSet<>(Arrays.asList("Gluten", "Dairy", "Eggs"));
        Set<String> tagsChickenCaesarSalad = new HashSet<>(Arrays.asList("Salad", "Chicken", "LowCarb"));
        ArrayList<Integer> ratingsChickenCaesarSalad = new ArrayList<>(Arrays.asList(8, 9, 7, 8, 9, 7, 6, 8, 9, 7));

        Meal chickenCaesarSalad = new Meal("Chicken Caesar Salad",
                "Crisp romaine lettuce with grilled chicken breast, croutons, parmesan cheese, and Caesar dressing.",
                nutritionalInfoChickenCaesarSalad, 9.75, allergensChickenCaesarSalad, tagsChickenCaesarSalad);

        for(int rating : ratingsChickenCaesarSalad){
            chickenCaesarSalad.addRating(rating);
        }


// Dish 6: Grilled Salmon with Asparagus
        Map<String, Double> nutritionalInfoGrilledSalmon = new HashMap<>();
        nutritionalInfoGrilledSalmon.put("Calories", 520.0);
        nutritionalInfoGrilledSalmon.put("Protein", 34.0);
        nutritionalInfoGrilledSalmon.put("Carbs", 20.0);
        nutritionalInfoGrilledSalmon.put("Fats", 34.0);

        Set<String> allergensGrilledSalmon = new HashSet<>(Arrays.asList("Fish"));
        Set<String> tagsGrilledSalmon = new HashSet<>(Arrays.asList("Seafood", "Healthy", "Grill"));
        ArrayList<Integer> ratingsGrilledSalmon = new ArrayList<>(Arrays.asList(9, 9, 8, 10, 7, 8, 9, 10, 8, 9));

        Meal grilledSalmonWithAsparagus = new Meal("Grilled Salmon with Asparagus",
                "Perfectly grilled salmon served with tender asparagus and a lemon butter sauce.",
                nutritionalInfoGrilledSalmon, 15.00, allergensGrilledSalmon, tagsGrilledSalmon);

        for(int rating : ratingsGrilledSalmon){
            grilledSalmonWithAsparagus.addRating(rating);
        }

// Dish 7: Butternut Squash Soup
        Map<String, Double> nutritionalInfoButternutSquashSoup = new HashMap<>();
        nutritionalInfoButternutSquashSoup.put("Calories", 180.0);
        nutritionalInfoButternutSquashSoup.put("Protein", 3.0);
        nutritionalInfoButternutSquashSoup.put("Carbs", 35.0);
        nutritionalInfoButternutSquashSoup.put("Fats", 5.0);

        Set<String> allergensButternutSquashSoup = new HashSet<>();
        Set<String> tagsButternutSquashSoup = new HashSet<>(Arrays.asList("Vegan", "Soup", "LowCalorie"));
        ArrayList<Integer> ratingsButternutSquashSoup = new ArrayList<>(Arrays.asList(10, 9, 8, 10, 7, 9, 10, 9, 8, 10));

        Meal butternutSquashSoup = new Meal("Butternut Squash Soup",
                "Creamy butternut squash soup seasoned with nutmeg and garnished with pumpkin seeds.",
                nutritionalInfoButternutSquashSoup, 7.00, allergensButternutSquashSoup, tagsButternutSquashSoup);

        for(int rating : ratingsButternutSquashSoup){
            butternutSquashSoup.addRating(rating);
        }

// Dish 8: Beef Tacos
        Map<String, Double> nutritionalInfoBeefTacos = new HashMap<>();
        nutritionalInfoBeefTacos.put("Calories", 300.0);
        nutritionalInfoBeefTacos.put("Protein", 20.0);
        nutritionalInfoBeefTacos.put("Carbs", 25.0);
        nutritionalInfoBeefTacos.put("Fats", 15.0);

        Set<String> allergensBeefTacos = new HashSet<>(Arrays.asList("Gluten"));
        Set<String> tagsBeefTacos = new HashSet<>(Arrays.asList("Mexican", "Beef", "FastFood"));
        ArrayList<Integer> ratingsBeefTacos = new ArrayList<>(Arrays.asList(5, 6, 7, 4, 3, 5, 6, 7, 4, 5));

        Meal beefTacos = new Meal("Beef Tacos",
                "Tender beef with fresh salsa, cheese, and lettuce wrapped in soft corn tortillas.",
                nutritionalInfoBeefTacos, 9.50, allergensBeefTacos, tagsBeefTacos);

        for(int rating : ratingsBeefTacos){
            beefTacos.addRating(rating);
        }

// Dish 9: Spinach and Feta Pie
        Map<String, Double> nutritionalInfoSpinachFetaPie = new HashMap<>();
        nutritionalInfoSpinachFetaPie.put("Calories", 250.0);
        nutritionalInfoSpinachFetaPie.put("Protein", 12.0);
        nutritionalInfoSpinachFetaPie.put("Carbs", 20.0);
        nutritionalInfoSpinachFetaPie.put("Fats", 14.0);

        Set<String> allergensSpinachFetaPie = new HashSet<>(Arrays.asList("Gluten", "Dairy"));
        Set<String> tagsSpinachFetaPie = new HashSet<>(Arrays.asList("Vegan", "Baked", "Greek"));
        ArrayList<Integer> ratingsSpinachFetaPie = new ArrayList<>(Arrays.asList(8, 9, 8, 7, 9, 10, 8, 7, 9, 8));

        Meal spinachAndFetaPie = new Meal("Spinach and Feta Pie",
                "Flaky pastry filled with creamy feta cheese and spinach, baked to perfection.",
                nutritionalInfoSpinachFetaPie, 12.75, allergensSpinachFetaPie, tagsSpinachFetaPie);

        for(int rating : ratingsSpinachFetaPie){
            spinachAndFetaPie.addRating(rating);
        }

// Dish 10: Mango Sticky Rice
        Map<String, Double> nutritionalInfoMangoStickyRice = new HashMap<>();
        nutritionalInfoMangoStickyRice.put("Calories", 400.0);
        nutritionalInfoMangoStickyRice.put("Protein", 6.0);
        nutritionalInfoMangoStickyRice.put("Carbs", 85.0);
        nutritionalInfoMangoStickyRice.put("Fats", 5.0);

        Set<String> allergensMangoStickyRice = new HashSet<>();
        Set<String> tagsMangoStickyRice = new HashSet<>(Arrays.asList("Dessert", "Vegan", "Asian"));
        ArrayList<Integer> ratingsMangoStickyRice = new ArrayList<>(Arrays.asList(10, 9, 10, 10, 8, 9, 10, 9, 10, 9));

        Meal mangoStickyRice = new Meal("Mango Sticky Rice",
                "Sweet sticky rice served with fresh mango slices and drizzled with coconut milk.",
                nutritionalInfoMangoStickyRice, 6.50, allergensMangoStickyRice, tagsMangoStickyRice);

        for(int rating : ratingsMangoStickyRice){
            mangoStickyRice.addRating(rating);
        }

        // Dish 11: Tofu Stir Fry
        Map<String, Double> nutritionalInfoTofuStirFry = new HashMap<>();
        nutritionalInfoTofuStirFry.put("Calories", 450.0);
        nutritionalInfoTofuStirFry.put("Protein", 25.0);
        nutritionalInfoTofuStirFry.put("Carbs", 50.0);
        nutritionalInfoTofuStirFry.put("Fats", 15.0);

        Set<String> allergensTofuStirFry = new HashSet<>(Arrays.asList("Soy"));
        Set<String> tagsTofuStirFry = new HashSet<>(Arrays.asList("Vegan", "Asian", "Healthy"));
        ArrayList<Integer> ratingsTofuStirFry = new ArrayList<>(Arrays.asList(9, 8, 7, 9, 10, 8, 7, 8, 9, 8));

        Meal tofuStirFry = new Meal("Tofu Stir Fry",
                "Sautéed tofu with a mix of fresh vegetables served with a side of brown rice.",
                nutritionalInfoTofuStirFry, 12.00, allergensTofuStirFry, tagsTofuStirFry);

// Dish 12: Italian Meatballs
        Map<String, Double> nutritionalInfoItalianMeatballs = new HashMap<>();
        nutritionalInfoItalianMeatballs.put("Calories", 520.0);
        nutritionalInfoItalianMeatballs.put("Protein", 35.0);
        nutritionalInfoItalianMeatballs.put("Carbs", 30.0);
        nutritionalInfoItalianMeatballs.put("Fats", 30.0);

        Set<String> allergensItalianMeatballs = new HashSet<>(Arrays.asList("Gluten", "Eggs"));
        Set<String> tagsItalianMeatballs = new HashSet<>(Arrays.asList("Italian", "Beef", "Baked"));
        ArrayList<Integer> ratingsItalianMeatballs = new ArrayList<>(Arrays.asList(10, 9, 8, 7, 6, 9, 10, 8, 7, 9));

        Meal italianMeatballs = new Meal("Italian Meatballs",
                "Juicy beef meatballs seasoned with herbs and simmered in a rich tomato sauce.",
                nutritionalInfoItalianMeatballs, 14.50, allergensItalianMeatballs, tagsItalianMeatballs);

// Dish 13: Avocado Toast
        Map<String, Double> nutritionalInfoAvocadoToast = new HashMap<>();
        nutritionalInfoAvocadoToast.put("Calories", 310.0);
        nutritionalInfoAvocadoToast.put("Protein", 9.0);
        nutritionalInfoAvocadoToast.put("Carbs", 30.0);
        nutritionalInfoAvocadoToast.put("Fats", 20.0);

        Set<String> allergensAvocadoToast = new HashSet<>(Arrays.asList("Gluten"));
        Set<String> tagsAvocadoToast = new HashSet<>(Arrays.asList("Vegan", "Breakfast", "Healthy"));
        ArrayList<Integer> ratingsAvocadoToast = new ArrayList<>(Arrays.asList(8, 9, 10, 7, 8, 9, 10, 8, 7, 9));

        Meal avocadoToast = new Meal("Avocado Toast",
                "Crispy whole grain toast topped with creamy avocado, radishes, and a sprinkle of sesame seeds.",
                nutritionalInfoAvocadoToast, 7.00, allergensAvocadoToast, tagsAvocadoToast);

// Dish 14: BBQ Chicken Pizza
        Map<String, Double> nutritionalInfoBBQChickenPizza = new HashMap<>();
        nutritionalInfoBBQChickenPizza.put("Calories", 500.0);
        nutritionalInfoBBQChickenPizza.put("Protein", 30.0);
        nutritionalInfoBBQChickenPizza.put("Carbs", 50.0);
        nutritionalInfoBBQChickenPizza.put("Fats", 22.0);

        Set<String> allergensBBQChickenPizza = new HashSet<>(Arrays.asList("Gluten", "Dairy"));
        Set<String> tagsBBQChickenPizza = new HashSet<>(Arrays.asList("Pizza", "Chicken", "Baked"));
        ArrayList<Integer> ratingsBBQChickenPizza = new ArrayList<>(Arrays.asList(9, 8, 7, 8, 9, 7, 8, 9, 10, 9));

        Meal bbqChickenPizza = new Meal("BBQ Chicken Pizza",
                "Smoky BBQ sauce, grilled chicken, red onions, and cilantro on a crispy crust.",
                nutritionalInfoBBQChickenPizza, 13.00, allergensBBQChickenPizza, tagsBBQChickenPizza);

// Dish 15: Greek Salad
        Map<String, Double> nutritionalInfoGreekSalad = new HashMap<>();
        nutritionalInfoGreekSalad.put("Calories", 200.0);
        nutritionalInfoGreekSalad.put("Protein", 8.0);
        nutritionalInfoGreekSalad.put("Carbs", 10.0);
        nutritionalInfoGreekSalad.put("Fats", 15.0);

        Set<String> allergensGreekSalad = new HashSet<>(Arrays.asList("Dairy"));
        Set<String> tagsGreekSalad = new HashSet<>(Arrays.asList("Salad", "Vegetarian", "Healthy"));
        ArrayList<Integer> ratingsGreekSalad = new ArrayList<>(Arrays.asList(8, 8, 9, 7, 6, 9, 8, 7, 8, 9));

        Meal greekSalad = new Meal("Greek Salad",
                "Crisp lettuce, fresh tomatoes, cucumbers, olives, feta cheese, and a light olive oil dressing.",
                nutritionalInfoGreekSalad, 9.50, allergensGreekSalad, tagsGreekSalad);

        for(int rating : ratingsTofuStirFry) {
            tofuStirFry.addRating(rating);
        }

        for(int rating : ratingsItalianMeatballs) {
            italianMeatballs.addRating(rating);
        }

        for(int rating : ratingsAvocadoToast) {
            avocadoToast.addRating(rating);
        }

        for(int rating : ratingsBBQChickenPizza) {
            bbqChickenPizza.addRating(rating);
        }

        for(int rating : ratingsGreekSalad) {
            greekSalad.addRating(rating);
        }

        menu.add(classicCheeseburger);
        menu.add(veganQuinoaSalad);
        menu.add(spicyRamenBowl);
        menu.add(margheritaPizza);
        menu.add(chickenCaesarSalad);
        menu.add(grilledSalmonWithAsparagus);
        menu.add(butternutSquashSoup);
        menu.add(beefTacos);
        menu.add(spinachAndFetaPie);
        menu.add(mangoStickyRice);
        menu.add(tofuStirFry);
        menu.add(italianMeatballs);
        menu.add(avocadoToast);
        menu.add(bbqChickenPizza);
        menu.add(greekSalad);
    }
    public List<Meal> generateMenu() {
        return menu;
    }
    public void printMenu() {
        int i = 0;
        for(Meal meal : menu){
            System.out.print(i);
            System.out.println(": " + meal.getName());
            System.out.println(meal.getDescription());
            i++;
        }
    }
}