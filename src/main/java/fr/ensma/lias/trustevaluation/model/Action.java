package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public abstract class Action {

	private ApplicationConstraint constraint;

	private String informalDescription;

	private Application parent;

	public Action() {
	}

	public Action(ApplicationConstraint pConstraint) {
		this.constraint = pConstraint;
	}

	public ApplicationConstraint getConstraint() {
		return constraint;
	}

	public String getInformalDescription() {
		return informalDescription;
	}

	public void setInformalDescription(String informalDescription) {
		this.informalDescription = informalDescription;
	}

	public void setApplication(Application pParent) {
		this.parent = pParent;
	}

	public Application getApplication() {
		return this.parent;
	}
}
