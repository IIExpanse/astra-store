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
@Table(name = "user", schema = "users")
public class User {
    @Id
    @GeneratedValue(generator = "user_id_seq_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_id_seq_generator", sequenceName = "users.user_id_seq",
            schema = "users", allocationSize = 1)
    private Long id;
    private String email;
}
