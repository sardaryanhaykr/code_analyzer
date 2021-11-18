package com.example.findbugs.analyzer;

import com.example.findbugs.entity.CodeSmell;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.resolution.types.ResolvedType;

import java.util.List;

public class CollectionInterfaceUsedChecker implements Checker {
    private final CompilationUnit n;
    private final List<CodeSmell> arg;

    public CollectionInterfaceUsedChecker(CompilationUnit n, List<CodeSmell> arg) {
        this.n = n;
        this.arg = arg;
    }

    @Override
    public void check() {
        List<VariableDeclarator> variables = n.findAll(VariableDeclarator.class);

        for (VariableDeclarator variable :  variables) {
            ResolvedType type = variable.resolve().getType();

            if (type.isReference()) {
                if (type.asReferenceType().getTypeDeclaration().orElseThrow().isClass()) {
                    boolean isCollectionLCass = type.asReferenceType().getAllAncestors().stream().
                            anyMatch(resolvedReferenceType ->
                                    "java.util.Collection".equals(resolvedReferenceType.getQualifiedName())
                            );

                    if (isCollectionLCass) {
                        CodeSmell bug = new CodeSmell();
                        bug.setMessage("Collection interface ...");
                        arg.add(bug);
                    }
                }
            }
        }
    }
}
