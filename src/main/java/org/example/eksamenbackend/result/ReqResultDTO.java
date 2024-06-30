package org.example.eksamenbackend.result;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ReqResultDTO {
    private Long participantId;
    private Long disciplineId;
    private LocalDate resultDate;
    private Integer hours;
    private Integer minutes;
    private Integer seconds;
    private Integer hundredths;
    private Integer meters;
    private Integer centimeters;
    private Integer points;
}
