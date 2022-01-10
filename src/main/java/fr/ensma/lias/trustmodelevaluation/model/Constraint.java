package fr.ensma.lias.trustmodelevaluation.model;

/**
 * @author Mickael BARON
 */
public abstract class Constraint<T extends ConstraintElement, U extends ConstraintValue> {

	protected T element;
	
	protected U value;
	
	protected ComparisonOperator operator;
	
	public T getConstraintElement() {
		return element;
	}
	
	public U getConstraintValue() {
		return value;
	}
	
	public void setConstraintValue(U p) {
		this.value = p;
	}
	
	public ComparisonOperator getOperator() {
		return this.operator;
	}
}
