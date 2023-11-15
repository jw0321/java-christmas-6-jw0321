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

}
