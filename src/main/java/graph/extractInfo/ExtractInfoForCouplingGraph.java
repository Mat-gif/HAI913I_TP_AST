package graph.extractInfo;


import graph.Graphe;
import graph.Noeud;
import graph.PetitArbre;
import graph.extractInfo.utils.Couplage;
import graph.extractInfo.utils.CouplingCalculator;

import graph.extractInfo.utils.Resultats;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import visitor.*;

import java.util.*;

public class ExtractInfoForCouplingGraph implements ExtractInformations{
    public int tot = 0;
    public float totCoup = 0f;
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

        String classFullName = getFullClassName(
                packageDeclarationVisitor.getPackageName(),
                classInterfaceVisitor.getClassName()
        );

        PetitArbre arbre = new PetitArbre(new Noeud(classFullName));

        // Pour tout les méthodes déclarées je cherche les méthodes quil invoque
        for (MethodDeclaration methodDeclaration : methodDeclarationVisitor.getMethods())
        {
            // Trouver les méthodes invoqués
            MethodInvocationVisitor methodInvocationVisitor = new MethodInvocationVisitor();
            methodDeclaration.accept(methodInvocationVisitor);
            // Pour chaque méthodes invoqué je regarde si c'est une class definit dans notre
            // projet
            for (MethodInvocation methodInvocation : methodInvocationVisitor.getMethods())
            {
                if (!getDeclaringClassNameForMethod(methodInvocation).contains("UnknownClass"))
                {

                    arbre.addEnfant2(new Noeud(getDeclaringClassNameForMethod(methodInvocation)));
                }
            }
            // Trouver les constructors invoqués
            ConstructorInvocationVisitor constructorInvocationVisitor = new ConstructorInvocationVisitor();
            methodDeclaration.accept(constructorInvocationVisitor);
            for (ClassInstanceCreation classInstanceCreation : constructorInvocationVisitor.getMethods())
            {
                if (!getDeclaringClassNameForConstruct(classInstanceCreation,importDeclarationVisitor,packageDeclarationVisitor.getPackageName()).contains("ExternClass")) {
                    arbre.addEnfant2(new Noeud(getDeclaringClassNameForConstruct(classInstanceCreation, importDeclarationVisitor, packageDeclarationVisitor.getPackageName())));
                }
            }
        }
        myGraph.addClass(arbre);
        return myGraph;
    }


    public Resultats couplingAnalysis(Graphe myGraph) {
        Resultats out = new Resultats();
        HashMap<String, PetitArbre> copyMap = new HashMap<>(myGraph.getGrapheNonTrie());
        CouplingCalculator calculator = new CouplingCalculator(myGraph.getCountTotalCall());
        for (PetitArbre A : copyMap.values())
        {
            for(Noeud B : A.getEnfants() )
            {
                String AB = String.format("(%s-%s)", A.getParent().getClasseName(), B.getClasseName());
                Couplage couplageAB = new Couplage();
                couplageAB.getClasses().add(A.getParent().getClasseName());
                couplageAB.getClasses().add(B.getClasseName());



                if (copyMap.get(B.getClasseName())!=null) {
                    Noeud ATrouve = copyMap.get(B.getClasseName()).getEnfants().stream()
                            .filter(objet -> Objects.equals(objet.getClasseName(), A.getParent().getClasseName()))
                            .findFirst()
                            .orElse(null);


                    if (ATrouve != null) {
                        out.getForCouplingG().put(AB, calculator.couplingBetweenTwoClasses(
                                B.getNbAppel(),
                                ATrouve.getNbAppel()));

                        couplageAB.setValue(calculator.couplingBetweenTwoClasses(
                                B.getNbAppel(),
                                ATrouve.getNbAppel()));
                        tot+=(B.getNbAppel()+ATrouve.getNbAppel());
                    } else {
                        out.getForCouplingG().put(AB, calculator.couplingBetweenTwoClasses(
                                B.getNbAppel(),
                                0));

                        couplageAB.setValue(calculator.couplingBetweenTwoClasses(
                                B.getNbAppel(),
                                0));
                        tot+=(B.getNbAppel());

                    }
                    totCoup+=out.getForCouplingG().get(AB);
                    out.getForDendo().add(couplageAB);
                }

            }


        }
        return out;
    };


}
