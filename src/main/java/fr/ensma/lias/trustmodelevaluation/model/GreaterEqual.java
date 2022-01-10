package fr.ensma.lias.trustmodelevaluation.model;

/**
 * @author Mickael BARON
 */
public class GreaterEqual extends AbstractComparisonOperator {

	public boolean eval(IValueInt leftElement, IValueInt rightElement) {
		return leftElement.getValue() >= rightElement.getValue();
	}
	
	public String toString() {
		return ">=";
	}
}
