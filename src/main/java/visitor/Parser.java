package visitor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.internal.utils.FileUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class Parser {
	
//	public static final String projectPath = "C:\\Users\\manil\\Desktop\\Master_ico\\Master__2\\HAI913I - Evolution et restructuration des logiciels\\Dev\\org.anonbnr.design_patterns";
	public static final String projectPath = "/home/mathieu/Documents/Projet/HAI913I_TP_AST";
	public static final String projectSourcePath = projectPath + "/src";
	public static final String jrePath = System.getProperty("java.home");
	public static int classCount = 0;
	public static int appLineCount = 0;
	public static int appMethodCount = 0;
	
	public static void main(String[] args) throws IOException {

		// read java files
		final File folder = new File(projectSourcePath);
		ArrayList<File> javaFiles = listJavaFilesForFolder(folder);
		String previousPackageName = "";

		
		for (File fileEntry : javaFiles) {
			String content = FileUtils.readFileToString(fileEntry);
			// System.out.println(content);
//			System.out.println(content);

			CompilationUnit parse = parse(content.toCharArray());
			//String currentPackageName = parse.getPackage().getName().getFullyQualifiedName();
			
						
			// print package info
			/*if (!currentPackageName.equals(previousPackageName)) {
				printPackageInfo(parse);
				previousPackageName = currentPackageName;
				System.out.println("Classes :\n");
			}*/
			
			// print class info
			//printClassInfo(parse);
			// print class & interface info
			//printClassInterfaceInfo(parse);
			
			// print enum info
			//printEnumInfo(parse);
			
			// print methods info
			//printMethodInfo(parse);

			// print variables info
			//printVariableInfo(parse);
			
			//print method invocations
			printMethodInvocationInfo(parse);
						
			System.out.println("\n");
			
			
		}
		
		/*System.out.println("nb de ligne de l'app : " + appLineCount);
		System.out.println("nb de classe de l'app : " + classCount);
		System.out.println("nb de methode de l'app : " + appMethodCount);
	*/
	}

	// read all java files from specific folder
	public static ArrayList<File> listJavaFilesForFolder(final File folder) {
		ArrayList<File> javaFiles = new ArrayList<File>();
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				javaFiles.addAll(listJavaFilesForFolder(fileEntry));
			} else if (fileEntry.getName().contains(".java")) {
				// System.out.println(fileEntry.getName());
				javaFiles.add(fileEntry);
				
			}
		}

		return javaFiles;
	}

	// create AST
	private static CompilationUnit parse(char[] classSource) {
		ASTParser parser = ASTParser.newParser(AST.JLS4); // java +1.6
		parser.setResolveBindings(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		
		
 
		parser.setBindingsRecovery(true);
 
		Map options = JavaCore.getOptions();
		JavaCore.setComplianceOptions(JavaCore.VERSION_1_7, options);
		parser.setCompilerOptions(options);
 
		parser.setUnitName("");
 
		String[] sources = { projectSourcePath }; 
		String[] classpath = {jrePath};
 
		parser.setEnvironment(classpath, sources, new String[] { "UTF-8"}, true);
		parser.setSource(classSource);
		
		return (CompilationUnit) parser.createAST(null); // create and parse
	}
	
	// package information
	public static void printPackageInfo(CompilationUnit parse) {
		PackageDeclarationVisitor visitor = new PackageDeclarationVisitor();
		parse.accept(visitor);
		
		visitor.printPackageName();
	}
	
	// class information
	public static void printClassInfo(CompilationUnit parse) {
		ClassInterfaceVisitor visitor = new ClassInterfaceVisitor();
	};
	// class & interface information
	public static void printClassInterfaceInfo(CompilationUnit parse) {
		ClassInterfaceVisitor visitor = new ClassInterfaceVisitor();
		parse.accept(visitor);
				
		if (visitor.isClass) {
			System.out.println("NOM | line count | attr count");
			System.out.println(visitor.getClassName() +" | "
					+ visitor.getLinesOfCode() + " | "
					+ visitor.getAttributeCount()
					+ "\n");
			System.out.println("nom : " + visitor.getClassName());
			System.out.println("line count : " + visitor.getLinesOfCode());
			System.out.println("attr count  : " + visitor.getAttributeCount());
			System.out.println("\n");
			classCount++;
		}
		if (visitor.isInterface) {
			System.out.println("INTERFACE : " + visitor.getClassName());
			System.out.println("line count : " + visitor.getLinesOfCode());
//			System.out.println("code : " + visitor.javaCode);
			System.out.println("\n");
		}
		
		appLineCount += visitor.linesOfCode;
		
	}
	
	// enum information
	public static void printEnumInfo(CompilationUnit parse) {
		EnumVisitor visitor = new EnumVisitor();
		parse.accept(visitor);
		
		if(visitor.enumName!=null){
			System.out.println("ENUMERATION : " + visitor.getEnumName());
			System.out.println("line count : " + visitor.getLinesOfCode());
			System.out.println("\n");
		}
	}
	
	// navigate method information
	public static void printMethodInfo(CompilationUnit parse) {
		MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
		parse.accept(visitor);
		
		visitor.printMethodCount();
		visitor.printMethodsNames();
		
		appMethodCount += visitor.getMethods().size();
	}


	// navigate variables inside method
	public static void printVariableInfo(CompilationUnit parse) {

		MethodDeclarationVisitor visitor1 = new MethodDeclarationVisitor();
		parse.accept(visitor1);
		for (MethodDeclaration method : visitor1.getMethods()) {

			VariableDeclarationFragmentVisitor visitor2 = new VariableDeclarationFragmentVisitor();
			method.accept(visitor2);

			for (VariableDeclarationFragment variableDeclarationFragment : visitor2
					.getVariables()) {
				System.out.println("variable name: "
						+ variableDeclarationFragment.getName()
						+ " variable Initializer: "
						+ variableDeclarationFragment.getInitializer()
						+ "\n");
			}

		}
	}
	
	// navigate method invocations inside method
		public static void printMethodInvocationInfo(CompilationUnit parse) {

			MethodDeclarationVisitor visitor1 = new MethodDeclarationVisitor();
			 parse.accept(visitor1);
			for (MethodDeclaration method : visitor1.getMethods()) {
				
				System.err.println("----------------"+ method.getName());
				MethodInvocationVisitor visitor2 = new MethodInvocationVisitor();
				method.accept(visitor2);

				for (MethodInvocation methodInvocation : visitor2.getMethods()) {
					
					System.out.println(method.getName()+"---"+method);
					
					 /*IMethodBinding methodBinding = methodInvocation.resolveMethodBinding();
				        if (methodBinding != null) {
				            ITypeBinding declaringClass = methodBinding.getDeclaringClass();
				            String className = declaringClass.getQualifiedName();
				            String packageName = declaringClass.getPackage().getName();
				            String methodName = methodBinding.getName();
				            
				           
				           // System.out.println("Package: " + packageName);
				            System.out.println("Class: " + className);
				            System.out.println("Method: " + methodName);
				        }*/
					
					
					
				}
			}

		}
}
