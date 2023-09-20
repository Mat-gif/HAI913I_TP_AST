package visitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class MethodDeclarationVisitor extends ASTVisitor {
	List<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();
	String methodCode = "";

	public boolean visit(MethodDeclaration node) {
		methods.add(node);
		methodCode = node.toString();
		return super.visit(node);
	}
	
	public List<MethodDeclaration> getMethods() {
		return methods;
	}
	
	public void printMethodsNames( ) {
		System.out.println("NOM | TYPE RETOUR | NB LINE");
		for (MethodDeclaration method : getMethods()) {
			System.out.println(method.getName()
					+ " | " + method.getReturnType2() + " | " + countLinesOfMethod(method.toString()));
		}
	}
	
	public void printMethodCount() {
		System.out.println("- NB OF METHODS : " + getMethods().size());
	}
	
    public int countLinesOfMethod(String methodCode) {
//    	javaCode = javaCode.replaceAll( "//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/", "$1 " );
        String[] lines = methodCode.split("\r\n|\r|\n");
        int lineCount = 0;
        for (String line : lines) {
            // Ignorer les lignes vides et les commentaires
            String trimmedLine = line.trim();
            if (!trimmedLine.isEmpty() && !trimmedLine.startsWith("//")) {
                lineCount++;
            }
        }
        return lineCount;
    }

	public int getLinesOfMethod() {
		return countLinesOfMethod(methodCode);
	}

}
