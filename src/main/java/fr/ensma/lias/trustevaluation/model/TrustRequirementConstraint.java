package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public class TrustRequirementConstraint extends Constraint<TrustRequirementConstraintElement, TrustRequirementValue> {

	public TrustRequirementConstraint(TrustRequirementConstraintElement pElement, ComparisonOperator pOperator, TrustRequirementValue pConditionValue) {
		this.element = pElement;
		this.operator = pOperator;
		this.value = pConditionValue;
	}

	public boolean eval(IValueInt pValue) {
		return this.operator.eval(pValue, this.getConstraintValue());
	}
}
