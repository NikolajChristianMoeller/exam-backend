package org.example.eksamenbackend.participant;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.eksamenbackend.discipline.Discipline;
import org.example.eksamenbackend.result.Result;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String gender;
    private int age;
    private String club;

    @ManyToMany
    private List<Discipline> disciplines;

    @OneToMany
    private List<Result> results;
}
