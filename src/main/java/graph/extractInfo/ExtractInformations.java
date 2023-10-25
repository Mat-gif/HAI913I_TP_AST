package graph.extractInfo;

import graph.Graphe;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import visitor.ImportDeclarationVisitor;

public interface ExtractInformations {
    Graphe extract(CompilationUnit parse, Graphe myGraph);

    default String getFullClassName(String packageName, String ClassName){
        return String.format("%s.%s",
                packageName,
                ClassName);
    }

    default String getFullMethodName(String packageName, String ClassName, String methodName){
        return String.format("%s.%s.%s",
                packageName,
                ClassName,
                methodName);
    }

    default String getDeclaringClassNameForMethod(MethodInvocation methodInvocation) {
        if (methodInvocation.resolveMethodBinding() != null)
        {
            return methodInvocation.resolveMethodBinding().getDeclaringClass().getQualifiedName();
        }
        return "UnknownClass";
    }

    default String getDeclaringClassNameForConstruct(ClassInstanceCreation classInstanceCreation, ImportDeclarationVisitor importDeclarationVisitor, String p)
    {
        String fullyQualifiedName = classInstanceCreation.getType().resolveBinding().getQualifiedName();

        for (ImportDeclaration i :importDeclarationVisitor.getImports())
        {
            if(i.getName().getFullyQualifiedName().contains(fullyQualifiedName))
            {
                  return  "ExternClass";
//                return i.getName().getFullyQualifiedName();
            }
        }
        return fullyQualifiedName;
    };
}
