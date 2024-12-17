package ru.fastdelivery.properties_provider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.properties.provider.CurrencyProperties;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyPropertiesTest {

    private CurrencyProperties properties;

    @BeforeEach
    void init() {
        properties = new CurrencyProperties();
        properties.setAvailable(List.of("USD", "EUR"));
    }

    @Test
    @DisplayName("Если код валюты в списке -> true")
    void whenCodeInList_thanIsAvailableCurrencyCodeReturnTrue() {
        assertTrue(properties.isAvailable("USD"));
        assertTrue(properties.isAvailable("EUR"));
        assertFalse(properties.isAvailable("RUB"));
    }

    @Test
    @DisplayName("Если код валюты НЕ в списке -> false")
    void whenCodeIsNotInList_thanIsAvailableCurrencyCodeReturnFalse() {
        assertFalse(properties.isAvailable("RUB"));
        assertFalse(properties.isAvailable("BYN"));
    }

    @Test
    @DisplayName("Получение списка доступной валюты -> список доступной валюты")
    void whenGetAvailable_thanAvailableList() {
        assertEquals(
                properties.getAvailable(),
                List.of("USD", "EUR")
        );
    }
}