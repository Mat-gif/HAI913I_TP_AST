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

	

	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getClasseName() {
		return classeName;
	}
	public void setClasseName(String classeName) {
		this.classeName = classeName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public int getNbAppel() {
		return nbAppel;
	}
	public void setNbAppel(int nbAppel) {
		this.nbAppel = nbAppel;
	}
	
	public void ajoutAppel() {
		this.nbAppel+= 1;
	}
	
	public String toStringID() {
		return  classeName + "." + methodName;
	}
	@Override

	public String toString() {
		return "Noeud [classeName=" + classeName + ", methodName=" + methodName + "]";
	}


}
