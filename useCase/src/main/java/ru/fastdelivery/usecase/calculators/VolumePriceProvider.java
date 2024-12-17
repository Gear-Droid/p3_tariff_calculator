package ru.fastdelivery.usecase.calculators;

import ru.fastdelivery.domain.common.price.Price;

/**
 * Получение параметров расчета цены по объему
 */
public interface VolumePriceProvider {
    Price costPerM3();
}
