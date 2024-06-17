package org.example.eksamenbackend.skabelon;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skabelon")
@CrossOrigin(origins = "http://localhost:5173")
public class SkabelonController {

private final SkabelonService skabelonService;

    public SkabelonController(SkabelonService skabelonService) {
        this.skabelonService = skabelonService;
    }

    @GetMapping
    public ResponseEntity<List<SkabelonDTO>> findAll () {
        return ResponseEntity.ok(skabelonService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkabelonDTO> findById (@PathVariable Long id) {
        return  ResponseEntity.of(skabelonService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SkabelonDTO> createSkabelon (@RequestBody SkabelonDTO skabelonDTO) {
        return ResponseEntity.status(201).body(skabelonService.createSkabelon(skabelonDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkabelonDTO> updateSkabelon (@PathVariable Long id, @RequestBody SkabelonDTO skabelonDTO) {
        return ResponseEntity.ok(skabelonService.updateSkabelon(id, skabelonDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SkabelonDTO> deleteSkabelon (@PathVariable Long id) {
        return ResponseEntity.ok(skabelonService.deleteSkabelon(id));
    }
}
