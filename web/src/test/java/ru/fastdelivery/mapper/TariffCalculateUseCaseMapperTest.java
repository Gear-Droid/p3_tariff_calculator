package ru.fastdelivery.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.fastdelivery.AbstractSpringTest;
import ru.fastdelivery.config.Config;
import ru.fastdelivery.presentation.api.request.CalculatePackagesShipmentRequest;
import ru.fastdelivery.presentation.api.request.CargoPackage;
import ru.fastdelivery.presentation.api.request.CoordinatesData;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

public class TariffCalculateUseCaseMapperTest extends AbstractSpringTest {

    @Autowired
    private Map<String, TariffCalculateUseCase> tariffCalculateUseCases;
    @Autowired
    private TariffCalculateUseCaseMapper tariffCalculateUseCaseMapper;

    @Test
    @DisplayName("Маппинг CalculatePackagesShipmentRequest -> TariffCalculateUseCase")
    public void whenCalculatePackagesShipmentRequest_thenShipment() {
        var request = new CalculatePackagesShipmentRequest(
                List.of(mock(CargoPackage.class)),
                Config.getTestCurrencyCode(),
                mock(CoordinatesData.class),
                mock(CoordinatesData.class)
        );

        var expected = tariffCalculateUseCases.get(Config.getTestCurrencyCode());

        assertThat(tariffCalculateUseCaseMapper.requestToTariffCalculatorUseCase(request))
                .isEqualTo(expected)
                .hasSameHashCodeAs(expected);
    }

    @Test
    @DisplayName("В CalculatePackagesShipmentRequest недопустимый код -> исключение")
    public void whenUnavailableCurrencyCode_thenException() {
        var unavailableCurrencyCode = "xxx";
        var request = new CalculatePackagesShipmentRequest(
                List.of(mock(CargoPackage.class)),
                unavailableCurrencyCode,
                mock(CoordinatesData.class),
                mock(CoordinatesData.class)
        );

        assertThatThrownBy(() -> tariffCalculateUseCaseMapper.requestToTariffCalculatorUseCase(request))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
