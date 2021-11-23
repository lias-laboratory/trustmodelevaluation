package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public abstract class ScoreConstraint extends Constraint<ScoreElement, ScoreValue> {

	public ScoreConstraint(ScoreElement pElement, ComparisonOperator pOperator, ScoreValue pConditionValue) {
		this.element = pElement;
		this.operator = pOperator;
		this.value = pConditionValue;
	}

	public boolean eval(IValueInt pValue) {
		return this.operator.eval(pValue, this.getConstraintValue());
	}
}
