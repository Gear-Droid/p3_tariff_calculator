package ru.fastdelivery.domain.common.calculation;

import ru.fastdelivery.domain.common.price.Price;

public record PackagesShipmentCalculation(
        Price totalPrice,
        Price minimalPrice
) {
    public PackagesShipmentCalculation {
        if (totalPrice == null || minimalPrice == null) {
            throw new IllegalArgumentException("TotalPrice or MinimalPrice cannot be Null!");
        }

        if (currencyIsNotEqual(totalPrice, minimalPrice)) {
            throw new IllegalArgumentException("Currency codes must be the same!");
        }
    }

    private static boolean currencyIsNotEqual(Price priceLeft, Price priceRight) {
        return !priceLeft.currency().equals(priceRight.currency());
    }
}
