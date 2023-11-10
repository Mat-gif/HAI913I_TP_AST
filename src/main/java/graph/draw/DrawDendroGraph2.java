package graph.draw;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxGeometry;
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
import java.util.*;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class DrawDendroGraph2 implements DrawGraph{
    private Map<Set,Object> myCells = new HashMap<>();
    private Map<String,Object> myArcs = new HashMap<>();
    private Map<Set<String>, List<Set<String>>> inter = new HashMap<>();

    private ArrayList<Couplage> copyOfResultsCoupling;
    private Map<String,Object> cells = new HashMap<>();
    
    private List<Object> listClassesDendro = new LinkedList<>();


    private mxGraph graph = new mxGraph();
    private Graphe myGraph = new Graphe();
    private Object parent = graph.getDefaultParent();
    private ArrayList<Couplage> resultsCoupling ;

    public DrawDendroGraph2(
            Map<Set, Object> myCells,
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


            graph.setCellsEditable(false); // Désactiver l'édition
            graph.setCellsMovable(false); // Désactiver le déplacement
            graph.setCellsResizable(false); // Désactiver le redimensionnement
            graph.setDropEnabled(false); // Désactiver le glisser-déposer
            graph.setSplitEnabled(false); // Désactiver la divisio


            graph.getModel().beginUpdate();

            
            
            
            ArrayList<String> listClasses = new ArrayList<>();
            copyOfResultsCoupling = new ArrayList<>(resultsCoupling);
            
            for (Couplage couplage : copyOfResultsCoupling) {
//                System.out.println(couplage.getClasses() + " - " + couplage.getValue());
            	
                for(String classe : couplage.getClasses()) {

                	if(!listClasses.contains(classe)) {
                		listClasses.add(classe);
                        Object  v1 = graph.insertVertex(parent, null, classe, 50,50, 80, 30);

                        mxRectangle dimensions = graph.getPreferredSizeForCell(v1);

                        // Msj à jour les dimensions du vertex
                        graph.resizeCell(v1, dimensions);

                        cells.put(classe,v1);

                    }
                }
            }
            
            for (String classe : listClasses) {
                myCells.put(new HashSet<>(Collections.singleton(classe)), null);
            }
            System.out.println("\nCellules (" + myCells.size()+ ") :\n" + myCells.toString() + "\n");

            
            System.out.println("Liste des classes :\n" + listClasses.toString() + "\n");

            
            
            listClassesDendro = new LinkedList<>(listClasses);


            while(copyOfResultsCoupling.size()>=1) {
            	
            	System.out.println("ListClassesDendro#############");
            	for(Object classe : listClassesDendro) {
            		System.out.println(classe);
            	}
            	
            	System.out.println(">>>>>>\nCouplage d'entrée : \n");
                for (Couplage couplage : copyOfResultsCoupling) {
                    System.out.println(couplage.getClasses() + " - " + couplage.getValue());
                }



                copyOfResultsCoupling = dendro(copyOfResultsCoupling);
            	
                
                
                
            	System.out.println("<<<<<<\nCouplage de sortie : \n");
                for (Couplage couplage : copyOfResultsCoupling) {
                    System.out.println(couplage.getClasses() + " - " + couplage.getValue());
                }
            }
            
            System.out.println("\nCellules (" + myCells.size()+ ") :\n" + myCells.toString());

            mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
            layout.setOrientation(SwingConstants.NORTH);	
            layout.setIntraCellSpacing(100); // Espacement entre les nœuds dans la même couche
            layout.setInterRankCellSpacing(150); // Espacement entre les couches
            layout.execute(parent);
            
            
            for (int i = 0; i < graph.getModel().getChildCount(parent); i++) {
                Object cell = graph.getModel().getChildAt(parent, i);
                int linkedElementCount = 0;

                // Parcourez toutes les arêtes du graphe
                for (int j = 0; j < graph.getModel().getEdgeCount(cell); j++) {
                    Object edge = graph.getModel().getEdgeAt(cell, j);
                    Object source = graph.getModel().getTerminal(edge, true);
                    Object target = graph.getModel().getTerminal(edge, false);

                    if (source == cell || target == cell) {
                        linkedElementCount++;
                    }
                }

                // Si l'élément est lié à un seul et un seul autre élément, ajustez sa coordonnée y à 0
                if (linkedElementCount == 1) {
                    mxGeometry geometry = graph.getModel().getGeometry(cell);
                    if (geometry != null) {
                        geometry.setY(0);
                    }
                }
            }
            
            
            
//            layout.setOrientation(SwingConstants.WEST); // Orientation de bas en haut (sur l'axe des y)	
//            layout.execute(parent); // parent est le conteneur racine de votre graphe
//            
            graph.getModel().endUpdate();

            mxGraphComponent graphComponent = new mxGraphComponent(graph);
            graphComponent.setPreferredSize(new Dimension(1000, 1000));

            frame.getContentPane().add(graphComponent);

            frame.pack();
            frame.setVisible(true);
            // Centrer le graphe dans le composant
            graphComponent.setCenterPage(true);
        });
    }
            
    public ArrayList<Couplage> dendro(ArrayList<Couplage> copyOfResultsCoupling) {
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
//                        System.err.println(maxKey.toString());


                        if(!maxKey.equals(couple.getClasses())  ) {
                            Set<String> olalala = new HashSet<>();
                            olalala.addAll(couple.getClasses());
                            olalala.addAll(maxKey);

                            List<Set<String>> l = new ArrayList<>();


                            Set<String> testest = new HashSet<>();

                            for (String k :couple.getClasses())
                            {
                                if(!maxKey.contains(k))
                                {
                                    testest.add(k);
//                                    System.err.println(k);
                                }
                            }

                            l.add(maxKey);
                            l.add(testest);

                            inter.put(olalala, l);
                        }
                        couple.getClasses().addAll(maxKey);
            	    }
            	}
            	
            	myCells.put(maxKey, null);
            	
                
            	List<Object> list = new LinkedList<>();
                for(String s : maxKey) {
                	list.add(s);
                }
                listClassesDendro.add(list);
                List<Object> listClassesDendroCopy = new LinkedList<>(listClassesDendro);
                
                for(Object a : listClassesDendro) {
                	String one = null;
                	LinkedList two = null;
                	
                	for(String c : maxKey) {
                		if(a instanceof String) {
                			if(a == c) {
                				one = a.toString();
                		}
                		} else {
                			LinkedList<String> listeClasse = (LinkedList<String>) a;
                			for( String b : listeClasse) {
                				if(b == c) {
                					two = listeClasse;
                				}
                			}
                		}
                	}
                	if((one!=null && two!=null)) {
                		System.out.println("ONE TWO");
                		System.out.println(one);
                		System.out.println(two);
	    				List<Object> nouvelleListe = new LinkedList<>();
	    				nouvelleListe.add(one);
	    	        	nouvelleListe.add(two);
	    	        	listClassesDendroCopy.add(nouvelleListe);
                	}
                }
                
                
