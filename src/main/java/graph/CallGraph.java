package graph;

import processor.MyProcessor;
import parsers.EclipseJDTParser;


public class CallGraph extends MyProcessor{

	public CallGraph(String path) {
		super(path);
	}
	
	@Override
	public void setParser(String projectPath) {
		parser = new EclipseJDTParser(projectPath);
	}
	
	public void setParser(EclipseJDTParser parser) {
		this.parser = parser;
	}

}
