package com.example.findbugs.analyzer;

import com.example.findbugs.entity.CodeSmell;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;

import java.util.List;

public class OneLineVariablesDeclarationChecker implements Checker {
    private final CompilationUnit n;
    private final List<CodeSmell> arg;

    public OneLineVariablesDeclarationChecker(CompilationUnit n, List<CodeSmell> arg) {
        this.n = n;
        this.arg = arg;
    }

    @Override
    public void check() {
        n.findAll(VariableDeclarationExpr.class).forEach(variableDeclarationExpr -> {
            if (variableDeclarationExpr.getVariables().size() > 1) {
                CodeSmell bug = new CodeSmell();
                bug.setMessage("Online variable");
                arg.add(bug);
            }
        });

        n.findAll(FieldDeclaration.class).forEach(fieldDeclaration -> {
            if (fieldDeclaration.getVariables().size() > 1) {
                CodeSmell bug = new CodeSmell();
                arg.add(bug);
            }
        });
    }
}
