package ru.fastdelivery.domain.common.coordinates;

import java.math.BigDecimal;

/**
 * Географические координаты
 *
 * @param latitude долгота
 * @param longitude широта
 */
public record Coordinates(
        Latitude latitude,
        Longitude longitude
) {
    public Coordinates {
        if (latitude == null || longitude == null) {
            throw new IllegalArgumentException("Latitude or Longitude can't be null!");
        }
    }

    public Coordinates(double lat, double lon) {
        this(
                new Latitude(BigDecimal.valueOf(lat)),
                new Longitude(BigDecimal.valueOf(lon))
        );
    }

    @Override
    public String toString() {
        return "[%s;%s]".formatted(latitude, longitude);
    }
}
