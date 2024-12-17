package ru.fastdelivery.usecase.calculators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import ru.fastdelivery.TestUtils;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.utils.GeoToolsUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DistancePriceCalculatorTest {

    private final Shipment mockShipment = mock(Shipment.class);
    private final DistanceCoordinatesProvider mockProvider = mock(DistanceCoordinatesProvider.class);

    @Test
    @DisplayName("Экземпляр доставки null -> исключение")
    public void whenShipmentIsNull_thenException() {
        var basePrice = TestUtils.createTestPrice("1");

        assertThrows(
                IllegalArgumentException.class,
                () -> new DistancePriceCalculator(null, mockProvider, basePrice)
        );
    }

    @Test
    @DisplayName("Провайдер null -> исключение")
    public void whenProviderIsNull_thenException() {
        var basePrice = TestUtils.createTestPrice("1");

        assertThrows(
                IllegalArgumentException.class,
                () -> new DistancePriceCalculator(mockShipment, null, basePrice)
        );
    }

    @Test
    @DisplayName("Базовая цена null -> исключение")
    public void whenBasePriceIsNull_thenException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new DistancePriceCalculator(mockShipment, mockProvider, null)
        );
    }

    @Test
    @DisplayName("Недопустимая долгота -> исключение")
    public void whenNotAvailableLatitude_thenException() {
        var basePrice = TestUtils.createTestPrice("1");

        when(mockProvider.isAvailableLatitude(any())).thenReturn(false);
        when(mockProvider.isAvailableLongitude(any())).thenReturn(true);
        when(mockShipment.departure())
                .thenReturn(TestUtils.createTestCoordinates("1", "1"));
        when(mockShipment.destination())
                .thenReturn(TestUtils.createTestCoordinates("1", "1"));

        assertThrows(IllegalArgumentException.class,
                () -> new DistancePriceCalculator(mockShipment, mockProvider, basePrice).calculatePrice());
    }

    @Test
    @DisplayName("Недопустимая широта -> исключение")
    public void whenNotAvailableLongitude_thenException() {
        var basePrice = TestUtils.createTestPrice("1");

        when(mockProvider.isAvailableLatitude(any())).thenReturn(true);
        when(mockProvider.isAvailableLongitude(any())).thenReturn(false);
        when(mockShipment.departure())
                .thenReturn(TestUtils.createTestCoordinates("1", "1"));
        when(mockShipment.destination())
                .thenReturn(TestUtils.createTestCoordinates("1", "1"));

        assertThrows(IllegalArgumentException.class,
                () -> new DistancePriceCalculator(mockShipment, mockProvider, basePrice).calculatePrice());
    }

    @ParameterizedTest
    @CsvSource({"450, 345, 500, 383.34",
            "450, 788.23, 200, 788.23"
    })
    public void testCalculatePrice(int minDistanceMeter,
                                   String basePriceValue,
                                   int distanceValue,
                                   String result) {
        var basePrice = TestUtils.createTestPrice(basePriceValue);
        var minDistance = TestUtils.createTestDistance(minDistanceMeter);
        var distance = TestUtils.createTestDistance(distanceValue);

        when(mockProvider.getMinDistanceKm()).thenReturn(minDistance.toKm());
        when(mockProvider.isAvailableLatitude(any())).thenReturn(true);
        when(mockProvider.isAvailableLongitude(any())).thenReturn(true);
        when(mockShipment.departure())
                .thenReturn(TestUtils.createTestCoordinates("1", "1"));
        when(mockShipment.destination())
                .thenReturn(TestUtils.createTestCoordinates("1", "1"));

        var expected = TestUtils.createTestPrice(result);

        try (MockedStatic<GeoToolsUtils> mockGeoTools = mockStatic(GeoToolsUtils.class)) {
            mockGeoTools.when(() ->
                    GeoToolsUtils.calculateDistance(
                            mockShipment.departure(),
                            mockShipment.destination()
                    )
            ).thenReturn(distance);

            var actual = new DistancePriceCalculator(mockShipment, mockProvider, basePrice)
                    .calculatePrice();
            assertEquals(expected, actual);
        }
    }
}
