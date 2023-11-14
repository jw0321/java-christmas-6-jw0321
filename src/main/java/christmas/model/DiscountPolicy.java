package christmas.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DiscountPolicy {

    public enum EventDiscountLogic {
        CHRISMAS_DISCOUNT((order) -> {
            Map<String, Integer> benefitDetails = new HashMap<>();
            int visitDate = order.getNumber();
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
            if (order.getDetails().containsKey("dessert")) {
                Map<String, Integer> benefitDetails = new HashMap<>();
                int perDiscountAmount = 2023;
                int quantity = order.getDetails().get("dessert");

                int discountAmount = perDiscountAmount * quantity;
                benefitDetails.put("평일 할인", discountAmount);
                return benefitDetails;
            }
            return null;
        }),
        WEEKEND_DISCOUNT((order) -> {
            if (order.getDetails().containsKey("mainMenu")) {
                Map<String, Integer> benefitDetails = new HashMap<>();
                int perDiscountAmount = 2023;
                int quantity = order.getDetails().get("mainMenu");

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

    private final ArrayList<EventDiscountLogic> discountPolicies;

    public DiscountPolicy() {
        this.discountPolicies = new ArrayList<>();
    }

    public void setDiscountPolicies(int visitDate) {
        validateNumberInRange(visitDate);
        DayOfWeek dayOfWeek = evaluateDate(visitDate);
        determineDiscountPolicyByDate(visitDate, dayOfWeek);
    }

    private DayOfWeek evaluateDate(int visitDate) {
        int year = 2023;
        int month = 12;
        LocalDate date = LocalDate.of(year, month, visitDate);
        return date.getDayOfWeek();
    }

    private void determineDiscountPolicyByDate(int visitDate, DayOfWeek dayOfWeek) {
        int endOfChrismasEvent = 26;
        int chrismasDay = 25;
        boolean isWeekend = (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY);
        if (visitDate < endOfChrismasEvent) {
            discountPolicies.add(EventDiscountLogic.CHRISMAS_DISCOUNT);
        }
        if (!isWeekend) {
            discountPolicies.add(EventDiscountLogic.WEEKDAY_DISCOUNT);
        }
        if (isWeekend) {
            discountPolicies.add(EventDiscountLogic.WEEKEND_DISCOUNT);
        }
        if (dayOfWeek == DayOfWeek.SUNDAY || visitDate == chrismasDay) {
            discountPolicies.add(EventDiscountLogic.SPECIAL_DISCOUNT);
        }
    }

    private void validateNumberInRange(int visitDate) {
        int minRange = 0;
        int maxRange = 32;

        if (visitDate > minRange && visitDate < maxRange) {
            return;
        }
        throw new IllegalArgumentException();
    }

    public ArrayList<EventDiscountLogic> getDiscountPolicies() {
        return discountPolicies;
    }
}
