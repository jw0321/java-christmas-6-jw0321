package christmas.model;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class OrderTest {

    private Order order;

    @BeforeEach
    void setUp() {
        this.order = new Order();
    }

    @DisplayName("주문 입력 테스트")
    @Test
    void setOrderTest() {
        Map<String, Integer> inputOrder = new HashMap<>(Map.of("티본스테이크",1, "제로콜라",3));

        order.setOrder(inputOrder);

        assertThat(order.getOrderReceipt().details().keySet()).contains("티본스테이크");
    }

    @ParameterizedTest
    @DisplayName("주문 입력 예외 테스트")
    @MethodSource("setOrderExceptionDataProvider")
    void setOrderExceptionTest(Map<String, Integer> inputOrder) {
        assertThatThrownBy(() -> order.setOrder(inputOrder))
                        .isInstanceOf(IllegalArgumentException.class);
    }
    private static Stream<Arguments> setOrderExceptionDataProvider() {
        return Stream.of(
                Arguments.of(Map.of("갈릭스테이크",1)),
                Arguments.of(Map.of("티본스테이크", -1)),
                Arguments.of(Map.of("티본스테이크", 21)),
                Arguments.of(Map.of("티본스테이크", 10, "제로콜라", 15))
        );
    }

    @DisplayName("총 주문 금액 계산 테스트")
    @Test
    void calculateRawOrderAmount() {
        Map<String, Integer> inputOrder = new HashMap<>(Map.of("티본스테이크",1, "제로콜라",3));
        int expectedAmount = 64000;

        order.setOrder(inputOrder);
        int rawOrderAmount = order.calculateRawOrderAmount();

        assertThat(rawOrderAmount).isEqualTo(expectedAmount);
    }

}
