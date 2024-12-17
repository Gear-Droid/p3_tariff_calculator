package ru.fastdelivery.domain.delivery.shipment;

import ru.fastdelivery.domain.common.coordinates.Coordinates;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.dimensions.OuterDimensions;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;

import java.util.List;

/**
 * Доставка
 *
 * @param packages упаковки в грузе
 * @param currency валюта объявленная для груза
 */
public record Shipment(
        List<Pack> packages,
        Currency currency,
        Coordinates departure,
        Coordinates destination
) {
    public Shipment {
        if (packages == null || packages.isEmpty()) {
            throw new IllegalArgumentException("Packages can't be null or Empty!");
        }

        if (currency == null || departure == null || destination == null) {
            throw new IllegalArgumentException("Currency, Departure or Destination can't be null!");
        }
    }

    public Weight weightAllPackages() {
        return packages.stream()
                .map(Pack::weight)
                .reduce(Weight.zero(), Weight::add);
    }

    public Volume getNormalizedAllPackagesVolume() {
        return packages.stream()
                .map(Pack::dimensions)
                .map(OuterDimensions::calculateLengthNormalizedVolume)
                .reduce(Volume.zero(), Volume::add);
    }

}
