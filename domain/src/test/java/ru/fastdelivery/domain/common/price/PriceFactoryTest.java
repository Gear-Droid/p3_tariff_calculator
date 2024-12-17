package ru.fastdelivery.domain.common.price;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PriceFactoryTest {

    PriceAbstractFactory notNullFactory = mock(PriceAbstractFactory.class);
    Price mockPrice = mock(Price.class);

    @Test
    @DisplayName("Фабрика NULL -> исключение")
    void whenFactoryIsNull_thenThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> PriceFactory.calculatePrice(null));
    }

    @Test
    @DisplayName("Фабрика не NULL -> создается")
    void whenCodeFactoryIsNotNull_thenCreated() {
        when(notNullFactory.calculatePrice())
                .thenReturn(mockPrice);

        assertEquals(mockPrice, PriceFactory.calculatePrice(notNullFactory));
    }

}
