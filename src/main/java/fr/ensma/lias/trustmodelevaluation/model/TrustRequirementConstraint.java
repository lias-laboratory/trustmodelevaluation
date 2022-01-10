package fr.ensma.lias.trustmodelevaluation.model;

/**
 * @author Mickael BARON
 */
public class TrustRequirementConstraint extends Constraint<TrustRequirementConstraintElement, TrustRequirementValue>
		implements Cloneable {

	public TrustRequirementConstraint(TrustRequirementConstraintElement pElement, ComparisonOperator pOperator,
			TrustRequirementValue pConditionValue) {
		this.element = pElement;
		this.operator = pOperator;
		this.value = pConditionValue;
	}

	public boolean eval(IValueInt pValue) {
		return this.operator.eval(pValue, this.getConstraintValue());
	}

	public TrustRequirementConstraint transform(int value) {
		TrustRequirementConstraint clone = null;
		try {
			clone = (TrustRequirementConstraint) super.clone();
			clone.element = this.element;
			clone.operator = (ComparisonOperator)this.operator.clone();
			clone.value = this.value.transformToExactValue(value);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return clone;
	}
	
	public String toString() {
		return ("score" + " " + this.operator.toString() + " " + this.value.toString());
	}
}
