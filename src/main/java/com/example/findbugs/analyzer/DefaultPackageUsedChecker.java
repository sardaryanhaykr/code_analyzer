package com.example.findbugs.analyzer;

import com.example.findbugs.entity.CodeSmell;
import com.github.javaparser.ast.CompilationUnit;

import java.util.List;

public class DefaultPackageUsedChecker implements Checker {
    private final CompilationUnit n;
    private final List<CodeSmell> arg;

    public DefaultPackageUsedChecker(CompilationUnit n, List<CodeSmell> arg) {
        this.n = n;
        this.arg = arg;
    }

    @Override
    public void check() {
        if (n.getPackageDeclaration().isEmpty()) {
            CodeSmell bug = new CodeSmell();
            bug.setMessage("Default package ...");
            arg.add(bug);
        }
    }
}
