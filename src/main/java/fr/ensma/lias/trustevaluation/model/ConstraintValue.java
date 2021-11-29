package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public abstract class ConstraintValue implements IValueInt {

	protected Integer value = null;

	public ConstraintValue(int pValue) {
		this.value = pValue;
	}

	public abstract int getValue();
}
