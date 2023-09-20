package parsers;



import java.io.File;
import java.util.ArrayList;
import java.util.List;

//import java.io.File;
//import java.util.Scanner;

public abstract class Parser<T> {

	//Attributes 
	private static final String sourcePathForJava = "/src";
	protected String projectPath;
	protected String jrePath;
	protected T parserType; 


	/*Constructor 
	 * 
	 * Appelle des setters dans le Constructeur afin de respecter 
	 * les principes solides tout en gérant les exceptions de ces derniers. 
	 * 
	 * */

	public Parser(String projectPath) {
		this.setProjectPath(projectPath);
		this.setJrePath();
		this.configure();
	}


	public String getProjectPath() {
		return projectPath;
	}

	public String getJrePath() {
		return jrePath;
	}


	/**
	 * Définit le chemin d'accès au répertoire du projet manuellement à l'aide d'un objet
	 * scanner. 
	 * Vérification de la validité du chemin à l'aide de {@link #verifyIfDirectoryExist(String)}.
	 * Si oui il est assignée à la variable
	 * Si non l'utilisateur doit à nouveau saisir le répertoire existant
	 * 
	 * */
	public void setProjectPath(String projectPath) {
		/*Scanner inputScanner = new Scanner(System.in); 

		do {
			System.out.print("Enter a valid project Path directory: ");
			String sourcePath = inputScanner.nextLine();

			if (verifyIfDirectoryExist(sourcePath)) { 
		*/
				this.projectPath = projectPath;
		/*
				break; // Exit the loop when a valid directory is entered
			} else {
				System.out.println("Please enter an existing project Path directory.");
			}
		} while (true);

		inputScanner.close();
		 */
	}

	/**
	 * Définit le chemin d'accès source (dernier path) du projet manuellement 
	 * à l'aide d'un objet scanner. 
	 * Vérification de la validité du chemin à l'aide de {@link #verifyIfDirectoryExist(String)}.
	 * en concaténant avec la {@value projectPath}, si le path exist il est assignée. 
	 * à la variable. si non l'utilisateur doit à nouveau saisir le répertoire existant.
	 * 
	 * */


	/** 
	 * Initialise le path de la Java Runtime Environment (JRE) 
	 * en fonction du java.home du système utilisateur  * 
	 * 
	 * Si la propriété existe sur le système on l'assigne sinon 
	 * un message d'erreur est print.
	 * 
	 * */
	public void setJrePath() {
		String jrePath = System.getProperty("java.home");

		if (jrePath != null) {
			this.jrePath = jrePath;
		}else {
			System.err.println("Attention la variable system 'java.home' "
					+ "n'est pas correctement initialisée sur votre system ou est null");
		}
	} 
	
	public List<File> listJavaFiles(String filePath){
		File folder = new File(filePath);
		List<File> javaFiles = new ArrayList<>();
		String fileName = "";
		
		for (File file: folder.listFiles()) {
			fileName = file.getName();
			
			if (file.isDirectory())
				javaFiles.addAll(listJavaFiles(file.getAbsolutePath()));
			else if (fileName.endsWith(".java"))
				javaFiles.add(file);
		}
		
		return javaFiles;
	}
	
	public List<File> listJavaProjectFiles(){
		return listJavaFiles(getProjectPath());
	}

	/**
	 * Vérifie si un répertoire ou fichier existe à l'emplacement spécifié.
	 *
	 * @param path Le chemin d'accès au répertoire ou fichier à vérifier.
	 * @return {@code true} si le répertoire ou fichier existe, sinon {@code false}.
	 *         Un {@link NullPointerException} peut être levé si le chemin est nul.
	 * */
	protected boolean verifyIfDirectoryExist(String path) {
		try {
			File directory = new File(path);
			return directory.exists() && directory.isDirectory();
		} catch (NullPointerException e) {
			System.err.println("Null Pointer Exception, le dossier  n'existe pas");
			return false; 
		}

	}

	/**
	 * 
	 * Verify if the selected Path Have SRC if it has one you can get it 
	 * otherwise it return null
	 * 
	 * @return the path
	 */
	private String verifyIfThePathHaveSrc() {
		if(this.getProjectPath().endsWith(sourcePathForJava)) {
			return getProjectPath(); 
		}else {
			if (verifyIfDirectoryExist(getProjectPath()+sourcePathForJava) == true) {
				this.setProjectPath(getProjectPath()+sourcePathForJava);
				return getProjectPath(); 
			}
		}
		throw new NullPointerException("Aucun path finissant par src n'a été trouvé.");
	}
	
	public abstract void configure(); 

}
