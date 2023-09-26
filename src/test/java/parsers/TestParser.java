package parsers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import beforeExecution.TempDirectoryExtension;

class TestParser {
	
	@RegisterExtension
	TempDirectoryExtension tempDirectoryExtension = new TempDirectoryExtension("C:/Users/33683/Desktop/M2/S9/EvoLogiciel/TP_NOTE/HAI913I_TP_AST/src/test/resources/testParserFile");
		
	public static final String TEMP_DIRECTORY_PATH = System.getProperty("java.io.tmpdir")+ "AST_TU"+ File.separator;
	
	@Test
	void projectPathEndWithSourcePathTest() {
	
		String validProjectPath = TEMP_DIRECTORY_PATH + File.separator + "src";
		Parser<ASTParser> parser = assertDoesNotThrow(() -> new EclipseJDTParser(validProjectPath));
		assertEquals(validProjectPath, parser.getProjectPath());
	}
	
	@Test
	void projectPathNotEndWithSourcePathTest() {
		
		Parser<ASTParser> parser = assertDoesNotThrow(() -> new EclipseJDTParser(TEMP_DIRECTORY_PATH));
		assertEquals(TEMP_DIRECTORY_PATH + File.separator + "src", parser.getProjectPath());
	}

	@Test
	void projectPathNotExistForTest() {
		
		String notExistProjectPath = TEMP_DIRECTORY_PATH + File.separator + "rcs";
		assertThrows(FileNotFoundException.class, () -> new EclipseJDTParser(notExistProjectPath));
	}
	
	@Test
	void projectWithJavaFileTest() {
		
		String projectPath = TEMP_DIRECTORY_PATH + File.separator + "src";
		Parser<ASTParser> parser = assertDoesNotThrow(() -> new EclipseJDTParser(projectPath));
		try {
			List<File> listJavaProjectFiles = parser.listJavaProjectFiles();
			assertEquals(2, listJavaProjectFiles.size());
		} catch (FileNotFoundException e) {
			 fail("Echec du test avec l'exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	void projectWithoutJavaFileTest() {
		
		String projectPath = TEMP_DIRECTORY_PATH + File.separator + "srcWithoutJavaFile" + File.separator
				+ "src";
		Parser<ASTParser> parser = assertDoesNotThrow(() -> new EclipseJDTParser(projectPath));
		assertThrows(FileNotFoundException.class, () -> parser.listJavaProjectFiles());
	}
	
}
