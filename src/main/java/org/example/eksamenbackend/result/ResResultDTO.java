package org.example.eksamenbackend.result;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.eksamenbackend.discipline.ResultsType;
import org.example.eksamenbackend.participant.AgeGroup;
import org.example.eksamenbackend.participant.Gender;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ResResultDTO {
    private long id;
    private String disciplineName;
    private ResultsType resultsType;
    private String resultValue;
    private LocalDate resultDate;
    private String participantName;
    private AgeGroup ageGroup;
    private Gender gender;
    private String adjacentClub;
}
