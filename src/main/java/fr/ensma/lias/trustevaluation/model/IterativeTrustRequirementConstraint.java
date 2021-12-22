package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 * 
 * Constraint defined on the last iterate element.
 */
public class IterativeTrustRequirementConstraint extends TrustRequirementConstraint {

	public IterativeTrustRequirementConstraint(TrustRequirementConstraintElement pElement, ComparisonOperator pOperator, TrustRequirementValue pConditionValue) {
		super(pElement, pOperator, pConditionValue);
	}
}
