package processor;

import java.io.IOException;

public abstract class Processor<T> {

    protected T parser;
   // private String projectPath;
    //private String sourcePath;

    // constructeur de la classe Processor
    public Processor( String projectPath) {
    	this.setParser(projectPath);
    }

    // constructeur de la classe Processor
    public Processor(T parser) {
        this.parser = parser;

    }


    public T getParser() {
        return parser;
    }

    public abstract void setParser(String projectPath);




    // methodes
    public abstract void process() throws IOException;

}
