package ru.fastdelivery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.fastdelivery.domain.common.dimensions.OuterDimensions;
import ru.fastdelivery.domain.common.length.Length;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.presentation.api.request.CargoPackage;

import java.math.BigInteger;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PackMapper {

    @Mapping(source = "cargoPackage", target = "dimensions")
    @Mapping(source = "cargoPackage.weight", target = "weight")
    Pack cargoPackageToPack(CargoPackage cargoPackage);

    default Weight bigIntegerToWeight(BigInteger value) {
        return new Weight(value);
    }

    default OuterDimensions cargoPackageToOuterDimensions(CargoPackage cargoPackage) {
        return new OuterDimensions(
                Length.fromMillimeter(cargoPackage.length()),
                Length.fromMillimeter(cargoPackage.width()),
                Length.fromMillimeter(cargoPackage.height())
        );
    }
}
