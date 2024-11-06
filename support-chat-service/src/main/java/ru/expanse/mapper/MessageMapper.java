package ru.expanse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.expanse.model.Message;
import ru.expanse.model.User;
import ru.expanse.schema.MessageRecord;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = BaseTypesMapper.class)
public interface MessageMapper {
    Message toModel(MessageRecord record, User author);

    @Mapping(target = "authorId", source = "message.author.id")
    MessageRecord toRecord(Message message);
}
