package christmas.view;

import christmas.model.Receipt;
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

    public static void printOrder(Receipt order) {
        int rawOrderAmount = order.getNumber();
        Map<String, Integer> orderSheet = order.getDetails();
        System.out.println("<주문 메뉴>");

        for (Entry<String, Integer> singleOrder : orderSheet.entrySet()) {
            String menu = singleOrder.getKey();
            Integer quantity = singleOrder.getValue();
            System.out.println(String.format("%s %d개", menu, quantity));
        }
        System.out.println();

        System.out.println("<할인 전 총주문 금액>");
        System.out.println(String.format("%,d원", rawOrderAmount));
        System.out.println();
    }

    public static void printGifts(Map<String, Integer> gifts) {
        System.out.println("<증정 메뉴>");
        if (gifts.isEmpty()) {
            System.out.println("없음");
        }
        if (!gifts.isEmpty()) {
            for (Entry<String, Integer> gift : gifts.entrySet()) {
                System.out.println(String.format("%s %d개", gift.getKey(), gift.getValue()));
            }
        }
        System.out.println();
    }

    public static void printBenefitsDetails(Map<String, Integer> benefitsDetails) {
        System.out.println("<혜택 내역>");
        if (benefitsDetails.isEmpty()) {
            System.out.println("없음");
        }
        if (!benefitsDetails.isEmpty()) {
            for (Entry<String, Integer> benefitDetail : benefitsDetails.entrySet()) {
                System.out.println(String.format("%s: %,d원", benefitDetail.getKey(), -benefitDetail.getValue()));
            }
        }
        System.out.println();
    }

    public static void printAmount(int totalBenefitAmount, int lastOrderAmount) {
        System.out.println("<총혜택 금액>");
        if (totalBenefitAmount == 0) {
            System.out.println(String.format("%,d원", totalBenefitAmount));
        }
        if (totalBenefitAmount != 0) {
            System.out.println(String.format("%,d원", -totalBenefitAmount));
        }
        System.out.println();

        System.out.println("<할인 후 예상 결제 금액>");
        System.out.println(String.format("%,d원", lastOrderAmount));
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
