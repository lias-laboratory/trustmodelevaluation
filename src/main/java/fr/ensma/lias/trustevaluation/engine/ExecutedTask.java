package fr.ensma.lias.trustevaluation.engine;

import fr.ensma.lias.trustevaluation.model.Cause;
import fr.ensma.lias.trustevaluation.model.TaskScoreConstraint;

/**
 * @author Mickael BARON
 */
public abstract class ExecutedTask {

	private Cause cause;

	private TaskScoreConstraint constraint;
	
	private String name;
	
	private int computedValue;
	
	public ExecutedTask(String pName, Cause pCause, TaskScoreConstraint pConstraint) {
		this.cause = pCause;
		this.constraint = pConstraint;
		this.name = pName;
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

	public int getComputedValue() {
		return computedValue;
	}

	public void setComputedValue(int computedValue) {
		this.computedValue = computedValue;
	}
}
