package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public abstract class ApplicationElement extends ConstraintElement {

	public abstract boolean checkValue(IValueInt current);
}
