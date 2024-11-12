package ru.expanse.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "chat", schema = "chat")
public class Chat {
    @Id
    @GeneratedValue(generator = "chat_id_seq_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "chat_id_seq_generator", sequenceName = "chat.chat_id_seq",
            schema = "chat", allocationSize = 1)
    private Long id;
    private String name;
}
