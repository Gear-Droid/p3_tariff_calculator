package ru.fastdelivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.currency.CurrencyPropertiesProvider;
import ru.fastdelivery.properties.provider.CoordinatesProperties;
import ru.fastdelivery.properties.provider.CurrencyProperties;
import ru.fastdelivery.properties.provider.PricesProperties;
import ru.fastdelivery.properties.provider.PriceProperties;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Определение реализаций бинов для всех модулей приложения
 */
@Configuration
public class Beans {

    @Bean
    public CurrencyFactory currencyFactory(CurrencyPropertiesProvider currencyProperties) {
        return new CurrencyFactory(currencyProperties);
    }

    @Bean
    public Map<String, TariffCalculateUseCase> tariffCalculateUseCases(
            CoordinatesProperties coordinatesProvider,
            CurrencyProperties currencyProperties,
            PricesProperties pricesProperties) {
        return initAvailablePriceProviders(currencyProperties, pricesProperties)
                .entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().toUpperCase(),
                        entry -> new TariffCalculateUseCase(
                                entry.getValue(), entry.getValue(), coordinatesProvider))
                );
    }

    private Map<String, PriceProperties> initAvailablePriceProviders(
            CurrencyProperties currencyProperties,
            PricesProperties pricesProperties) {
        var availablePriceProviders = currencyProperties.getAvailable().stream()
                .map(String::toLowerCase)
                .collect(Collectors.toMap(
                        String::valueOf,
                        pricesProperties.getCosts()::get));

        availablePriceProviders.forEach((currencyCode, provider) -> {
            provider.setCurrencyCode(currencyCode.toUpperCase());
            provider.setCurrencyFactory(currencyFactory(currencyProperties));
        });

        return availablePriceProviders;
    }
}
