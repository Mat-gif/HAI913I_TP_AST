package processor;

import parsers.EclipseJDTParser;
import java.util.List;

public class MyProcessor extends Processor<EclipseJDTParser> {

    public MyProcessor(EclipseJDTParser parser) {
        super(parser);
    }

    public String getProjectPath() {

        // récupère le chemin du projet provenant de Mathieu pour le moment null
        return null;
    }

    public String getSourcePath() {

        // récupère le chemin du projet provenant de Mathieu pour le moment null
        return null;
    }

    public List<String> getClasses() {

        // récupère le chemin du projet provenant de Mathieu pour le moment null
        return null;
    }

    public List<String> getTotalMethods() {

        // récupère le chemin du projet provenant de Mathieu pour le moment null
        return null;
    }

    public List<Integer> getLinesOfCode(int wanted) {

        // récupère le chemin du projet provenant de Mathieu pour le moment null
        return null;
    }

    public List<Integer> getPackages(int wanted) {

        // récupère le chemin du projet provenant de Mathieu pour le moment null
        return null;
    }



    @Override
    public void process() {

        // Dans le parseur il doit avoir une sourcePath par défaur ou un constructeur
        // qui ne prend pas le sourcePath
        EclipseJDTParser parser = new EclipseJDTParser(getProjectPath(), getSourcePath());
        parser.defaultSetterParser();
        

    }

    

    // si le parser est un AST je dois utiliser  

  

}
