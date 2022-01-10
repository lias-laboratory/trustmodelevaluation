package fr.ensma.lias.trustmodelevaluation.model;

/**
 * @author Mickael BARON
 */
public abstract class ApplicationRequirementConstraintElement extends ConstraintElement {

	public abstract boolean checkValue(IValueInt current);
}
