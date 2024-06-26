package org.example.eksamenbackend.result;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.eksamenbackend.discipline.Discipline;
import org.example.eksamenbackend.participant.Participant;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Result")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String resultType;
    private LocalDate date;
    private String resultValue;

    @ManyToOne
    private Participant participant;

    @ManyToOne
    private Discipline discipline;

}
