package parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.apache.commons.io.FileUtils;

public class EclipseJDTParser extends Parser<ASTParser> {

	public EclipseJDTParser(String projectPath) throws NullPointerException, FileNotFoundException {
		super(projectPath);
	}

	public void defaultSetterParser(int level, int kind, boolean resolveBindings, boolean bindingsRecovery,
			String encoding) {
		parserType = ASTParser.newParser(level);
		parserType.setUnitName("");
		parserType.setKind(kind);
		parserType.setCompilerOptions(JavaCore.getOptions());
		parserType.setEnvironment(new String[] { getJrePath() },
				new String[] { getProjectPath() },
				new String[] { encoding }, true);
		parserType.setResolveBindings(resolveBindings);
		parserType.setBindingsRecovery(bindingsRecovery);
	}

	//
	@Override
	public void configure() {
		defaultSetterParser(AST.JLS4, ASTParser.K_COMPILATION_UNIT, true, true, "UTF-8");
	}

	//
	public CompilationUnit parse(File file) throws IOException {
		parserType.setSource(FileUtils.readFileToString(file, Charset.defaultCharset()).toCharArray());
		return (CompilationUnit) parserType.createAST(null);
	}
  
	public List<CompilationUnit> parseProject() throws IOException, FileNotFoundException {
		
		List<CompilationUnit> cUnits = new ArrayList<>();

		for (File sourceFile : listJavaProjectFiles())
			cUnits.add(parse(sourceFile));
		// System.out.println(this.getProjectPath());
		return cUnits;
	}

}
