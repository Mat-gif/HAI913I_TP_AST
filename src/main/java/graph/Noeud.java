package graph;

public class Noeud {
	
	private String packageName;
	private String classeName;
	private String methodName;
	
	public Noeud(String packageName, String classeName, String methodName) {
		this.packageName = packageName;
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
		return packageName + "." + classeName + "." + methodName;
	}
	

	
}
