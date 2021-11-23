package fr.ensma.lias.trustevaluation.engine;

import fr.ensma.lias.trustevaluation.model.Cause;
import fr.ensma.lias.trustevaluation.model.TaskScoreConstraint;

/**
 * @author Mickael BARON
 */
public class PositiveExecutedTask extends ExecutedTask {

	public PositiveExecutedTask(String pName, Cause pCause, TaskScoreConstraint pConstraint) {
		super(pName, pCause, pConstraint);
	}
}
