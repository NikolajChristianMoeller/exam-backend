package org.example.eksamenbackend.discipline;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplines")
public class DisciplineController {

    private final DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @GetMapping
    public ResponseEntity<List<DisciplineDTO>> findAll () {
        return ResponseEntity.ok(disciplineService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplineDTO> findById (@PathVariable Long id) {
        return  ResponseEntity.of(disciplineService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DisciplineDTO> createDiscipline (@RequestBody DisciplineDTO disciplineDTO) {
        return ResponseEntity.status(201).body(disciplineService.createDiscipline(disciplineDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisciplineDTO> updateDiscipline (@PathVariable Long id, @RequestBody DisciplineDTO disciplineDTO) {
        return ResponseEntity.ok(disciplineService.updateDiscipline(id, disciplineDTO));
    }

}
