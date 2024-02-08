package dining_management;
import java.time.LocalDateTime;
public class Main {
    public static void main(String[] awgs){
        Meal cheeseBurger = new Meal("Cheese burger", 2);
        Meal fries = new Meal("French fries", 1);
        MealPurchase newPurchase = new MealPurchase(LocalDateTime.now());
        newPurchase.addMeal(cheeseBurger, 2);
        newPurchase.addMeal(fries, 1);
        StudentAccount studentA = new StudentAccount("zw-25442", "Joey Wan", "zwan@stu.socsd.org");
        studentA.updateBalance(1000);
        studentA.addMealPurchase(newPurchase);
        System.out.println(studentA.getBalance());
    }
}
