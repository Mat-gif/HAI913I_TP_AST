package graph;

public class Noeud {

	/**
	 * Attribut
	 * 
	 * {@code private int nbAppel = 1} 
	 * nbAppel représente le nombre de fois que la méthode est appelée 
	 * dans une autre méthode 
	 */
	private String packageName;
	private String classeName;
	private String methodName;
	private int nbAppel = 1;

	/**
	 * Constructeur 
	 */
	public Noeud( String classeName, String methodName) {
		this.classeName = classeName;
		this.methodName = methodName;
	}

	/**
	 * Getter
	 */
	public String getPackageName() {
		return packageName;
	}

	public String getClasseName() {
		return classeName;
	}

	public String getMethodName() {
		return methodName;
	}

	public int getNbAppel() {
		return nbAppel;
	}

	/**
	 * Setter
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setClasseName(String classeName) {
		this.classeName = classeName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void setNbAppel(int nbAppel) {
		this.nbAppel = nbAppel;
	}

	/**
	 * ToString
	 */
	public String toStringID() {
		return  classeName + "." + methodName;
	}
	public String toString() {
		return "Noeud [classeName=" + classeName + ", methodName=" + methodName + "]";
	}

}
