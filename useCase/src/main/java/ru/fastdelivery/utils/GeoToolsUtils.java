package ru.fastdelivery.utils;

import lombok.experimental.UtilityClass;
import org.geotools.referencing.GeodeticCalculator;
import ru.fastdelivery.domain.common.coordinates.Coordinates;
import ru.fastdelivery.domain.common.distance.Distance;

@UtilityClass
public class GeoToolsUtils {

    public Distance calculateDistance(Coordinates startCoordinates, Coordinates endCoordinates) {
        GeodeticCalculator gc = new GeodeticCalculator();

        gc.setStartingGeographicPoint(
                startCoordinates.longitude().toDouble(),
                startCoordinates.latitude().toDouble()
        );
        gc.setDestinationGeographicPoint(
                endCoordinates.longitude().toDouble(),
                endCoordinates.latitude().toDouble()
        );

        double distanceMeters = gc.getOrthodromicDistance();

        return new Distance((int) distanceMeters);
    }
}
