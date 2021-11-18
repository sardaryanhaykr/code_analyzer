package com.example.findbugs.analyzer;

import com.example.findbugs.entity.CodeSmell;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.util.List;

public class OptionalAsMethodArgumentChecker implements Checker {
    private final MethodDeclaration n;
    private final List<CodeSmell> arg;

    public OptionalAsMethodArgumentChecker(MethodDeclaration n, List<CodeSmell> arg) {
        this.n = n;
        this.arg = arg;
    }

    @Override
    public void check() {
        boolean hasOptionalAsArgument = n.getParameters().stream()
                .anyMatch(parameter -> parameter.getType().asString().startsWith("Optional"));
        if (hasOptionalAsArgument) {
            CodeSmell bugReport = new CodeSmell();
            bugReport.setMessage("Optional");
            arg.add(bugReport);
        }
    }
}
