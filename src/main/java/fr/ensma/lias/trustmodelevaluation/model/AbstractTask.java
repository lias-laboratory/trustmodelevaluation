package fr.ensma.lias.trustmodelevaluation.model;

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
