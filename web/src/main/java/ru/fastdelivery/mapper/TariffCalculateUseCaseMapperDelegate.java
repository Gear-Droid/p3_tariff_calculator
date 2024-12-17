package ru.fastdelivery.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import ru.fastdelivery.presentation.api.request.CalculatePackagesShipmentRequest;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

import java.util.Map;

public abstract class TariffCalculateUseCaseMapperDelegate implements TariffCalculateUseCaseMapper {

    @Autowired
    private Map<String, TariffCalculateUseCase> tariffCalculateUseCases;

    public TariffCalculateUseCase requestToTariffCalculatorUseCase(
            CalculatePackagesShipmentRequest request) {
        var currencyCode = request.currencyCode();

        if (!tariffCalculateUseCases.containsKey(currencyCode)) {
            throw new IllegalArgumentException("Not available currency code!");
        }

        return tariffCalculateUseCases.get(request.currencyCode());
    }
}
