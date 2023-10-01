package processor;

import parsers.EclipseJDTParser;
import ui.controller.Resultat;
import visitor.ClassInterfaceVisitor;
import visitor.MethodDeclarationVisitor;
import visitor.PackageDeclarationVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

import graph.Graphe;

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
    	
}