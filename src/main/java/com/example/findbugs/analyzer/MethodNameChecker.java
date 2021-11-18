package com.example.findbugs.analyzer;

import com.example.findbugs.entity.CodeSmell;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.util.List;

public class MethodNameChecker implements Checker {
    private final MethodDeclaration n;
    private final List<CodeSmell> arg;

    public MethodNameChecker(MethodDeclaration n, List<CodeSmell> arg) {
        this.n = n;
        this.arg = arg;
    }

    @Override
    public void check() {
        char c = n.getName().getIdentifier().charAt(0);
        if (Character.isUpperCase(c)) {
            CodeSmell bugReport = new CodeSmell();
            arg.add(bugReport);
        }
    }
}
