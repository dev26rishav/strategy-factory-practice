package com.practice.strategyandfactorypim.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.practice.strategyandfactorypim.exception.ExportStrategyNotFound;
import com.practice.strategyandfactorypim.service.strategies.ExportStrategy;

@Service
public class ExportService {
    private final List<ExportStrategy> strategies;

    public ExportService(List<ExportStrategy> strategies) {
        this.strategies = strategies;
    }

    public String exportDataById(int id) {
        for (ExportStrategy s : strategies) {
            if (s.canHandle(id)) {
                return s.exportData(id);
            }
        }

        throw new ExportStrategyNotFound("no valid export strategy found for ID: " + id);
    }
}
