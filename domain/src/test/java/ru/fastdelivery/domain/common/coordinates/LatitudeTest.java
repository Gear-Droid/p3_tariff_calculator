package ru.fastdelivery.domain.common.coordinates;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LatitudeTest {

    @Test
    @DisplayName("Получено BigDecimal NULL -> исключение")
    public void whenBigDecimalNull_thenException() {
        assertThatThrownBy(() -> new Latitude((BigDecimal) null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Получено String NULL -> исключение")
    public void whenStringNull_thenException() {
        assertThatThrownBy(() -> new Latitude((String) null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Получено правильное значение -> объект создан")
    public void whenCorrectValue_thenCreated() {
        var legal = new Latitude(BigDecimal.TEN);
        var expected = new Latitude(BigDecimal.TEN);

        assertThat(legal)
                .hasSameHashCodeAs(expected)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Получено максимальное значение + 1 -> исключение")
    public void whenHigherMaxValue_thenException() {
        var illegal = Latitude.MAX_VALUE
                .add(BigDecimal.ONE);

        assertThatThrownBy(() -> new Latitude(illegal))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Получено минимальное значение - 1 -> исключение")
    public void whenLowerMinValue_thenException() {
        var illegal = Latitude.MIN_VALUE
                .subtract(BigDecimal.ONE);

        assertThatThrownBy(() -> new Latitude(illegal))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({"-1.0, 1.0, -1",
            "1.0, 1.0, 0",
            "1.0, -1.0, 1",
    })
    public void testCompareTo(BigDecimal firstValue, BigDecimal secondValue, int expected) {
        var first = new Latitude(firstValue);
        var second = new Latitude(secondValue);

        assertThat(first.compareTo(second))
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Получено слишком точное значение значение -> объект создан корректно")
    public void whenHighPrecisionBigDecimal_thenCreated() {
        var legal = new Latitude(new BigDecimal("10.0000000000000001"));
        var expected = new Latitude(BigDecimal.TEN.setScale(6));

        assertThat(legal)
                .hasSameHashCodeAs(expected)
                .isEqualTo(expected);
    }
}
