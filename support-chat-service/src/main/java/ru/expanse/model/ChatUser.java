package ru.expanse.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "chat_user", schema = "chat")
public class ChatUser {
    @EmbeddedId
    private ChatUserId chatUserId;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
