package christmas.model;

import static christmas.controller.OrderController.DEFAULT_VALUE;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DiscountPolicy {

    public enum EventDiscountLogic {
        CHRISMAS_DISCOUNT((order) -> {
            Map<String, Integer> benefitDetails = new HashMap<>();
            int visitDate = order.number();
            int baseDiscount = 900;
            int discountPerDday = 100;

            int discountAmount = baseDiscount + (visitDate) * discountPerDday;
            benefitDetails.put("크리스마스 디데이 할인", discountAmount);
            return benefitDetails;
        }),
        SPECIAL_DISCOUNT((order) -> {
            Map<String, Integer> benefitDetails = new HashMap<>();
            int specialDiscountAmount = 1000;

            benefitDetails.put("특별 할인", specialDiscountAmount);
            return benefitDetails;
        }),
        WEEKDAY_DISCOUNT((order) -> {
            if (order.details().containsKey("dessert")) {
                Map<String, Integer> benefitDetails = new HashMap<>();
                int perDiscountAmount = 2023;
                int quantity = order.details().get("dessert");

                int discountAmount = perDiscountAmount * quantity;
                benefitDetails.put("평일 할인", discountAmount);
                return benefitDetails;
            }
            return null;
        }),
        WEEKEND_DISCOUNT((order) -> {
            if (order.details().containsKey("mainMenu")) {
                Map<String, Integer> benefitDetails = new HashMap<>();
                int perDiscountAmount = 2023;
                int quantity = order.details().get("mainMenu");

                int discountAmount = perDiscountAmount * quantity;
                benefitDetails.put("주말 할인", discountAmount);
                return benefitDetails;
            }
            return null;
        });

        @FunctionalInterface
        interface DiscountLogic {

            Map<String, Integer> calculateDiscount(Receipt order);
        }

        private final DiscountLogic logic;

        EventDiscountLogic(DiscountLogic logic) {
            this.logic = logic;
        }

        public Map<String, Integer> calculateDiscount(Receipt order) {
            return logic.calculateDiscount(order);
        }
    }

    private final int END_OF_CHRISMAS_EVENT = 26;
    private final int CHRISMASDAY = 25;
    private final ArrayList<EventDiscountLogic> discountPolicies;
    private final Map<String, Integer> discountDetails;


    public DiscountPolicy() {
        this.discountPolicies = new ArrayList<>();
        this.discountDetails = new HashMap<>();
    }

    public void setDiscountPolicies(int visitDate) {
        DayOfWeek dayOfWeek = evaluateDate(visitDate);
        addDiscountPolicyByDate(visitDate, dayOfWeek);
    }

    private DayOfWeek evaluateDate(int visitDate) {
        int year = 2023;
        int month = 12;
        LocalDate date = LocalDate.of(year, month, visitDate);
        return date.getDayOfWeek();
    }

    private void addDiscountPolicyByDate(int visitDate, DayOfWeek dayOfWeek) {
        boolean isWeekend = (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY);
        if (visitDate < END_OF_CHRISMAS_EVENT) {
            discountPolicies.add(EventDiscountLogic.CHRISMAS_DISCOUNT);
        }
        if (!isWeekend) {
            discountPolicies.add(EventDiscountLogic.WEEKDAY_DISCOUNT);
        }
        if (isWeekend) {
            discountPolicies.add(EventDiscountLogic.WEEKEND_DISCOUNT);
        }
        if (dayOfWeek == DayOfWeek.SUNDAY || visitDate == CHRISMASDAY) {
            discountPolicies.add(EventDiscountLogic.SPECIAL_DISCOUNT);
        }
    }

    public ArrayList<EventDiscountLogic> getDiscountPolicies() {
        return discountPolicies;
    }
    public Map<String, Integer> createDiscountDetails(Receipt rawOrder) {
        for (EventDiscountLogic discountPolicy : discountPolicies) {
            Map<String, Integer> singleDiscountDetail = discountPolicy.calculateDiscount(rawOrder);

            if (singleDiscountDetail != null) {
                discountDetails.putAll(singleDiscountDetail);
            }
        }
        return discountDetails;
    }

    public int calculateTotalDiscountAmount() {
        int totalDiscountAmount = DEFAULT_VALUE;
        for (int discountAmount : discountDetails.values()) {
            totalDiscountAmount += discountAmount;
        }
        return totalDiscountAmount;
    }
}
