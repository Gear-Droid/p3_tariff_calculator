package ru.fastdelivery.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fastdelivery.domain.common.calculation.PackagesShipmentCalculation;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

@Service
@RequiredArgsConstructor
public class TariffCalculateService {

    private final CacheService cacheService;

    public PackagesShipmentCalculation calculateShipmentPriceByUseCase(
            Shipment shipment,
            TariffCalculateUseCase useCase) {
        return new PackagesShipmentCalculation(
                calculateTotalPrice(shipment, useCase),
                useCase.getMinimalPrice()
        );
    }

    private Price calculateTotalPrice(Shipment shipment,
                                      TariffCalculateUseCase useCase) {
        Price basePrice = useCase.calculateBasePrice(shipment);

        return cacheService.calculatePriceByDistance(shipment, basePrice, useCase);
    }

}
