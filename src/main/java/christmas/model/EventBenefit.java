package christmas.model;

import christmas.model.Order.FoodCategory;
import java.util.HashMap;
import java.util.Map;

public class EventBenefit {

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
        if (!gift.isEmpty()) {
            giftReceipt.put(GIFT_MENU, quantity);

            return giftReceipt;
        }
        return giftReceipt;
    }

    public String determineEventBadge(int totalBenefitAmount) {
        final int starBadgeAmount = 5000;
        final int treeBadgeAmount = 10000;
        final int santaBadgeAmount = 20000;
        if (totalBenefitAmount < starBadgeAmount) {
            return "없음";
        }
        if (totalBenefitAmount < treeBadgeAmount) {
            return "별";
        }
        if (totalBenefitAmount < santaBadgeAmount) {
            return "트리";
        }
        return "산타";
    }

    public Map<String, Integer> createBenefitsDetails(Map<String, Integer> discountDetails) {
        Map<String, Integer> benefitsDetails = new HashMap<>(discountDetails);
        if (!gift.isEmpty()) {
            String giftReceipt = "증정 이벤트:";
            benefitsDetails.put(giftReceipt, FoodCategory.BEVERAGE.getFoodMenu().get(GIFT_MENU));
            return benefitsDetails;
        }
        return discountDetails;
    }

    public int calculateTotalBenefitAmount(int totalDiscountAmount) {
        int totalBenefitAmount;
        if (!gift.isEmpty()) {
            totalBenefitAmount = gift.get(GIFT_MENU) + totalDiscountAmount;
            return totalBenefitAmount;
        }
        totalBenefitAmount = totalDiscountAmount;
        return totalBenefitAmount;
    }
}
