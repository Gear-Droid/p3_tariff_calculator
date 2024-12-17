package ru.fastdelivery.usecase.calculators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.fastdelivery.TestUtils;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VolumePriceCalculatorTest {

    private final Shipment mockShipment = mock(Shipment.class);
    private final VolumePriceProvider mockProvider = mock(VolumePriceProvider.class);

    @Test
    @DisplayName("Экземпляр доставки null -> исключение")
    public void whenShipmentIsNull_thenException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new VolumePriceCalculator(null, mockProvider)
        );
    }

    @Test
    @DisplayName("Провайдер null -> исключение")
    public void whenProviderIsNull_thenException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new VolumePriceCalculator(mockShipment, null)
        );
    }

    @ParameterizedTest
    @CsvSource({"0, 0, 0.0000",
            "100, 10, 1000.0000",
            "0.0001, 10, 0.0010",
            "0, 10, 0.0000",
            "10, 0, 0.0000",
            "10, 0, 0.0000",
            "1000, 1, 1000.0000"
    })
    public void testCalculatePrice(String costPerM3,
                                   BigDecimal volumeM3,
                                   String result) {
        var testVolumeValue = volumeM3.multiply(BigDecimal.valueOf(1000000000));

        when(mockProvider.costPerM3()).thenReturn(TestUtils.createTestPrice(costPerM3));
        when(mockShipment.getNormalizedAllPackagesVolume())
                .thenReturn(TestUtils.createTestVolume(String.valueOf(testVolumeValue)));

        var actual = new VolumePriceCalculator(mockShipment, mockProvider)
                .calculatePrice();
        var expected = TestUtils.createTestPrice(result);

        assertEquals(expected, actual);
    }
}
