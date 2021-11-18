package com.example.findbugs.service;

import com.example.findbugs.analyzer.Visitor;
import com.example.findbugs.dto.AnalyzeRequestDTO;
import com.example.findbugs.entity.AnalyzeReport;
import com.example.findbugs.entity.AnalyzeResult;
import com.example.findbugs.entity.CodeSmell;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AnalyzeService {

    private final AnalyzeReportService analyzeReportService;
    private final RestTemplateBuilder restTemplateBuilder;
    private final Visitor visitor;

    @Transactional
    public AnalyzeReport analyze(AnalyzeRequestDTO analyzeRequest) {
        /*RestTemplate restTemplate = restTemplateBuilder.build();
        List<URL> urls = restTemplate.exchange("url", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<URL>>() {}, analyzeRequest).getBody();

        if (urls == null) {
            throw new RuntimeException();
        }*/

        List<URL> urls = new ArrayList<>();
        try {
            urls.add(new URL("https://raw.githubusercontent.com/RedHatOfficial/IoT_Summit_Lab/5b5f3266dcb7293da9a7daf84487f6b107ab8985/Software_Sensor/src/main/java/com/redhat/demo/DataSet.java"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //List<CodeSmell> bugs = new ArrayList<>();
        AnalyzeResult analyzeResult=new AnalyzeResult();

        for (URL url : urls) {
            try {
                JavaParser javaParser = new JavaParser();
                ReflectionTypeSolver reflectionTypeSolver = new ReflectionTypeSolver();
                JavaSymbolSolver javaSymbolSolver = new JavaSymbolSolver(reflectionTypeSolver);
                javaParser.getParserConfiguration().setSymbolResolver(javaSymbolSolver);
                CompilationUnit compilationUnit = javaParser.parse(url.openStream()).getResult().orElseThrow();
                visitor.visit(compilationUnit, analyzeResult);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        AnalyzeReport analyzeReport = new AnalyzeReport();
        analyzeReport.setAnalyzeResult();
        return analyzeReportService.save(analyzeReport);


    }
}
