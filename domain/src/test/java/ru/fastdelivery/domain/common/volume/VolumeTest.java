package ru.fastdelivery.domain.common.volume;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.fastdelivery.domain.TestUtils;
import ru.fastdelivery.domain.common.length.Length;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class VolumeTest {

    @Test
    @DisplayName("Попытка создать отрицательный объем -> исключение")
    void whenVolumeBelowZero_thenException() {
        var volumeMm3 = new BigInteger("-1");

        assertThatThrownBy(() -> new Volume(volumeMm3))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Сравниваем одинаковые объемы -> значения и хэш-код равны")
    void whenEqualsTypeVolume_thenSame() {
        var volume = new BigInteger("100");
        var volumeSame = new BigInteger("100");

        assertThat(volume)
                .isEqualTo(volumeSame)
                .hasSameHashCodeAs(volumeSame);
    }

    @Test
    @DisplayName("Создаем объем -> не равен null")
    void whenEqualsNull_thenFalse() {
        var volume = TestUtils.createTestVolume("0");

        assertThat(volume).isNotEqualTo(null);
    }

    @ParameterizedTest
    @CsvSource({ "0, 0, 0, 0",
            "1, 1, 1, 1",
            "100, 100, 100, 1000000",
            Integer.MAX_VALUE + ", " + Integer.MAX_VALUE + ", " + Integer.MAX_VALUE + ", " + "9903520300447984150353281023" })
    void testVolumeCalculate(Integer length,
                             Integer width,
                             Integer height,
                             BigInteger expected) {
        var calculatedVolume = Volume.calculate(new Length(length), new Length(width), new Length(height));
        var expectedVolume = new Volume(expected);

        assertThat(calculatedVolume)
                .isEqualTo(expectedVolume)
                .hasSameHashCodeAs(expectedVolume);
    }

    @ParameterizedTest
    @CsvSource({ "1, 9, 0.000000001",
            "0, 1, 0.0",
            "1000000, 2, 0.00",
            "1000000, 3, 0.001",
            "1000000000, 0, 1" })
    void testConvertToM3(BigInteger value, int scale, BigDecimal expected) {
        var actual = new Volume(value)
                .convertToM3(scale);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Запрос объема в мм3 -> получено корректное значение")
    void whenGetMillimeters3_thenReceiveMm3() {
        var volume = new Volume(new BigInteger("1001"));
        var actual = volume.mm3();

        assertThat(actual).isEqualByComparingTo(new BigInteger("1001"));
    }
}
