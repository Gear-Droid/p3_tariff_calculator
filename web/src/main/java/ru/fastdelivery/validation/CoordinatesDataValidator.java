package ru.fastdelivery.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;
import ru.fastdelivery.domain.common.coordinates.Latitude;
import ru.fastdelivery.domain.common.coordinates.Longitude;
import ru.fastdelivery.presentation.api.request.CoordinatesData;

public class CoordinatesDataValidator
        implements ConstraintValidator<CoordinatesDataValid, CoordinatesData> {

    @Override
    public boolean isValid(CoordinatesData value, ConstraintValidatorContext context) {
        if (ObjectUtils.anyNull(value.latitude(), value.longitude())) {
            return false;
        }

        return Latitude.MIN_VALUE.compareTo(value.latitude()) < 1
                && Latitude.MAX_VALUE.compareTo(value.latitude()) > -1
                && Longitude.MIN_VALUE.compareTo(value.longitude()) < 1
                && Longitude.MAX_VALUE.compareTo(value.longitude()) > -1;
    }
}
