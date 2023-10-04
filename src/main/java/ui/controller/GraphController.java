package ui.controller;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxGraph;

import graph.Graphe;
import graph.Noeud;
import graph.PetitArbre;
import parsers.EclipseJDTParser;
import visitor.ClassInterfaceVisitor;
import visitor.ConstructorInvocationVisitor;
import visitor.EnumVisitor;
import visitor.ImportDeclarationVisitor;
import visitor.MethodDeclarationVisitor;
import visitor.MethodInvocationVisitor;
import visitor.PackageDeclarationVisitor;
import visitor.VariableDeclarationFragmentVisitor;

public class GraphController {


	public static EclipseJDTParser parserEclipse;
	public int classCount = 0;
	public int appLineCount = 0;
	public int appMethodCount = 0;

	private static Map<String,Object> myCells = new HashMap<>();
	private static Map<String,Object> myArcs = new HashMap<>();
	private static Graphe myGraph = new Graphe();

	public  void GraphPanel(String path) throws IOException {

		parserEclipse = new EclipseJDTParser(path);

		List<File> javaFiles = parserEclipse.listJavaProjectFiles();
		for (File content : javaFiles) {
			parserEclipse.configure();
			CompilationUnit parse = parserEclipse.parse(content);
			printMethodInvocationInfo(parse);
		}



		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("AST Graph Viewer");
			frame.setBounds(100, 100, 1000, 800);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			mxGraph graph = new mxGraph();
			graph.setCellsEditable(false); // Désactiver l'édition
			graph.setCellsMovable(false); // Désactiver le déplacement
			graph.setCellsResizable(false); // Désactiver le redimensionnement
			graph.setDropEnabled(false); // Désactiver le glisser-déposer
			graph.setSplitEnabled(false); // Désactiver la divisio
			Object parent = graph.getDefaultParent();

			graph.getModel().beginUpdate();

			try {

				for(PetitArbre pa : myGraph.getGrapheNonTrie().values()) {
					
					Object v1 = null;
					if(!pa.getEnfants().isEmpty()) {
						if (!myCells.containsKey(pa.getParent().toStringID())){
							v1 = graph.insertVertex(parent, null, pa.getParent().getMethodName(), 20, 20, 80, 30);
							myCells.put(pa.getParent().toStringID(), v1);
						} else {
							v1 = myCells.get(pa.getParent().toStringID());
						}
						myRec(pa.getEnfants(), myGraph.getGrapheNonTrie(), graph, parent, v1, pa.getParent().toStringID());
					}
				};

				// Utilisez l'algorithme hierarchique pour organiser les vertex
				mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
				layout.execute(parent);

			} finally {
				graph.getModel().endUpdate();
			}

			mxGraphComponent graphComponent = new mxGraphComponent(graph);
			graphComponent.setPreferredSize(new Dimension(500, 500)); // Modifiez la taille selon vos besoins

			frame.getContentPane().add(graphComponent); // Ajoutez le composant au contenu de la fenêtre

			frame.pack(); // Ajustez la taille de la fenêtre pour contenir le composant
			frame.setVisible(true);
		});

		/*
		 * System.out.println("nb de ligne de l'app : " + appLineCount);
		 * System.out.println("nb de classe de l'app : " + classCount);
		 * System.out.println("nb de methode de l'app : " + appMethodCount);
		 */
	}

	public static void myRec(Set<Noeud> enfants, HashMap<String, PetitArbre> grapheNonTrie, mxGraph graph,
			Object parent, Object vp, String idP) {
		if (!enfants.isEmpty()) {
			enfants.forEach(e -> {
				
				Object ve = null;
				PetitArbre petitArbre = grapheNonTrie.get(e.toStringID());
				
				if(!myCells.containsKey(e.toStringID())) {
	
					ve = graph.insertVertex(parent, null, e.toStringID(), 20, 20, 80, 30);
					myCells.put(e.toStringID(), ve);

				} else {
					ve = myCells.get(e.toStringID());
					
				}
				
				// Obtenez les dimensions préférées en fonction du contenu textuel
				mxRectangle dimensions2 = graph.getPreferredSizeForCell(ve);

				// Mettez à jour les dimensions du vertex
				graph.resizeCell(ve, dimensions2);

				Object a = null;
				
				if(!myArcs.containsKey(e.toStringID()+"-"+ idP)) {
					a = graph.insertEdge(parent, null, e.getNbAppel(), vp, ve);
				} else {
					a = myArcs.get(e.toStringID()+"-"+ idP);
				}
				

					
				myArcs.put(e.toStringID()+"-"+ idP, a);
				
				if(petitArbre!=null) {
					myRec(petitArbre.getEnfants(), grapheNonTrie, graph, parent, ve, e.toStringID());
				}
			});
		}
	}

	// package information
	public static void printPackageInfo(CompilationUnit parse) {
		PackageDeclarationVisitor visitor = new PackageD	eclarationVisitor();
		parse.accept(visitor);

		visitor.printPackageName();
	}

	// class & interface information
	public  void printClassInterfaceInfo(CompilationUnit parse) {
		ClassInterfaceVisitor visitor = new ClassInterfaceVisitor();
		parse.accept(visitor);

		if (visitor.getIsClass()) {
			System.out.println("NOM | line count | attr count");
			System.out.println(visitor.printClassName() + " | " + visitor.getLinesOfCode() + " | "
					+ visitor.getAttributeCount() + "\n");
			System.out.println("nom : " + visitor.getClassName());
			System.out.println("line count : " + visitor.getLinesOfCode());
			System.out.println("attr count  : " + visitor.getAttributeCount());
			System.out.println("\n");
			classCount++;
		}
		if (visitor.getIsInterface()) {
			System.out.println("INTERFACE : " + visitor.getClassName());
			System.out.println("line count : " + visitor.getLinesOfCode());
			//			System.out.println("code : " + visitor.javaCode);
			System.out.println("\n");
		}

		appLineCount += visitor.getLinesOfCode();

	}

	// enum information
	public static void printEnumInfo(CompilationUnit parse) {
		EnumVisitor visitor = new EnumVisitor();
		parse.accept(visitor);

		if (visitor.getEnumName() != null) {
			System.out.println("ENUMERATION : " + visitor.getEnumName());
			System.out.println("line count : " + visitor.getLinesOfCode());
			System.out.println("\n");
		}
	}

	// navigate method information
	public  void printMethodInfo(CompilationUnit parse) {
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

			for (VariableDeclarationFragment variableDeclarationFragment : visitor2.getVariables()) {
				System.out.println("variable name: " + variableDeclarationFragment.getName() + " variable Initializer: "
						+ variableDeclarationFragment.getInitializer() + "\n");
			}

		}
	}

	// navigate method invocations inside method
	public static void printMethodInvocationInfo(CompilationUnit parse) {
		// Trouver les méthodes déclaré
		MethodDeclarationVisitor methodDeclarationVisitor = new MethodDeclarationVisitor();
		parse.accept(methodDeclarationVisitor);


		ImportDeclarationVisitor importDeclarationVisitor = new ImportDeclarationVisitor();
		parse.accept(importDeclarationVisitor);

		// Trouver la classe courant
		ClassInterfaceVisitor classInterfaceVisitor = new ClassInterfaceVisitor();
		parse.accept(classInterfaceVisitor);

		// Trouver le package courant
		PackageDeclarationVisitor packageDeclarationVisitor = new PackageDeclarationVisitor();
		parse.accept(packageDeclarationVisitor);

		// Pour tout les méthodes déclarées je cherche les méthodes quil invoque
		for (MethodDeclaration methodDeclaration : methodDeclarationVisitor.getMethods()) {



		

			// Trouver les méthodes invoqués
			MethodInvocationVisitor methodInvocationVisitor = new MethodInvocationVisitor();
			methodDeclaration.accept(methodInvocationVisitor);
			PetitArbre arbre;

			if (!myGraph
					.isExist(packageDeclarationVisitor.getPackageName() + "." + classInterfaceVisitor.printClassName()
					+ "." + methodDeclaration.getName().getFullyQualifiedName())) {
				arbre = new PetitArbre(new Noeud(
						packageDeclarationVisitor.getPackageName() + "." + classInterfaceVisitor.printClassName(),
						methodDeclaration.getName().getFullyQualifiedName()));

			} else {
				arbre = myGraph.getPetitArbreByKey(
						packageDeclarationVisitor.getPackageName() + "." + classInterfaceVisitor.printClassName() + "."
								+ methodDeclaration.getName().getFullyQualifiedName());
			}

			// Pour chaque méthodes invoqué je regarde si c'est une class definit dans notre
			// projet
			for (MethodInvocation methodInvocation : methodInvocationVisitor.getMethods()) {


				if (!getDeclaringClassName(methodInvocation).contains("UnknownClass")) {

					arbre.addEnfant(new Noeud(getDeclaringClassName(methodInvocation),
							methodInvocation.getName().getFullyQualifiedName()));
				}
			}

			// Trouver les constructors invoqués
			ConstructorInvocationVisitor constructorInvocationVisitor = new ConstructorInvocationVisitor();
			methodDeclaration.accept(constructorInvocationVisitor);

			for (ClassInstanceCreation classInstanceCreation : constructorInvocationVisitor.getMethods()) {

				if (!getDeclaringClassName2(classInstanceCreation,importDeclarationVisitor).contains("UnknownClass")) {

					arbre.addEnfant(new Noeud(getDeclaringClassName2(classInstanceCreation,importDeclarationVisitor),
							classInstanceCreation.getType().toString()));
				}

			}
			myGraph.checkMainOrSommet(arbre);

		}
	}

	private static String getDeclaringClassName(MethodInvocation methodInvocation) {
		if (methodInvocation.resolveMethodBinding()!= null) {
			String fullyQualifiedName = methodInvocation.resolveMethodBinding().getDeclaringClass().getQualifiedName();
			//System.out.println(fullyQualifiedName);


			return fullyQualifiedName;

		}
		return "UnknownClass";
	}
	/*
	 * 
	 * private static String getDeclaringClassName(MethodInvocation
	 * methodInvocation) { IMethodBinding methodBinding =
	 * methodInvocation.resolveMethodBinding();
	 * 
	 * 
	 * if (methodBinding != null) { ITypeBinding typeBinding =
	 * methodBinding.getDeclaringClass(); if (typeBinding != null) { return
	 * typeBinding.getQualifiedName(); } } return "UnknownClass"; }
	 */
	/*
	 * private static String getDeclaringClassName2(ClassInstanceCreation
	 * classInstanceCreation) { IMethodBinding methodBinding =
	 * classInstanceCreation.resolveConstructorBinding();
	 * System.out.println(classInstanceCreation); if (methodBinding != null) {
	 * ITypeBinding typeBinding = methodBinding.getDeclaringClass(); if (typeBinding
	 * != null) { return typeBinding.getQualifiedName(); } } return "UnknownClass";
	 * }
	 */

	private static String getDeclaringClassName2(ClassInstanceCreation classInstanceCreation, ImportDeclarationVisitor importDeclarationVisitor) {

		String fullyQualifiedName = classInstanceCreation.getType().resolveBinding().getQualifiedName();
		String pac = null;


		for (ImportDeclaration i :importDeclarationVisitor.getImports()) {
			if(i.getName().getFullyQualifiedName().contains(fullyQualifiedName)) {
				pac = i.getName().getFullyQualifiedName();
				//System.out.println(pac);
				return pac;

			}
		}


		return fullyQualifiedName;

	}
}
