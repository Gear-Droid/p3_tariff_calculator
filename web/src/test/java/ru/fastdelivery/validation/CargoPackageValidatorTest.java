package ru.fastdelivery.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.fastdelivery.AbstractSpringTest;
import ru.fastdelivery.presentation.api.request.CargoPackage;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class CargoPackageValidatorTest extends AbstractSpringTest {

    @Mock
    private ConstraintValidatorContext context;
    private final CargoPackageValidator validator = new CargoPackageValidator();

    @Test
    @DisplayName("Вес равен 0 -> isValid = false")
    public void whenInCorrectWeight_thenIsValidFalse() {
        var testCargoPackage = new CargoPackage(
                BigInteger.ZERO,
                1,2,3
        );

        assertThat(validator.isValid(testCargoPackage, context))
                .isEqualTo(false);
    }

    @Test
    @DisplayName("Длина меньше 0 -> isValid = false")
    public void whenInCorrectLength_thenIsValidFalse() {
        var testCargoPackage = new CargoPackage(
                BigInteger.ONE,
                -1,2,3
        );

        assertThat(validator.isValid(testCargoPackage, context))
                .isEqualTo(false);
    }

    @Test
    @DisplayName("Корректные данные -> isValid = true")
    public void whenCorrectData_thenIsValidTrue() {
        var testCargoPackage = new CargoPackage(
                BigInteger.ONE,
                1,2,3
        );

        assertThat(validator.isValid(testCargoPackage, context))
                .isEqualTo(true);
    }
}
