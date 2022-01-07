package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public interface ComparisonOperator extends Cloneable {

	boolean eval(IValueInt leftElement, IValueInt rightElement);
	
	Object clone();
}
