package fr.ensma.lias.trustmodelevaluation.model;

/**
 * @author Mickael BARON
 */
public enum Decomposition {

	SEQ("SEQ"), LEAF("LEAF"), UNKNOWN("UNK");
	
	private final String value;

	private Decomposition(String s) {
		value = s;
	}
	
	public String toString() {
		return value;
	}
}
