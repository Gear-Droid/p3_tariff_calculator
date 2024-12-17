package ru.fastdelivery.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.fastdelivery.presentation.api.request.CalculatePackagesShipmentRequest;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

@DecoratedWith(TariffCalculateUseCaseMapperDelegate.class)
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CurrencyMapper.class}
)
public interface TariffCalculateUseCaseMapper {

    default TariffCalculateUseCase requestToTariffCalculatorUseCase(
            CalculatePackagesShipmentRequest request) {
        return null;
    }
}
