package ru.fastdelivery.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;
import ru.fastdelivery.presentation.api.request.CargoPackage;

import java.math.BigInteger;

public class CargoPackageValidator
        implements ConstraintValidator<CargoPackageValid, CargoPackage> {

    @Override
    public boolean isValid(CargoPackage value, ConstraintValidatorContext context) {
        if (ObjectUtils.anyNull(value.weight(), value.length(), value.width(), value.height())) {
            return false;
        }

        var minimal = BigInteger.valueOf(
                ObjectUtils.min(value.length(), value.width(), value.height()))
                .min(value.weight());

        return minimal.compareTo(BigInteger.ZERO) > 0;
    }
}
