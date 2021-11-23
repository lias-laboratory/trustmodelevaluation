package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public class ConstraintValue implements IValueInt {

	private int value;

	public ConstraintValue(int pValue) {
		this.value = pValue;
	}

	@Override
	public int getValue() {
		return this.value;
	}
}
