package graph.draw;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxGraph;
import graph.Graphe;
import graph.Noeud;
import graph.PetitArbre;
import graph.extractInfo.utils.Couplage;
import graph.extractInfo.utils.Resultats;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

public class DrawDendroGraph implements DrawGraph{
    private Map<String,Object> myCells = new HashMap<>();
    private Map<String,Object> myArcs = new HashMap<>();
    private Graphe myGraph = new Graphe();
    private ArrayList<Couplage> resultsCoupling ;

    public DrawDendroGraph(
            Map<String, Object> myCells,
            Map<String, Object> myArcs,
            Graphe myGraph,
            ArrayList<Couplage> listDendro
    )
    {
        this.myCells = myCells;
        this.myArcs = myArcs;
        this.myGraph = myGraph;
        this.resultsCoupling = listDendro;
        draw();
    }
    
    @Override
    public void draw() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Dendrogramme");
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

            
            
            
            ArrayList<String> listClasses = new ArrayList<>();
            ArrayList<Couplage> copyOfResultsCoupling = new ArrayList<>(resultsCoupling);
            
            for (Couplage couplage : copyOfResultsCoupling) {
                System.out.println(couplage.getClasses() + " - " + couplage.getValue());
                for(String classe : couplage.getClasses()) {
                	if(!listClasses.contains(classe)) {
                		listClasses.add(classe);
                	}
                }
            }
            System.out.println(listClasses.toString());
            
            
            
//            while(!copyOfResultsCoupling.isEmpty()) {
            	Set<String> maxKey = null;
            	float maxValue = 0;
            	
            	//recup de la plus gde valeur de couplage
            	for(Couplage couple : copyOfResultsCoupling) {
            		if(couple.getValue() > maxValue) {
            			maxValue = couple.getValue();
            			maxKey = couple.getClasses();
            		}
            	}
            	
            	//fusion des noms de clés nécessaires
            	for (Couplage couple : copyOfResultsCoupling) {
            	    boolean aAjouter = false;

            	    for (String classeMaxKey : maxKey) {
            	        if (couple.getClasses().contains(classeMaxKey)) {
            	        	aAjouter = true;
            	            break;
            	        }
            	    }
            	    if (aAjouter) {
            	        couple.getClasses().addAll(maxKey);
            	    }
            	}
            	
            	/////lier cellule filles et la "cellule" parent
            	
