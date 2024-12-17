package ru.fastdelivery.domain.common.coordinates;

import java.math.BigDecimal;

/**
 * Географическая долгота
 */
public class Latitude extends Coordinate<Latitude> {

    public static final BigDecimal MAX_VALUE = BigDecimal.valueOf(90);
    public static final BigDecimal MIN_VALUE = BigDecimal.valueOf(-90);

    public Latitude(BigDecimal value) {
        super(value);

        if (value.compareTo(MIN_VALUE) < 0 || value.compareTo(MAX_VALUE) > 0) {
            throw new IllegalArgumentException("Latitude can't be Lower %s or Higher %s!"
                    .formatted(MIN_VALUE, MAX_VALUE)
            );
        }
    }

    public Latitude(Double value) {
        this(BigDecimal.valueOf(value));
    }

    public Latitude(Integer value) {
        this(BigDecimal.valueOf(value));
    }

    public Latitude(String number) {
        this(number == null ? null : new BigDecimal(number));
    }
}
