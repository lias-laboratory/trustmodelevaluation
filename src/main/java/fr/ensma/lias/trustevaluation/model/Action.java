package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public abstract class Action {

	private ApplicationRequirementConstraint constraint;

	private String informalDescription;

	private ApplicationRequirement reference;

	public Action() {
	}

	public Action(ApplicationRequirementConstraint pConstraint) {
		this.constraint = pConstraint;
	}

	public ApplicationRequirementConstraint getConstraint() {
		return constraint;
	}

	public String getInformalDescription() {
		return informalDescription;
	}

	public void setInformalDescription(String informalDescription) {
		this.informalDescription = informalDescription;
	}

	public void setApplicationRequirement(ApplicationRequirement pParent) {
		this.reference = pParent;
	}

	public ApplicationRequirement getApplicationRequirement() {
		return this.reference;
	}
}
