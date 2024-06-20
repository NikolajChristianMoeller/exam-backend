package org.example.eksamenbackend.participant;

import org.example.eksamenbackend.errorhandling.exception.NotFoundException;
import org.example.eksamenbackend.errorhandling.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;

    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public List<ParticipantDTO> findAll() {
        return participantRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ParticipantDTO createParticipant(ParticipantDTO participantDTO) {
        if (participantDTO.getName() == null || participantDTO.getName().isEmpty() || participantDTO.getAge() < 0) {
            throw new ValidationException("Name and age must be provided");
        }
        Participant participant = new Participant();
        participant.setName(participantDTO.getName());
        participant.setAge(participantDTO.getAge());
        participantRepository.save(participant);
        return toDTO(participant);
    }

    private ParticipantDTO toDTO(Participant participant) {
        ParticipantDTO participantDTO = new ParticipantDTO();
        participantDTO.setId(participant.getId());
        participantDTO.setName(participant.getName());
        participantDTO.setAge(participant.getAge());
        return participantDTO;
    }

    public ParticipantDTO deleteParticipant(Long id) {
        Participant participant = participantRepository.findById(id).orElseThrow(() -> new NotFoundException("Participant not found"));
        participantRepository.deleteById(id);
        return toDTO(participant);
    }

    public ParticipantDTO updateParticipant(Long id, ParticipantDTO participantDTO) {
        Participant participant = participantRepository.findById(id).orElseThrow(() -> new NotFoundException("Participant not found"));
        if (participantDTO.getName() == null || participantDTO.getName().isEmpty() || participantDTO.getAge() < 0) {
            throw new ValidationException("Name and age must be provided");
        }
        participant.setName(participantDTO.getName());
        participant.setAge(participantDTO.getAge());
        participantRepository.save(participant);
        return toDTO(participant);
    }

    public Optional<ParticipantDTO> findById(Long id) {
        if (id == null || id < 0) {
            throw new ValidationException("Id must be provided");
        }
        return participantRepository.findById(id).map(this::toDTO);
    }
}
