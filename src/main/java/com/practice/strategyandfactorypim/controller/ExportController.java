package com.practice.strategyandfactorypim.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.strategyandfactorypim.exception.ExportStrategyNotFound;
import com.practice.strategyandfactorypim.service.ExportService;

@RestController()
@RequestMapping("/export")
public class ExportController {

    private final ExportService exportService;

    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> exportItems(@PathVariable int id) {
        try {
            String message = exportService.exportDataById(id);
            return ResponseEntity.accepted().body(message);
        } catch (ExportStrategyNotFound ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
