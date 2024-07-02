package org.example.eksamenbackend.participant;

import org.example.eksamenbackend.discipline.DisciplineRepository;
import org.example.eksamenbackend.errorhandling.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ValidateParticipantDTOTest {

    ParticipantService participantService;

    @BeforeEach
    void setUp() {
        ParticipantRepository participantRepository = Mockito.mock(ParticipantRepository.class);
        DisciplineRepository disciplineRepository = Mockito.mock(DisciplineRepository.class);
        participantService = new ParticipantService(participantRepository, disciplineRepository);
    }

    @Test
    void validateParticipantDTO_ValidInput_NoExceptionThrown() {
        ParticipantDTO participantDTO = new ParticipantDTO();
        participantDTO.setFullName("Nikolaj Christian Møller");
        participantDTO.setAge(23);
        participantDTO.setGender(Gender.MALE);
        participantDTO.setAdjacentClub("Club Name");
        participantDTO.setCountry(Country.DENMARK);

        assertDoesNotThrow(() -> participantService.validateParticipantDTO(participantDTO));
    }

    @Test
    void validateParticipantDTO_InvalidInput_ExceptionThrown() {
        ParticipantDTO participantDTO = new ParticipantDTO();
        participantDTO.setFullName(null);
        participantDTO.setAge(23);
        participantDTO.setGender(Gender.MALE);
        participantDTO.setAdjacentClub(null);
        participantDTO.setCountry(Country.DENMARK);

        assertThrows(ValidationException.class, () -> participantService.validateParticipantDTO(participantDTO));
    }

}
