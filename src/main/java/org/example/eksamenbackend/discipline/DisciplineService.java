package org.example.eksamenbackend.discipline;

import org.example.eksamenbackend.errorhandling.exception.NotFoundException;
import org.example.eksamenbackend.errorhandling.exception.ValidationException;
import org.example.eksamenbackend.participant.Participant;
import org.example.eksamenbackend.participant.ParticipantDTO;
import org.example.eksamenbackend.participant.ParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DisciplineService {
    DisciplineRepository disciplineRepository;
    ParticipantRepository participantRepository;

    public DisciplineService(DisciplineRepository disciplineRepository, ParticipantRepository participantRepository) {
        this.disciplineRepository = disciplineRepository;
        this.participantRepository = participantRepository;
    }

    public void validateDisciplineDTO(DisciplineDTO disciplineDTO) {
        if (
                disciplineDTO.getName() == null ||
                        disciplineDTO.getName().isEmpty() ||
                        disciplineDTO.getDescription() == null ||
                        disciplineDTO.getDescription().isEmpty() ||
                        disciplineDTO.getResultsType() == null
        ) {
            throw new ValidationException("name, description and resultsType must be provided");
        }
    }

    public List<DisciplineDTO> findAll() {
        return disciplineRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<DisciplineDTO> findById(Long id) {
        if (id == null || id < 0) {
            throw new ValidationException("Id must be provided");
        }

        Optional<Discipline> participantOptional = disciplineRepository.findById(id);

        if (participantOptional.isEmpty()) {
            throw new NotFoundException("Discipline not found, provided id: " + id);
        }

        return participantOptional.map(this::toDTO);
    }

    public DisciplineDTO createDiscipline(DisciplineDTO disciplineDTO) {
        validateDisciplineDTO(disciplineDTO);

        List<Participant> participants = disciplineDTO.getParticipants().stream()
                .map(participantDTO -> participantRepository.findById(participantDTO.getId())
                        .orElseThrow(() -> new NotFoundException(" Participants not found, provided id: " + participantDTO.getId())))
                .toList();

        Discipline discipline = toEntity(disciplineDTO);
        discipline.setParticipants(participants);
        participants.forEach(participant -> participant.getDisciplines().add(discipline));
        disciplineRepository.save(discipline);

        return toDTO(discipline);
    }

    public DisciplineDTO updateDiscipline(Long id, DisciplineDTO disciplineDTO) {
        Discipline existingDiscipline = disciplineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Discipline not found, provided id: " + id));

        validateDisciplineDTO(disciplineDTO);

        List<Participant> participants = disciplineDTO.getParticipants().stream()
                .map(participantDTO -> participantRepository.findById(participantDTO.getId())
                        .orElseThrow(() -> new NotFoundException("Participants not found, provided id: " + participantDTO.getId())))
                .toList();

        Discipline disciplineToUpdate = toEntity(disciplineDTO);
        disciplineToUpdate.setId(existingDiscipline.getId());
        disciplineToUpdate.setParticipants(participants);
        participants.forEach(participant -> participant.getDisciplines().add(disciplineToUpdate));
        Discipline updatedDiscipline = disciplineRepository.save(disciplineToUpdate);

        return toDTO(updatedDiscipline);
    }

    private DisciplineDTO toDTO(Discipline discipline) {
        DisciplineDTO disciplineDTO = new DisciplineDTO();
        disciplineDTO.setId(discipline.getId());
        disciplineDTO.setName(discipline.getName());
        disciplineDTO.setDescription(discipline.getDescription());
        disciplineDTO.setResultsType(discipline.getResultsType());

        List<ParticipantDTO> participants = participantRepository.findByDisciplinesId(discipline.getId()).stream()
                .map(participant -> {
                    ParticipantDTO participantDTO = new ParticipantDTO();
                    participantDTO.setId(participant.getId());
                    participantDTO.setFullName(participant.getFullName());
                    participantDTO.setAge(participant.getAge());
                    participantDTO.setGender(participant.getGender());
                    participantDTO.setAdjacentClub(participant.getAdjacentClub());
                    participantDTO.setAgeGroup(participant.getAgeGroup());
                    participantDTO.setCountry(participant.getCountry());

                    return participantDTO;
                }).collect(Collectors.toList());

        disciplineDTO.setParticipants(participants);

        return disciplineDTO;
    }

    public Discipline toEntity(DisciplineDTO disciplineDTO) {
        Discipline discipline = new Discipline();
        discipline.setName(disciplineDTO.getName());
        discipline.setDescription(disciplineDTO.getDescription());
        discipline.setResultsType(disciplineDTO.getResultsType());
        return discipline;
    }
}