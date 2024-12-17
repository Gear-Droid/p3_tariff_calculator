package ru.fastdelivery.properties.provider;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.usecase.calculators.VolumePriceProvider;
import ru.fastdelivery.usecase.calculators.WeightPriceProvider;

import java.math.BigDecimal;

/**
 * Настройки базовых цен стоимости перевозки из конфига для конкретной валюты
 */
@Validated
@Setter
public class PriceProperties implements WeightPriceProvider, VolumePriceProvider {

    private String currencyCode;
    private CurrencyFactory currencyFactory;
    @NotNull
    @Min(0)
    private BigDecimal perKg;
    @NotNull
    @Min(0)
    private BigDecimal minimal;
    @NotNull
    @Min(0)
    private BigDecimal perM3;

    @Override
    public Price costPerKg() {
        return new Price(perKg, currencyFactory.create(currencyCode));
    }

    @Override
    public Price minimalPrice() {
        return new Price(minimal, currencyFactory.create(currencyCode));
    }

    @Override
    public Price costPerM3() {
        return new Price(perM3, currencyFactory.create(currencyCode));
    }
}
