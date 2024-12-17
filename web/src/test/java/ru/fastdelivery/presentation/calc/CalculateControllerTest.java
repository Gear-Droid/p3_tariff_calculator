package ru.fastdelivery.presentation.calc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.fastdelivery.ControllerTest;
import ru.fastdelivery.config.Config;
import ru.fastdelivery.domain.common.calculation.PackagesShipmentCalculation;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.mapper.ShipmentMapper;
import ru.fastdelivery.mapper.TariffCalculateUseCaseMapper;
import ru.fastdelivery.presentation.api.request.CalculatePackagesShipmentRequest;
import ru.fastdelivery.presentation.api.request.CargoPackage;
import ru.fastdelivery.presentation.api.request.CoordinatesData;
import ru.fastdelivery.presentation.api.response.CalculatePackagesShipmentResponse;
import ru.fastdelivery.service.TariffCalculateService;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CalculateControllerTest extends ControllerTest {

    private final String baseCalculateApi = "/api/v1/calculate/";

    @Autowired
    private Map<String, TariffCalculateUseCase> tariffCalculateUseCases;
    @MockBean
    private TariffCalculateService tariffCalculateService;
    @MockBean
    private ShipmentMapper shipmentMapper;
    @MockBean
    private TariffCalculateUseCaseMapper useCaseMapper;

    @Test
    @DisplayName("Валидные данные для расчета стоимости -> Ответ 200")
    void whenValidInputData_thenReturn200() {
        CargoPackage cargoPackage = new CargoPackage(BigInteger.TEN, 1, 2, 3);
        var departure = new CoordinatesData(BigDecimal.ONE, BigDecimal.ONE);
        var destination = new CoordinatesData(BigDecimal.TEN, BigDecimal.TEN);
        var currencyCode = Config.getTestCurrencyCode();

        var request = new CalculatePackagesShipmentRequest(
                List.of(cargoPackage),
                currencyCode,
                destination,
                departure
        );
        var mockedUseCase = tariffCalculateUseCases.get(currencyCode);
        var mockedShipment = mock(Shipment.class);

        when(useCaseMapper.requestToTariffCalculatorUseCase(request))
                .thenReturn(mockedUseCase);
        when(shipmentMapper.requestToShipment(request))
                .thenReturn(mockedShipment);
        when(tariffCalculateService.calculateShipmentPriceByUseCase(mockedShipment, mockedUseCase))
                .thenReturn(mock(PackagesShipmentCalculation.class));
        when(shipmentMapper.calculationToResponse(any(PackagesShipmentCalculation.class)))
                .thenReturn(mock(CalculatePackagesShipmentResponse.class));

        ResponseEntity<CalculatePackagesShipmentResponse> response =
                restTemplate.postForEntity(baseCalculateApi, request, CalculatePackagesShipmentResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Список упаковок == null -> Ответ 400")
    void whenEmptyListPackages_thenReturn400() {
        CoordinatesData from = new CoordinatesData(BigDecimal.ONE, BigDecimal.ONE);
        CoordinatesData to = new CoordinatesData(BigDecimal.TEN, BigDecimal.TEN);
        var request = new CalculatePackagesShipmentRequest(
                null, "RUB", to, from
        );

        ResponseEntity<String> response = restTemplate.postForEntity(baseCalculateApi, request, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
