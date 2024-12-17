package ru.fastdelivery.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.presentation.api.request.CalculatePackagesShipmentRequest;

public abstract class CurrencyMapperDelegate implements CurrencyMapper {

    @Autowired
    private CurrencyFactory currencyFactory;

    @Override
    public Currency requestToCurrency(CalculatePackagesShipmentRequest request) {
        return currencyFactory
                .create(request.currencyCode());
    }
}
