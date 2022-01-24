package fr.ensma.lias.trustmodelevaluation.model;

/**
 * @author Mickael BARON
 */
public class Reason {

	protected Action action;
	
	protected int value;

	public Reason() {
		
	}
	
	public Reason(Action p) {
		this.action = p;
	}
	
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
