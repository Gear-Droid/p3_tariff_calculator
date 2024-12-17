package ru.fastdelivery.usecase.calculators;

import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.price.PriceAbstractFactory;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

/**
 * Расчет цены на основе веса с проверками
 */
public class WeightPriceCalculator implements PriceAbstractFactory {

    private final Shipment shipment;
    private final WeightPriceProvider provider;

    public WeightPriceCalculator(Shipment shipment, WeightPriceProvider provider) {
        if (shipment == null || provider == null) {
            throw new IllegalArgumentException("Shipment or WeightPriceProvider cannot be null!");
        }

        this.shipment = shipment;
        this.provider = provider;
    }

    @Override
    public Price calculatePrice() {
        var allPackagesWeightKg = shipment.weightAllPackages().kilograms();

        return provider.costPerKg()
                .multiply(allPackagesWeightKg);
    }
}
