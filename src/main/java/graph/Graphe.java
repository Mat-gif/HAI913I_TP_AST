package graph;

import java.util.ArrayList;
import java.util.HashMap;

public class Graphe {


	
	/**
	 * Attribut
	 */
	private HashMap<String, PetitArbre> grapheNonTrie = new HashMap<String, PetitArbre>();
	private ArrayList<PetitArbre> listOfMain = new ArrayList<PetitArbre>();
	private int countTotalCall = 0;
	/**
	 * Constructeur vide
	 */
	public Graphe() {};
	
	/**
	 * Getter
	 */
	public HashMap<String, PetitArbre> getGrapheNonTrie() {
		return grapheNonTrie;
	}
	
	public void setGrapheNonTrie(HashMap<String, PetitArbre> grapheNonTrie) {
		this.grapheNonTrie = grapheNonTrie;
	}
	
	public ArrayList<PetitArbre> getListOfMain() {
		return listOfMain;
	}
	
	/**
	 * Setter
	 */

	public void setListOfMain(ArrayList<PetitArbre> listOfMain) {
		this.listOfMain = listOfMain;
	}

	private void addMain(PetitArbre arbre) {
		getListOfMain().add(arbre);
	}

	private void addSommetMethod(PetitArbre arbre) {
		getGrapheNonTrie().put(arbre.getParent().toStringID(), arbre);
	}
	private void addSommetClass(PetitArbre arbre) {
		getGrapheNonTrie().put(arbre.getParent().getClasseName(), arbre);
	}

	
	
	
	/**
	 * Vérifie si l'instance de petit arbre à comme nom de Méthode main
	 * Si oui elle est ajoutée dans la liste du main contenant les racines 
	 * de l'arbre (point d'entrée de l'ast)
	 * Si non elle est ajoutée comme sommet du graphe
	 * 
	 * @param arbre l'instance de PetitArbre à tester
	 */

	public void addMethod(PetitArbre arbre) {
			addSommetMethod(arbre);
	}

	public void addClass(PetitArbre arbre) {
		addSommetClass(arbre);
		countTotalCall = getCountTotalCall() + arbre.getCountTotalByClass();
	}

	
	/**
	 * Vérifie si une clé existe dans le graph non trié.
	 * 
	 * @param val La clé à rechercher.
	 * @return {@code true} si la clé existe dans la HashMap, sinon {@code false}.
	 */
	public boolean isExist(String val) {
		return grapheNonTrie.containsKey(val);
	}
	
	/**
	 * Récupère un PetitArbre associé à une clé dans le graph non trié.
	 * 
	 * @param val La clé pour laquelle récupérer le PetitArbre.
	 * @return Le PetitArbre associé à la clé, ou {@code null} si la clé n'existe pas.
	 */

	public PetitArbre getPetitArbreByKey(String val) {
		return grapheNonTrie.get(val);
	}

	/**
	 * ToString
	 */

	public String toString() {
		return "Graphe [grapheNonTrie=" + grapheNonTrie + ", listOfMain=" + listOfMain + "]";
	}


	public void deleteMyPetitArbre(String id) {
		this.grapheNonTrie.remove(id);
	}

	public int getCountTotalCall() {
		return countTotalCall;
	}





}
