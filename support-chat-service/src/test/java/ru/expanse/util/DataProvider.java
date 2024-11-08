package ru.expanse.util;

import ru.expanse.model.Message;
import ru.expanse.model.User;

import java.time.OffsetDateTime;

public class DataProvider {
    public static Message getMessage(User user) {
        Message message = new Message();
        message.setText("Some chat message");
        message.setAuthor(user);
        message.setTimestamp(OffsetDateTime.now());
        return message;
    }

    public static User getUser() {
        User user = new User();
        user.setEmail("some-mail@mail.com");
        return user;
    }
}
