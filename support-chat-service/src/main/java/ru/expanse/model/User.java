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
@Table(name = "users", schema = "users")
public class User {
    @Id
    @GeneratedValue(generator = "users_id_seq_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "users_id_seq_generator", sequenceName = "users_id_seq_generator",
            schema = "users", allocationSize = 1)
    private Long id;
    private String email;
}
