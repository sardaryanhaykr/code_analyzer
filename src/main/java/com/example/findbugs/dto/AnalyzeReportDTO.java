package com.example.findbugs.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class AnalyzeReportDTO {
    private Long id;
    private String username;
    private Date date;
    private List<CodeSmellDTO> codeSmells;
}
