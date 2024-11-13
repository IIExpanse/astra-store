package ru.expanse.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.expanse.model.ChatUser;
import ru.expanse.schema.UpdateChatUserRequest;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChatUserMapper {
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    ChatUser toModel(UpdateChatUserRequest updateChatUserRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ChatUser updateModel(ChatUser newChatUser, @MappingTarget ChatUser oldChatUser);
}
