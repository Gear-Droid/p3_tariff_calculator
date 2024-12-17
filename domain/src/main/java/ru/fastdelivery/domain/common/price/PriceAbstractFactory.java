package ru.fastdelivery.domain.common.price;

/**
 * Правила расчета цены
 */
public interface PriceAbstractFactory {
    Price calculatePrice();
}
