package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PetitArbre {

	private Noeud parent;
	private Set<Noeud> enfants = new HashSet<Noeud>();
	
	
	public PetitArbre(Noeud parent) {
		this.parent = parent;
	}
	
	
	public Noeud getParent() {
		return parent;
	}
	public void setParent(Noeud parent) {
		this.parent = parent;
	}
	public Set<Noeud> getEnfants() {
		return enfants;
	}
	public void setEnfants(Set<Noeud> enfants) {
		this.enfants = enfants;
	}
	
	public void addEnfant(Noeud enfant) {
		
			boolean existe = false;
			for(Noeud e : enfants) {
				if(e.toStringID().equals(enfant.toStringID())) {
					existe=true;
					break;
				}
			}
			if(!existe) {
				getEnfants().add(enfant);
			}
			
	
			
		
	}


	@Override
	public String toString() {
		return "PetitArbre [parent=" + parent + ", enfants=" + enfants + "]";
	}
	

	
}
