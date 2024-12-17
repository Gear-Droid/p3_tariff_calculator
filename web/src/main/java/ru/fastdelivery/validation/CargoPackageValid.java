package ru.fastdelivery.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = CargoPackageValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CargoPackageValid {

    String message() default "Все поля упаковки должны быть указаны"
            + ", вес упаковки и ее габариты должны быть больше 0!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
