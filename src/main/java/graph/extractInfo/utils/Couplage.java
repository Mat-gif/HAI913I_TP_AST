package graph.extractInfo.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Couplage {

    private Set<String> classes =new HashSet<>();

    private Float value;


    public Couplage(Set<String> classes, Float value) {
		super();
		this.classes = classes;
		this.value = value;
	}
    
    public Couplage() {}
    

	public Set<String> getClasses() {
        return classes;
    }

    public void setClasses(Set<String> classes) {
        this.classes = classes;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }
}
