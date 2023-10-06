package ui.template.CustomJPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxGraph;

import graph.Graphe;
import graph.Noeud;
import graph.PetitArbre;
import ui.controller.LabelMap;
import ui.controller.Resultat;
import ui.controller.SelectProjectController;
import ui.paramater.MyViewParameter;


public class GraphPanel {
	   private MyViewParameter myParam = new MyViewParameter();
	   private JButton btnTerminer;

	   
	public JButton getBtnTerminer() {
		return btnTerminer;
	}


	public void setBtnTerminer(JButton btnTerminer) {
		this.btnTerminer = btnTerminer;
	}


	public GraphPanel( JFrame frame ) {
		/*
		frame.getContentPane().add(this, BorderLayout.CENTER);
		this.setLayout(null);


	    JLabel titleLabel = new JLabel( "--> Resultats :");
	    titleLabel.setBounds(myParam.getxBouton(), (int) Math.round((myParam.getyBouton()/2)), myParam.getLargeurBouton(), myParam.getHauteurBouton());
	    titleLabel.setFont(MyViewParameter.getMyFontStyleTitle());
	    this.add(titleLabel);
	       
	    btnTerminer = new JButton("Terminer");
	    btnTerminer.setBounds(myParam.getLargeurFenetre()-myParam.getxBouton()-myParam.getLargeurBouton(), (int) Math.round((myParam.getyBouton()/2)), myParam.getLargeurBouton(), myParam.getHauteurBouton());
	    btnTerminer.setFont(MyViewParameter.getMyFontStyle());
	    btnTerminer.setBackground(Color.lightGray);
	       this.add(btnTerminer);
	     */  


	}
	
	public void printResults( Graphe myGraph)
	{

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

				myGraph.getListOfMain().forEach(m -> {
					
					if(!m.getEnfants().isEmpty()) {
<<<<<<< HEAD
						Object v1 = graph.insertVertex(parent, null, m.getParent().getMethodName(), 20, 20, 80, 30);
=======
						Object v1 = graph.insertVertex(parent, null, m.getParent().toStringID(), 20, 20, 80, 30);
>>>>>>> 478daac54191a0aeb79806e8215aa1ab863e6164
						// Obtenez les dimensions préférées en fonction du contenu textuel
						mxRectangle dimensions = graph.getPreferredSizeForCell(v1);

			

						myRec(m.getEnfants(), myGraph.getGrapheNonTrie(), graph, parent, v1);
					}
					

				});

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

	       }
	

	public static void myRec(Set<Noeud> enfants, HashMap<String, PetitArbre> grapheNonTrie, mxGraph graph,
			Object parent, Object vp) {
		if (!enfants.isEmpty()) {
			enfants.forEach(e -> {
				PetitArbre petitArbre = grapheNonTrie.get(e.toStringID());

				Object ve = graph.insertVertex(parent, null, e.toStringID(), 20, 20, 80, 30);
				// Obtenez les dimensions préférées en fonction du contenu textuel
				mxRectangle dimensions2 = graph.getPreferredSizeForCell(ve);

				// Mettez à jour les dimensions du vertex
				graph.resizeCell(ve, dimensions2);

				graph.insertEdge(parent, null, "", vp, ve);

				if(petitArbre!=null) {
					myRec(petitArbre.getEnfants(), grapheNonTrie, graph, parent, ve);
				}
				

			});
		}
	}
	
	
   	
   	public void addAllListener(SelectProjectController myController) 
   	{
        btnTerminer.addActionListener(myController.buttonQuitListener);

   	}

}