package fr.ensma.lias.trustevaluation.model;

import java.util.List;

/**
 * @author Mickael BARON
 */
public class AbstractTask extends Task {

	public AbstractTask(String pName) {
		super(pName);
	}

	@Override
	public List<Reason> getReasons() {
		return null;
	}
}
