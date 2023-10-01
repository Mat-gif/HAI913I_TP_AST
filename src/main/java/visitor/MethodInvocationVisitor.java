package visitor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;

public class MethodInvocationVisitor extends ASTVisitor {
	List<MethodInvocation> methods = new ArrayList<MethodInvocation>();
	List<SuperMethodInvocation> superMethods = new ArrayList<SuperMethodInvocation>();
	public boolean visit(MethodInvocation node) {
		methods.add(node);
		return super.visit(node);
	}
	
	@Override
	public boolean visit(SuperMethodInvocation node) {
		superMethods.add(node);
		return super.visit(node);
	}

	
	public List<MethodInvocation> getMethods() {
		return methods;
	}
	
	public List<SuperMethodInvocation> getSuperMethod() {
		return superMethods;
	}
	
	public Method getMethodFromInvocation(MethodInvocation methodInvocation) {
        try {
            // Utilisation de la réflexion pour accéder à la méthode "getMethod" dans MethodInvocation
            java.lang.reflect.Method getMethodMethod = methodInvocation.getClass().getMethod("getMethod");
            
            // Appel de la méthode "getMethod" pour obtenir l'objet Method
            return (Method) getMethodMethod.invoke(methodInvocation);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
