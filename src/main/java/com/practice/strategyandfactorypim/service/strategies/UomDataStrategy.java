package com.practice.strategyandfactorypim.service.strategies;

import org.springframework.stereotype.Component;

@Component
public class UomDataStrategy implements ExportStrategy{

    @Override
    public boolean canHandle(int id) {
        return (id > 20 && id <= 30);
    }

    @Override
    public String exportData(int id) {
        return "UOM Data Exported";
    }
    
}
