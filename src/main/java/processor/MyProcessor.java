package processor;

import parsers.EclipseJDTParser;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;
import java.util.ArrayList;
import visitor.ClassVisitor;

public class MyProcessor extends Processor<EclipseJDTParser> {

    public MyProcessor(EclipseJDTParser parser, String projectPath) {
        super(parser, projectPath);
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
        EclipseJDTParser parser = new EclipseJDTParser(getProjectPath());
        parser.parseProject();
        // // TODO : vérifier si le chemin du projet est valide
        // try {
         
        // } catch (Exception e) {
        // throw new RuntimeException("Le chemin du projet n'est pas valide");
        // }

        // parser.configure();
        // System.out.println("le processus est lancé et le parser est configuré");

    }

    public int getNumberOfClasses() {
        // EclipseJDTParser parser = new EclipseJDTParser(getProjectPath());
        // ClassVisitor visitor = new ClassVisitor();
        // parser.accept(visitor);
        // List<String> classes = visitor.getClasses();
        // return classes.size();
        return 0;
    }

    public int getApplicationCodeLines() {
        // TODO : le parser accept le visitor et le visitor récupère les classes
        // TODO : le visitor récupère les lignes de code de chaque classe
        // TODO : le processor récupère les lignes de code de chaque classe et les
        // additionne
        // TODO : le processor retourne le nombre de lignes de code total
        return 0;
    }

    public int getNumberOfMethods() {

        // TODO : le parser accept le visitor et le visitor récupère les classes
        // TODO : le visitor récupère les méthodes de chaque classe
        // TODO : le processor récupère les méthodes de chaque classe et les additionne
        // TODO : le processor retourne le nombre de méthodes total
        return 0;
    }

    public ArrayList<String> displayClasses() {
        // TODO : le parser accept le visitor et le visitor récupère les classes
        // TODO : le processor récupère les classes
        // TODO : le processor retourne la liste des classes
        return null; // pour le moment retourne null pour que le code compile
    }

    public ArrayList<String> displayMethods() {
        // TODO : le parser accept le visitor et le visitor récupère les classes
        // TODO : le processor récupère les méthodes
        // TODO : le processor retourne la liste des méthodes
        return null; // pour le moment retourne null pour que le code compile
    }

    public int getNumberTheAppPackages() {
        // TODO : le parser accept le visitor et le visitor récupère les classes
        // TODO : le processor récupère les packages
        // TODO : le processor retourne le nombre de packages
        return 0;
    }

    public int getMediumNumberOfMethodsPerClass(String className) {
        // TODO : le parser accept le visitor et le visitor récupère les classes
        // TODO : le processor récupère les méthodes de la classe passée en paramètres
        // et retourne la moitié
        return 0;
    }

    public int getNumbersOfCodeLinesPerMethods(String givenMethod) {
        // TODO: le
        return 0;
    }
}
