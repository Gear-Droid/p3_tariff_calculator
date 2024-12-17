package ru.fastdelivery.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.fastdelivery.AbstractSpringTest;
import ru.fastdelivery.domain.common.coordinates.Latitude;
import ru.fastdelivery.domain.common.coordinates.Longitude;
import ru.fastdelivery.presentation.api.request.CoordinatesData;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class CoordinatesDataValidatorTest extends AbstractSpringTest {

    @Mock
    private ConstraintValidatorContext context;
    private final CoordinatesDataValidator validator = new CoordinatesDataValidator();

    @Test
    @DisplayName("Некорректная широта -> isValid = false")
    public void whenInCorrectLongitude_thenIsValidFalse() {
        var testCoordinatesData = new CoordinatesData(
                BigDecimal.ONE,
                Longitude.MAX_VALUE.add(BigDecimal.ONE)
        );

        assertThat(validator.isValid(testCoordinatesData, context))
                .isEqualTo(false);
    }

    @Test
    @DisplayName("Некорректная долгота -> isValid = false")
    public void whenInCorrectLatitude_thenIsValidFalse() {
        var testCoordinatesData = new CoordinatesData(
                Latitude.MIN_VALUE.subtract(BigDecimal.ONE),
                BigDecimal.ONE
        );

        assertThat(validator.isValid(testCoordinatesData, context))
                .isEqualTo(false);
    }

    @Test
    @DisplayName("Корректные данные -> isValid = true")
    public void whenCorrectData_thenIsValidTrue() {
        var testCoordinatesData = new CoordinatesData(BigDecimal.ONE, BigDecimal.ONE);

        assertThat(validator.isValid(testCoordinatesData, context))
                .isEqualTo(true);
    }
}
