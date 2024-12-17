package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.fastdelivery.validation.CoordinatesDataValid;

import java.math.BigDecimal;

@CoordinatesDataValid
public record CoordinatesData(
        @Schema(description = "Географическая долгота", example = "53.398660")
        BigDecimal latitude,

        @Schema(description = "Географическая широта", example = "85.027532")
        BigDecimal longitude
) {}
