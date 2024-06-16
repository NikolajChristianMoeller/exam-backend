package org.example.eksamenbackend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class Something4Controller {

    final CaseService caseService;

    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }

    @GetMapping
    public ResponseEntity<List<Case>> getAllCases() {
        try {
            return new ResponseEntity<>(caseService.getAllCases(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity getCaseById(@PathVariable int id) {
        try {
            return new ResponseEntity<>(caseService.getCaseById(id), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/undefined")
    public ResponseEntity<Case> getAllCasesTopLoad() {
        try {
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Case> addCase(@RequestBody Case c) {
        try {
            return new ResponseEntity<>(caseService.createCase(c), HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity updateCase(@PathVariable int id, @RequestBody Case c) {
        try {
            return new ResponseEntity<>(caseService.updateCase(id, c), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteCase(@PathVariable int id) {
        try {
            return caseService.deleteCase(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
    }
}

}
