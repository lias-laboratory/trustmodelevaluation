package fr.ensma.lias.trustevaluation.engine;

import fr.ensma.lias.trustevaluation.model.Cause;
import fr.ensma.lias.trustevaluation.model.TaskScoreConstraint;

/**
 * @author Mickael BARON
 */
public class SimulatedTask {

	private Cause cause;

	private TaskScoreConstraint constraint;

	private String name;

	private boolean isPositive = false;

	public SimulatedTask(String pName, Cause pCause, TaskScoreConstraint pConstraint, boolean pIsPositive) {
		this.cause = pCause;
		this.constraint = pConstraint;
		this.name = pName;
		this.isPositive = pIsPositive;
	}

	public Cause getCause() {
		return cause;
	}

	public TaskScoreConstraint getConstraint() {
		return this.constraint;
	}

	public String getName() {
		return this.name;
	}

	public boolean isPositive() {
		return this.isPositive;
	}
}
