package parsers;



import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Exception.JRENotFoundException;

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

	public Parser(String projectPath) throws JRENotFoundException {
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
		if(this.getProjectPath().endsWith(sourcePathForJava)) {
			this.projectPath = projectPath; 
		}else if (verifyIfDirectoryExist(getProjectPath()+sourcePathForJava) == true) {
			this.setProjectPath(getProjectPath()+sourcePathForJava);
		}else {
			throw new NullPointerException("NullPointerException, le dossier ne contient pas de fichier source");
		}
	}

	/** 
	 * Initialise le path de la Java Runtime Environment (JRE) 
	 * en fonction du java.home du système utilisateur  * 
	 * 
	 * Si la propriété existe sur le système on assigne le path
	 * Sinon * @throws JRENotFoundException 
	 * 
	 * */
	public void setJrePath() throws JRENotFoundException {
		String jrePath = System.getProperty("java.home");

		if (jrePath != null) {
			this.jrePath = jrePath;
		}else {
			throw new JRENotFoundException();
		}
	} 

	public List<File> listJavaFiles(String filePath){
		File folder = new File(filePath);
		List<File> javaFiles = new ArrayList<>();

		for (File file: folder.listFiles()) {
			if (file.isDirectory())
				javaFiles.addAll(listJavaFiles(file.getAbsolutePath()));
			else if (file.getName().endsWith(".java"))
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
			//System.err.println("Null Pointer Exception, le dossier  n'existe pas");
			return false; 
		}

	}

	public abstract void configure(); 

}
