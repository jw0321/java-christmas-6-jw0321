package christmas.model;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
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

    @ParameterizedTest
    @DisplayName("증정 내역 작성 테스트")
    @MethodSource("setGiftDataProvider")
    void getGiftDetailsTest(int inputAmount, boolean expected) {
        final String expectedGift= "샴페인";
        eventBenefit.setGift(inputAmount);

        assertThat(eventBenefit.getGiftDetails().containsKey(expectedGift)).isEqualTo(!expected);
    }

    @ParameterizedTest
    @DisplayName("혜택 금액 별 이벤트 배지 부여 테스트")
    @CsvSource(value = {"3000, 없음", "5000, 별", "10000, 트리", "20000, 산타"})
    void eventBadgeTest(int input, String expected) {
        String eventBadge = eventBenefit.determineEventBadge(input);

        assertThat(eventBadge).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("할인 상세 정보 반환 테스트")
    @MethodSource("createBenefitsDetailsDataProvider")
    void createBenefitsDetailsTest(int rawOrderAmount, boolean expected) {
        eventBenefit.setGift(rawOrderAmount);
        Map<String, Integer> discountDetails = new HashMap<>();
        discountDetails.put("특별 할인:", 1000);

        Map<String, Integer> benefitsDetails = eventBenefit.createBenefitsDetails(discountDetails);

        assertThat(benefitsDetails.containsKey("증정 이벤트:")).isEqualTo(expected);
    }
    private static Stream<Arguments> createBenefitsDetailsDataProvider() {
        return Stream.of(
                Arguments.of(60000, false),
                Arguments.of(120000, true)
        );
    }
}
