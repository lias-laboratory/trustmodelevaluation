package fr.ensma.lias.trustmodelevaluation.model;

/**
 * @author Mickael BARON
 */
public abstract class ConstraintValue implements IValueInt {

	protected Integer value = null;

	public ConstraintValue(int pValue) {
		this.value = pValue;
	}

	@Override
	public int getValue() {
		return this.value;
	}	
}
