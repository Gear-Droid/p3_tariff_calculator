package ru.fastdelivery.usecase.calculators;

import ru.fastdelivery.domain.common.price.Price;

/**
 * Получение параметров расчета цены по весу
 */
public interface WeightPriceProvider {
    Price costPerKg();

    Price minimalPrice();
}
