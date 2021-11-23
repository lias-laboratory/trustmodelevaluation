package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public class AbstractTask extends Task {

	public AbstractTask(String pName) {
		super(pName);
	}

	@Override
	public Cause getCause() {
		return null;
	}
}
