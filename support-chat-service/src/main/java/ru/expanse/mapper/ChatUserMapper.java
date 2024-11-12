package ru.expanse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.expanse.model.ChatUser;
import ru.expanse.schema.UpdateChatUserRequest;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChatUserMapper {
    @Mapping(target = "chatUserId", ignore = true)
    ChatUser updateModel(UpdateChatUserRequest request, @MappingTarget ChatUser chatUser);
}
