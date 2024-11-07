package ru.expanse.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.expanse.model.Message;
import ru.expanse.model.User;
import ru.expanse.schema.MessageRecord;
import ru.expanse.schema.UpdateMessageRequest;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = BaseTypesMapper.class)
public interface MessageMapper {
    Message toModel(MessageRecord record, User author);

    @Mapping(target = "authorId", source = "message.author.id")
    MessageRecord toRecord(Message message);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Message updateModel(UpdateMessageRequest request, @MappingTarget Message message);
}
