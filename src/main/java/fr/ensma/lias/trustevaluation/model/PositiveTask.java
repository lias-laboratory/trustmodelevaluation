package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public class PositiveTask extends Task {

	public PositiveTask(String pName, NegativeAction pAction, int value) {
		super(pName);

		this.decomposition = Decomposition.LEAF;

		this.addNewCause(pAction, value);
	}

	public PositiveTask(String pName) {
		super(pName);

		this.decomposition = Decomposition.LEAF;
	}
}
