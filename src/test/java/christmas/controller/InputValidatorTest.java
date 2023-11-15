package christmas.controller;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class InputValidatorTest {

    @ParameterizedTest
    @DisplayName("날짜 입력 검증 예외")
    @ValueSource(strings = {"", "  ", "ab", "-1", "!"})
    void validateNumberTest(String input) {
        assertThatThrownBy(() -> InputValidator.validateNumber(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("날짜 입력 범위 예외")
    @ValueSource(ints = {0, 32, 99})
    void checkNumberInRangeTest(int input) {
    assertThatThrownBy(() -> InputValidator.checkNumberInRange(input))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("입력 메뉴 공백 예외")
    @ValueSource(strings = {"", " "})
    void checkInputOrderEmptyTest(String input) {
        assertThatThrownBy(() -> InputValidator.checkInputOrderEmpty(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("메뉴 입력 형식 검증 예외")
    @MethodSource("validateOrderDataProvider")
    void validateOrderTest(Map<String, Integer> separatedOrder, String[] keyValue) {
        assertThatThrownBy(() -> InputValidator.validateOrder(separatedOrder, keyValue))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> validateOrderDataProvider() {
        return Stream.of(
                Arguments.of(Map.of("메뉴", 1), new String[]{"한덩어리"}),
                Arguments.of(Map.of("메뉴", 1), new String[]{"세","덩","어리"}),
                Arguments.of(Map.of("중복메뉴", 1), new String[]{"중복메뉴", "1"}),
                Arguments.of(Map.of("129", 1), new String[]{"2312", "3"}),
                Arguments.of(Map.of("메뉴", 1), new String[]{"문자수량", "a"})
        );
    }
}
