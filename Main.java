package dining_management;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
public class Main {
    public static void main(String[] awgs){
        Meal cheeseBurger = new Meal("Cheese burger", 2);
        Meal fries = new Meal("French fries", 1);
        fries.addAllergen("potato");
        MealPurchase newPurchase = new MealPurchase(LocalDateTime.now());
        newPurchase.addMeal(cheeseBurger, 2);
        newPurchase.addMeal(fries, 1);
        StudentAccount studentA = new StudentAccount("zw-14331", "Joey Wan", "zwan@stu.socsd.org");
        studentA.updateBalance(1000);
        studentA.addAllergy("potato");
        studentA.addMealPurchase(newPurchase);
        System.out.println(studentA.getBalance());
        System.out.println(fries.getAverageRating());
    }
}
