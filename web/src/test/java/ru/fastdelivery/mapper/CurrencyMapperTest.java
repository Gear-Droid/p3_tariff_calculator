package ru.fastdelivery.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.fastdelivery.AbstractSpringTest;
import ru.fastdelivery.config.Config;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.presentation.api.request.CalculatePackagesShipmentRequest;
import ru.fastdelivery.presentation.api.request.CargoPackage;
import ru.fastdelivery.presentation.api.request.CoordinatesData;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class CurrencyMapperTest extends AbstractSpringTest {

    @Autowired
    private CurrencyMapper currencyMapper;
    @Autowired
    private CurrencyFactory currencyFactory;

    @Test
    @DisplayName("Маппинг CalculatePackagesShipmentRequest -> Currency")
    public void whenRequest_thenCurrency() {
        var request = new CalculatePackagesShipmentRequest(
                List.of(
                        new CargoPackage(new BigInteger("1"), 1, 1, 1)
                ),
                Config.getTestCurrencyCode(),
                new CoordinatesData(BigDecimal.ONE, BigDecimal.ONE),
                new CoordinatesData(BigDecimal.ONE, BigDecimal.ONE)
        );
        var expected = Config.getTestCurrency();

        assertThat(currencyMapper.requestToCurrency(request))
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Недопустимый код валюты в CalculatePackagesShipmentRequest -> исключение")
    public void whenUnavailableCurrencyCode_thenException() {
        String unavailableCode = "xxx";
        var request = new CalculatePackagesShipmentRequest(
                List.of(
                        new CargoPackage(new BigInteger("1"), 1, 1, 1)
                ),
                unavailableCode,
                new CoordinatesData(BigDecimal.ONE, BigDecimal.ONE),
                new CoordinatesData(BigDecimal.ONE, BigDecimal.ONE)
        );

        when(currencyFactory.create(unavailableCode))
                .thenThrow(IllegalArgumentException.class);

        assertThatThrownBy(() -> currencyMapper.requestToCurrency(request))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
