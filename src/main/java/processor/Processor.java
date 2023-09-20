package processor;

import java.io.IOException;

public abstract class Processor<T> {

    private T parser;
    private String projectPath ;
    private String sourcePath ;

    // constructeur de la classe Processor
    public Processor(T parser, String projectPath) {
        this.parser = parser;
        this.projectPath = projectPath;
    }

    // getters et setters
    public String getProjectPath() {
        return projectPath;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public T getParser() {
        return parser;
    }

    public void setParser(T parser) {
        this.parser = parser;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    // methodes
    public abstract void process() throws IOException;

}
