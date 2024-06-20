package org.example.eksamenbackend.result;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ResultDTO {

    private Long id;
    private String resultType;
    private LocalDate date;
    private String resultValue;
}
