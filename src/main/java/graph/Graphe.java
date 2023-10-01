package graph;

import java.util.ArrayList;
import java.util.HashMap;

public class Graphe {
	
	private HashMap<String, PetitArbre> grapheNonTrie = new HashMap<String, PetitArbre>();
	private ArrayList<PetitArbre> listOfMain = new ArrayList<PetitArbre>();
	
	public Graphe() {};
	
	public HashMap<String, PetitArbre> getGrapheNonTrie() {
		return grapheNonTrie;
	}
	public void setGrapheNonTrie(HashMap<String, PetitArbre> grapheNonTrie) {
		this.grapheNonTrie = grapheNonTrie;
	}
	public ArrayList<PetitArbre> getListOfMain() {
		return listOfMain;
	}
	public void setListOfMain(ArrayList<PetitArbre> listOfMain) {
		this.listOfMain = listOfMain;
	}
	
	private void addMain(PetitArbre arbre) {
		getListOfMain().add(arbre);
	}
	
	private void addSommet(PetitArbre arbre) {
		getGrapheNonTrie().put(arbre.getParent().toStringID(), arbre);
	}
	
	
	public void checkMainOrSommet (PetitArbre arbre) {
		if (arbre.getParent().getMethodName().equals("main")) {
			addMain(arbre);
		}else {
			addSommet(arbre);
		}
	}
	
	public boolean isExist(String val) {
		return grapheNonTrie.containsKey(val);
	}
	
	public PetitArbre getPetitArbreByKey(String val) {
		return grapheNonTrie.get(val);
	}

	@Override
	public String toString() {
		return "Graphe [grapheNonTrie=" + grapheNonTrie + ", listOfMain=" + listOfMain + "]";
	}
	
	
	
}
