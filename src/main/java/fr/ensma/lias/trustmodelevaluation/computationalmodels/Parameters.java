package fr.ensma.lias.trustmodelevaluation.computationalmodels;

import java.util.HashMap;
import java.util.Map;

public class Parameters {

	private Map<String, Integer> values;
	
	public Parameters() {
		this.values = new HashMap<>();
	}
	
	public boolean isExistKey(String key) {
		return values.containsKey(key);
	}
	
	public Integer getParameterValue(String key) {
		return this.values.get(key);
	}
	
	public void addParameterValue(String key, Integer value) {
		this.values.put(key, value);
	}
}
