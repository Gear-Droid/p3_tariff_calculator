package ru.fastdelivery.domain.common.length;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LengthTest {

    @Test
    @DisplayName("Попытка создать длину = null -> исключение")
    void whenLengthIsNull_thenException() {
        assertThatThrownBy(() -> new Length(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Попытка создать отрицательную длину -> исключение")
    void whenLengthBelowZero_thenException() {
        var lengthMm = Integer.valueOf(-1);

        assertThatThrownBy(() -> new Length(lengthMm))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Сравниваем одинаковые длины -> значения и хэш-код равны")
    void whenEqualsTypeLength_thenSame() {
        var length = Length.fromMillimeter(100);
        var lengthSame = Length.fromMillimeter(100);

        assertThat(length)
                .isEqualTo(lengthSame)
                .hasSameHashCodeAs(lengthSame);
    }

    @Test
    @DisplayName("Создаем длину -> не равна null")
    void whenEqualsNull_thenFalse() {
        var length = Length.fromMillimeter(100);

        assertThat(length).isNotEqualTo(null);
    }

    @Test
    @DisplayName("Создаем длину с помощью fromMillimeter() -> значения и хэш-код равны")
    void whenFromMillimeter_thenSame() {
        var newLength = Length.fromMillimeter(100);
        var lengthSame = new Length(100);

        assertThat(newLength)
                .isEqualTo(lengthSame)
                .hasSameHashCodeAs(lengthSame);
    }

    @ParameterizedTest
    @CsvSource({ "1000, 1, true",
            "199, 199, false",
            "50, 999, false" })
    void testIsLongerThan(Integer low, Integer high, boolean expected) {
        var lengthLow = Length.fromMillimeter(low);
        var lengthHigh = Length.fromMillimeter(high);

        assertThat(lengthLow.isLongerThan(lengthHigh))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({ "99, 50, 100",
            "100, 50, 100",
            "101, 50, 150" })
    void testNormalizedTo(Integer value, Integer norm, Integer expected) {
        var length = Length.fromMillimeter(value);
        var expectedLength = Length.fromMillimeter(expected);

        assertThat(length.normalizedTo(norm))
                .isEqualTo(expectedLength);
    }

    @Test
    @DisplayName("Запрос длины в мм -> получено корректное значение")
    void whenGetMillimeters_thenReceiveMm() {
        var actual = Length.fromMillimeter(1001).mm();

        assertThat(actual).isEqualByComparingTo(1001);
    }
}
