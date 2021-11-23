package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public class GreaterEqual implements ComparisonOperator {

	public boolean eval(IValueInt leftElement, IValueInt rightElement) {
		return leftElement.getValue() >= rightElement.getValue();
	}
}
