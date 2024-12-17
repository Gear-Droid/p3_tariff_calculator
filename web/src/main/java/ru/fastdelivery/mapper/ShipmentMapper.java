package ru.fastdelivery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.fastdelivery.domain.common.calculation.PackagesShipmentCalculation;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.presentation.api.request.CalculatePackagesShipmentRequest;
import ru.fastdelivery.presentation.api.response.CalculatePackagesShipmentResponse;

import java.math.BigDecimal;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CurrencyMapper.class, PackMapper.class, CoordinatesMapper.class}
)
public interface ShipmentMapper {

    @Mapping(source = "request", target = "currency")
    Shipment requestToShipment(CalculatePackagesShipmentRequest request);

    @Mapping(source = "calculation.totalPrice.currency.code", target = "currencyCode")
    CalculatePackagesShipmentResponse calculationToResponse(
            PackagesShipmentCalculation calculation);

    default BigDecimal priceToBigDecimal(Price price) {
        return price.amount();
    }
}
