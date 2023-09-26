package processor;

import parsers.EclipseJDTParser;
import visitor.ClassVisitor;
import visitor.MethodDeclarationVisitor;
import visitor.PackageDeclarationVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
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

    public String getProjectPath() {

        // récupère le chemin du projet provenant de Mathieu pour le moment null
        return null;
    }

    public String getSourcePath() {

        // récupère le chemin du projet provenant de Mathieu pour le moment null
        return null;
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
        parser = new EclipseJDTParser(projectPath);
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

    public int countClassesInProject() {

        // TODO: à compléter
        return 0;
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

    public int countLinesCodeInProject() {
        // TODO : à compléter
        return 0;
    }

    public int meanFieldPerClass() throws IOException {

        int field = 0;
        // TODO: à compléter
        return field / countClassesInProject();
    }

    public int meanLinesPerMethod() throws IOException {
        return countLinesCodeInProject() / countMethodInProject();
    }

    public Map<String, Integer> getTopClassesByMethodsCount() {
        Map<String, Integer> myMap = new HashMap<>();
        for (CompilationUnit cu : parser.parseProject()) {
            ClassVisitor visitor = new ClassVisitor();
            cu.accept(visitor);
            myMap.put(visitor.getClassName(), visitor.getMethods.size());//

        }
        Map<String, Integer> sortedMap = myMap.entrySet()
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
        return firstNElements;

    }

    public Map<String, Integer> getTopClassesByFieldsCount() throws IOException {
        Map<String, Integer> myMap = new HashMap<>();
        for (CompilationUnit cu : parser.parseProject()) {
            ClassVisitor visitor = new ClassVisitor();
            cu.accept(visitor);
            myMap.put(visitor.getClassName(), visitor.getAttributeCount());//

        }
        Map<String, Integer> sortedMap = myMap.entrySet()
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
        return firstNElements;

    }

<<<<<<< Updated upstream
=======
    // Question 10

    public Map<String, Integer> getTopClassByMethodsAndField() throws IOException {
    	Map<String, Integer> myResults = new HashMap<>();
        getTopClassesByMethodsCount().keySet().forEach(e -> {
            try {
                if (getTopClassesByFieldsCount().keySet().contains(e)) {
                	myResults.put(e,null);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        return myResults;
    }

    // Queston 11 hashset

    public Map<String, Integer> getTopClassWithXGivenMethods(int x) throws IOException {
    	Map<String, Integer> myResults = new HashMap<>();
        for (CompilationUnit cu : parser.parseProject()) {
            ClassInterfaceVisitor visitor = new ClassInterfaceVisitor();
            MethodDeclarationVisitor visitor2 = new MethodDeclarationVisitor();
            cu.accept(visitor);
            cu.accept(visitor2);
            if (visitor2.getMethods().size() > x) {
            	myResults.put(visitor.getClassName(),null);
            }
        }	
        return myResults;
    }

    // 12 Map<String, Integer>
    // Question 12 : 10% methodes avec le plus de lignes de code
    
    public Map<String, Integer> getTopMethodsByLinesCode() throws IOException {
    	Map<String,Integer> myMap = new HashMap<>();
    	for (CompilationUnit cu : parser.parseProject()) {
            MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
            cu.accept(visitor);
            myMap.putAll(visitor.getMethodsLines());
        }
    	
        Map<String, Integer> sortedMap = myMap.entrySet()
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
        
    	
    	return firstNElements;
    }
    
    
    // 13 probablement un Map<String, Integer>
    
    public Map<String, Integer> getTopMethodsByParameters() throws IOException {
    	Map<String,Integer> myMap = new HashMap<>();
    	for (CompilationUnit cu : parser.parseProject()) {
            MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
            cu.accept(visitor);
            myMap.putAll(visitor.getMethodsParameters());
        }
    	
        Map<String, Integer> sortedMap = myMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
        System.out.println("SORTED" + sortedMap.toString());

        Map<String, Integer> myResult = sortedMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(sortedMap.values().iterator().next()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
        System.out.println("RES" + myResult.toString());
    	return myResult;
    }
    public void testmethod(int a, int b, int c, int d, int e) {
    	
    }
    	
>>>>>>> Stashed changes
}