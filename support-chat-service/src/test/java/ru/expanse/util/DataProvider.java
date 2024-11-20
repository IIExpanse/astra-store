package ru.expanse.util;

import ru.expanse.model.Chat;
import ru.expanse.model.Message;
import ru.expanse.model.User;
import ru.expanse.schema.MessageRecord;
import ru.expanse.schema.SaveMessageRequest;

import java.time.OffsetDateTime;

public class DataProvider {
    public static Message getDefaultMessage(User user, Chat chat) {
        Message message = new Message();
        message.setText("Some chat message");
        message.setTimestamp(OffsetDateTime.now());
        message.setAuthor(user);
        message.setChat(chat);
        return message;
    }

    public static User getDefaultUser() {
        User user = new User();
        user.setEmail("some-mail@mail.com");
        return user;
    }

    public static Chat getDefaultChat() {
        Chat chat = new Chat();
        chat.setName("chat_name");
        return chat;
    }

    public static SaveMessageRequest getDefaultSaveMessageRequest() {
        return new SaveMessageRequest(
                "abc",
                OffsetDateTime.now(),
                1L,
                1L,
                1L
        );
    }

    public static MessageRecord getDefaultMessageRecord() {
        return new MessageRecord(
                1L,
                "abc",
                OffsetDateTime.now(),
                null,
                2L
        );
    }
}