            	//remove de copyOfResultsCoupling le couplage maxKey,maxValue
            	//Si deux set ont les meemes classes, suppr tous sauf 1 et additionner les couplages
            	//Affichage
                for (Couplage couplage : copyOfResultsCoupling) {
                    System.out.println(couplage.getClasses() + " - " + couplage.getValue());
                }
//            }
            
//            while(!copyCoupling.isEmpty()) {
//            	
//            	String maxKey = null;
//                float maxValue = 0;
//                
//            	for (Entry<String, Float> entry : copyCoupling.entrySet()) {
//                    if (entry.getValue() > maxValue) {
//                    	maxValue = entry.getValue();
//                        maxKey = entry.getKey();
//                    }
//                }
//            	String[] classNames = maxKey.split("-");
//            	for (String className : classNames) {
//            	    System.out.println(className);
//            	}
//            	
//            	for (Entry<String, Float> entry : copyCoupling.entrySet()) {
//        			String originalKey = entry.getKey();
//        			
//        			if(originalKey != maxKey) {
//	        			Float originalValue = entry.getValue();
//	            		for(String className : classNames) {
//	            			if(originalKey.contains(className)) {
//	            				String replacedKey = originalKey.replaceAll(Pattern.quote(className), maxKey);  
////	            				+ value de className et originalKey
////	            				String key1 = String.format("%s-%s",className, originalKey);
////	                            String key2 = String.format("%s-%s", originalKey, className);
////	                            String valueOfCoupling = copyCoupling.get(key1).toString();
////	                            if (valueOfCoupling.isEmpty()){
////	                                valueOfCoupling = copyCoupling.get(key2).toString();
////	                            }
////	                            
//	            				dendroMap.put(replacedKey, originalValue	);
//	                            break;
//	                            //######################## value à ajouter à maxkey ? max couplage ?
//	            			} else {
//	            				dendroMap.put(originalKey, originalValue);
//	            				dendroMap.remove(maxKey);
//	            			}
//	                	}
//	            	}
//            	}
//                dendroMap.forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));
//                float sum = 0;
//            	for (Entry<String, Float> entry : dendroMap.entrySet()) {
//            		sum+=entry.getValue();
//            	}
//            	System.out.println("total :" + sum);
//
//                System.out.println("\n");
//                copyCoupling.remove(maxKey);
//            }
            
            
            
            
//            try {
//
//                for(PetitArbre pa : myGraph.getGrapheNonTrie().values()) {
//                    Object v1 = null;
//                    if(!pa.getEnfants().isEmpty()) {
//                        if (!myCells.containsKey(pa.getParent().getClasseName())){
//                            v1 = graph.insertVertex(parent, null, pa.getParent().getClasseName(), 20, 20, 80, 30);
//
//                            myCells.put(pa.getParent().getClasseName(), v1);
//                        } else {
//                            v1 = myCells.get(pa.getParent().getClasseName());
//                        }
//
//                        mxRectangle dimensions = graph.getPreferredSizeForCell(v1);
//
//                        // Msj à jour les dimensions du vertex
//                        graph.resizeCell(v1, dimensions);
//                        
//                        recursiveGraphFunction(pa.getEnfants(), myGraph.getGrapheNonTrie(), graph, parent, v1, pa.getParent().getClasseName());
//                    }
//                };
//
//
//                // Utilisez l'algorithme hierarchique pour organiser les vertex
//                mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
//                // orientation verticale
//                layout.setOrientation(SwingConstants.NORTH);
//
//
//                layout.setIntraCellSpacing(100); // Espacement entre les nœuds dans la même couche
//                layout.setInterRankCellSpacing(150); // Espacement entre les couches
//                layout.execute(parent);
//
//            } finally {
//                graph.getModel().endUpdate();
//            }
//
//            mxGraphComponent graphComponent = new mxGraphComponent(graph);
//            graphComponent.setPreferredSize(new Dimension(1000, 1000));
//
//            frame.getContentPane().add(graphComponent);
//
//            frame.pack();
//            frame.setVisible(true);
//            // Centrer le graphe dans le composant
//            graphComponent.setCenterPage(true);
        });
    }

    
    
    @Override
    public void recursiveGraphFunction(Set<Noeud> enfants, HashMap<String, PetitArbre> grapheNonTrie, mxGraph graph, Object parent, Object vp, String idP) {
//        if (!enfants.isEmpty()) {
//            enfants.forEach(e ->
//            {
//                Object ve = null;
//                PetitArbre petitArbre = grapheNonTrie.get(e.getClasseName());
//                if(!myCells.containsKey(e.getClasseName()))
//                {
//                    ve = graph.insertVertex(
//                            parent,
//                            null,
//                            e.getClasseName(),
//                            20,
//                            20,
//                            80,
//                            30
//                    );
//                    myCells.put(e.getClasseName(), ve);
//                }
//                else
//                {
//                    ve = myCells.get(e.getClasseName());
//                }
//
//                mxRectangle dimensions2 = graph.getPreferredSizeForCell(ve);
//                // Msj à jour les dimensions du vertex
//                graph.resizeCell(ve, dimensions2);
//                Object a = null;
//                if(!myArcs.containsKey(e.getClasseName()+"-"+ idP))	
//                {
//                    String key1 = String.format("%s-%s",idP, e.getClasseName());
//                    String key2 = String.format("%s-%s", e.getClasseName(), idP);
//
//                    String valueOfCoupling = resultsCoupling.get(key1).toString();
//                    if (valueOfCoupling.isEmpty()){
//                        valueOfCoupling = resultsCoupling.get(key2).toString();
//                    }
//                  a = graph.insertEdge(
//                            parent,
//                            null,
//                          valueOfCoupling,
//                            vp,
//                            ve
//                    );
//                }
//                else
//                {
//                    a = myArcs.get(e.getClasseName()+"-"+ idP);
//                }
//                myArcs.put(
//                        e.getClasseName()+"-"+ idP,
//                        a
//                );
//                if(petitArbre!=null && !e.getClasseName().equals(idP))
//                {
//                    recursiveGraphFunction(
//                            petitArbre.getEnfants(),
//                            grapheNonTrie,
//                            graph,
//                            parent,
//                            ve,
//                            e.getClasseName()
//                    );
//                }
//            });
//        }
    }
}
