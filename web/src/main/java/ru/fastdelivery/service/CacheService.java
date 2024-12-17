package ru.fastdelivery.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

@Service
@CacheConfig(cacheManager = "distancePriceCacheManager")
public class CacheService {

    @Cacheable(
            value = "priceByCoordinatesAndBasePrice",
            key = "#shipment.departure.toString() + #shipment.destination.toString()"
                    + " + #basePrice.toString()"
    )
    public Price calculatePriceByDistance(Shipment shipment,
                                          Price basePrice,
                                          TariffCalculateUseCase useCase) {
        return useCase.calculatePriceByDistance(shipment, basePrice);
    }
}
