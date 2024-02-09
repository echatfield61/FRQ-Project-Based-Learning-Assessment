package dining_management;
import java.util.*;

public class Main {
    public static void main(String[] awgs){
        // Initialize Meals
        MenuGenerator menuGenerator = new MenuGenerator();
        List<Meal> menu = menuGenerator.generateMenu();
        // Initialize StudentAccount with preferences and allergies
        String studentId = "zw-25442";
        String name = "Zongyi Wan";
        String email = "zwan@stu.socsd.org";
        StudentAccount student = new StudentAccount(studentId, name, email);

        student.addPreference("Healthy");
        student.addPreference("Italian");
        student.addAllergy("Nuts");
        student.updateBalance(1500);
        student.setBudgetLimit(150);

        // menuGenerator.printMenu();
        // make a new purchase
        MealPurchase newPurchase = new MealPurchase();
        newPurchase.addMeal(menu.get(5));
        newPurchase.addMeal(menu.get(1));
        student.addMealPurchase(newPurchase, menu);
        List<Meal> recommended = MealRecommender.recommendMeals(student, menu);
        for(Meal meal : recommended)
            System.out.println(meal.getName());
        AccountManage students = new AccountManage(new ArrayList<>(List.of(student)));
    }
}
