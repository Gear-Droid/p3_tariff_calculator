package ru.fastdelivery.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.presentation.api.request.CalculatePackagesShipmentRequest;

@DecoratedWith(CurrencyMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CurrencyMapper {

    default Currency requestToCurrency(CalculatePackagesShipmentRequest request) {
        return null;
    }
}
