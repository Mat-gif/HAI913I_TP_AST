package graph.extractInfo.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Resultats {
    public Resultats() {
    }

    public ArrayList<Couplage> getForDendo() {
        return forDendo;
    }

    public void setForDendo(ArrayList<Couplage> forDendo) {
        this.forDendo = forDendo;
    }

    public Map<String, Float> getForCouplingG() {
        return forCouplingG;
    }

    public void setForCouplingG(Map<String, Float> forCouplingG) {
        this.forCouplingG = forCouplingG;
    }

    private ArrayList<Couplage> forDendo = new ArrayList<>();

    private Map<String,Float> forCouplingG = new HashMap<>();

}
