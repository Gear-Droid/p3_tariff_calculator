package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(description = "Данные для расчета стоимости доставки")
public record CalculatePackagesShipmentRequest(
        @Schema(description = "Список упаковок отправления",
                example = "[{\"weight\": 4056, \"length\": 350, \"width\": 600, \"height\": 250}]")
        @Valid
        @NotEmpty(message = "Поле упаковок должно содержать минимум одну упаковку!")
        @NotNull(message = "Отсутствует поле упаковок доставки!")
        List<CargoPackage> packages,

        @Schema(description = "Трехбуквенный код валюты", example = "RUB")
        @Size(min = 3, max = 3, message = "Длина кода валюты должна равняться 3!")
        @NotNull(message = "Отсутствует трехбуквенный код валюты!")
        String currencyCode,

        @Schema(description = "Координаты адреса доставки товара")
        @Valid
        @NotNull(message = "Отсутствует поле с координатами адреса доставки товара!")
        CoordinatesData destination,

        @Schema(description = "Координаты адреса отправления товара")
        @Valid
        @NotNull(message = "Отсутствует поле с координатами адреса отправления товара!")
        CoordinatesData departure
) {}
