package ru.fastdelivery;

import lombok.experimental.UtilityClass;
import ru.fastdelivery.domain.common.coordinates.Coordinates;
import ru.fastdelivery.domain.common.coordinates.Latitude;
import ru.fastdelivery.domain.common.coordinates.Longitude;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.currency.CurrencyPropertiesProvider;
import ru.fastdelivery.domain.common.dimensions.OuterDimensions;
import ru.fastdelivery.domain.common.distance.Distance;
import ru.fastdelivery.domain.common.length.Length;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@UtilityClass
public class TestUtils {

    public Weight createTestWeight(String stringValue) {
        return new Weight(new BigInteger(stringValue));
    }

    public Length createTestLength(int value) {
        return new Length(value);
    }

    public OuterDimensions createTestDimensions(int length, int width, int height) {
        return new OuterDimensions(
                createTestLength(length), createTestLength(width), createTestLength(height)
        );
    }

    public Currency createTestCurrency(String currencyCode, CurrencyPropertiesProvider provider) {
        return new CurrencyFactory(provider).create(currencyCode);
    }

    public Price createTestPrice(String stringValue) {
        return new Price(
                new BigDecimal(stringValue),
                Config.getTestCurrency()
        );
    }

    public Volume createTestVolume(String stringValue) {
        return new Volume(new BigInteger(stringValue));
    }

    public Coordinates createTestCoordinates(BigDecimal latitudeValue, BigDecimal longitudeValue) {
        return new Coordinates(
                new Latitude(latitudeValue),
                new Longitude(longitudeValue)
        );
    }

    public Coordinates createTestCoordinates(String latitudeStringValue, String longitudeStringValue) {
        return createTestCoordinates(
                new BigDecimal(latitudeStringValue),
                new BigDecimal(longitudeStringValue)
        );
    }

    public Distance createTestDistance(int meter) {
        return new Distance(meter);
    }

    public Pack createTestPack(Weight weight, OuterDimensions dimensions) {
        return new Pack(weight, dimensions);
    }

    public Pack createTestPack() {
        return createTestPack(new Weight(BigInteger.ONE), createTestDimensions(1, 1, 1));
    }

    public Shipment createTestShipment(List<Pack> packages,
                                       Currency currency,
                                       Coordinates departureCoordinates,
                                       Coordinates destinationCoordinates) {
        return new Shipment(packages, currency, departureCoordinates, destinationCoordinates);
    }

    public Shipment createTestShipment() {
        return createTestShipment(
                List.of(createTestPack(), createTestPack(), createTestPack()),
                createTestCurrency("RUB", code -> true),
                createTestCoordinates("66.666666", "66.666666"),
                createTestCoordinates("77.777777", "77.777777")
        );
    }
}
