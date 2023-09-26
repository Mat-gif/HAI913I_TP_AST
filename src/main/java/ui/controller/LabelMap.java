package ui.controller;

import java.util.HashMap;
import java.util.Map;

public class LabelMap extends HashMap<String,Map<String,String>>
{
	public LabelMap()
	{
		new HashMap<String,Map<String,String>>();

		Map<String, String> labels1 = new HashMap<>();
		labels1.put("1", "Nombre de	classes de l’application");
		labels1.put("2", "Nombre de	lignes de code de l’application");
		labels1.put("3", "Nombre total de méthodes de l’application");
		labels1.put("4", "Nombre total de packages de l’application");
		labels1.put("5", "Nombre moyen de méthodes par classe");
		labels1.put("6", "Nombre moyen de lignes de code par méthode");
		labels1.put("7", "Nombre moyen d’attributs par classe");
		Map<String, String> labels2 = new HashMap<>();
		labels2.put("8", " Les 10% des classes avec le plus méthodes (a)");
		labels2.put("9", "Les 10% des classes avec le plus d’attribut (b)");
		labels2.put("10", "Les classes des 	deux catégories précédentes (a, b)");
		labels2.put("11", "Les classes qui possèdent plus de X méthodes");
		labels2.put("12", "Les 10%	des	méthodes avec le plus de lignes par classe");
		labels2.put("13", "Le nombre maximal de	paramètres");
		
		this.put("Analyse de base", labels1);
		this.put("Analyse complémentaire", labels2);
	}
	
	
	public Map<String,String> getAnalyseDeBase()
	{
		return this.get("Analyse de base");
	}
	
	public int sizeAnalyseDeBase()
	{
		return getAnalyseDeBase().size();
	}
	
	public String[] getAnalyseDeBaseKeys()
	{
		return (String[]) getAnalyseDeBase().keySet().toArray();
	}
	public String[] getAnalyseDeBaseValues() {
	    return getAnalyseDeBase().values().toArray(new String[0]);
	}

	
	
	public Map<String,String> getAnalyseComplementaire()
	{
		return this.get("Analyse complémentaire");
	}
	
	public String[] getAnalyseComplementaireKeys()
	{
		return (String[]) getAnalyseComplementaire().keySet().toArray();
	}
	
	public String[] getAnalyseComplementaireValues()
	{
		return (String[]) getAnalyseComplementaire().values().toArray(new String[0]);
	}
	
	
	public int sizeAnalyseComplementaire()
	{
		return getAnalyseComplementaire().size();
	}
	
	public void setXmethode11(int i) {
		
		this.get("Analyse complémentaire").put("11", "Les classes qui possèdent plus de "+i+" méthodes");
		
	}
	
}
