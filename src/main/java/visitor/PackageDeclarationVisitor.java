package visitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.PackageDeclaration;

public class PackageDeclarationVisitor extends ASTVisitor {
	String packageName = "";
	
	
	public boolean visit(PackageDeclaration node) {
        // Extraire le nom du package
		packageName = node.getName().getFullyQualifiedName();
		return super.visit(node);
	}
	
	public String getPackageName() {
		return packageName;
	}

	
	public void printPackageName() {
		System.out.println("\n\nNOM DU PACKAGE : " + getPackageName() + "\n");
	}
	
}	
