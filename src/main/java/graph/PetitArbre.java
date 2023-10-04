package graph;

import java.util.HashSet;
import java.util.Set;

public class PetitArbre {

	/**
	 * Attribut
	 */
	private Noeud parent;
	private Set<Noeud> enfants = new HashSet<Noeud>();
	
	
	/**
	 * Constructeur 
	 */
	public PetitArbre(Noeud parent) {
		this.parent = parent;
	}
	
	/**
	 * Getter
	 */
	public Noeud getParent() {
		return parent;
	}

	public Set<Noeud> getEnfants() {
		return enfants;
	}
	
	/**
	 * Setter
	 */
	public void setParent(Noeud parent) {
		this.parent = parent;
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
			for(Noeud enfantExisting : getEnfants()) {
				if(enfantExisting.toStringID().equals(enfant.toStringID())) {
					existe=true;
					break;
				}
			}
			if(!existe) {
				getEnfants().add(enfant);
			}
	}


	/**
	 * ToString
	 */
	public String toString() {
		return "PetitArbre [parent=" + parent + ", enfants=" + enfants + "]";
	}
	

	
}
