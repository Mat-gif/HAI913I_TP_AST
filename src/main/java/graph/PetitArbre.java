package graph;

import java.util.ArrayList;

public class PetitArbre {

	private Noeud parent;
	private ArrayList<Noeud> enfants = new ArrayList<Noeud>();
	
	
	public PetitArbre(Noeud parent) {
		this.parent = parent;
	}
	
	
	public Noeud getParent() {
		return parent;
	}
	public void setParent(Noeud parent) {
		this.parent = parent;
	}
	public ArrayList<Noeud> getEnfants() {
		return enfants;
	}
	public void setEnfants(ArrayList<Noeud> enfants) {
		this.enfants = enfants;
	}
	
	public void addEnfant(Noeud enfant) {
		getEnfants().add(enfant);
	}
	

	
}
