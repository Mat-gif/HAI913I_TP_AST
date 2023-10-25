package ui.controller;

import java.io.File;
import java.io.IOException;
import java.util.*;

import graph.draw.DrawCallGraph;
import graph.extractInfo.ExtractInfoForCallGraph;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

import graph.Graphe;
import graph.Noeud;
import graph.PetitArbre;
import parsers.EclipseJDTParser;
import visitor.ClassInterfaceVisitor;
import visitor.ConstructorInvocationVisitor;
import visitor.ImportDeclarationVisitor;
import visitor.MethodDeclarationVisitor;
import visitor.MethodInvocationVisitor;
import visitor.PackageDeclarationVisitor;

public class GraphController {

	public static EclipseJDTParser parserEclipse;
	private static final Map<String,Object> myCells = new HashMap<>();
	private static final Map<String,Object> myArcs = new HashMap<>();
	private static  Graphe myGraph = new Graphe();
	private static  Graphe myGraph2 = new Graphe();
	private static final ExtractInfoForCallGraph infoForCallGraph = new ExtractInfoForCallGraph();

	public  void GraphPanel(String path) throws IOException {


		parserEclipse = new EclipseJDTParser(path);

		List<File> javaFiles = parserEclipse.listJavaProjectFiles();
		for (File content : javaFiles) {
			parserEclipse.configure();
			CompilationUnit parse = parserEclipse.parse(content);
			myGraph=infoForCallGraph.extract(parse,myGraph);
//			printMethodInvocationInfo2(parse);
		}

		DrawCallGraph callGaph = new DrawCallGraph(
				myCells,
				myArcs,
				myGraph
		);

	}


//
//
//	// navigate method invocations inside method
//	public static void printMethodInvocationInfo2(CompilationUnit parse) {
//		// Trouver les méthodes déclaré
//		MethodDeclarationVisitor methodDeclarationVisitor = new MethodDeclarationVisitor();
//		parse.accept(methodDeclarationVisitor);
//
//		ImportDeclarationVisitor importDeclarationVisitor = new ImportDeclarationVisitor();
//		parse.accept(importDeclarationVisitor);
//
//		// Trouver la classe courant
//		ClassInterfaceVisitor classInterfaceVisitor = new ClassInterfaceVisitor();
//		parse.accept(classInterfaceVisitor);
//
//		// Trouver le package courant
//		PackageDeclarationVisitor packageDeclarationVisitor = new PackageDeclarationVisitor();
//		parse.accept(packageDeclarationVisitor);
//
//		PetitArbre arbre = new PetitArbre(new Noeud(packageDeclarationVisitor.getPackageName() + "." + classInterfaceVisitor.getClassName()));
//
//		// Pour tout les méthodes déclarées je cherche les méthodes quil invoque
//		for (MethodDeclaration methodDeclaration : methodDeclarationVisitor.getMethods())
//		{
//			// Trouver les méthodes invoqués
//			MethodInvocationVisitor methodInvocationVisitor = new MethodInvocationVisitor();
//			methodDeclaration.accept(methodInvocationVisitor);
//			// Pour chaque méthodes invoqué je regarde si c'est une class definit dans notre
//			// projet
//			for (MethodInvocation methodInvocation : methodInvocationVisitor.getMethods())
//			{
//				if (!getDeclaringClassNameForMethod(methodInvocation).contains("UnknownClass"))
//				{
//					arbre.addEnfant2(new Noeud(getDeclaringClassNameForMethod(methodInvocation)));
//				}
//			}
//			// Trouver les constructors invoqués
//			ConstructorInvocationVisitor constructorInvocationVisitor = new ConstructorInvocationVisitor();
//			methodDeclaration.accept(constructorInvocationVisitor);
//			for (ClassInstanceCreation classInstanceCreation : constructorInvocationVisitor.getMethods())
//			{
//				arbre.addEnfant2(new Noeud(getDeclaringClassNameForConstruct(classInstanceCreation,importDeclarationVisitor,packageDeclarationVisitor.getPackageName())));
//			}
//		}
//		myGraph2.add(arbre);
//	}
}
