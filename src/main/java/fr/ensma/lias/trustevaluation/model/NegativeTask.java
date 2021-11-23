package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public class NegativeTask extends Task {
	
	public NegativeTask(String pName, NegativeAction pAction, int value) {
		super(pName);
		
		this.decomposition = Decomposition.LEAF;
		
		this.cause = new Cause();
		this.cause.action = pAction;
		this.cause.value = value;
	}
	
	public NegativeTask(String pName) {
		super(pName);
		
		this.decomposition = Decomposition.LEAF;
		
		this.cause = null;
	}

	@Override
	public Cause getCause() {
		return this.cause;
	}
}
