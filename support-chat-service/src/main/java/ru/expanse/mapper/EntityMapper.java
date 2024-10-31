package ru.expanse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.expanse.model.Model;
import ru.expanse.schema.EntityRecord;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EntityMapper {
    Model toModel(EntityRecord record);
}
