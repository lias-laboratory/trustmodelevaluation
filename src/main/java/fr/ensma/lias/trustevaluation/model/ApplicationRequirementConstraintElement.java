package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public abstract class ApplicationRequirementConstraintElement extends ConstraintElement {

	public abstract boolean checkValue(IValueInt current);
}
