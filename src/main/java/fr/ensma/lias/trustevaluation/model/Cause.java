package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public class Cause {

	protected Action action;
	
	protected int value;

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
