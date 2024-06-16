package org.example.eksamenbackend.skabelon;

import errorhandling.exception.NotFoundException;
import errorhandling.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SkabelonService {
    private final SkabelonRepository skabelonRepository;

    public SkabelonService(SkabelonRepository skabelonRepository) {
        this.skabelonRepository = skabelonRepository;
    }

    public List<SkabelonDTO> findAll() {
        return skabelonRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());

    }

    public SkabelonDTO createSkabelon(SkabelonDTO skabelonDTO) {
        if (skabelonDTO.getName() == null || skabelonDTO.getName().isEmpty() || skabelonDTO.getAge() < 0) {
            throw new ValidationException("Name and age must be provided");
        }
        Skabelon skabelon = new Skabelon();
        skabelon.setName(skabelonDTO.getName());
        skabelon.setAge(skabelonDTO.getAge());
        skabelonRepository.save(skabelon);
        return toDTO(skabelon);
    }

    private SkabelonDTO toDTO(Skabelon skabelon) {
        SkabelonDTO skabelonDTO = new SkabelonDTO();
        skabelonDTO.setId(skabelon.getId());
        skabelonDTO.setName(skabelon.getName());
        skabelonDTO.setAge(skabelon.getAge());
        return skabelonDTO;
    }

    public SkabelonDTO deleteSkabelon(Long id) {
        Skabelon skabelon = skabelonRepository.findById(id).orElseThrow(() -> new NotFoundException("Skabelon not found"));
        skabelonRepository.deleteById(id);
        return toDTO(skabelon);
    }

    public SkabelonDTO updateSkabelon(Long id, SkabelonDTO skabelonDTO) {
        Skabelon skabelon = skabelonRepository.findById(id).orElseThrow(() -> new NotFoundException("Skabelon not found"));
        if (skabelonDTO.getName() == null || skabelonDTO.getName().isEmpty() || skabelonDTO.getAge() < 0) {
            throw new ValidationException("Name and age must be provided");
        }
        skabelon.setName(skabelonDTO.getName());
        skabelon.setAge(skabelonDTO.getAge());
        skabelonRepository.save(skabelon);
        return toDTO(skabelon);
    }

    public Optional<SkabelonDTO> findById(Long id) {
        if (id == null || id < 0) {
            throw new ValidationException("Id must be provided");
        }
        return skabelonRepository.findById(id).map(this::toDTO);
    }
}
