package ru.fastdelivery.domain.common.calculation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.TestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PackagesShipmentCalculationTest {

    @Test
    @DisplayName("Итоговая цена = NULL -> исключение")
    public void whenTotalPriceNull_thenException() {
        assertThatThrownBy(
                () -> new PackagesShipmentCalculation(null, TestUtils.createTestPrice("1")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Мин. цена = NULL -> исключение")
    public void whenMinPriceNull_thenException() {
        assertThatThrownBy(
                () -> new PackagesShipmentCalculation(TestUtils.createTestPrice("1"), null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Получено правильное значение -> объект создан")
    public void whenCorrectValue_thenCreated() {
        var expected = new PackagesShipmentCalculation(
                TestUtils.createTestPrice("1"),
                TestUtils.createTestPrice("1")
        );
        var actual = new PackagesShipmentCalculation(
                TestUtils.createTestPrice("1"),
                TestUtils.createTestPrice("1")
        );

        assertThat(actual)
                .hasSameHashCodeAs(expected)
                .isEqualTo(expected);
    }
}
