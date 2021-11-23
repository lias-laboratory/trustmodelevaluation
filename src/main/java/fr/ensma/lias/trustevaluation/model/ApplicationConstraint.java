package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public class ApplicationConstraint extends Constraint<ApplicationElement, ApplicationValue> {

	public ApplicationConstraint(ApplicationElement pFeedback, ComparisonOperator pOperator, ApplicationValue pConstraint) {
		this.element = pFeedback;
		this.operator = pOperator;
		this.value = pConstraint;
	}

	public boolean eval(IValueInt pValue) {
		return this.operator.eval(pValue, this.getConstraintValue());
	}

	public boolean checkValue(IValueInt value) {
		return element.checkValue(value);
	}
}
