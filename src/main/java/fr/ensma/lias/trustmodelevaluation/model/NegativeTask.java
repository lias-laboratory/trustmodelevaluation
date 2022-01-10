package fr.ensma.lias.trustmodelevaluation.model;

/**
 * @author Mickael BARON
 */
public class NegativeTask extends Task {

	public NegativeTask(String pName, NegativeAction pAction, int value) {
		super(pName);

		this.decomposition = Decomposition.LEAF;

		this.addNewCause(pAction, value);
	}

	public NegativeTask(String pName) {
		super(pName);

		this.decomposition = Decomposition.LEAF;
	}
}
