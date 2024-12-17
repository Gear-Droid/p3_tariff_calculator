package ru.fastdelivery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.fastdelivery.domain.common.coordinates.Coordinates;
import ru.fastdelivery.domain.common.coordinates.Latitude;
import ru.fastdelivery.domain.common.coordinates.Longitude;
import ru.fastdelivery.presentation.api.request.CoordinatesData;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CoordinatesMapper {

    default Coordinates coordinatesDataToCoordinates(CoordinatesData coordinatesData) {
        return new Coordinates(
                new Latitude(coordinatesData.latitude()),
                new Longitude(coordinatesData.longitude())
        );
    }
}
