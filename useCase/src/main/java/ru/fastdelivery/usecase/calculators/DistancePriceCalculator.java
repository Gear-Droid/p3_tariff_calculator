package ru.fastdelivery.usecase.calculators;

import ru.fastdelivery.domain.common.coordinates.Coordinates;
import ru.fastdelivery.domain.common.distance.Distance;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.price.PriceAbstractFactory;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.utils.GeoToolsUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Расчет цены на основе дистанции с проверками
 */
public class DistancePriceCalculator implements PriceAbstractFactory {

    private final Shipment shipment;
    private final DistanceCoordinatesProvider provider;
    private final Price basePrice;

    public DistancePriceCalculator(Shipment shipment,
                                   DistanceCoordinatesProvider provider,
                                   Price basePrice) {
        if (shipment == null || provider == null || basePrice == null) {
            throw new IllegalArgumentException(
                    "Shipment, DistanceCoordinatesProvider or BasePrice cannot be null!"
            );
        }

        this.shipment = shipment;
        this.provider = provider;
        this.basePrice = basePrice;
    }

    @Override
    public Price calculatePrice() {
        if (!isValidShipmentCoordinates()) {
            throw new IllegalArgumentException(
                    "Not valid coordinates! Latitude must be %s, Longitude must be %s".formatted(
                            provider.getLatitudeProperties(), provider.getLongitudeProperties()
                    )
            );
        }

        return basePrice
                .multiply(getDistanceMultiplier(), RoundingMode.UP);
    }

    private boolean isValidShipmentCoordinates() {
        return isValidCoordinates(shipment.departure())
                && isValidCoordinates(shipment.destination());
    }

    private boolean isValidCoordinates(Coordinates coordinates) {
        return provider.isAvailableLatitude(coordinates.latitude())
                && provider.isAvailableLongitude(coordinates.longitude());
    }

    private BigDecimal getDistanceMultiplier() {
        Distance shipmentDistance = GeoToolsUtils.calculateDistance(
                shipment.departure(), shipment.destination()
        );

        return BigDecimal.valueOf(
                Math.max(1, shipmentDistance.toKm() / provider.getMinDistanceKm())
        );
    }

}
