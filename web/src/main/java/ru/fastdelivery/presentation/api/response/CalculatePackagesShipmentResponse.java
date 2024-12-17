package ru.fastdelivery.presentation.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.fastdelivery.domain.common.price.Price;

import java.math.BigDecimal;

public record CalculatePackagesShipmentResponse(
        @Schema(description = "Итоговая цена (с учетом дистанции)", example = "405.05")
        BigDecimal totalPrice,

        @Schema(description = "Базовая цена (по весу и объему)", example = "350.00")
        BigDecimal minimalPrice,

        @Schema(description = "Трехбуквенный код валюты рассчитанных цен", example = "RUB")
        String currencyCode
) {

    public CalculatePackagesShipmentResponse(Price totalPrice, Price minimalPrice) {
        this(totalPrice.amount(), minimalPrice.amount(), totalPrice.currency().getCode());

        if (currencyIsNotEqual(totalPrice, minimalPrice)) {
            throw new IllegalArgumentException("Currency codes must be the same");
        }
    }

    private static boolean currencyIsNotEqual(Price priceLeft, Price priceRight) {
        return !priceLeft.currency().equals(priceRight.currency());
    }
}
