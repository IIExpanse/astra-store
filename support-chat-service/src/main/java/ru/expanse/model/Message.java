package ru.expanse.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "message", schema = "chat")
public class Message {
    @Id
    @GeneratedValue(generator = "message_id_seq_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "message_id_seq_generator", sequenceName = "chat.message_id_seq",
            schema = "chat", allocationSize = 1)
    private Long id;
    private String text;
    private OffsetDateTime timestamp;
    @ManyToOne
    @JoinColumn(name = "replied_to", referencedColumnName = "id")
    private Message repliedTo;
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;
    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private Chat chat;
    @OneToMany(mappedBy = "repliedTo")
    private List<Message> repliedBy;
}
