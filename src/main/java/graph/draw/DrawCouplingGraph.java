package graph.draw;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxGraph;
import graph.Graphe;
import graph.Noeud;
import graph.PetitArbre;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DrawCouplingGraph implements DrawGraph{
    private  Map<String,Object> myCells = new HashMap<>();
    private  Map<String,Object> myArcs = new HashMap<>();
    private Graphe myGraph = new Graphe();
    private   Map<String,Float> resultsCoupling ;

    public DrawCouplingGraph(
            Map<String, Object> myCells,
            Map<String, Object> myArcs,
            Graphe myGraph,
            Map<String,Float> resultsCoupling
    )
    {
        this.myCells = myCells;
        this.myArcs = myArcs;
        this.myGraph = myGraph;
        this.resultsCoupling = resultsCoupling;
        draw();
    }
    @Override
    public void draw() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Graphe de Couplage");
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
                        if (!myCells.containsKey(pa.getParent().getClasseName())){
                            v1 = graph.insertVertex(parent, null, pa.getParent().getClasseName(), 20, 20, 80, 30);

                            myCells.put(pa.getParent().getClasseName(), v1);
                        } else {
                            v1 = myCells.get(pa.getParent().getClasseName());
                        }

                        mxRectangle dimensions = graph.getPreferredSizeForCell(v1);

                        // Msj à jour les dimensions du vertex
                        graph.resizeCell(v1, dimensions);
                        recursiveGraphFunction(pa.getEnfants(), myGraph.getGrapheNonTrie(), graph, parent, v1, pa.getParent().getClasseName());
                    }
                };


                // Utilisez l'algorithme hierarchique pour organiser les vertex
                mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
                // orientation verticale
                layout.setOrientation(SwingConstants.NORTH);


                layout.setIntraCellSpacing(100); // Espacement entre les nœuds dans la même couche
                layout.setInterRankCellSpacing(150); // Espacement entre les couches
                layout.execute(parent);

            } finally {
                graph.getModel().endUpdate();
            }

            mxGraphComponent graphComponent = new mxGraphComponent(graph);
            graphComponent.setPreferredSize(new Dimension(1000, 1000));

            frame.getContentPane().add(graphComponent);

            frame.pack();
            frame.setVisible(true);
            // Centrer le graphe dans le composant
            graphComponent.setCenterPage(true);
        });
    }

    @Override
    public void recursiveGraphFunction(Set<Noeud> enfants, HashMap<String, PetitArbre> grapheNonTrie, mxGraph graph, Object parent, Object vp, String idP) {
        if (!enfants.isEmpty()) {
            enfants.forEach(e ->
            {
                Object ve = null;
                PetitArbre petitArbre = grapheNonTrie.get(e.getClasseName());
                if(!myCells.containsKey(e.getClasseName()))
                {
                    ve = graph.insertVertex(
                            parent,
                            null,
                            e.getClasseName(),
                            20,
                            20,
                            80,
                            30
                    );
                    myCells.put(e.getClasseName(), ve);
                }
                else
                {
                    ve = myCells.get(e.getClasseName());
                }

                mxRectangle dimensions2 = graph.getPreferredSizeForCell(ve);
                // Msj à jour les dimensions du vertex
                graph.resizeCell(ve, dimensions2);
                Object a = null;
                if(!myArcs.containsKey(e.getClasseName()+"-"+ idP))
                {
                    String key1 = String.format("(%s-%s)",idP, e.getClasseName());
                    String key2 = String.format("(%s-%s)", e.getClasseName(), idP);

                    String valueOfCoupling = resultsCoupling.get(key1).toString();
                    if (valueOfCoupling.isEmpty()){
                        valueOfCoupling = resultsCoupling.get(key2).toString();
                    }
                  a = graph.insertEdge(
                            parent,
                            null,
                          valueOfCoupling,
                            vp,
                            ve
                    );
                }
                else
                {
                    a = myArcs.get(e.getClasseName()+"-"+ idP);
                }
                myArcs.put(
                        e.getClasseName()+"-"+ idP,
                        a
                );
                if(petitArbre!=null && !e.getClasseName().equals(idP))
                {
                    recursiveGraphFunction(
                            petitArbre.getEnfants(),
                            grapheNonTrie,
                            graph,
                            parent,
                            ve,
                            e.getClasseName()
                    );
                }
            });
        }
    }
}
