package graph;

import java.util.HashSet;
import java.util.Set;

public class PetitArbre {

	private Noeud parent; // Noeud parent de l'arbre : c'est le noeud qui prend le nom de la classe
	private Set<Noeud> enfants = new HashSet<Noeud>();// Noeuds enfants de l'arbre : ce sont les noeuds qui prennent le nom des méthodes

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
		for (Noeud e : enfants) {
			if (e.toStringID().equals(enfant.toStringID())) {
				existe = true;
				break;
			}
		}
		if (!existe) {
			getEnfants().add(enfant);
		}

	}

	@Override
	public String toString() {
		return "PetitArbre [parent=" + parent + ", enfants=" + enfants + "]";
	}

}
