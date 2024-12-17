package ru.fastdelivery.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.fastdelivery.AbstractSpringTest;
import ru.fastdelivery.config.Config;
import ru.fastdelivery.domain.common.calculation.PackagesShipmentCalculation;
import ru.fastdelivery.domain.common.coordinates.Coordinates;
import ru.fastdelivery.domain.common.coordinates.Latitude;
import ru.fastdelivery.domain.common.coordinates.Longitude;
import ru.fastdelivery.domain.common.dimensions.OuterDimensions;
import ru.fastdelivery.domain.common.length.Length;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.presentation.api.request.CalculatePackagesShipmentRequest;
import ru.fastdelivery.presentation.api.request.CargoPackage;
import ru.fastdelivery.presentation.api.request.CoordinatesData;
import ru.fastdelivery.presentation.api.response.CalculatePackagesShipmentResponse;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ShipmentMapperTest extends AbstractSpringTest {

    @Autowired
    private ShipmentMapper shipmentMapper;
    @MockBean
    private PackMapper packMapper;
    @MockBean
    private CurrencyMapper currencyMapper;
    @MockBean
    private CoordinatesMapper coordinatesMapper;

    @Test
    @DisplayName("Маппинг CalculatePackagesShipmentRequest -> Shipment")
    public void whenCalculatePackagesShipmentRequest_thenShipment() {
        var testCargoPackage = new CargoPackage(
                new BigInteger("1"),
                1, 1, 1
        );
        var request = new CalculatePackagesShipmentRequest(
                List.of(testCargoPackage, testCargoPackage),
                Config.getTestCurrencyCode(),
                new CoordinatesData(BigDecimal.ONE, BigDecimal.ONE),
                new CoordinatesData(BigDecimal.ONE, BigDecimal.ONE)
        );

        var mockedCurrency = Config.getTestCurrency();
        var mockedCoordinates = new Coordinates(new Latitude(BigDecimal.ONE), new Longitude(BigDecimal.ONE));
        var mockedPack = new Pack(
                new Weight(new BigInteger("1")),
                new OuterDimensions(Length.fromMillimeter(1), Length.fromMillimeter(1), Length.fromMillimeter(1))
        );

        when(currencyMapper.requestToCurrency(request)).thenReturn(mockedCurrency);
        when(coordinatesMapper.coordinatesDataToCoordinates(any())).thenReturn(mockedCoordinates);
        when(packMapper.cargoPackageToPack(any())).thenReturn(mockedPack);

        var expected = new Shipment(
                List.of(mockedPack, mockedPack),
                mockedCurrency,
                mockedCoordinates,
                mockedCoordinates
        );

        assertThat(shipmentMapper.requestToShipment(request))
                .isEqualTo(expected)
                .hasSameHashCodeAs(expected);
    }

    @Test
    @DisplayName("Маппинг Price -> BigDecimal")
    public void whenPrice_thenBigDecimal() {
        var price = new Price(BigDecimal.ONE, Config.getTestCurrency());
        var expected = new BigDecimal("1.00");

        assertThat(shipmentMapper.priceToBigDecimal(price))
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Маппинг PackagesShipmentCalculation -> CalculatePackagesShipmentResponse")
    public void whenPackagesShipmentCalculation_thenCalculatePackagesShipmentResponse() {
        var price = new Price(BigDecimal.ONE, Config.getTestCurrency());
        var given = new PackagesShipmentCalculation(price, price);

        var expected = new CalculatePackagesShipmentResponse(
                new BigDecimal("1.00"),
                new BigDecimal("1.00"),
                Config.getTestCurrencyCode()
        );

        assertThat(shipmentMapper.calculationToResponse(given))
                .isEqualTo(expected);
    }
}
