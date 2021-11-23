package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public class Application {

	private Action positiveAction;

	private Action negativeAction;

	private String name;

	public Application(String name, Action positiveAction, Action negativeAction) {
		super();
		this.positiveAction = positiveAction;
		this.positiveAction.setApplication(this);
		this.negativeAction = negativeAction;
		this.negativeAction.setApplication(this);
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
