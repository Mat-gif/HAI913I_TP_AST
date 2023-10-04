package beforeExecution;

import java.io.File;
import java.io.IOException;
import java.lang.System.Logger.Level;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TempDirectoryExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback{
	
	private static final System.Logger logger = System.getLogger(TempDirectoryExtension.class.getName());
	private String sourcePath;
	
	public TempDirectoryExtension(String path) {
		setPath(path);
	}
	public void setPath (String path) {this.sourcePath = path;}
	
	/** Répertoire de travail pour les tests*/
	public static final String TEST_WORK_DIR = System.getProperty("java.io.tmpdir")
			+ File.separator + "AST_TU"; 
	
	@Override
	public void beforeTestExecution(ExtensionContext arg0) throws Exception {
		File file = new File(TEST_WORK_DIR);
		if (!file.exists()) {
			file.mkdir();
			//logger.log(Level.INFO, sourcePath);
			//copy all the file of a destinaton
			copyFiles(new File(sourcePath), file);
		}
		
	}

	@Override
	public void afterTestExecution(ExtensionContext arg0) throws Exception {
		File file = new File(TEST_WORK_DIR);
		if (file.exists()) {
			logger.log(Level.INFO, "Nettoyage du répertoire temporaire des TU");
			deleteFiles(file);
		}
		
	}
	
	private void copyFiles(File source, File destination) throws IOException {
        if (source.isDirectory()) {
        	//System.out.println(source);
            if (!destination.exists()) {
            	//logger.log(Level.INFO, "Création du répertoire : " + destination);
                destination.mkdir();
            }
            String[] files = source.list();
            for (String file : files) {
                File srcFile = new File(source, file);
                File destFile = new File(destination, file);
                copyFiles(srcFile, destFile);
                //logger.log(Level.INFO, "Création du fichier : " + destFile.getAbsolutePath());
            }
        } else {
            Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
	}
	
    private void deleteFiles(File file) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    deleteFiles(f);
                }
            }
        }
        Files.deleteIfExists(file.toPath());
    }

}
