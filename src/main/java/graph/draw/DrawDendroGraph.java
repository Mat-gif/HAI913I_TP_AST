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
import java.util.*;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class DrawDendroGraph implements DrawGraph{
    private Map<Set,Object> myCells = new HashMap<>();
    private Map<String,Object> myArcs = new HashMap<>();
    private Map<Set<String>, List<Set<String>>> inter = new HashMap<>();

    private ArrayList<Couplage> copyOfResultsCoupling;
    private Map<String,Object> cells = new HashMap<>();


    private mxGraph graph = new mxGraph();
    private Graphe myGraph = new Graphe();
    private Object parent = graph.getDefaultParent();
    private ArrayList<Couplage> resultsCoupling ;

    public DrawDendroGraph(
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



            while(copyOfResultsCoupling.size()>=1) {

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

        if(maxKey.size()==2)
        {

            Object  v2 = graph.insertVertex(parent, null, maxValue, 50,50, 80, 30);

            mxRectangle dimensions = graph.getPreferredSizeForCell(v2);

            // Msj à jour les dimensions du vertex
            graph.resizeCell(v2, dimensions);

            for (String key : maxKey)
            {
                Object  v1 = cells.get(key);
                Object a = graph.insertEdge(
                        parent,
                        null,
                        "",
                        v1,
                        v2
                );
            }


            cells.put(maxKey.toString(),v2);


        }
        else {


            Object  v2 = graph.insertVertex(parent, null, maxValue, 50,50, 80, 30);

            mxRectangle dimensions = graph.getPreferredSizeForCell(v2);

            // Msj à jour les dimensions du vertex
            graph.resizeCell(v2, dimensions);


            List<Set<String>> yeah = inter.get(maxKey);
            System.err.println(yeah);

            for (Set<String> set : yeah){
//            System.err.println("********");

                if (set.size()==1){
                    for (String kk : set) {
                        Object v1 = cells.get(kk);
                        cells.put(maxKey.toString(),v2);
                        Object a = graph.insertEdge(
                                parent,
                                null,
                                "",
                                v1,
                                v2
                        );
                    }
                }
                else {
                    Object v1 = cells.get(set.toString());
                    cells.put(maxKey.toString(),v2);
                    Object a = graph.insertEdge(
                            parent,
                            null,
                            "",
                            v1,
                            v2
                    );
                }
            }
//
//            cells.put(maxKey.toString(),v2);
//
//            System.err.println(inter);
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



//                            System.err.println("maxKey");
//                            System.err.println(maxKey);
//                            System.err.println(couple.getClasses());
//                            System.err.println(olalala);

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
            	
            	/////lier cellule filles et la "cellule" parent

//                System.err.println(maxKey);
//                System.err.println(maxValue);

//                cells.put(classe,v1);

            	myCells.put(maxKey, null);
//                for (String t : maxKey)
//                {
//                    Set<String> test = new HashSet<>();
//                    test.add(t);
//                    myCells.remove(test);
//                }

//                System.err.println(myCells.toString());
            	/////
            	/////
            	/////
            	/////
            	/////
            	
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
