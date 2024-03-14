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

        student.addPreference("Italian");
        // student.addPreference("Healthy");
        // student.addAllergy("Gluten");
        student.updateBalance(1500);
        student.setBudgetLimit(150);

        // menuGenerator.printMenu();
        // make a new purchase
        MealPurchase newPurchase = new MealPurchase();
        MealPurchase newPurchase1 = new MealPurchase();
        newPurchase.addMeal(menu.get(5));
        newPurchase.addMeal(menu.get(14));
        newPurchase1.addMeal(menu.get(2));
        newPurchase1.addMeal(menu.get(3));
        student.addMealPurchase(newPurchase, menu);
        student.addMealPurchase(newPurchase1, menu);
        System.out.println("Your Balance:");
        System.out.println(student.getBalance());

        System.out.println("Here are some meals recommended for you:");
        List<Meal> recommended = MealRecommender.recommendMeals(student, menu);
        for(int i = 0; i < 5; i++) {
            Meal meal = recommended.get(i);
            System.out.println(meal.getName());
        }
        AccountManage students = new AccountManage(new ArrayList<>(List.of(student)));
        System.out.println("\nMost popular meals:");
        for(int i = 0; i < 3; i++) System.out.println(students.mostPopularDishes(menu).get(i).getName());
    }
}
