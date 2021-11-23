package fr.ensma.lias.trustevaluation.model;

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
}
