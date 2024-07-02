package org.example.eksamenbackend.result;

import org.example.eksamenbackend.discipline.Discipline;
import org.example.eksamenbackend.discipline.DisciplineRepository;
import org.example.eksamenbackend.discipline.ResultsType;
import org.example.eksamenbackend.errorhandling.exception.ValidationException;
import org.example.eksamenbackend.participant.Gender;
import org.example.eksamenbackend.participant.Participant;
import org.example.eksamenbackend.participant.ParticipantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ValidateAndSetParticipantAndDisciplineTest {

    ResultService resultService;

    @BeforeEach
    void setUp() {
        ResultRepository resultRepository = Mockito.mock(ResultRepository.class);
        DisciplineRepository disciplineRepository = Mockito.mock(DisciplineRepository.class);
        ParticipantRepository participantRepository = Mockito.mock(ParticipantRepository.class);
        resultService = new ResultService(resultRepository, disciplineRepository, participantRepository);
    }

    @Test
    void validateAndSetParticipantAndDiscipline_ValidInput_NoExceptionThrown() {

        ReqResultDTO resultDTO = new ReqResultDTO();
        resultDTO.setParticipantId(1L);
        resultDTO.setDisciplineId(1L);
        resultDTO.setResultDate(LocalDate.parse("2023-05-06"));
        resultDTO.setHours(1);
        resultDTO.setMinutes(30);
        resultDTO.setSeconds(15);
        resultDTO.setHundredths(50);
        resultDTO.setMeters(100);
        resultDTO.setCentimeters(50);
        resultDTO.setPoints(10);

        Discipline discipline = new Discipline();
        discipline.setId(1L);
        discipline.setName("Discipline A");
        discipline.setDescription("Description A");
        discipline.setResultsType(ResultsType.TIME);

        Participant participant = new Participant();
        participant.setId(1L);
        participant.setFullName("Nikolaj Christian Møller");
        participant.setAge(23);
        participant.setGender(Gender.MALE);
        participant.setAdjacentClub("Club Name");
        participant.setDisciplines(Collections.singletonList(discipline));

        when(resultService.disciplineRepository.findById(1L)).thenReturn(Optional.of(discipline));
        when(resultService.participantRepository.findById(1L)).thenReturn(Optional.of(participant));

        Result result = new Result();

        assertDoesNotThrow(() -> resultService.validateAndSetParticipantAndDiscipline(resultDTO, result));
    }

    @Test
    void validateAndSetParticipantAndDiscipline_InvalidInput_ExceptionThrown() {

        ReqResultDTO resultDTO = new ReqResultDTO();
        resultDTO.setParticipantId(null);
        resultDTO.setDisciplineId(null);
        resultDTO.setResultDate(LocalDate.parse("2023-05-06"));
        resultDTO.setHours(1);
        resultDTO.setMinutes(30);
        resultDTO.setSeconds(15);
        resultDTO.setHundredths(50);
        resultDTO.setMeters(100);
        resultDTO.setCentimeters(50);
        resultDTO.setPoints(10);

        Result result = new Result();

        assertThrows(ValidationException.class, () -> resultService.validateAndSetParticipantAndDiscipline(resultDTO, result));
    }

    @Test
    void validateAndSetParticipantAndDiscipline_ParticipantIsNotPartOfTheProvidedDiscipline_ExceptionThrown() {
        ReqResultDTO resultDTO = new ReqResultDTO();
        resultDTO.setParticipantId(1L);
        resultDTO.setDisciplineId(1L);
        resultDTO.setResultDate(LocalDate.parse("2023-05-06"));
        resultDTO.setHours(1);
        resultDTO.setMinutes(30);
        resultDTO.setSeconds(15);
        resultDTO.setHundredths(50);
        resultDTO.setMeters(100);
        resultDTO.setCentimeters(50);
        resultDTO.setPoints(10);

        Discipline discipline = new Discipline();
        discipline.setId(1L);
        discipline.setName("Discipline A");
        discipline.setDescription("Description A");
        discipline.setResultsType(ResultsType.TIME);

        Participant participant = new Participant();
        participant.setId(1L);
        participant.setFullName("Nikolaj Christian Møller");
        participant.setAge(33);
        participant.setGender(Gender.MALE);
        participant.setAdjacentClub("Club Name");

        when(resultService.disciplineRepository.findById(1L)).thenReturn(Optional.of(discipline));
        when(resultService.participantRepository.findById(1L)).thenReturn(Optional.of(participant));

        Result result = new Result();

        assertThrows(ValidationException.class, () -> resultService.validateAndSetParticipantAndDiscipline(resultDTO, result));
    }

}
