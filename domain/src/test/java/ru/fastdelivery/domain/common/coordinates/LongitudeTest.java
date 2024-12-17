package ru.fastdelivery.domain.common.coordinates;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LongitudeTest {

    @Test
    @DisplayName("Получено BigDecimal NULL -> исключение")
    public void whenBigDecimalNull_thenException() {
        assertThatThrownBy(() -> new Longitude((BigDecimal) null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Получено String NULL -> исключение")
    public void whenStringNull_thenException() {
        assertThatThrownBy(() -> new Longitude((String) null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Получено правильное значение -> объект создан")
    public void whenCorrectValue_thenCreated() {
        var legal = new Longitude(BigDecimal.TEN);
        var expected = new Longitude(BigDecimal.TEN);

        assertThat(legal)
                .hasSameHashCodeAs(expected)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Получено правильное значение -> объект создан")
    public void whenCoordinateCreated_thenSameClass() {
        var legal = new Longitude("10");
        var expected = new Longitude(BigDecimal.TEN);

        assertThat(legal)
                .hasSameHashCodeAs(expected)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Получено максимальное значение + 1 -> исключение")
    public void whenHigherMaxValue_thenException() {
        var illegal = Longitude.MAX_VALUE.add(BigDecimal.ONE);

        assertThatThrownBy(() -> new Longitude(illegal))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Получено минимальное значение - 1 -> исключение")
    public void whenLowerMinValue_thenException() {
        var illegal = Longitude.MIN_VALUE.subtract(BigDecimal.ONE);

        assertThatThrownBy(() -> new Longitude(illegal))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({"-1.0, 1.0, -1",
            "1.0, 1.0, 0",
            "1.0, -1.0, 1",
    })
    public void testCompareTo(BigDecimal firstValue, BigDecimal secondValue, int expected) {
        var first = new Longitude(firstValue);
        var second = new Longitude(secondValue);

        assertThat(first.compareTo(second))
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Получено слишком точное значение значение -> объект создан корректно")
    public void whenHighPrecisionBigDecimal_thenCreated() {
        var legal = new Longitude(new BigDecimal("10.0000000000000001"));
        var expected = new Longitude(BigDecimal.TEN.setScale(6));

        assertThat(legal)
                .hasSameHashCodeAs(expected)
                .isEqualTo(expected);
    }
}
