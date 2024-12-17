package ru.fastdelivery.properties.provider;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import ru.fastdelivery.domain.common.coordinates.Coordinate;
import ru.fastdelivery.domain.common.coordinates.Latitude;
import ru.fastdelivery.domain.common.coordinates.Longitude;
import ru.fastdelivery.usecase.calculators.DistanceCoordinatesProvider;

import java.math.BigDecimal;

/**
 * Настройки базовых цен стоимости перевозки из конфига
 */
@Validated
@ConfigurationProperties("coordinates")
@Setter
public class CoordinatesProperties implements DistanceCoordinatesProvider {

    @NotNull
    @Min(1)
    private BigDecimal minKm;
    @NotNull
    private CoordinatesProperties.CoordinateProperty<Latitude> latitude;
    @NotNull
    private CoordinatesProperties.CoordinateProperty<Longitude> longitude;

    @Getter
    @Setter
    public static class CoordinateProperty<T extends Coordinate<T>>  {
        @NotNull
        private T min;
        @NotNull
        private T max;

        @Override
        public String toString() {
            return "[ %s ; %s ]".formatted(min, max);
        }
    }

    @Override
    public Double getMinDistanceKm() {
        return minKm.doubleValue();
    }

    @Override
    public String getLatitudeProperties() {
        return latitude.toString();
    }

    @Override
    public String getLongitudeProperties() {
        return longitude.toString();
    }

    @Override
    public boolean isAvailableLatitude(Latitude lat) {
        return latitude.min.compareTo(lat) < 1
                && latitude.max.compareTo(lat) > -1;
    }

    @Override
    public boolean isAvailableLongitude(Longitude lon) {
        return longitude.min.compareTo(lon) < 1
                && longitude.max.compareTo(lon) > -1;
    }
}
