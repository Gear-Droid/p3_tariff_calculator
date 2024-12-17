package ru.fastdelivery.properties_provider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.properties.provider.PriceProperties;
import ru.fastdelivery.properties.provider.PricesProperties;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class PricesPropertiesTest {

    private PricesProperties properties;
    private final PriceProperties mockedPriceProperty = mock(PriceProperties.class);

    @BeforeEach
    void init() {
        properties = new PricesProperties();
        properties.setCosts(Map.of(
                "RUB", mockedPriceProperty,
                "USD", mockedPriceProperty
        ));
    }

    @Test
    @DisplayName("Получение списка доступной валюты -> список доступной валюты")
    void whenGetAvailable_thanAvailableList() {
        assertEquals(
                properties.getCosts(),
                Map.of(
                        "RUB", mockedPriceProperty,
                        "USD", mockedPriceProperty
                )
        );
    }
}
