package ru.fastdelivery.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.fastdelivery.TestUtils;
import ru.fastdelivery.domain.common.coordinates.Coordinates;
import ru.fastdelivery.domain.common.coordinates.Latitude;
import ru.fastdelivery.domain.common.coordinates.Longitude;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeoToolsUtilsTest {

    @ParameterizedTest
    @CsvSource({
            "77.1539, -139.398, 77.1539, -139.398, 0",
            "77.1539, -139.398, -77.1804, -139.55, 17166029",
            "77.1539, 120.398, 77.1804, 129.55, 225883",
            "77.1539, -120.398, 77.1804, 129.55, 2332669",
    })
    public void testCalculateDistance(BigDecimal startLatitude,
                                      BigDecimal startLongitude,
                                      BigDecimal endLatitude,
                                      BigDecimal endLongitude,
                                      double expected) {
        var start = TestUtils.createTestCoordinates(startLatitude, startLongitude);
        var end = new Coordinates(new Latitude(endLatitude), new Longitude(endLongitude));

        int distanceMeters = GeoToolsUtils.calculateDistance(start, end).meter();
        double precision = 0.005;
        var measurementError = expected * precision;

        assertTrue(
                Math.abs(distanceMeters - expected) <= measurementError
        );
    }

}
