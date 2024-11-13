package ru.expanse.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.expanse.model.Message;
import ru.expanse.model.User;
import ru.expanse.schema.MessageRecord;
import ru.expanse.schema.UpdateMessageRequest;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = BaseTypesMapper.class)
public interface MessageMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "repliedTo", source = "repliedTo")
    @Mapping(target = "text", source = "record.text")
    @Mapping(target = "timestamp", source = "record.timestamp")
    Message toModel(MessageRecord record, User author, Message repliedTo);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    Message toModel(UpdateMessageRequest request);

    @Mapping(target = "authorId", source = "message.author.id")
    @Mapping(target = "repliedTo", expression = "java(message.getRepliedTo() == null ? null : message.getRepliedTo().getId())")
    MessageRecord toRecord(Message message);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Message updateModel(Message newMessage, @MappingTarget Message oldMessage);
}
