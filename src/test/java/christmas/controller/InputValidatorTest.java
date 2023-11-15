package christmas.controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class InputValidatorTest {

    @ParameterizedTest
    @DisplayName("날짜 입력 검증 예외")
    @ValueSource(strings = {"", " ", "ab", "-1", "!"})
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

}
