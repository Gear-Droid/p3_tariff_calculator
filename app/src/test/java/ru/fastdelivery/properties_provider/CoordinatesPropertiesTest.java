package ru.fastdelivery.properties_provider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.fastdelivery.domain.common.coordinates.Latitude;
import ru.fastdelivery.domain.common.coordinates.Longitude;
import ru.fastdelivery.properties.provider.CoordinatesProperties;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoordinatesPropertiesTest {

    private CoordinatesProperties properties;

    @BeforeEach
    void init() {
        properties = new CoordinatesProperties();

        properties.setMinKm(BigDecimal.valueOf(100));

        var latitudeProperties = new CoordinatesProperties.CoordinateProperty<Latitude>();
        latitudeProperties.setMin(new Latitude(35));
        latitudeProperties.setMax(new Latitude(55));
        properties.setLatitude(latitudeProperties);

        var longitudeProperties = new CoordinatesProperties.CoordinateProperty<Longitude>();
        longitudeProperties.setMin(new Longitude(60));
        longitudeProperties.setMax(new Longitude(91));
        properties.setLongitude(longitudeProperties);
    }

    @Test
    @DisplayName("Получение мин. дистанции -> корректное значение")
    public void whenGetMinDistanceKm_thenReturnMinKm() {
        assertEquals(
                100.0,
                properties.getMinDistanceKm()
        );
    }

    @Test
    @DisplayName("Получение пар-ров долготы -> корректное значение")
    public void whenGetLatitudeProperties_thenCorrect() {
        assertEquals(
                "[ 35.000000 ; 55.000000 ]",
                properties.getLatitudeProperties()
        );
    }

    @Test
    @DisplayName("Получение пар-ров широты -> корректное значение")
    public void whenGetLongitudeProperties_thenCorrect() {
        assertEquals(
                "[ 60.000000 ; 91.000000 ]",
                properties.getLongitudeProperties()
        );
    }

    @ParameterizedTest
    @CsvSource({"-1, false",
            "34, false",
            "34.9999, false",
            "34.9999999999, true",
            "35, true",
            "55, true",
            "54.9999999, true",
            "55.001, false",
            "55.0000009, false"})
    public void testIsAvailableLatitude(BigDecimal value, Boolean expected) {
        boolean actual = properties
                .isAvailableLatitude(new Latitude(value));

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"-1, false",
            "59.999999, false",
            "60, true",
            "90.99999999999, true",
            "91, true",
            "91.0001, false",
            "91.0000009, false"})
    public void testIsAvailableLongitude(BigDecimal value, Boolean expected) {
        boolean actual = properties
                .isAvailableLongitude(new Longitude(value));

        assertEquals(expected, actual);
    }
}
