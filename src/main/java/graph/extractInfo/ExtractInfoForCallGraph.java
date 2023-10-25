package graph.extractInfo;

import graph.Graphe;
import graph.Noeud;
import graph.PetitArbre;
import org.eclipse.jdt.core.dom.*;
import visitor.*;

public class ExtractInfoForCallGraph implements ExtractInformations{
    @Override
    public Graphe extract(CompilationUnit parse, Graphe myGraph) {
        // Trouver les méthodes déclaré
        MethodDeclarationVisitor methodDeclarationVisitor = new MethodDeclarationVisitor();
        parse.accept(methodDeclarationVisitor);

        ImportDeclarationVisitor importDeclarationVisitor = new ImportDeclarationVisitor();
        parse.accept(importDeclarationVisitor);

        // Trouver la classe courant
        ClassInterfaceVisitor classInterfaceVisitor = new ClassInterfaceVisitor();
        parse.accept(classInterfaceVisitor);

        // Trouver le package courant
        PackageDeclarationVisitor packageDeclarationVisitor = new PackageDeclarationVisitor();
        parse.accept(packageDeclarationVisitor);

        // Pour tout les méthodes déclarées je cherche les méthodes quil invoque
        for (MethodDeclaration methodDeclaration : methodDeclarationVisitor.getMethods()) {



            // Trouver les méthodes invoqués
            MethodInvocationVisitor methodInvocationVisitor = new MethodInvocationVisitor();
            methodDeclaration.accept(methodInvocationVisitor);
            PetitArbre arbre;

            //format => Package.Class.Method
            String methodFullName = String.format("%s.%s.%s",
                    packageDeclarationVisitor.getPackageName(),
                    classInterfaceVisitor.getClassName(),
                    methodDeclaration.getName().getFullyQualifiedName());
            String classFullName = String.format("%s.%s",
                    packageDeclarationVisitor.getPackageName(),
                    classInterfaceVisitor.getClassName());
            if (!myGraph.isExist(methodFullName))
            {
                arbre = new PetitArbre(new Noeud(classFullName,methodDeclaration.getName().getFullyQualifiedName()));
            } else
            {
                arbre = myGraph.getPetitArbreByKey(methodFullName);
            }

            // Pour chaque méthodes invoqué je regarde si c'est une class definit dans notre
            // projet
            for (MethodInvocation methodInvocation : methodInvocationVisitor.getMethods()) {

                if (!getDeclaringClassNameForMethod(methodInvocation).contains("UnknownClass")) {
                    arbre.addEnfant(new Noeud(
                                    getDeclaringClassNameForMethod(methodInvocation),
                                    methodInvocation.getName().getFullyQualifiedName()
                            )
                    );
                }
            }
            // Trouver les constructors invoqués
            ConstructorInvocationVisitor constructorInvocationVisitor = new ConstructorInvocationVisitor();
            methodDeclaration.accept(constructorInvocationVisitor);

            for (ClassInstanceCreation classInstanceCreation : constructorInvocationVisitor.getMethods())
            {
                String classNameForConstruct = getDeclaringClassNameForConstruct(classInstanceCreation,importDeclarationVisitor,packageDeclarationVisitor.getPackageName());
                arbre.addEnfant(new Noeud(classNameForConstruct,
                        classInstanceCreation.getType().toString()));
            }
            myGraph.add(arbre);
        }
        return myGraph;
    }

    @Override
    public String getDeclaringClassNameForMethod(MethodInvocation methodInvocation) {
        if (methodInvocation.resolveMethodBinding() != null)
        {
            return methodInvocation.resolveMethodBinding().getDeclaringClass().getQualifiedName();
        }
        return "UnknownClass";
    }

    @Override
    public String getDeclaringClassNameForConstruct(ClassInstanceCreation classInstanceCreation, ImportDeclarationVisitor importDeclarationVisitor, String p) {
        String fullyQualifiedName = classInstanceCreation.getType().resolveBinding().getQualifiedName();
        for (ImportDeclaration i :importDeclarationVisitor.getImports())
        {
            if(i.getName().getFullyQualifiedName().contains(fullyQualifiedName))
            {
                return i.getName().getFullyQualifiedName();
            }
        }
        return p+"."+fullyQualifiedName;
    }
}
