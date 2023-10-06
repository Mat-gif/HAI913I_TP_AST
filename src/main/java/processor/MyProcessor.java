
package processor;

import parsers.EclipseJDTParser;
import ui.controller.Resultat;
import visitor.ClassInterfaceVisitor;
import visitor.ConstructorInvocationVisitor;
import visitor.ImportDeclarationVisitor;
import visitor.MethodDeclarationVisitor;
import visitor.MethodInvocationVisitor;
import visitor.PackageDeclarationVisitor;

import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

import graph.Graphe;
import graph.Noeud;
import graph.PetitArbre;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MyProcessor extends Processor<EclipseJDTParser> {

    // private int countPackage = 0;
	


    public MyProcessor(String path) {
        super(path);
    }

    @Override
    public void process() throws IOException {
        EclipseJDTParser parser = new EclipseJDTParser(
                "");
        // parser.configure();
        parser.parseProject();
        // System.out.println(parser.parseProject());
    }

    @Override
    public void setParser(String projectPath) {
        try {
			parser = new EclipseJDTParser(projectPath);
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }

    public int countPackagesInProject() throws IOException {
        HashSet<String> packageSet = new HashSet<>();
        for (CompilationUnit cu : parser.parseProject()) {
            PackageDeclarationVisitor visitor = new PackageDeclarationVisitor();
            cu.accept(visitor);
            packageSet.add(visitor.getPackageName());

        }
        return packageSet.size();
    }

    public int countClassesInProject() throws IOException {
        HashSet<String> classSet = new HashSet<>();
        for (CompilationUnit cu : parser.parseProject()) {
            ClassInterfaceVisitor visitor = new ClassInterfaceVisitor();
            cu.accept(visitor);	
            System.out.println(visitor.getClassName());
            if(visitor.getClassName()!=null) {
            	classSet.add(visitor.getClassName());
            }
        }
        return classSet.size();

    }

    public int countMethodInProject() throws IOException {
        int val = 0;
        for (CompilationUnit cu : parser.parseProject()) {
            MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
            cu.accept(visitor);
            val += visitor.getMethods().size();

        }
        return val;
    }

    public int meanMethodsPerClass() throws IOException {
        return countMethodInProject() / countClassesInProject();
    }

    public int countLinesCodeInProject() throws IOException {
        int val = 0;
        for (CompilationUnit cu : parser.parseProject()) {
            ClassInterfaceVisitor visitor = new ClassInterfaceVisitor();
            cu.accept(visitor);
            val += visitor.getLinesOfCode();

        }
        return val;
    }

    public int meanFieldPerClass() throws IOException {

        int field = 0;
        for (CompilationUnit cu : parser.parseProject()) {
            ClassInterfaceVisitor visitor = new ClassInterfaceVisitor();
            cu.accept(visitor);
            field += visitor.getAttributeCount();
        }
        return field / countClassesInProject();
    }

    public int meanLinesPerMethod() throws IOException {
        return countLinesCodeInProject() / countMethodInProject();
    }

    
    
    public Resultat getTopClassesByMethodsCount() throws IOException {
    	Resultat resultat = new Resultat();
        for (CompilationUnit cu : parser.parseProject()) {
            ClassInterfaceVisitor visitor = new ClassInterfaceVisitor();
            MethodDeclarationVisitor visitor2 = new MethodDeclarationVisitor();

            cu.accept(visitor);
            cu.accept(visitor2);
            resultat.addResultat(visitor.getClassName(), visitor2.getMethods().size());//

        }
        Map<String, Integer> sortedMap = resultat.getResultats().entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));

        // Extraire les N premiers éléments (par exemple, les 2 premiers)
        Long N = Math.round(0.1 * countClassesInProject());
        Map<String, Integer> firstNElements = sortedMap.entrySet()
                .stream()
                .limit(N)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
        
        Resultat resultatFinal = new Resultat();
        resultatFinal.addAllResultat(firstNElements);
        return resultatFinal;

    }

    public Resultat getTopClassesByFieldsCount() throws IOException {
    	Resultat resultat = new Resultat();
    	for (CompilationUnit cu : parser.parseProject()) {
            ClassInterfaceVisitor visitor = new ClassInterfaceVisitor();
            cu.accept(visitor);
            resultat.addResultat(visitor.getClassName(), visitor.getAttributeCount());//

        }
        Map<String, Integer> sortedMap = resultat.getResultats().entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
        Long N = Math.round(0.1 * countClassesInProject());
        Map<String, Integer> firstNElements = sortedMap.entrySet()
                .stream()
                .limit(N)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
        
        Resultat resultatFinal = new Resultat();
        resultatFinal.addAllResultat(firstNElements);
        return resultatFinal;

    }

    // Question 10

    public Resultat getTopClassByMethodsAndField() throws IOException {
    	Resultat resultat = new Resultat();
        getTopClassesByMethodsCount().getKeys().forEach(e -> {
            try {
                if (getTopClassesByFieldsCount().getKeys().contains(e)) {
                	resultat.addResultat(e, 0);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        return resultat;
    }

    // Queston 11 hashset

    public Resultat getTopClassWithXGivenMethods(int x) throws IOException {
    	Resultat resultat = new Resultat();
        for (CompilationUnit cu : parser.parseProject()) {
            ClassInterfaceVisitor visitor = new ClassInterfaceVisitor();
            MethodDeclarationVisitor visitor2 = new MethodDeclarationVisitor();
            cu.accept(visitor);
            cu.accept(visitor2);
            if (visitor2.getMethods().size() > x) {
            	resultat.addResultat(visitor.getClassName(), 0);
            }
        }	
        return resultat;
    }

    // 12 Map<String, Integer>
    // Question 12 : 10% methodes avec le plus de lignes de code
    
    public Resultat getTopMethodsByLinesCode() throws IOException {
    	Resultat resultat = new Resultat();
    	for (CompilationUnit cu : parser.parseProject()) {
            MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
            cu.accept(visitor);
            resultat.addAllResultat(visitor.getMethodsLines());
        }
    	
        Map<String, Integer> sortedMap = resultat.getResultats().entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
        
        Long N = Math.round(0.1 * countMethodInProject());
        Map<String, Integer> firstNElements = sortedMap.entrySet()
                .stream()
                .limit(N)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
        
    	
        Resultat resultatFinal = new Resultat();
        resultatFinal.addAllResultat(firstNElements);
        return resultatFinal;
    }
    
    
    // 13 probablement un Map<String, Integer>
    
    public Resultat getTopMethodsByParameters() throws IOException {
    	Resultat resultat = new Resultat();
    	for (CompilationUnit cu : parser.parseProject()) {
            MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
            cu.accept(visitor);
            resultat.addAllResultat(visitor.getMethodsParameters());
        }
    	
    	 Map<String, Integer> sortedMap = resultat.getResultats().entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
        System.out.println("SORTED" + sortedMap.toString());

        Map<String, Integer> firstNElements = sortedMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(sortedMap.values().iterator().next()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
        System.out.println("RES" + firstNElements.toString());
        Resultat resultatFinal = new Resultat();
        resultatFinal.addAllResultat(firstNElements);
        return resultatFinal;
    }
    
    public void showAbre() {
    	Graphe graphe = new Graphe();
    	
    }
    
    public void testmethod(int a, int b, int c, int d, int e) {
    	
    }
    
	// navigate method invocations inside method
	public Graphe printMethodInvocationInfo( ) throws IOException {
		Graphe myGraph = new Graphe();
		for (CompilationUnit parse : parser.parseProject()) {

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
			
			if (!myGraph
					.isExist(packageDeclarationVisitor.getPackageName() + "." + classInterfaceVisitor.printClassName()
							+ "." + methodDeclaration.getName().getFullyQualifiedName())) {
				arbre = new PetitArbre(new Noeud(
						packageDeclarationVisitor.getPackageName() + "." + classInterfaceVisitor.printClassName(),
						methodDeclaration.getName().getFullyQualifiedName()));

			} else {
				arbre = myGraph.getPetitArbreByKey(
						packageDeclarationVisitor.getPackageName() + "." + classInterfaceVisitor.printClassName() + "."
								+ methodDeclaration.getName().getFullyQualifiedName());
			}

			// Pour chaque méthodes invoqué je regarde si c'est une class definit dans notre
			// projet
			for (MethodInvocation methodInvocation : methodInvocationVisitor.getMethods()) {


				if (!getDeclaringClassName(methodInvocation).contains("UnknownClass")) {
				
					arbre.addEnfant(new Noeud(getDeclaringClassName(methodInvocation),
							methodInvocation.getName().getFullyQualifiedName()));
				}
			}

			// Trouver les constructors invoqués
			ConstructorInvocationVisitor constructorInvocationVisitor = new ConstructorInvocationVisitor();
			methodDeclaration.accept(constructorInvocationVisitor);

			for (ClassInstanceCreation classInstanceCreation : constructorInvocationVisitor.getMethods()) {
				
				if (!getDeclaringClassName2(classInstanceCreation,importDeclarationVisitor).contains("UnknownClass")) {
					
					arbre.addEnfant(new Noeud(getDeclaringClassName2(classInstanceCreation,importDeclarationVisitor),
							classInstanceCreation.getType().toString()));
				}
				
			}
			myGraph.checkMainOrSommet(arbre);

		}
		}
		return myGraph;
	}

	private static String getDeclaringClassName(MethodInvocation methodInvocation) {
		if (methodInvocation.resolveMethodBinding()!= null) {
			String fullyQualifiedName = methodInvocation.resolveMethodBinding().getDeclaringClass().getQualifiedName();
			//System.out.println(fullyQualifiedName);
			
		
				return fullyQualifiedName;
	
		}
		return "UnknownClass";
	}


	private static String getDeclaringClassName2(ClassInstanceCreation classInstanceCreation, ImportDeclarationVisitor importDeclarationVisitor) {

			
		if(classInstanceCreation.getType().resolveBinding()!=null){
			String fullyQualifiedName = classInstanceCreation.getType().resolveBinding().getQualifiedName();
			String pac = null;
			

			for (ImportDeclaration i :importDeclarationVisitor.getImports()) {
				if(i.getName().getFullyQualifiedName().contains(fullyQualifiedName)) {
					 pac = i.getName().getFullyQualifiedName();
					//System.out.println(pac);
					return pac;
					
				}
			}
			
		
			return fullyQualifiedName;
			}
		return "UnknownClass";
	}
	
    	
}