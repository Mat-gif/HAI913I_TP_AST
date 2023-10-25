package graph.draw;

import com.mxgraph.view.mxGraph;
import graph.Noeud;
import graph.PetitArbre;

import java.util.HashMap;
import java.util.Set;

public interface DrawGraph {
    void draw();
    void  recursiveGraphFunction(
            Set<Noeud> enfants,
            HashMap<String, PetitArbre> grapheNonTrie,
            mxGraph graph,
            Object parent,
            Object vp,
            String idP
    );
}
