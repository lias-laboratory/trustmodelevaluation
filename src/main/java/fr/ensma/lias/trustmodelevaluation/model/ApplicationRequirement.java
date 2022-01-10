package fr.ensma.lias.trustmodelevaluation.model;

/**
 * @author Mickael BARON
 */
public class ApplicationRequirement {

	private Action positiveAction;

	private Action negativeAction;

	private String name;

	public ApplicationRequirement(String name, Action positiveAction, Action negativeAction) {
		super();
		this.positiveAction = positiveAction;
		this.positiveAction.setApplicationRequirement(this);
		this.negativeAction = negativeAction;
		this.negativeAction.setApplicationRequirement(this);
		this.name = name;
	}

	public Action getPositiveAction() {
		return positiveAction;
	}

	public Action getNegativeAction() {
		return negativeAction;
	}

	public String getName() {
		return name;
	}
}
