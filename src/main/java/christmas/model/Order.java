package christmas.model;

import static christmas.controller.OrderController.DEFAULT_VALUE;
import static christmas.controller.OrderController.INVALID_INPUT_MENU;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Order {

    public enum FoodCategory {
        APPETIZER("appetizer", Map.of("양송이수프", 6000, "타파스", 5500, "시저샐러드", 8000)),
        MAIN_MENU("mainMenu", Map.of("티본스테이크", 55000, "바비큐립", 54000, "해산물파스타", 35000, "크리스마스파스타", 25000)),
        DESSERT("dessert", Map.of("초코케이크", 15000, "아이스크림", 5000)),
        BEVERAGE("beverage", Map.of("제로콜라", 3000, "레드와인", 60000, "샴페인", 25000));

        private final String foodType;
        private final Map<String, Integer> foodMenu;

        FoodCategory(String foodType, Map<String, Integer> foodMenu) {
            this.foodType = foodType;
            this.foodMenu = foodMenu;
        }

        public String getFoodType() {
            return foodType;
        }

        public Map<String, Integer> getFoodMenu() {
            return foodMenu;
        }
    }

    private final Map<String, Integer> orderMenu;

    public Order() {
        this.orderMenu = new HashMap<>();
    }

    public void setOrder(Map<String, Integer> inputOrder) {
        checkOrderMenu(inputOrder);
        this.orderMenu.putAll(inputOrder);
    }

    private void checkOrderMenu(Map<String, Integer> inputOrder) {
        for (String inputMenu : inputOrder.keySet()) {
            validateMenuInCategory(inputMenu);
        }

        int totalQuantity = DEFAULT_VALUE;
        for (int inputQuantity : inputOrder.values()) {
            validateQuantityInRange(inputQuantity);
            totalQuantity += inputQuantity;
        }
        validateTotalQuantityInRange(totalQuantity);
    }

    private void validateMenuInCategory(String input) {
        FoodCategory foodCategory = checkEqualFoodCategory(input);
        if (foodCategory == null) {
            throw new IllegalArgumentException(INVALID_INPUT_MENU);
        }
    }

    private void validateQuantityInRange(int inputQuantity) {
        final int minimumOrderQuantity = 1;
        if (inputQuantity < minimumOrderQuantity) {
            throw new IllegalArgumentException(INVALID_INPUT_MENU);
        }
    }
    private void validateTotalQuantityInRange(int totalQuantity) {
        final int minimumTotalOrderQuantity = 1;
        final int maximumTotalOrderQuantity = 20;
        if (totalQuantity < minimumTotalOrderQuantity || totalQuantity > maximumTotalOrderQuantity) {
            throw new IllegalArgumentException(INVALID_INPUT_MENU);
        }
    }

    private FoodCategory checkEqualFoodCategory(String menu) {
        for (FoodCategory foodCategory : FoodCategory.values()) {
            if (foodCategory.getFoodMenu().containsKey(menu)) {
                return foodCategory;
            }
        }
        return null;
    }

    public int calculateRawOrderAmount() {
        int amount = DEFAULT_VALUE;
        for (Entry<String, Integer> singleOrder : orderMenu.entrySet()) {
            String menu = singleOrder.getKey();
            int quantity = singleOrder.getValue();
            int foodPrice = findFoodPrice(menu);

            amount += foodPrice * quantity;
        }
        return amount;
    }

    private int findFoodPrice(String menu) {
        int foodPrice = DEFAULT_VALUE;
        FoodCategory foodCategory = checkEqualFoodCategory(menu);

        if (foodCategory != null) {
            foodPrice = foodCategory.getFoodMenu().get(menu);
        }

        return foodPrice;
    }

    public Receipt getOrderReceipt() {
        return new Receipt(calculateRawOrderAmount(), orderMenu);
    }

    public Receipt getRawOrderReceipt(int visitDate) {
        Map<String, Integer> foodTypeOrder = new HashMap<>();

        for (Entry<String, Integer> singleMenu : orderMenu.entrySet()) {
            String foodType = findFoodType(singleMenu.getKey());
            foodTypeOrder.put(foodType, singleMenu.getValue());
        }

        return new Receipt(visitDate, foodTypeOrder);
    }

    private String findFoodType(String menu) {
        FoodCategory foodCategory = checkEqualFoodCategory(menu);

        if (foodCategory == null) {
            return null;
        }
        return foodCategory.getFoodType();
    }

    public int calculateFinalOrderAmount(int totalDiscountAmount) {
        return calculateRawOrderAmount() - totalDiscountAmount;
    }
}