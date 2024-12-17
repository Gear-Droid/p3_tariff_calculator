package ru.fastdelivery.domain.common.coordinates;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CoordinatesTest {

    @Test
    @DisplayName("Получена долгота = NULL -> исключение")
    public void whenLatitudeNull_thenException() {
        assertThatThrownBy(() -> new Coordinates(null, new Longitude(BigDecimal.TEN)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Получена широта = NULL -> исключение")
    public void whenLongitudeNull_thenException() {
        assertThatThrownBy(() -> new Coordinates(new Latitude(BigDecimal.TEN), null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Получено правильное значение -> объект создан")
    public void whenCorrectValue_thenCreated() {
        var latitude = new Latitude(BigDecimal.TEN);
        var longitude = new Longitude(BigDecimal.TEN);
        var expected = new Coordinates(latitude, longitude);

        assertThat(new Coordinates(latitude, longitude))
                .hasSameHashCodeAs(expected)
                .isEqualTo(expected);
    }
}
