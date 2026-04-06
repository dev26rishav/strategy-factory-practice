package com.practice.strategyandfactorypim.service.strategies;

import org.springframework.stereotype.Component;

@Component
public class ItemDataExportStrategy implements ExportStrategy{

    @Override
    public boolean canHandle(int id) {
        return (id >= 0 && id <= 10);
    }

    @Override
    public String exportData(int id) {
        return "item data exported";
    }
    
}
