package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public class ApplicationValue extends ConstraintValue {

	public ApplicationValue(int pValue) {
		super(pValue);
	}

	@Override
	public int getValue() {
		return this.value;
	}	
}
