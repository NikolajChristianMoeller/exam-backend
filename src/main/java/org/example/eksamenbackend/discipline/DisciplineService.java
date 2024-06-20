package org.example.eksamenbackend.discipline;

import org.example.eksamenbackend.errorhandling.exception.NotFoundException;
import org.example.eksamenbackend.errorhandling.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DisciplineService {
    private final DisciplineRepository disciplineRepository;

    public DisciplineService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    public List<DisciplineDTO> findAll() {
        return disciplineRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public DisciplineDTO createDiscipline(DisciplineDTO disciplineDTO) {
        if (disciplineDTO.getName() == null || disciplineDTO.getName().isEmpty() || disciplineDTO.getResultType() == null) {
            throw new ValidationException("Name and result type must be provided");
        }
        Discipline discipline = new Discipline();
        discipline.setName(disciplineDTO.getName());
        discipline.setResultType(disciplineDTO.getResultType());
        disciplineRepository.save(discipline);
        return toDTO(discipline);
    }

    private DisciplineDTO toDTO(Discipline discipline) {
        DisciplineDTO disciplineDTO = new DisciplineDTO();
        disciplineDTO.setId(discipline.getId());
        disciplineDTO.setName(discipline.getName());
        disciplineDTO.setResultType(discipline.getResultType());
        return disciplineDTO;
    }

    public DisciplineDTO deleteDiscipline(Long id) {
        Discipline discipline = disciplineRepository.findById(id).orElseThrow(() -> new NotFoundException("Discipline not found"));
        disciplineRepository.deleteById(id);
        return toDTO(discipline);
    }

    public DisciplineDTO updateDiscipline(Long id, DisciplineDTO disciplineDTO) {
        Discipline discipline = disciplineRepository.findById(id).orElseThrow(() -> new NotFoundException("Discipline not found"));
        if (disciplineDTO.getName() == null || disciplineDTO.getName().isEmpty() || disciplineDTO.getResultType() == null) {
            throw new ValidationException("Name and result type must be provided");
        }
        discipline.setName(disciplineDTO.getName());
        discipline.setResultType(disciplineDTO.getResultType());
        disciplineRepository.save(discipline);
        return toDTO(discipline);
    }

    public Optional<DisciplineDTO> findById(Long id) {
        if (id == null || id < 0) {
            throw new ValidationException("Id must be provided");
        }
        return disciplineRepository.findById(id).map(this::toDTO);
    }
}