package org.example.eksamenbackend.participant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.eksamenbackend.discipline.DisciplineDTO;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ParticipantDTO {
    private Long id;
    private String fullName;
    private int age;
    private Gender gender;
    private String adjacentClub;
    private AgeGroup ageGroup;
    private Country country;
    private List<DisciplineDTO> disciplines = new ArrayList<>();
}
