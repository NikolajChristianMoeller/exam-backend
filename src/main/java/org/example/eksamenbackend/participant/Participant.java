package org.example.eksamenbackend.participant;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.eksamenbackend.discipline.Discipline;
import org.example.eksamenbackend.result.Result;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int age;
    private String adjacentClub;
    @Enumerated(EnumType.STRING)
    private AgeGroup ageGroup;
    @Enumerated(EnumType.STRING)
    private Country country;
    @ManyToMany
    private List<Discipline> disciplines = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER)
    private List<Result> results;

    public void calculateAndSetAgeGroup() {
        if (age >= 6 && age <= 9) {
            this.ageGroup = AgeGroup.KIDS;
        } else if (age >= 10 && age <= 13) {
            this.ageGroup = AgeGroup.YOUTH;
        } else if (age >= 14 && age <= 22) {
            this.ageGroup = AgeGroup.JUNIOR;
        } else if (age >= 23 && age <= 40) {
            this.ageGroup = AgeGroup.ADULT;
        } else {
            this.ageGroup = AgeGroup.SENIOR;
        }
    }
}
