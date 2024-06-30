package org.example.eksamenbackend.result;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/results")
public class ResultController {
    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping
    public ResponseEntity<List<ResResultDTO>> findAll () {
        return ResponseEntity.ok(resultService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResResultDTO> findById (@PathVariable Long id) {
        return ResponseEntity.of(resultService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ResResultDTO> createResult (@RequestBody ReqResultDTO reqResultDTO) {
        return ResponseEntity.status(201).body(resultService.createResult(reqResultDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResResultDTO> deleteResult (@PathVariable Long id) {
        return ResponseEntity.ok(resultService.deleteResult(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResResultDTO> updateResult (@PathVariable Long id, @RequestBody ReqResultDTO reqResultDTO) {
        return ResponseEntity.ok(resultService.updateResult(id, reqResultDTO));
    }
}
