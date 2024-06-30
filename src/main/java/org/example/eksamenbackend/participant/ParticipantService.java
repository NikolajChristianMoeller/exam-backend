package org.example.eksamenbackend.participant;

import org.example.eksamenbackend.discipline.Discipline;
import org.example.eksamenbackend.discipline.DisciplineDTO;
import org.example.eksamenbackend.discipline.DisciplineRepository;
import org.example.eksamenbackend.errorhandling.exception.NotFoundException;
import org.example.eksamenbackend.errorhandling.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParticipantService {
    ParticipantRepository participantRepository;
    DisciplineRepository disciplineRepository;

    public ParticipantService(ParticipantRepository participantRepository, DisciplineRepository disciplineRepository) {
        this.participantRepository = participantRepository;
        this.disciplineRepository = disciplineRepository;
    }

    public void validateParticipantDTO(ParticipantDTO participantDTO) {
        if (
                participantDTO.getFullName() == null ||
                        participantDTO.getFullName().isEmpty() ||
                        participantDTO.getAge() < 0 ||
                        participantDTO.getGender() == null ||
                        participantDTO.getAdjacentClub() == null ||
                        participantDTO.getCountry() == null
        ) {
            throw new ValidationException("fullName, age, gender, adjacentClub and country must be provided");
        }
    }

    public List<ParticipantDTO> findAll() {
        return participantRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<ParticipantDTO> findById(Long id) {
        if (id == null || id < 0) {
            throw new ValidationException("Id must be provided");
        }

        Optional<Participant> participantOptional = participantRepository.findById(id);

        if (participantOptional.isEmpty()) {
            throw new NotFoundException("Participant not found, provided id: " + id);
        }

        return participantOptional.map(this::toDTO);
    }

    public ParticipantDTO createParticipant(ParticipantDTO participantDTO) {

        validateParticipantDTO(participantDTO);

        List<Discipline> disciplines = participantDTO.getDisciplines().stream()
                .map(disciplineDTO -> disciplineRepository.findById(disciplineDTO.getId())
                        .orElseThrow(() -> new NotFoundException("Discipline not found, provided id: " + disciplineDTO.getId())))
                .toList();

        Participant participant = toEntity(participantDTO);
        participant.calculateAndSetAgeGroup();
        participant.setDisciplines(disciplines);
        participantRepository.save(participant);

        return toDTO(participant);
    }

    public ParticipantDTO deleteParticipant(Long id) {
        Participant participant = participantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Participant not found, provided id: " + id));
        ParticipantDTO participantDTO = toDTO(participant);
        participantRepository.deleteById(id);
        return participantDTO;
    }

    public ParticipantDTO updateParticipant(Long id, ParticipantDTO participantDTO) {
        Participant existingParticipant = participantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Participant not found, provided id: " + id));

        validateParticipantDTO(participantDTO);

        List<Discipline> disciplines = participantDTO.getDisciplines().stream()
                .map(disciplineDTO -> disciplineRepository.findById(disciplineDTO.getId())
                        .orElseThrow(() -> new NotFoundException("Discipline not found, provided id: " + disciplineDTO.getId())))
                .toList();

        Participant participantToUpdate = toEntity(participantDTO);
        participantToUpdate.setId(existingParticipant.getId());
        participantToUpdate.calculateAndSetAgeGroup();
        participantToUpdate.setDisciplines(disciplines);
        participantRepository.save(participantToUpdate);

        return toDTO(participantToUpdate);
    }

    public ParticipantDTO toDTO(Participant participant) {
        ParticipantDTO participantDTO = new ParticipantDTO();
        participantDTO.setId(participant.getId());
        participantDTO.setFullName(participant.getFullName());
        participantDTO.setAge(participant.getAge());
        participantDTO.setGender(participant.getGender());
        participantDTO.setAdjacentClub(participant.getAdjacentClub());
        participantDTO.setAgeGroup(participant.getAgeGroup());
        participantDTO.setCountry(participant.getCountry());


        participantDTO.setDisciplines(participant.getDisciplines().stream().map(discipline -> {
            DisciplineDTO disciplineDTO = new DisciplineDTO();
            disciplineDTO.setId(discipline.getId());
            disciplineDTO.setName(discipline.getName());
            disciplineDTO.setDescription(discipline.getDescription());
            disciplineDTO.setResultsType(discipline.getResultsType());
            return disciplineDTO;
        }).collect(Collectors.toList()));

        return participantDTO;
    }

    public Participant toEntity(ParticipantDTO participantDTO) {
        Participant participant = new Participant();
        participant.setId(participantDTO.getId());
        participant.setFullName(participantDTO.getFullName());
        participant.setAge(participantDTO.getAge());
        participant.setGender(participantDTO.getGender());
        participant.setAdjacentClub(participantDTO.getAdjacentClub());
        participant.setCountry(participantDTO.getCountry());
        return participant;
    }
}
