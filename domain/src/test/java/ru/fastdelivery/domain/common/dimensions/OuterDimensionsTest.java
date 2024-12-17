package ru.fastdelivery.domain.common.dimensions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.fastdelivery.domain.TestUtils;
import ru.fastdelivery.domain.common.length.Length;
import ru.fastdelivery.domain.common.volume.Volume;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OuterDimensionsTest {

    @Test
    @DisplayName("Попытка создать отрицательные размеры -> исключение")
    void whenOuterDimensionsLengthBelowZero_thenException() {
        assertThatThrownBy(() -> new OuterDimensions(
                Length.fromMillimeter(-10),
                Length.fromMillimeter(-1),
                Length.fromMillimeter(0))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Сравниваем одинаковые размеры -> значения и хэш-код равны")
    void whenEqualsTypeOuterDimensions_thenSame() {
        var actual = TestUtils.createTestDimensions(10, 5, 1);
        var expected = new OuterDimensions(
                Length.fromMillimeter(10),
                Length.fromMillimeter(5),
                Length.fromMillimeter(1)
        );

        assertThat(actual)
                .isEqualTo(expected)
                .hasSameHashCodeAs(expected);
    }

    @Test
    @DisplayName("Если размер = null -> исключение")
    void whenLengthIsNull_thenException() {
        assertThatThrownBy(() -> new OuterDimensions(
                null,
                Length.fromMillimeter(5),
                Length.fromMillimeter(1)
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Создаем размеры -> не равно null")
    void whenEqualsNull_thenFalse() {
        assertThat(TestUtils.createTestDimensions(1, 1, 1))
                .isNotEqualTo(null);
    }

    @ParameterizedTest
    @CsvSource({ Integer.MAX_VALUE + ", 1, 1",
            "1, " + Integer.MAX_VALUE + ", 0",
            "1, 1, " + Integer.MAX_VALUE })
    void whenCreateGreaterThanMax_thenException(Integer length, Integer width, Integer height) {
        assertThatThrownBy(() -> TestUtils.createTestDimensions(length, width, height))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({ "0, 0, 0, 0",
            "1, 1, 1, 125000",
            "100, 100, 100, 1000000" })
    void testDimensionsCalculateLengthNormalizedVolume(Integer length,
                                                       Integer width,
                                                       Integer height,
                                                       BigInteger expected) {
        var dimensions = TestUtils.createTestDimensions(length, width, height);
        var calculatedVolume = dimensions.calculateLengthNormalizedVolume();
        var expectedVolume = new Volume(expected);

        assertThat(calculatedVolume)
                .isEqualTo(expectedVolume)
                .hasSameHashCodeAs(expectedVolume);
    }
}
