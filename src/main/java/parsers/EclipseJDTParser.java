package parsers;

import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class EclipseJDTParser extends Parser<ASTParser>{

	public EclipseJDTParser(String projectPath, String sourcePath) {
		super(projectPath, sourcePath);
	}

	public void defaultSetterParser() {
		//parser = ASTParser
		
	}
	
	@Override
	public void configure() {
		
	}
	
	protected CompilationUnit parse(char [] classSource) {
		return null;
		
	}
}
