package christmas.controller;

import static christmas.controller.OrderController.INVALID_INPUT_DATE;
import static christmas.controller.OrderController.INVALID_INPUT_MENU;

import java.util.Map;

public class InputValidator {

    private InputValidator() {
    }

    public static void validateNumber(String input) {
        checkInputDateEmpty(input);
        checkForWhiteSpace(input);
        checkDateNumeric(input);
    }


    private static void checkInputDateEmpty(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException(INVALID_INPUT_DATE);
        }
    }

    private static void checkForWhiteSpace(String input) {
        if (input.contains(" ")) {
            throw new IllegalArgumentException(INVALID_INPUT_DATE);
        }
    }

    private static void checkDateNumeric(String input) {
        if (!input.matches("\\d+")) {
            throw new IllegalArgumentException(INVALID_INPUT_DATE);
        }
    }

    public static void checkNumberInRange(int visitDate) {
        int firstDate = 1;
        int lastDate = 31;

        if (visitDate >= firstDate && visitDate <= lastDate) {
            return;
        }
        throw new IllegalArgumentException();
    }

    public static void validateOrder(Map<String, Integer> separatedOrder, String[] keyValue) {
        checkInputFormat(keyValue);
        checkDuplicatedInput(separatedOrder, keyValue);
        checkQuantityNumeric(keyValue[1]);

    }

    public static void checkQuantityNumeric(String input) {
        if (!input.matches("\\d+")) {
            throw new IllegalArgumentException(INVALID_INPUT_MENU);
        }
    }

    private static void checkInputFormat(String[] keyValue) {
        if (keyValue.length != 2) {
            throw new IllegalArgumentException(INVALID_INPUT_MENU);
        }
    }

    private static void checkDuplicatedInput(Map<String, Integer> separatedOrder, String[] keyValue) {
        if (separatedOrder.containsKey(keyValue[0])) {
            throw new IllegalArgumentException(INVALID_INPUT_MENU);
        }
    }

    public static void checkInputQuantityEmpty(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException(INVALID_INPUT_MENU);
        }
    }
}
