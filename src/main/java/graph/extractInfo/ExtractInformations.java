package graph.extractInfo;

import graph.Graphe;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodInvocation;
import visitor.ImportDeclarationVisitor;

public interface ExtractInformations {
    Graphe extract(CompilationUnit parse, Graphe myGraph);
    String getDeclaringClassNameForMethod(MethodInvocation methodInvocation);
    String getDeclaringClassNameForConstruct(ClassInstanceCreation classInstanceCreation, ImportDeclarationVisitor importDeclarationVisitor, String p);
}
