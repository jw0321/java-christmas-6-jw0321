package christmas.controller;

import christmas.model.DiscountPolicy;
import christmas.model.EventBenefit;
import christmas.model.Order;
import christmas.model.Receipt;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.util.HashMap;
import java.util.Map;

public class OrderController {

    public static final String INVALID_INPUT_DATE = "[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.";
    public static final String INVALID_INPUT_MENU = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
    public static final int DEFAULT_VALUE = 0;
    private final DiscountPolicy discountPolicy;
    private final EventBenefit eventBenefit;
    private final Order order;

    public OrderController() {
        this.discountPolicy = new DiscountPolicy();
        this.eventBenefit = new EventBenefit();
        this.order = new Order();
    }

    public void run() {
        OutputView.printStartMessage();

        int visitDate = readAndSetDiscountPolicies();
        readAndCreateOrder();

        OutputView.printVisitDate(visitDate);
        printOrder();
        createAndPrintEventBenefit(visitDate);
        calculateAndPrintAmount();
    }

    private int readAndSetDiscountPolicies() {
        int visitDate = 0;
        boolean vaildVisitDate = false;
        while (!vaildVisitDate) {
            try {
                String inputDate = InputView.readVisitDate();
                visitDate= validateInputDate(inputDate);
                discountPolicy.setDiscountPolicies(visitDate);
                vaildVisitDate = true;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e);
            }
        }
        return visitDate;
    }

    private int validateInputDate(String inputDate) {
        int visitDate;
        InputValidator.validateNumber(inputDate);
        visitDate = Integer.parseInt(inputDate);
        InputValidator.checkNumberInRange(visitDate);
        return visitDate;
    }

    private void readAndCreateOrder() {
        Map<String, Integer> processedOrder;
        boolean validOrder = false;
        while (!validOrder) {
            try {
                String inputOrder = InputView.readOrderMenu();
                InputValidator.checkInputOrderEmpty(inputOrder);
                processedOrder = separateStringToMap(inputOrder);
                order.setOrder(processedOrder);
                validOrder = true;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e);
            }
        }
    }

    private Map<String, Integer> separateStringToMap(String inputOrder) {
        Map<String, Integer> separatedOrder = new HashMap<>();

        String removedBlankOrder = inputOrder.replaceAll(" ", "");
        String[] pairs = removedBlankOrder.split(",");

        for (String pair : pairs) {
            String[] keyValue = pair.split("-");
            InputValidator.validateOrder(separatedOrder, keyValue);
            separatedOrder.put(keyValue[0], Integer.parseInt(keyValue[1]));
        }
        return separatedOrder;
    }

    private void printOrder() {
        Receipt orderReceipt = order.getOrderReceipt();
        OutputView.printOrder(orderReceipt);
    }

    private void createAndPrintEventBenefit(int visitDate) {
        Receipt rawOrder = order.getRawOrderReceipt(visitDate);
        int rawOrderAmount = order.calculateRawOrderAmount();
        Map<String, Integer> discountDetails = discountPolicy.createDiscountDetails(rawOrder, rawOrderAmount);

        eventBenefit.setGift(rawOrderAmount);
        OutputView.printGifts(eventBenefit.getGiftDetails());

        Map<String, Integer> benefitsDetails = eventBenefit.createBenefitsDetails(discountDetails);
        OutputView.printBenefitsDetails(benefitsDetails);
    }

    private void calculateAndPrintAmount() {
        int totalDiscountAmount = discountPolicy.calculateTotalDiscountAmount();
        int totalBenefitAmount = eventBenefit.calculateTotalBenefitAmount(totalDiscountAmount);

        OutputView.printAmount(totalBenefitAmount, order.calculateFinalOrderAmount(totalDiscountAmount));
        OutputView.printEventBadge(eventBenefit.determineEventBadge(totalBenefitAmount));
    }
}