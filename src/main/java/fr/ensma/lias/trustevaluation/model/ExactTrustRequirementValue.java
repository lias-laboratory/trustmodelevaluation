package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public class ExactTrustRequirementValue extends TrustRequirementValue {

	public ExactTrustRequirementValue(int pExactValue) {
		super(pExactValue);
	}

	@Override
	public int getValue() {
		return this.value;
	}	
}
