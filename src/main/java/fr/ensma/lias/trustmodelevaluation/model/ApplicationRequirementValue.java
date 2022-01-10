package fr.ensma.lias.trustmodelevaluation.model;

/**
 * @author Mickael BARON
 */
public class ApplicationRequirementValue extends ConstraintValue {

	public ApplicationRequirementValue(int pValue) {
		super(pValue);
	}

	@Override
	public int getValue() {
		return this.value;
	}	
}
