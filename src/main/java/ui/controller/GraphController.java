package ui.controller;

import java.io.File;
import java.io.IOException;
import java.util.*;

import graph.draw.DrawCallGraph;
import graph.draw.DrawCouplingGraph;
import graph.draw.DrawDendroGraph;
import graph.draw.DrawDendroGraph2;
import graph.extractInfo.ExtractInfoForCallGraph;
import graph.extractInfo.ExtractInfoForCouplingGraph;
import org.eclipse.jdt.core.dom.CompilationUnit;
import graph.Graphe;
import parsers.EclipseJDTParser;
public class GraphController {

	public static EclipseJDTParser parserEclipse;
	private static final Map<String,Object> myCells = new HashMap<>();
	private static final Map<Set,Object> myCells2 = new HashMap<>();
	private static final Map<String,Object> myArcs = new HashMap<>();
	private static  Graphe callGraph = new Graphe();
	private static  Graphe couplingGraph = new Graphe();
	private static final ExtractInfoForCallGraph infoForCallGraph = new ExtractInfoForCallGraph();
	private static final ExtractInfoForCouplingGraph infoForCouplingGraph = new ExtractInfoForCouplingGraph();

	public  void GraphPanel(String path) throws IOException {


		parserEclipse = new EclipseJDTParser(path);

		List<File> javaFiles = parserEclipse.listJavaProjectFiles();
		for (File content : javaFiles) {
			parserEclipse.configure();
			CompilationUnit parse = parserEclipse.parse(content);
			callGraph=infoForCallGraph.extract(parse,callGraph);
			couplingGraph=infoForCouplingGraph.extract(parse,couplingGraph);
		}


		couplingGraph.getGrapheNonTrie().values().forEach(System.out::println);

//		DrawCallGraph drawCallGraph = new DrawCallGraph(
//				myCells,
//				myArcs,
//				callGraph
//		);
//		DrawCouplingGraph drawCouplingGraph = new DrawCouplingGraph(
//				myCells,
//				myArcs,
//				couplingGraph,
//				infoForCouplingGraph.couplingAnalysis(couplingGraph).getForCouplingG()
//		);
		DrawDendroGraph drawDendroGraph = new DrawDendroGraph(
				myCells2,
				myArcs,
				couplingGraph,
				infoForCouplingGraph.couplingAnalysis(couplingGraph).getForDendo()
		);
		
		DrawDendroGraph2 drawDendroGraph2 = new DrawDendroGraph2(
				myCells2,
				myArcs,
				couplingGraph,
				infoForCouplingGraph.couplingAnalysis(couplingGraph).getForDendo()
		);


//		infoForCouplingGraph.couplingAnalysis(couplingGraph).keySet().forEach(System.out::println);
//		infoForCouplingGraph.couplingAnalysis(couplingGraph).values().forEach(System.out::println);
//		System.out.println(infoForCouplingGraph.totCoup);
	}
}
