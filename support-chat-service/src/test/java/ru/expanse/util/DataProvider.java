package ru.expanse.util;

import ru.expanse.model.Chat;
import ru.expanse.model.Message;
import ru.expanse.model.User;

import java.time.OffsetDateTime;

public class DataProvider {
    public static Message getMessage(User user, Chat chat) {
        Message message = new Message();
        message.setText("Some chat message");
        message.setTimestamp(OffsetDateTime.now());
        message.setAuthor(user);
        message.setChat(chat);
        return message;
    }

    public static User getUser() {
        User user = new User();
        user.setEmail("some-mail@mail.com");
        return user;
    }

    public static Chat getChat() {
        Chat chat = new Chat();
        chat.setName("chat_name");
        return chat;
    }
}
