package com.practice.strategyandfactorypim.service.strategies;

public interface ExportStrategy {
    boolean canHandle(int id);
    String exportData(int id);
}
