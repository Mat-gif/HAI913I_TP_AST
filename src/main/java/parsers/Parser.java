package parsers;

import java.io.File;
import java.util.ArrayList;

//import java.io.File;
//import java.util.Scanner;

public abstract class Parser<T> {

	//Attributes 
	protected String projectPath;
	protected String sourcePath;
	protected String jrePath;
	protected T parserType; 


	/*Constructor 
	 * 
	 * Appelle des setters dans le Constructeur afin de respecter 
	 * les principes solides tout en gérant les exceptions de ces derniers. 
	 * 
	 * */

	public Parser(String projectPath, String sourcePath) {
		this.setProjectPath(projectPath);
		this.setSourcePath(sourcePath);
		this.setJrePath();
		this.configure();
	}


	public String getProjectPath() {
		return projectPath;
	}


	public String getSourcePath() {
		return sourcePath;
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
	public void setProjectPath(String sourcePath) {
		/*Scanner inputScanner = new Scanner(System.in); 

		do {
			System.out.print("Enter a valid project Path directory: ");
			String sourcePath = inputScanner.nextLine();

			if (verifyIfDirectoryExist(sourcePath)) { 
		*/
				this.sourcePath = sourcePath;
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
	public void setSourcePath(String sourcePath) {
		
		/* Scanner inputScanner = new Scanner(System.in); 

		do {
			System.out.print("Enter a valid source directory: ");
			String sourcePath = inputScanner.nextLine();
			//Appel de la focntion getProjectPath pour respecter les principes solid
			// Si question de performances préférer appel sur this
			 
			if (verifyIfDirectoryExist(getProjectPath()+sourcePath)) {*/
				this.sourcePath = sourcePath;
				
				
			/*	break; // Exit the loop when a valid directory is entered
			} else {
				System.out.println("Please enter an existing source directory.");
			}
		} while (true);

		inputScanner.close();
		*/
		 
	}

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
			System.err.println("Warning the system variable 'java.home' "
					+ "variable is not set correctly or is null");
		}
	} 

	/**
	 * Vérifie si un répertoire ou fichier existe à l'emplacement spécifié.
	 *
	 * @param path Le chemin d'accès au répertoire ou fichier à vérifier.
	 * @return {@code true} si le répertoire ou fichier existe, sinon {@code false}.
	 *         Un {@link NullPointerException} peut être levé si le chemin est nul.
	 * */
	/*private boolean verifyIfDirectoryExist(String path) {
		try {
			File directory = new File(path);
			return directory.exists() && directory.isDirectory();
		} catch (NullPointerException e) {
			System.err.println("Null Pointer Exception, le fichier n'existe pas");
			return false; 
		}

	}*/
	
	
	// read all java files from specific folder
	public static ArrayList<File> listJavaFilesForFolder(final File folder) {
		ArrayList<File> javaFiles = new ArrayList<File>();
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				javaFiles.addAll(listJavaFilesForFolder(fileEntry));
			} else if (fileEntry.getName().contains(".java")) {
				// System.out.println(fileEntry.getName());
				javaFiles.add(fileEntry);
			}
		}

		return javaFiles;
	}

	
	public abstract void configure(); 

}
