package parsers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import beforeExecution.TempDirectoryExtension;

class TestEclipseJDTParser {
	
	@RegisterExtension
	TempDirectoryExtension tempDirectoryExtension = new TempDirectoryExtension("C:/Users/33683/Desktop/M2/S9/EvoLogiciel/TP_NOTE/HAI913I_TP_AST/src/test/resources/testParserFile");

	private static final String TEMP_DIRECTORY_PATH = System.getProperty("java.io.tmpdir")+ "AST_TU";
	private static final String SRC_TEMP_DIRECT_PATH = TEMP_DIRECTORY_PATH + File.separator + "src";
	
    
	
	@Test
    void testConfigure() throws NullPointerException, FileNotFoundException {
		Parser <ASTParser> parser = new EclipseJDTParser(SRC_TEMP_DIRECT_PATH);
        // Appelez la méthode configure
        parser.configure();
        //Récuper l'ASTParser
        ASTParser parserAST =  parser.parserType;
        // Utilisez la réflexion pour accéder à l'objet ASTParser interne


        assertEquals(AST.JLS4, (Integer)getPrivateField(parserAST, "apiLevel"));
        assertEquals(ASTParser.K_COMPILATION_UNIT, (Integer)getPrivateField(parserAST, "astKind"));
        
        //Test du compiler
        Parser <ASTParser> compilerGoodParamParser = new EclipseJDTParser(SRC_TEMP_DIRECT_PATH);
        compilerGoodParamParser.parserType.setCompilerOptions(JavaCore.getOptions());
        HashMap<String, String> compilerTest = getPrivateField(compilerGoodParamParser.parserType, "compilerOptions");
        
        HashMap<String, String> compilerToTest = getPrivateField(parserAST, "compilerOptions");
        assertEquals(compilerTest, compilerToTest);
        
        
        //assertEquals(new String[], (String[])getPrivateField(parserAST, "classpaths"));
        assertEquals(0x31, (Integer)getPrivateField(parserAST, "bits"));
        
        String[] jrepathArray = getPrivateField(parserAST, "classpaths"); 
        for (String encoding : jrepathArray) {
        	assertEquals(System.getProperty("java.home"), encoding);
        }
        //assertEquals(new String[] {System.getProperty("java.home")}, getPrivateField(parserAST, "classpaths"));
       
        String[] sourcepathsArray = getPrivateField(parserAST, "sourcepaths");
        for (String encoding : sourcepathsArray) {
        	assertEquals(SRC_TEMP_DIRECT_PATH, encoding);
        }
        
        String[] sourcepathsEncodingsArray = getPrivateField(parserAST, "sourcepathsEncodings");
        for (String encoding : sourcepathsEncodingsArray) {
            assertEquals("UTF-8", encoding);
        }
    }
	
    @Test
    void testParsingProject() {
    	
		EclipseJDTParser parser;
		try {
			parser = new EclipseJDTParser(SRC_TEMP_DIRECT_PATH);
			parser.configure();
			String contentAJava = FileUtils.readFileToString(new File(SRC_TEMP_DIRECT_PATH + File.separator +"A.java"),Charset.defaultCharset());
			String contentBJava = FileUtils.readFileToString(new File(SRC_TEMP_DIRECT_PATH + File.separator +"B.java"),Charset.defaultCharset());
			
			try {
				List<CompilationUnit> cu = parser.parseProject();
				assertEquals(2, cu.size());
				String cuContent0 = cu.get(0).toString();
				String cuContent1 = cu.get(1).toString();
				// Supprimez les espaces inutiles et normalisez le formatage
				contentAJava = contentAJava.replaceAll("\\s+", "");
				cuContent0 = cuContent0.replaceAll("\\s+", "");
				assertEquals(contentAJava, cuContent0);
				contentBJava = contentBJava.replaceAll("\\s+", "");
				cuContent1 = cuContent1.replaceAll("\\s+", "");
				assertEquals(contentAJava, cuContent0);

			} catch (IOException e) {
				fail("Une erreur est survenue lors du parsing du Projet \n" + e.getMessage());
				e.printStackTrace();
			}
			
		} catch (NullPointerException | IOException e) {
			if (e instanceof IOException) {
				e.printStackTrace();
			}else {
				fail("Une exception est survenue lors de l'initialisation de l'AST : \n" + e.getMessage());
				e.printStackTrace();
			}
		} 
    	
    }

    
    @Test
    void testInvalidFile() throws NullPointerException, FileNotFoundException {
    	File fileNull = null;
    	File Directory = new File(TEMP_DIRECTORY_PATH);
    	File NotEndWithJava = new File(TEMP_DIRECTORY_PATH+File.separator+"srcWithoutJavaFile"+
    	File.separator+"src"+File.separator+".gitkeep");
    	EclipseJDTParser parser = new EclipseJDTParser(SRC_TEMP_DIRECT_PATH);
    	
    	assertFalse(parser.isValidJavaFile(fileNull));
    	assertFalse(parser.isValidJavaFile(Directory));
    	assertFalse(parser.isValidJavaFile(NotEndWithJava)); 
    }
    

    @Test
    void testValidFile() throws NullPointerException, FileNotFoundException {
    	File javaFile = new File(TEMP_DIRECTORY_PATH+File.separator+"src"+
    	    	File.separator+"A.java");
    	EclipseJDTParser parser = new EclipseJDTParser(SRC_TEMP_DIRECT_PATH);
    	assertTrue(parser.isValidJavaFile(javaFile));
    }
    



    // Méthode d'aide pour accéder aux champs privés via la réflexion
    private <T> T getPrivateField(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(obj);
        } catch (Exception e) {
            throw new RuntimeException("Impossible d'accéder au champ privé : " + fieldName, e);
        }
    }
	
}
