package ru.fastdelivery.usecase.calculators;

import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.price.PriceAbstractFactory;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

/**
 * Расчет цены на основе объема с проверками
 */
public class VolumePriceCalculator implements PriceAbstractFactory {

    private final Shipment shipment;
    private final VolumePriceProvider provider;

    public VolumePriceCalculator(Shipment shipment, VolumePriceProvider provider) {
        if (shipment == null || provider == null) {
            throw new IllegalArgumentException("Shipment or VolumePriceProvider cannot be null!");
        }

        this.shipment = shipment;
        this.provider = provider;
    }

    @Override
    public Price calculatePrice() {
        var costPerM3 = provider.costPerM3();
        var normalizedAllPackagesVolumeM3 = shipment.getNormalizedAllPackagesVolume()
                .convertToM3(4);

        return costPerM3.multiply(normalizedAllPackagesVolumeM3);
    }
}
