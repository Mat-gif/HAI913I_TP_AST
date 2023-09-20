package processor;

public abstract class Processor<T> {

    private T parser;
    private String projectPath = "";
    private String sourcePath = "";

    public String getProjectPath() {
        return projectPath;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public Processor(T parser) {
        this.parser = parser;
    }

    public T getParser() {
        return parser;
    }

    public abstract void process();

   

}
