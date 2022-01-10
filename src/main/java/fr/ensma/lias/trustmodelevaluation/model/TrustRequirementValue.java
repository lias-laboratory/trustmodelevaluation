package fr.ensma.lias.trustmodelevaluation.model;

/**
 * @author Mickael BARON
 */
public abstract class TrustRequirementValue extends ConstraintValue {

	public TrustRequirementValue(int pValue) {
		super(pValue);
	}
	
	public abstract ExactTrustRequirementValue transformToExactValue(int value);
}
