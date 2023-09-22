package visitor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
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
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class Parser {

	// public static final String projectPath =
	// "C:\\Users\\victo\\Desktop\\MASTER_INFO_ICO\\M2\\S3\\HAI913I - Evolution
	// Restructuration
	// Logicielle\\TP\\TP1\\CorrectionTP2_Partie1_Pom_Version\\CorrectionTP2_Partie1";
	// public static final String projectPath =
	// "C:\\Users\\victo\\eclipse-workspace\\HAI822I";
	public static final String projectPath = "C:\\Users\\manil\\Desktop\\Master_ico\\Master__2\\HAI913I - Evolution et restructuration des logiciels\\Dev\\org.anonbnr.design_patterns";
	public static final String projectSourcePath = projectPath + "\\src";
	public static final String jrePath = "C:\\Program Files\\Java\\jre1.8.0_51\\lib\\rt.jar";
	public static int classCount = 0;

	public static void main(String[] args) throws IOException {

		// read java files
		final File folder = new File(projectSourcePath); // il est fait avec le parser
		ArrayList<File> javaFiles = listJavaFilesForFolder(folder); // le parser a un attribut de liste de javaFiles
		String previousPackageName = "";
		int packageCount = 0;

		for (File fileEntry : javaFiles) {
			String content = FileUtils.readFileToString(fileEntry);
			// System.out.println(content);

			CompilationUnit parse = parse(content.toCharArray()); //parse  le 

			//
			String currentPackageName = parse.getPackage().getName().getFullyQualifiedName();

			// print package info
			if (!currentPackageName.equals(previousPackageName)) {
				packageCount++;
				printPackageInfo(parse);
				previousPackageName = currentPackageName;
				// Set<String> packageSet = new HashSet<>();
				// packageSet.add(previousPackageName);

			}

			// print class info
			printClassInfo(parse);

			// print methods info
			// printMethodInfo(parse);

			// print variables info
			// printVariableInfo(parse);

			// print method invocations
			// printMethodInvocationInfo(parse);

		}
		System.out.println("nb de classe au total : " + classCount);
		System.out.println("le nombre de package de l'application est : " + packageCount);
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
		parser.setCompilerOptions(options);

		parser.setUnitName("");

		String[] sources = { projectSourcePath };
		String[] classpath = { jrePath };

		parser.setEnvironment(classpath, sources, new String[] { "UTF-8" }, true);
		parser.setSource(classSource);

		return (CompilationUnit) parser.createAST(null); // create and parse
	}

	// package information
	public static void printPackageInfo(CompilationUnit parse) {
		PackageDeclarationVisitor visitor = new PackageDeclarationVisitor();
		parse.accept(visitor);

		visitor.printPackageName();

	}

	// le nombre de package in projet

	public static void countPackage(CompilationUnit parse) {
		PackageDeclarationVisitor visitor = new PackageDeclarationVisitor();
		parse.accept(visitor);

		System.out.println("le nombre de package de l'app est:  " + visitor.countPackagesInProject());
	}

	// class information
	public static void printClassInfo(CompilationUnit parse) {
		ClassVisitor visitor = new ClassVisitor();
		parse.accept(visitor);

		if (visitor.isClass) {
			System.out.println("NOM | line count | attr count");
			System.out.println(visitor.printClassName() + " | "
					+ visitor.getLinesOfCode() + " | "
					+ visitor.getAttributeCount()
					+ "\n");
			classCount++;
		}

	}

	// navigate method information
	public static void printMethodInfo(CompilationUnit parse) {
		MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
		parse.accept(visitor);

		visitor.printMethodCount();
		visitor.printMethodsNames();
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
						+ variableDeclarationFragment.getInitializer());
			}

		}
	}

	// navigate method invocations inside method
	public static void printMethodInvocationInfo(CompilationUnit parse) {

		MethodDeclarationVisitor visitor1 = new MethodDeclarationVisitor();
		parse.accept(visitor1);
		for (MethodDeclaration method : visitor1.getMethods()) {

			MethodInvocationVisitor visitor2 = new MethodInvocationVisitor();
			method.accept(visitor2);

			for (MethodInvocation methodInvocation : visitor2.getMethods()) {
				System.out.println("method " + method.getName() + " invoc method "
						+ methodInvocation.getName());
			}

		}
	}

}
