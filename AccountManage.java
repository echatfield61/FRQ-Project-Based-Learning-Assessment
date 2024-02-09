package dining_management;
import java.util.List;
import java.util.Comparator;

public class AccountManage {
    private List<StudentAccount> roster;
    public AccountManage(List<StudentAccount> roster) {
        this.roster = roster;
    }

    public List<Meal> mostPopularDishes(List<Meal> menu){
        int MIN_PURCHASE_THRESHOLD = 5;
        double RATING_WEIGHT = 0.6;
        double PURCHASE_WEIGHT = 0.4;
        menu.sort(new Comparator<Meal>() {
            public int compare(Meal m1, Meal m2) {
                double score1 = calculatePopularityScore(m1);
                double score2 = calculatePopularityScore(m2);
                return Double.compare(score2, score1); // Descending order
            }

            private double calculatePopularityScore(Meal meal) {
                double averageRating = meal.getAverageRating();
                int purchases = meal.getAmountBought(); // Number of purchases

                double purchasePenalty = purchases >= MIN_PURCHASE_THRESHOLD ? 1.0 : (double) purchases / MIN_PURCHASE_THRESHOLD;
                return (RATING_WEIGHT * averageRating + PURCHASE_WEIGHT * purchases) * purchasePenalty;
            }
        });
        return menu;
    }

    public double averageAmountSpent() {
        double sum = 0;
        for(StudentAccount student : roster){
            sum += student.getAmountSpent();
        }
        return sum / roster.size();
    }

}
