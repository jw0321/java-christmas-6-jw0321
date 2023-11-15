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

}