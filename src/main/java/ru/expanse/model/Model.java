package ru.expanse.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Model {
    @Id
    @GeneratedValue(generator = "model_id_seq_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "model_id_seq_generator", sequenceName = "model_id_seq_generator", allocationSize = 1)
    private Long id;
}
