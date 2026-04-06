package com.practice.strategyandfactorypim.service.strategies;

import org.springframework.stereotype.Component;

@Component
public class UserDataExportStrategy implements ExportStrategy{

    @Override
    public boolean canHandle(int id) {
        return (id >= 11 && id <= 20);
    }

    @Override
    public String exportData(int id) {
       return "User Data Exported";
    }
    
}
