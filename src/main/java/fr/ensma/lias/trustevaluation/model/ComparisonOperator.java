package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public interface ComparisonOperator {

	boolean eval(IValueInt leftElement, IValueInt rightElement);
}
