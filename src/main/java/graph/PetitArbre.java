package graph;


import java.util.HashSet;

import java.util.ArrayList;

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
	

	/**
	 * Vérifie si un Noeud enfant n'existe pas déjà 
	 * dans la liste des noeuds enfant du petitArbre 
	 * 
	 * Si les deux éléments ont la même stringID (même nom de méthode et classe)
	 * @see Noeud#toStringID() alors son nombre d'appel est augmentée de 1
	 * Sinon il est ajoutée dans la liste des enfants du petitArbre 
	 * 
	 * @param Noeud enfant
	 * 
	 */

	public void addEnfant(Noeud enfant) {
		
			boolean existe = false;
			for(Noeud e : enfants) {
				if(e.toStringID().equals(enfant.toStringID())) {
					existe=true;
					e.ajoutAppel();
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