//            	for(String classesNewCouplage : maxKey){
//            		for(Object a : listClassesDendro) {
//            			if (!(a instanceof String)) {
//            	            LinkedList<String> listeClasse = (LinkedList<String>) a;
//            	            System.out.println("LISTE DE A");
//            	            listeClasse.toString();
//            	            
//            	           if(!listeClasse.contains(classesNewCouplage)){
//	            				System.out.println("DIIIIIIIIIIIIIIIIIIIIINNNNNNNNNNNNNGGGGGGG");
//	            				List<Object> nouvelleListe = new LinkedList<>();
//	            				nouvelleListe.add(classesNewCouplage);
//	            	        	nouvelleListe.add(a);
//	            	        	listClassesDendroCopy.add(nouvelleListe);
//	
//	            	        }
//	            		}
//	            		listClassesDendroCopy.remove(classesNewCouplage);
//
//            		}
//            	}
            	
                listClassesDendro = new LinkedList<>(listClassesDendroCopy);
                System.out.println(listClassesDendro.toString());

            	
            	
            	
            	//remove de copyOfResultsCoupling le couplage maxKey,maxValue
            	for (Couplage couple :  copyOfResultsCoupling) {
            		if(couple.getClasses() == maxKey && couple.getValue() == maxValue) {
            			copyOfResultsCoupling.remove(couple);
            			break;
            		}
            	}
            	
            	//Si deux set ont les memes classes, suppr tous sauf 1 et additionner les couplages
            	
                ArrayList<Couplage> copyOfResultsCouplingTempo = new ArrayList<>();
//                System.err.println("***");
            	for (Couplage couple : copyOfResultsCoupling) {
            		Set<String> key = couple.getClasses();

//                    System.err.println(key);
                	float value = 0;
                	for (Couplage coupleATester : copyOfResultsCoupling) {
                		
                		if(coupleATester.getClasses().equals(key) && !Objects.equals(coupleATester.getValue(), couple.getValue())) {
                            System.out.println(coupleATester.getValue());
                			value = coupleATester.getValue()+couple.getValue();


                		}
                	}
                    if (value!=0) {
                        System.out.println(value);
//                        copyOfResultsCoupling.remove(couple);
                        copyOfResultsCouplingTempo.add(new Couplage(key, value));
                    }
                    else
                    {
                        copyOfResultsCouplingTempo.add(couple);
                    }
            	}

                HashSet<Couplage> ensemble = new HashSet<>(copyOfResultsCouplingTempo);
                copyOfResultsCouplingTempo.clear();
                copyOfResultsCouplingTempo.addAll(ensemble);

//                copyOfResultsCouplingTempo.forEach(e->{
//                    System.err.println( e.getClasses() +"   "+e.getValue());
//                });


//        System.err.println("***");
//            	copyOfResultsCoupling = copyOfResultsCouplingTempo;

                return copyOfResultsCouplingTempo;

//            	//Affichage
//                for (Couplage couplage : copyOfResultsCoupling) {
//                    System.out.println(couplage.getClasses() + " - " + couplage.getValue());
//                }

//        System.err.println(myGraph.toString());

    }

    
    
    @Override
    public void recursiveGraphFunction(Set<Noeud> enfants, HashMap<String, PetitArbre> grapheNonTrie, mxGraph graph, Object parent, Object vp, String idP) {}
}
