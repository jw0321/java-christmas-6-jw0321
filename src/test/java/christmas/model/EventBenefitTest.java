package christmas.model;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class EventBenefitTest {

    private EventBenefit eventBenefit;
    @BeforeEach
    void setUp() {
        eventBenefit = new EventBenefit();
    }
    @ParameterizedTest
    @DisplayName("증정 이벤트 기능 테스트")
    @MethodSource("setGiftDataProvider")
    void setGiftTest(int inputAmount, boolean expected) {
        eventBenefit.setGift(inputAmount);

        assertThat(eventBenefit.getGiftDetails().isEmpty()).isEqualTo(expected);
    }
    private static Stream<Arguments> setGiftDataProvider() {
        return Stream.of(
                Arguments.of(50000, true),
                Arguments.of(120000, false),
                Arguments.of(200000, false)
        );
    }

}
