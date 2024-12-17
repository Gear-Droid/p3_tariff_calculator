package ru.fastdelivery.domain.common.dimensions;

import ru.fastdelivery.domain.common.length.Length;
import ru.fastdelivery.domain.common.volume.Volume;

/**
 * Габариты
 *
 * @param length Длина
 * @param width Ширина
 * @param height Высота
 */
public record OuterDimensions(
        Length length,
        Length width,
        Length height
) {

    public static final Integer MAX_LENGTH_DIMENSION = 1500;  // в мм
    public static final Integer NORMALIZATION_VALUE = 50;

    public OuterDimensions {
        var maxLength = Length.fromMillimeter(MAX_LENGTH_DIMENSION);

        if (length == null || width == null || height == null) {
            throw new IllegalArgumentException("Length, width or height cannot be null!");
        }

        if (length.isLongerThan(maxLength)
                || width.isLongerThan(maxLength)
                || height.isLongerThan(maxLength)) {
            throw new IllegalArgumentException(
                    "Length, width or height is longer than maxLength %s!".formatted(maxLength)
            );
        }
    }

    public Volume calculateLengthNormalizedVolume() {
        return Volume.calculate(
                length.normalizedTo(NORMALIZATION_VALUE),
                width.normalizedTo(NORMALIZATION_VALUE),
                height.normalizedTo(NORMALIZATION_VALUE)
        );
    }

}
