package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.price.PriceFactory;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.usecase.calculators.*;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {

    private final WeightPriceProvider weightPriceProvider;
    private final VolumePriceProvider volumePriceProvider;
    private final DistanceCoordinatesProvider distanceCoordinatesProvider;

    public Price getMinimalPrice() {
        return weightPriceProvider.minimalPrice();
    }

    public Price calculatePriceByWeight(Shipment shipment) {
        return PriceFactory.calculatePrice(
                new WeightPriceCalculator(shipment, weightPriceProvider)
        );
    }

    public Price calculatePriceByVolume(Shipment shipment) {
        return PriceFactory.calculatePrice(
                new VolumePriceCalculator(shipment, volumePriceProvider)
        );
    }

    public Price calculateBasePrice(Shipment shipment) {
        return getMinimalPrice()
                .max(calculatePriceByWeight(shipment))
                .max(calculatePriceByVolume(shipment));
    }

    public Price calculatePriceByDistance(Shipment shipment, Price basePrice) {
        return PriceFactory.calculatePrice(
                new DistancePriceCalculator(shipment, distanceCoordinatesProvider, basePrice)
        );
    }
}
