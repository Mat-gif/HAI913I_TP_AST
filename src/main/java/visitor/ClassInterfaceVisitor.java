package visitor;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ClassInterfaceVisitor extends ASTVisitor {
	String className = null;
	int linesOfCode = 0;
	int attributeCount = 0;
	String javaCode = "";
	private boolean isClass = false;
	private boolean isInterface = false;
	
	public boolean visit(TypeDeclaration node) {
        if(!node.isInterface()){
        	className = node.getName().getFullyQualifiedName();
        	javaCode = node.toString();
        	linesOfCode = countLinesOfCode(javaCode);
        	setClass(true);
        }
        if(node.isInterface()) {
        	javaCode = node.toString();
        	linesOfCode = countLinesOfCode(javaCode);
        	setInterface(true);
        }
		return super.visit(node);
	}
	
	public boolean visit(FieldDeclaration node) {
        // Comptez chaque déclaration de champ comme un attribut
        attributeCount++;
        return super.visit(node);
    }

    public int getAttributeCount() {
        return attributeCount;
    }
	
	public String getClassName() {
		return className;
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
    
	public String printClassName() {
		return getClassName();
	}

	public boolean getIsClass() {
		return isClass;
	}

	public void setClass(boolean isClass) {
		this.isClass = isClass;
	}

	public boolean getIsInterface() {
		return isInterface;
	}

	public void setInterface(boolean isInterface) {
		this.isInterface = isInterface;
	}

}
