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

}