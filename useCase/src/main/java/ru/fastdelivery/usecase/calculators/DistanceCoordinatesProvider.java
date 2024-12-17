package ru.fastdelivery.usecase.calculators;

import ru.fastdelivery.domain.common.coordinates.Latitude;
import ru.fastdelivery.domain.common.coordinates.Longitude;

/**
 * Получение параметров расчета цены по дистанции
 */
public interface DistanceCoordinatesProvider {
    boolean isAvailableLatitude(Latitude latitude);

    boolean isAvailableLongitude(Longitude longitude);

    Double getMinDistanceKm();

    String getLatitudeProperties();

    String getLongitudeProperties();
}
