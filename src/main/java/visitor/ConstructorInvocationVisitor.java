package visitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;

public class ConstructorInvocationVisitor extends ASTVisitor {
	List<ClassInstanceCreation> constructors = new ArrayList<ClassInstanceCreation>();
	List<SuperConstructorInvocation> superConstructors = new ArrayList<SuperConstructorInvocation>();
	
	
	
	public boolean visit(ClassInstanceCreation node) {
		
		constructors.add(node);
		//System.err.println(node.getType());
		//node..forEach(e->System.err.println(e));
		
		
		return super.visit(node);
	}
	
	@Override
	public boolean visit(SuperConstructorInvocation node) {
		superConstructors.add(node);
		return super.visit(node);
	}

	
	public List<ClassInstanceCreation> getMethods() {
		return constructors;
	}
	
	public List<SuperConstructorInvocation> getSuperMethod() {
		return superConstructors;
	}
	
	
	

	
	
}
