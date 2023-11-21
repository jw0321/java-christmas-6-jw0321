package christmas.model;

import java.util.Map;

public record Receipt(int number, Map<String, Integer> details) {

}
