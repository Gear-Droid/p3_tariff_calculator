package ru.fastdelivery.domain.delivery.pack;

import ru.fastdelivery.domain.common.dimensions.OuterDimensions;
import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigInteger;

/**
 * Упаковка груза
 *
 * @param weight вес товаров в упаковке
 */
public record Pack(
        Weight weight,
        OuterDimensions dimensions
) {

    private static final Weight maxWeight = new Weight(BigInteger.valueOf(150_000));

    public Pack {
        if (weight == null || dimensions == null) {
            throw new IllegalArgumentException("Weight or OuterDimensions can't be null!");
        }

        if (weight.greaterThan(maxWeight)) {
            throw new IllegalArgumentException("Package weight can't be more than " + maxWeight);
        }
    }
}
