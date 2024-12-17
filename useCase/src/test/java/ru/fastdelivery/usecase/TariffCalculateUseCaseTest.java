package ru.fastdelivery.usecase;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import ru.fastdelivery.TestUtils;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.price.PriceFactory;
import ru.fastdelivery.usecase.calculators.*;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TariffCalculateUseCaseTest {

    private final Shipment mockShipment = mock(Shipment.class);
    private final WeightPriceCalculator mockWeightCalculator = mock(WeightPriceCalculator.class);
    private final VolumePriceCalculator mockVolumeCalculator = mock(VolumePriceCalculator.class);
    private final DistancePriceCalculator mockDistanceCalculator = mock(DistancePriceCalculator.class);
    private final WeightPriceProvider mockWeightProvider = mock(WeightPriceProvider.class);
    private final TariffCalculateUseCase tariffCalculateUseCase = new TariffCalculateUseCase(
            mockWeightProvider, mock(VolumePriceProvider.class), mock(DistanceCoordinatesProvider.class)
    );

    @Test
    @DisplayName("Получение минимальной стоимости -> успешно")
    void whenMinimalPrice_thenSuccess() {
        when(mockWeightProvider.minimalPrice())
                .thenReturn(TestUtils.createTestPrice("10"));

        var expected = TestUtils.createTestPrice("10");

        assertEquals(expected, tariffCalculateUseCase.getMinimalPrice());
    }

    @Test
    @DisplayName("Расчет стоимости доставки по весу -> рез-т по весу")
    public void whenCalculatePriceByWeight_thenResult() {
        var testPrice = TestUtils.createTestPrice("1");

        try (MockedStatic<PriceFactory> mockPriceFactory = mockStatic(PriceFactory.class)) {
            mockPriceFactory.when(() ->
                    PriceFactory.calculatePrice(any(mockWeightCalculator.getClass())))
                    .thenReturn(testPrice);

            assertEquals(
                    testPrice,
                    tariffCalculateUseCase.calculatePriceByWeight(mockShipment)
            );
        }
    }

    @Test
    @DisplayName("Расчет стоимости доставки по объему -> рез-т по объему")
    public void whenCalculatePriceByVolume_thenResult() {
        var testPrice = TestUtils.createTestPrice("1");

        try (MockedStatic<PriceFactory> mockPriceFactory = mockStatic(PriceFactory.class)) {
            mockPriceFactory.when(() ->
                    PriceFactory.calculatePrice(any(mockVolumeCalculator.getClass())))
                    .thenReturn(testPrice);

            assertEquals(
                    testPrice,
                    tariffCalculateUseCase.calculatePriceByVolume(mockShipment)
            );
        }
    }

    @Test
    @DisplayName("Расчет стоимости доставки по дистанции -> рез-т по дистанции")
    public void whenCalculatePriceByDistance_thenResult() {
        var testPrice = TestUtils.createTestPrice("1");

        try (MockedStatic<PriceFactory> mockPriceFactory = mockStatic(PriceFactory.class)) {
            mockPriceFactory.when(() ->
                    PriceFactory.calculatePrice(any(mockDistanceCalculator.getClass())))
                    .thenReturn(testPrice);

            assertEquals(
                    testPrice,
                    tariffCalculateUseCase.calculatePriceByDistance(mockShipment, testPrice)
            );
        }
    }

    @ParameterizedTest
    @CsvSource({"0, 10, 20, 20",
            "200, 0, 100, 200",
            "1000, 2000, 0, 2000"})
    void testCalculateBasePrice(String minPriceValue,
                                String weightPriceValue,
                                String volumePriceValue,
                                String expectedValue) {
        var weightPrice = TestUtils.createTestPrice(weightPriceValue);
        var volumePrice = TestUtils.createTestPrice(volumePriceValue);

        when(mockWeightProvider.minimalPrice())
                .thenReturn(TestUtils.createTestPrice(minPriceValue));
        Map<MockedStatic.Verification, Price> mocks = Map.of(
                () -> PriceFactory.calculatePrice(any(mockWeightCalculator.getClass())), weightPrice,
                () -> PriceFactory.calculatePrice(any(mockVolumeCalculator.getClass())), volumePrice
        );

        try (MockedStatic<PriceFactory> mockPriceFactory = mockStatic(PriceFactory.class)) {
            mocks.forEach((key, value) -> mockPriceFactory
                    .when(key)
                    .thenReturn(value));

            var expected = TestUtils.createTestPrice(expectedValue);
            var actual = tariffCalculateUseCase.calculateBasePrice(mockShipment);

            assertEquals(expected, actual);

            mockPriceFactory.verify(() ->
                    PriceFactory.calculatePrice(any()),
                    times(mocks.size()));
            mocks.forEach((key, value) -> mockPriceFactory
                    .verify(key, times(1)));
        }

    }
}