package ru.fastdelivery.domain.common.coordinates;

import java.math.BigDecimal;

/**
 * Географическая широта
 */
public class Longitude extends Coordinate<Longitude> {

    public static final BigDecimal MAX_VALUE = BigDecimal.valueOf(180);
    public static final BigDecimal MIN_VALUE = BigDecimal.valueOf(-180);

    public Longitude(BigDecimal value) {
        super(value);

        if (value.compareTo(MIN_VALUE) < 0 || value.compareTo(MAX_VALUE) > 0) {
            throw new IllegalArgumentException("Longitude can't be Lower %s or Higher %s!"
                    .formatted(MIN_VALUE, MAX_VALUE)
            );
        }
    }

    public Longitude(Double value) {
        this(BigDecimal.valueOf(value));
    }

    public Longitude(Integer value) {
        this(BigDecimal.valueOf(value));
    }

    public Longitude(String number) {
        this(number == null ? null : new BigDecimal(number));
    }
}
