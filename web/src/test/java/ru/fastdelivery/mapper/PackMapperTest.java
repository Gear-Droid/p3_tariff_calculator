package ru.fastdelivery.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.fastdelivery.AbstractSpringTest;
import ru.fastdelivery.domain.common.dimensions.OuterDimensions;
import ru.fastdelivery.domain.common.length.Length;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.presentation.api.request.CargoPackage;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class PackMapperTest extends AbstractSpringTest {

    @Autowired
    private PackMapper packMapper;
    private CargoPackage cargoPackage;

    @BeforeEach
    void init() {
        cargoPackage = new CargoPackage(
                new BigInteger("1"),
                1, 2, 3
        );
    }

    @Test
    @DisplayName("Маппинг BigInteger -> Weight")
    public void whenBigInteger_thenWeight() {
        var given = cargoPackage.weight();
        var expected = new Weight(new BigInteger("1"));

        assertThat(packMapper.bigIntegerToWeight(given))
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Маппинг CargoPackage -> OuterDimensions")
    public void whenCargoPackage_thenOuterDimensions() {
        var given = cargoPackage;
        var expected = new OuterDimensions(
                Length.fromMillimeter(1),
                Length.fromMillimeter(2),
                Length.fromMillimeter(3)
        );

        assertThat(packMapper.cargoPackageToOuterDimensions(given))
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Маппинг CargoPackage -> Pack")
    public void whenCargoPackage_thenPack() {
        var given = cargoPackage;
        var expected = new Pack(
                new Weight(new BigInteger("1")),
                new OuterDimensions(
                        Length.fromMillimeter(1),
                        Length.fromMillimeter(2),
                        Length.fromMillimeter(3)
                )
        );

        assertThat(packMapper.cargoPackageToPack(given))
                .isEqualTo(expected);
    }
}
