package visitor;


import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class JavaFileVisitor extends ASTVisitor {
	int linesOfCode = 0;
	String javaCode = "";
	boolean isClass = false;
	
	public boolean visit(TypeDeclaration node) {
    	javaCode = node.toString();
    	linesOfCode = countLinesOfCode(javaCode);
        if(!node.isInterface()){
        	isClass = true;
        }
		return super.visit(node);
	}
	
    public int getLinesOfCode() {
        return linesOfCode;
    }	
	
    public int countLinesOfCode(String javaCode) {
//    	javaCode = javaCode.replaceAll( "//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/", "$1 " );
        String[] lines = javaCode.split("\r\n|\r|\n");
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
}
