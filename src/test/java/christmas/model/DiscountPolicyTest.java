package christmas.model;

import static org.assertj.core.api.Assertions.*;

import christmas.model.DiscountPolicy.EventDiscountLogic;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class DiscountPolicyTest {

    private DiscountPolicy discountPolicy;

    @BeforeEach
    void setUp() {
        discountPolicy = new DiscountPolicy();
    }

    @ParameterizedTest
    @DisplayName("날짜별 할인 정책 테스트")
    @MethodSource("discountPolicyDataProvider")
    void discountPolicyTest(int visitDate, boolean isContain, EventDiscountLogic eventDiscountLogic) {
        discountPolicy.setDiscountPolicies(visitDate);
        ArrayList<EventDiscountLogic> discountPolicies = discountPolicy.getDiscountPolicies();

        if (isContain) {
            assertThat(discountPolicies).contains(eventDiscountLogic);
        }
        if (!isContain) {
            assertThat(discountPolicies).doesNotContain(eventDiscountLogic);
        }
    }

    private static Stream<Arguments> discountPolicyDataProvider() {
        return Stream.of(
                Arguments.of(10, true, EventDiscountLogic.CHRISMAS_DISCOUNT),
                Arguments.of(26, false, EventDiscountLogic.CHRISMAS_DISCOUNT),
                Arguments.of(25, true, EventDiscountLogic.SPECIAL_DISCOUNT),
                Arguments.of(18, false, EventDiscountLogic.SPECIAL_DISCOUNT),
                Arguments.of(20, true, EventDiscountLogic.WEEKDAY_DISCOUNT),
                Arguments.of(20, false, EventDiscountLogic.WEEKEND_DISCOUNT),
                Arguments.of(29, true, EventDiscountLogic.WEEKEND_DISCOUNT),
                Arguments.of(29, false, EventDiscountLogic.WEEKDAY_DISCOUNT)
        );
    }

    @ParameterizedTest
    @DisplayName("정책별 할인 금액 계산 테스트")
    @MethodSource("discountAmountDateProvider")
    void discountAmountTest(int visitDate, int expectedAmount, String expectName) {
        Map<String, Integer> foodTypeOrder = Map.of("dessert", 2, "mainMenu", 3);
        Receipt rawOrder = new Receipt(visitDate, foodTypeOrder);

        discountPolicy.setDiscountPolicies(visitDate);
        Map<String, Integer> discountDetails = discountPolicy.createDiscountDetails(rawOrder);

        assertThat(discountDetails.get(expectName)).isEqualTo(expectedAmount);
    }

    private static Stream<Arguments> discountAmountDateProvider() {
        return Stream.of(
                Arguments.of(10, 1900, "크리스마스 디데이 할인"),
                Arguments.of(25, 1000, "특별 할인"),
                Arguments.of(13, 4046, "평일 할인"),
                Arguments.of(15, 6069, "주말 할인")
        );
    }
}
