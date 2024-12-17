package ru.fastdelivery.domain.delivery.pack;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.TestUtils;
import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PackTest {

    @Test
    @DisplayName("Вес больше допустимого -> исключение")
    void whenWeightMoreThanMaxWeight_thenThrowException() {
        var weight = TestUtils.createTestWeight("150001");
        var dimensions = TestUtils.createTestDimensions(1, 1, 1);

        assertThatThrownBy(() -> new Pack(weight, dimensions))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Допустимый вес -> объект создан")
    void whenWeightLessThanMaxWeight_thenObjectCreated() {
        var actual = new Pack(
                TestUtils.createTestWeight("1000"),
                TestUtils.createTestDimensions(1, 1, 1)
        );

        assertThat(actual.weight())
                .isEqualTo(new Weight(BigInteger.valueOf(1_000)));
    }
}