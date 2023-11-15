package christmas.view;

import java.util.Map;
import java.util.Map.Entry;

public class OutputView {

    private OutputView() {

    }

    public static void printStartMessage() {
        System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");
    }

    public static void printVisitDate(int visitDate) {
        System.out.println(String.format("12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!", visitDate));
        System.out.println();
    }

    public static void printOrder(Map<String, Integer> orderSheet, int rawOrderAmount) {
        System.out.println("<주문 메뉴>");

        for (Entry<String, Integer> order : orderSheet.entrySet()) {
            String menu = order.getKey();
            Integer quantity = order.getValue();
            System.out.println(String.format("%s %d개", menu, quantity));
        }
        System.out.println();

        System.out.println("<할인 전 총주문 금액>");
        System.out.println(String.format("%,d원",rawOrderAmount));
        System.out.println();
    }

    public static void printDiscountEvent(Map<String, Map<String, Integer>> eventSheet) {
        Map<String, Integer> gifts = eventSheet.get("gift");
        Map<String, Integer> benefitDetails = eventSheet.get("benefitDetails");

        printGifts(gifts);
        printBenefitDetails(benefitDetails);
    }

    private static void printGifts(Map<String, Integer> gifts) {
        System.out.println("<증정 메뉴>");
        if (gifts == null) {
            System.out.println("없음");
        }
        if (gifts != null) {
            for (Entry<String, Integer> gift : gifts.entrySet()) {
                System.out.println(String.format("%s %d개", gift.getKey(), gift.getValue()));
            }
        }
        System.out.println();
    }

    private static void printBenefitDetails(Map<String, Integer> benefitDetails) {
        System.out.println("<혜택 내역>");
        if (benefitDetails == null) {
            System.out.println("없음");
        }
        if (benefitDetails != null) {
            for (Entry<String, Integer> benefitDetail : benefitDetails.entrySet()) {
                System.out.println(String.format("%s: %,d원", benefitDetail.getKey(), -benefitDetail.getValue()));
            }
        }
        System.out.println();
    }

    public static void printAmount(int discountAmount, int lastPurchaseAmount) {
        System.out.println("<총혜택 금액>");
        if (discountAmount == 0) {
            System.out.println(String.format("%,d원", discountAmount));
        }
        if (discountAmount != 0) {
            System.out.println(String.format("%,d원", -discountAmount));
        }
        System.out.println();

        System.out.println("<할인 후 예상 결제 금액>");
        System.out.println(String.format("%,d원", lastPurchaseAmount));
        System.out.println();
    }

    public static void printEventBadge(String eventBadge) {
        System.out.println("<12월 이벤트 배지>");
        if (eventBadge == null) {
            System.out.println("없음");
        }
        if (eventBadge != null) {
            System.out.println(eventBadge);
        }
        System.out.println();
    }

    public static void printErrorMessage(IllegalArgumentException e) {
        System.out.println(e.getMessage());
    }
}
