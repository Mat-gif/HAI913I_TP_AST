package graph;

public class Noeud {
	
	private String packageName;
	private String classeName;
	private String methodName;

	
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

	
	public String toStringID() {
		return  classeName + "." + methodName;
	}
	@Override
	public String toString() {
		return "Noeud [classeName=" + classeName + ", methodName=" + methodName + "]";
	}
	

	
}
