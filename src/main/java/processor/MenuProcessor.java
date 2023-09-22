package processor;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MenuProcessor {
	
	private MyProcessor myProcessor;
	

	public MenuProcessor(String my_path) {
		super();
		this.myProcessor= new MyProcessor(my_path);
	}
	
	
	public Map<String,Integer>  selectBasicAnalytics(HashSet<String> methodsForProcessor)
	{
		Map<String,Integer> results = new HashMap<>();
		
		methodsForProcessor.forEach(a->{
			try {
				switch (a) {
				case "1":
					results.put(a, myProcessor.countClassesInProject());
					break;
				case "2":
					results.put(a, myProcessor.countLinesCodeInProject());
					break;
				case "3":
					results.put(a, myProcessor.countMethodInProject());
					break;
				case "4":
					results.put(a, myProcessor.countPackagesInProject());
					break;
				case "5":
					results.put(a, myProcessor.meanMethodsPerClass());
					break;
				case "6":
					results.put(a, myProcessor.meanLinesPerMethod());
					break;
				case "7":
					results.put(a, myProcessor.meanFieldPerClass());
					break;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		return results;
	}
	
	

	public Map<String,Map<String, Integer>>  selectGetTopClasses(HashSet<String> methodsForProcessor)
	{
		
		 Map<String,Map<String, Integer>> results = new HashMap<>();
		methodsForProcessor.forEach(a->{
			try {
				switch (a) {
				case "8":
					results.put(a, myProcessor.getTopClassesByMethodsCount());
					break;
				case "9":
					results.put(a, myProcessor.getTopClassesByFieldsCount());
					break;
				case "12":
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		return results;
	};
	
	
	public Map<String,HashSet<String>> selectOther(HashSet<String> methodsForProcessor, int n)
	{
		Map<String,HashSet<String>>  results = new HashMap<>();
		
		methodsForProcessor.forEach(a->{
			try {
				switch (a) {
				case "10":
						results.put(a, myProcessor.getTopClassByMethodsAndField());
					break;
				case "11":
					results.put(a, myProcessor.getTopClassWithXGivenMethods(n));
					break;
		
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return results;
	}

	public Map<String,HashSet<String>> selectOther(HashSet<String> methodsForProcessor)
	{
		Map<String,HashSet<String>>  results = new HashMap<>();
		
		methodsForProcessor.forEach(a->{
			try {
				switch (a) {
				case "10":
						results.put(a, myProcessor.getTopClassByMethodsAndField());
					break;
		
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return results;
	}


	public MyProcessor getMyProcessor() {
		return myProcessor;
	}


	

}
