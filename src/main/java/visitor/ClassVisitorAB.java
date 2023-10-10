package visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ClassVisitorAB extends ASTVisitor {

    private String A;
    private String B;

    private boolean isClass = false;
    
    
    public ClassVisitorAB(String A, String B) {
        this.A = A;
        this.B = B;
    }

    public boolean visit(TypeDeclaration node) {
        if (!node.isInterface()) {
            A = node.getName().getFullyQualifiedName();
            B = node.getName().getFullyQualifiedName();
        }
        return super.visit(node);
    }

    public String getA() {
        return A;
    }

    public String getB() {
        return B;
    }

    public boolean isClass() {
        return isClass;
    }
}
