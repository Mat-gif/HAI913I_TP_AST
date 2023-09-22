package visitor;


import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.EnumDeclaration;

public class EnumVisitor extends ASTVisitor {
	int linesOfCode = 0;
	String javaCode = "";
	String enumName = null;
	
	public boolean visit(EnumDeclaration node) {
    	javaCode = node.toString();
    	linesOfCode = countLinesOfCode(javaCode);
    	enumName = node.getName().toString();
		return super.visit(node);
	}
	
    public String getEnumName() {
		return enumName;
	}

	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}

	public void setLinesOfCode(int linesOfCode) {
		this.linesOfCode = linesOfCode;
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
