package ru.fastdelivery.usecase.calculators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.fastdelivery.TestUtils;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WeightPriceCalculatorTest {

    private final Shipment mockShipment = mock(Shipment.class);
    private final WeightPriceProvider mockProvider = mock(WeightPriceProvider.class);

    @Test
    @DisplayName("Экземпляр доставки null -> исключение")
    public void whenShipmentIsNull_thenException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new WeightPriceCalculator(null, mockProvider)
        );
    }

    @Test
    @DisplayName("Провайдер null -> исключение")
    public void whenProviderIsNull_thenException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new WeightPriceCalculator(mockShipment, null)
        );
    }

    @ParameterizedTest
    @CsvSource({"0, 0, 0",
            "1, 0, 0",
            "0, 1, 0",
            "10, 20, 200",
            "10, 1, 10"})
    public void testCalculatePrice(String costPerKg,
                                   BigInteger weightKg,
                                   String result) {
        var testStringWeightGrams = String.valueOf(weightKg.multiply(BigInteger.valueOf(1000)));

        when(mockProvider.costPerKg()).thenReturn(TestUtils.createTestPrice(costPerKg));
        when(mockShipment.weightAllPackages())
                .thenReturn(TestUtils.createTestWeight(testStringWeightGrams));

        var actual = new WeightPriceCalculator(mockShipment, mockProvider)
                .calculatePrice();
        var expected = TestUtils.createTestPrice(result);

        assertEquals(expected, actual);
    }
}
