package ui.controller;

import java.util.HashMap;
import java.util.Map;

public class LabelMap 
{
	
	private Map<String,String> basicAnalysisMap = new HashMap<>();
	private Map<String,String> additionalAnalysisMap = new HashMap<>();
	
	public LabelMap()
	{
		basicAnalysisMap.put("1", "Nombre de	classes de l’application");
		basicAnalysisMap.put("2", "Nombre de	lignes de code de l’application");
		basicAnalysisMap.put("3", "Nombre total de méthodes de l’application");
		basicAnalysisMap.put("4", "Nombre total de packages de l’application");
		basicAnalysisMap.put("5", "Nombre moyen de méthodes par classe");
		basicAnalysisMap.put("6", "Nombre moyen de lignes de code par méthode");
		basicAnalysisMap.put("7", "Nombre moyen d’attributs par classe");
		additionalAnalysisMap.put("8", " Les 10% des classes avec le plus méthodes (a)");
		additionalAnalysisMap.put("9", "Les 10% des classes avec le plus d’attribut (b)");
		additionalAnalysisMap.put("10", "Les classes des 	deux catégories précédentes (a, b)");
		additionalAnalysisMap.put("11", "Les classes qui possèdent plus de X méthodes");
		additionalAnalysisMap.put("12", "Les 10%	des	méthodes avec le plus de lignes par classe");
		additionalAnalysisMap.put("13", "Le nombre maximal de	paramètres");
	}

	public Map<String, String> getBasicAnalysisMap() {
		return basicAnalysisMap;
	}
	
	public String getBasicAnalysisByID(String key) {
		return basicAnalysisMap.get(key);
	}


	public Map<String, String> getAdditionalAnalysisMap() {
		return additionalAnalysisMap;
	}
	
	
	public String getAdditionalAnalysisByID(String key) {
		return additionalAnalysisMap.get(key);
	}
	
	
	public void setAdditionalAnalysis11(int n) {
		additionalAnalysisMap.put("11", "Les classes qui possèdent plus de "+n+" méthodes");;
	}
	
	
}
