package parsers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
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
        ASTParser parsert =  parser.parserType;
        // Utilisez la réflexion pour accéder à l'objet ASTParser interne
        Integer astParser = getPrivateField(parsert, "apiLevel");
        assertEquals(AST.JLS4, astParser);
        System.out.println(astParser);
        // Vérifiez la configuration
        /* Todo */
        // Ajoutez d'autres assertions pour d'autres propriétés si nécessaire
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
