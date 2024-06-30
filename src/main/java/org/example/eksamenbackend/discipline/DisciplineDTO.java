package org.example.eksamenbackend.discipline;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.eksamenbackend.participant.ParticipantDTO;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DisciplineDTO {

    private Long id;
    private String name;
    private String description;
    private ResultsType resultsType;
    private List<ParticipantDTO> participants = new ArrayList<>();
}
