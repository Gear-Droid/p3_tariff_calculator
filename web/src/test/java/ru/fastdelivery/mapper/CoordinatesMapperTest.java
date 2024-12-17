package ru.fastdelivery.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.fastdelivery.AbstractSpringTest;
import ru.fastdelivery.domain.common.coordinates.Coordinates;
import ru.fastdelivery.domain.common.coordinates.Latitude;
import ru.fastdelivery.domain.common.coordinates.Longitude;
import ru.fastdelivery.presentation.api.request.CoordinatesData;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class CoordinatesMapperTest extends AbstractSpringTest {

    @Autowired
    private CoordinatesMapper coordinatesMapper;

    @Test
    @DisplayName("Маппинг CoordinatesData -> Coordinates")
    public void whenCargoPackage_thenOuterDimensions() {
        var given = new CoordinatesData(
                new BigDecimal("1"),
                new BigDecimal("1")
        );
        var expected = new Coordinates(
                new Latitude(BigDecimal.ONE),
                new Longitude(BigDecimal.ONE)
        );

        assertThat(coordinatesMapper.coordinatesDataToCoordinates(given))
                .isEqualTo(expected);
    }
}
