package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.fastdelivery.validation.CargoPackageValid;

import java.math.BigInteger;

@CargoPackageValid
public record CargoPackage(
        @Schema(description = "Вес упаковки, граммы", example = "4056")
        BigInteger weight,

        @Schema(description = "Длина упаковки, мм", example = "350")
        Integer length,

        @Schema(description = "Ширина упаковки, мм", example = "600")
        Integer width,

        @Schema(description = "Высота упаковки, мм", example = "250")
        Integer height
) {}
