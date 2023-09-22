package processor;

import parsers.EclipseJDTParser;
import visitor.ClassInterfaceVisitor;
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

    public Map<String, Integer> getTopClassesByMethodsCount() throws IOException {
        Map<String, Integer> myMap = new HashMap<>();
        for (CompilationUnit cu : parser.parseProject()) {
            ClassInterfaceVisitor visitor = new ClassInterfaceVisitor();
            MethodDeclarationVisitor visitor2 = new MethodDeclarationVisitor();

            cu.accept(visitor);
            cu.accept(visitor2);
            myMap.put(visitor.getClassName(), visitor2.getMethods().size());//

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
            ClassInterfaceVisitor visitor = new ClassInterfaceVisitor();
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

    // Question 10

    public HashSet<String> getTopClassByMethodsAndField() throws IOException {
        HashSet<String> mySet = new HashSet<>();
        getTopClassesByMethodsCount().keySet().forEach(e -> {
            try {
                if (getTopClassesByFieldsCount().keySet().contains(e)) {
                    mySet.add(e);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });
        return mySet;

    }

    // Queston 11 hashset

    public HashSet<String> getTopClassWithXGivenMethods(int x) throws IOException {
        HashSet<String> mySet = new HashSet<>();
        for (CompilationUnit cu : parser.parseProject()) {
            ClassInterfaceVisitor visitor = new ClassInterfaceVisitor();
            MethodDeclarationVisitor visitor2 = new MethodDeclarationVisitor();
            cu.accept(visitor);
            cu.accept(visitor2);
            if (visitor2.getMethods().size() > x) {
                mySet.add(visitor.getClassName());
            }
        }
        return mySet;

    }

    // 12 Map<String, Integer>
    // 13 probablement un int

}