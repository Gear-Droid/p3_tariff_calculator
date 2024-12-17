package ru.fastdelivery.domain.common.price;

/**
 * Создание цены с проверками
 */
public class PriceFactory {

    public static Price calculatePrice(PriceAbstractFactory factory) {
        if (factory == null) {
            throw new IllegalArgumentException("Factory cannot be null!");
        }

        return factory.calculatePrice();
    }
}
