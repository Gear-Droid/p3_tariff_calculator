package ru.fastdelivery.presentation.calc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fastdelivery.aop.LoggableController;
import ru.fastdelivery.domain.common.calculation.PackagesShipmentCalculation;
import ru.fastdelivery.mapper.ShipmentMapper;
import ru.fastdelivery.mapper.TariffCalculateUseCaseMapper;
import ru.fastdelivery.presentation.api.request.CalculatePackagesShipmentRequest;
import ru.fastdelivery.presentation.api.response.CalculatePackagesShipmentResponse;
import ru.fastdelivery.service.TariffCalculateService;

@RestController
@RequestMapping("/api/v1/calculate/")
@RequiredArgsConstructor
@Tag(name = "Расчеты стоимости доставки")
public class CalculateController {

    private final ShipmentMapper shipmentMapper;
    private final TariffCalculateUseCaseMapper tariffCalculateUseCaseMapper;
    private final TariffCalculateService tariffCalculateService;

    @PostMapping
    @LoggableController
    @Operation(summary = "Расчет стоимости по упаковкам груза")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    public CalculatePackagesShipmentResponse calculate(
            @RequestBody @Valid CalculatePackagesShipmentRequest request) {
        PackagesShipmentCalculation calculation = tariffCalculateService
                .calculateShipmentPriceByUseCase(
                        shipmentMapper.requestToShipment(request),
                        tariffCalculateUseCaseMapper.requestToTariffCalculatorUseCase(request)
                );

        return shipmentMapper.calculationToResponse(calculation);
    }
}

