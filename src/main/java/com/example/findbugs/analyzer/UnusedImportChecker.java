package com.example.findbugs.analyzer;

import com.example.findbugs.entity.CodeSmell;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.type.Type;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UnusedImportChecker implements Checker {
    private final CompilationUnit n;
    private final List<CodeSmell> arg;

    public UnusedImportChecker(CompilationUnit n, List<CodeSmell> arg) {
        this.n = n;
        this.arg = arg;
    }

    @Override
    public void check() {
        Set<String> imports = new HashSet<>();

        n.getImports().forEach(importDeclaration -> imports.add(importDeclaration.getNameAsString()));

        n.findAll(VariableDeclarator.class).forEach(variableDeclarator -> {
            Type type = variableDeclarator.getType();
            if (type.isClassOrInterfaceType()) {
                String packageName =  type.asClassOrInterfaceType()
                        .resolve()
                        .getTypeDeclaration()
                        .orElseThrow()
                        .asReferenceType()
                        .getPackageName();

                imports.remove(packageName);
            }
        });

        if (!imports.isEmpty()) {
            CodeSmell bug = new CodeSmell();
            bug.setMessage("Unused import ...");
            arg.add(bug);
        }
    }
}
