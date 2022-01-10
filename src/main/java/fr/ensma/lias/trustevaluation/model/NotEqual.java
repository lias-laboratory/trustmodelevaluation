package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public class NotEqual extends AbstractComparisonOperator {

	public boolean eval(IValueInt leftElement, IValueInt rightElement) {
		return leftElement.getValue() != rightElement.getValue();
	}

	public String toString() {
		return "!=";
	}
}
