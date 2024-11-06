package ru.expanse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BaseTypesMapper {
    default OffsetDateTime toOffsetDateTime(ZonedDateTime dateTime) {
        return OffsetDateTime.from(dateTime);
    }

    default ZonedDateTime toZonedDateTime(OffsetDateTime offsetDateTime) {
        return offsetDateTime.toZonedDateTime();
    }
}
