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

public class DrawCallGraph implements DrawGraph{
    private  Map<String,Object> myCells = new HashMap<>();
    private  Map<String,Object> myArcs = new HashMap<>();
    private Graphe myGraph = new Graphe();
    public DrawCallGraph() {
    }


    public DrawCallGraph(
            Map<String, Object> myCells,
            Map<String, Object> myArcs,
            Graphe myGraph
    )
    {
        this.myCells = myCells;
        this.myArcs = myArcs;
        this.myGraph = myGraph;
        draw();
    }

    @Override
    public void draw(){
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
                            v1 = graph.insertVertex(parent, null, pa.getParent().toStringID(), 20, 20, 80, 30);

                            myCells.put(pa.getParent().toStringID(), v1);
                        } else {
                            v1 = myCells.get(pa.getParent().toStringID());
                        }

                        mxRectangle dimensions = graph.getPreferredSizeForCell(v1);

                        // Msj à jour les dimensions du vertex
                        graph.resizeCell(v1, dimensions);
                        recursiveGraphFunction(pa.getEnfants(), myGraph.getGrapheNonTrie(), graph, parent, v1, pa.getParent().toStringID());
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
            graphComponent.setPreferredSize(new Dimension(800, 800));

            frame.getContentPane().add(graphComponent);

            frame.pack();
            frame.setVisible(true);
        });
    }


    @Override
    public   void  recursiveGraphFunction(
            Set<Noeud> enfants,
            HashMap<String, PetitArbre> grapheNonTrie,
            mxGraph graph,
            Object parent,
            Object vp,
            String idP
    )
    {
        if (!enfants.isEmpty()) {
            enfants.forEach(e ->
            {
                Object ve = null;
                PetitArbre petitArbre = grapheNonTrie.get(e.toStringID());
                if(!myCells.containsKey(e.toStringID()))
                {
                    ve = graph.insertVertex(
                            parent,
                            null,
                            e.toStringID(),
                            20,
                            20,
                            80,
                            30
                    );
                    myCells.put(e.toStringID(), ve);
                }
                else
                {
                    ve = myCells.get(e.toStringID());
                }

                mxRectangle dimensions2 = graph.getPreferredSizeForCell(ve);
                // Msj à jour les dimensions du vertex
                graph.resizeCell(ve, dimensions2);
                Object a = null;
                if(!myArcs.containsKey(e.toStringID()+"-"+ idP))
                {
                    a = graph.insertEdge(
                            parent,
                            null,
                            e.getNbAppel(),
                            vp,
                            ve
                    );
                }
                else
                {
                    a = myArcs.get(e.toStringID()+"-"+ idP);
                }
                myArcs.put(
                        e.toStringID()+"-"+ idP,
                        a
                );
                if(petitArbre!=null && !e.toStringID().equals(idP))
                {
                    recursiveGraphFunction(
                            petitArbre.getEnfants(),
                            grapheNonTrie,
                            graph,
                            parent,
                            ve,
                            e.toStringID()
                    );
                }
            });
        }
    }

}
