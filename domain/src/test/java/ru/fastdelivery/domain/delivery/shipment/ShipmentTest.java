package ru.fastdelivery.domain.delivery.shipment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.TestUtils;
import ru.fastdelivery.domain.common.dimensions.OuterDimensions;

import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ShipmentTest {

    private Shipment createTestShipment() {
        return TestUtils.createTestShipment(
                List.of(
                        TestUtils.createTestPack(
                                TestUtils.createTestWeight("10"),
                                TestUtils.createTestDimensions(1, 1, 1)
                        ),
                        TestUtils.createTestPack(
                                TestUtils.createTestWeight("1"),
                                TestUtils.createTestDimensions(100, 100, 100)
                        )
                ),
                TestUtils.createTestCurrency("RUB", code -> true),
                TestUtils.createTestCoordinates("1", "1"),
                TestUtils.createTestCoordinates("10", "10")
        );
    }

    @Test
    @DisplayName("При расчете суммарного веса -> корректное значение")
    void whenSummarizingWeightOfAllPackages_thenReturnSum() {
        var massOfShipment = createTestShipment()
                .weightAllPackages();

        assertThat(massOfShipment.weightGrams())
                .isEqualByComparingTo(BigInteger.valueOf(11));
    }

    @Test
    @DisplayName("При расчете нормированного объема -> корректное значение")
    void whenGetNormalizedAllPackagesVolume_thenReturnCorrectVolume() {
        var normalizedVolume = createTestShipment()
                .getNormalizedAllPackagesVolume();

        var pack1Volume = Math.pow(OuterDimensions.NORMALIZATION_VALUE, 3);
        var pack2Volume = Math.pow(OuterDimensions.NORMALIZATION_VALUE * 2, 3);
        long expected = (long) (pack1Volume + pack2Volume);

        assertThat(normalizedVolume.mm3())
                .isEqualByComparingTo(BigInteger.valueOf(expected));
    }
}