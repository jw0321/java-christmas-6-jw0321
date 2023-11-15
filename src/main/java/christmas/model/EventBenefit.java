package christmas.model;

import christmas.model.Order.FoodCategory;
import java.util.HashMap;
import java.util.Map;

public class EventBenefit { //이벤트 배지 부여 및 증정 메뉴 로직 처리

    private final String GIFT_MENU = "샴페인";
    private final Map<String, Integer> gift;

    public EventBenefit() {
        this.gift = new HashMap<>();
    }

    public void setGift(int rawOrderAmount) {
        int givingGiftByAmount = 120000;
        if (rawOrderAmount >= givingGiftByAmount) {
            int giftPrice = FoodCategory.BEVERAGE.getFoodMenu().get(GIFT_MENU);
            this.gift.put(GIFT_MENU, giftPrice);
        }
    }

    public Map<String, Integer> getGiftDetails() {
        Map<String, Integer> giftReceipt = new HashMap<>();
        int quantity = 1;

        giftReceipt.put(GIFT_MENU, quantity);

        return giftReceipt;
    }

    public String determineEventBadge(int totalBenefitAmount) {

        if (totalBenefitAmount >= 5000 && totalBenefitAmount < 10000) {
            return "별";
        }
        if (totalBenefitAmount >= 10000 && totalBenefitAmount < 20000) {
            return "트리";
        }
        if (totalBenefitAmount >= 20000) {
            return "산타";
        }
        return null;
    }

    public Map<String, Integer> createBenefitsDetails(Map<String, Integer> discountDetails) {
        Map<String, Integer> benefitsDetails = new HashMap<>(discountDetails);
        if (gift.containsKey(GIFT_MENU)) {
            String giftReceipt = "증정 이벤트:";
            benefitsDetails.put(giftReceipt, FoodCategory.BEVERAGE.getFoodMenu().get(GIFT_MENU));
            return benefitsDetails;
        }
        return discountDetails;
    }

    public int calculateTotalBenefitAmount(int totalDiscountAmount) {
        int totalBenefitAmount;
        if (gift.containsKey(GIFT_MENU)) {
            totalBenefitAmount = gift.get(GIFT_MENU) + totalDiscountAmount;
            return totalBenefitAmount;
        }
        totalBenefitAmount = totalDiscountAmount;
        return totalBenefitAmount;
    }
}
