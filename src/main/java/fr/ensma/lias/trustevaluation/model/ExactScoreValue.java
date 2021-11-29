package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public class ExactScoreValue extends ScoreValue {

	public ExactScoreValue(int pExactValue) {
		super(pExactValue);
	}

	@Override
	public int getValue() {
		return this.value;
	}	
}
