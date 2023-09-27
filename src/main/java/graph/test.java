package graph;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import test.test.ASTGraphPanel;

public class ASTGraphPanel extends JPanel {

    public ASTGraphPanel() throws IOException {
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
        String cheminVersFichier = "./src/main/java/test/test/ATester.java";
        ArrayList<Object> te = new ArrayList<Object>();
        graph.getModel().beginUpdate();

        try {
        	 StringBuilder codeSource = new StringBuilder();
             BufferedReader br = new BufferedReader(new FileReader(cheminVersFichier));
             String ligne;
             while ((ligne = br.readLine()) != null) {
                 codeSource.append(ligne).append("\n");
             }
             br.close();

            // Créez l'ASTParser et parcourez l'AST
            ASTParser parser = ASTParser.newParser(AST.JLS3);
            parser.setSource(codeSource.toString().toCharArray());
            CompilationUnit cu = (CompilationUnit) parser.createAST(null);

            // Parcourez l'AST pour extraire les informations nécessaires et ajouter des nœuds au graphe
            cu.accept(new ASTVisitor() {
                @Override
                public boolean visit(MethodDeclaration node) {
                    // Ajoutez ici votre code pour extraire les informations sur les méthodes
                    // et ajouter des nœuds au graphe
                    String methodName = node.getName().getIdentifier();
                    System.out.println(methodName);
                    Object v1 = graph.insertVertex(parent, null, methodName, 20, 20, 80, 30);
                    te.add(v1);
                    
                    if(te.size()>=2) {
                    	graph.insertEdge(parent, null, "", te.get(0), v1);
                    }
                    
                    
                    return super.visit(node);
                }

                // Ajoutez d'autres méthodes visit() pour d'autres éléments de l'AST si nécessaire
            });
        } finally {
            graph.getModel().endUpdate();
        }

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphComponent.setPreferredSize(new Dimension(500, 500)); // Modifiez la taille selon vos besoins

        add(graphComponent);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("AST Graph Viewer");
            frame.setBounds(100, 100, 1000, 800);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            try {
                ASTGraphPanel test7 = new ASTGraphPanel();

                frame.add(test7);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            frame.setVisible(true);
        });
    }

}