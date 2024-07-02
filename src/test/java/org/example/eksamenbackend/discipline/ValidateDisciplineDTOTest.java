package org.example.eksamenbackend.discipline;

import org.example.eksamenbackend.errorhandling.exception.ValidationException;
import org.example.eksamenbackend.participant.ParticipantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidateDisciplineDTOTest {

    private DisciplineService disciplineService;

    @BeforeEach
    void setUp() {
        DisciplineRepository disciplineRepository = Mockito.mock(DisciplineRepository.class);
        ParticipantRepository participantRepository = Mockito.mock(ParticipantRepository.class);
        disciplineService = new DisciplineService(disciplineRepository, participantRepository);
    }

    @Test
    void validateParticipantDTO_ValidInput_NoExceptionThrown() {
        DisciplineDTO disciplineDTO = new DisciplineDTO();
        disciplineDTO.setName("Discipline A");
        disciplineDTO.setDescription("Description A");
        disciplineDTO.setResultsType(ResultsType.TIME);
        assertDoesNotThrow(() -> disciplineService.validateDisciplineDTO(disciplineDTO));
    }

    @Test
    void validateParticipantDTO_InvalidInput_ExceptionThrown() {
        DisciplineDTO disciplineDTO = new DisciplineDTO();
        disciplineDTO.setName(null);
        disciplineDTO.setDescription("Description A");
        disciplineDTO.setResultsType(ResultsType.TIME);

        assertThrows(ValidationException.class, () -> disciplineService.validateDisciplineDTO(disciplineDTO));
    }

}
