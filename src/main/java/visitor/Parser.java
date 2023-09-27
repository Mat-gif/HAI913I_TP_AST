package visitor;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import graph.Graphe;
import graph.Noeud;
import graph.PetitArbre;


public class Parser {
	
//	public static final String projectPath = "C:\\Users\\manil\\Desktop\\Master_ico\\Master__2\\HAI913I - Evolution et restructuration des logiciels\\Dev\\org.anonbnr.design_patterns";
	public static final String projectPath = "/home/mathieu/Téléchargements/promotions";
	public static final String projectSourcePath = projectPath + "/src";
	public static final String jrePath = System.getProperty("java.home");
	public static int classCount = 0;
	public static int appLineCount = 0;
	public static int appMethodCount = 0;
	
	private static Graphe myGraph = new Graphe();
	
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
		//System.out.println(myGraph.toString());
		
		System.out.println(myGraph.getGrapheNonTrie().get("promotions.Etudiant.statut"));

	
		
	      SwingUtilities.invokeLater(() -> {
	            JFrame frame = new JFrame("AST Graph Viewer");
	            frame.setBounds(100, 100, 1000, 800);
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	            mxGraph graph = new mxGraph();
				Object parent = graph.getDefaultParent();
          

				graph.getModel().beginUpdate();

				try {
					Object v1 = graph.insertVertex(parent, null, myGraph.getGrapheNonTrie().get("promotions.Etudiant.statut").getParent().toStringID(), 20, 20, 80, 30);
					myGraph.getGrapheNonTrie().get("promotions.Etudiant.statut").getEnfants().forEach(e->{
						
						 Object v2 = graph.insertVertex(parent, null, e.toStringID(), 20, 20, 80, 30);
				         

				           
				                graph.insertEdge(parent, null, "", v1, v2);
				           
						
					});

				  


				           

				       
				   
				} finally {
				    graph.getModel().endUpdate();
				}

				mxGraphComponent graphComponent = new mxGraphComponent(graph);
				graphComponent.setPreferredSize(new Dimension(500, 500)); // Modifiez la taille selon vos besoins

				frame.getContentPane().add(graphComponent); // Ajoutez le composant au contenu de la fenêtre

	            frame.pack(); // Ajustez la taille de la fenêtre pour contenir le composant
	            frame.setVisible(true);
	        });
		
		
		
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
			System.out.println(visitor.printClassName() +" | "
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
        
        
        ClassInterfaceVisitor visitor = new ClassInterfaceVisitor();
		parse.accept(visitor);
		PackageDeclarationVisitor vis = new PackageDeclarationVisitor();
		parse.accept(vis);
		
		
        
        
        for (MethodDeclaration method : visitor1.getMethods()) {
            MethodInvocationVisitor visitor2 = new MethodInvocationVisitor();
            method.accept(visitor2);
            
            //ystem.out.println(vis.getPackageName()+"."+visitor.printClassName()+"--------- method " + method.getName());
            
            PetitArbre arbre = new PetitArbre(new Noeud(vis.getPackageName()+"."+visitor.printClassName(), method.getName().getFullyQualifiedName()));

            
            
            for (MethodInvocation methodInvocation : visitor2.getMethods()) {
            	
            	if(!getDeclaringClassName(methodInvocation).contains("UnknownClass")) {
            		//System.out.println("--- invoc method "+ methodInvocation.getName() + " de la classe " + getDeclaringClassName(methodInvocation));}
            		
            		arbre.addEnfant(new Noeud(getDeclaringClassName(methodInvocation), methodInvocation.getName().getFullyQualifiedName()));
            		
            		
            	}
            	
            	
            }
            
            
            
            myGraph.checkMainOrSommet(arbre);
            	
        }
        


    }
    
    private static String getDeclaringClassName(MethodInvocation methodInvocation) {
        IMethodBinding methodBinding = methodInvocation.resolveMethodBinding();
        if (methodBinding != null) {
            ITypeBinding typeBinding = methodBinding.getDeclaringClass();
            if (typeBinding != null) {
                return typeBinding.getQualifiedName();
            }
        }
        return "UnknownClass";
    }
}
