package Exception;

public class JRENotFoundException extends Exception{

	private static String jreNotFound = "Attention la variable system 'java.home' "
			+ "n'est pas correctement initialisée sur votre system ou est null"; 
	
	public JRENotFoundException() {
		super(jreNotFound); 
	}
}
