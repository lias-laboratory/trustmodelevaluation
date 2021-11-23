package fr.ensma.lias.trustevaluation.engine;

import java.util.ArrayList;
import java.util.List;

import fr.ensma.lias.trustevaluation.model.ScoreConstraint;

/**
 * @author Mickael BARON
 */
public class Scenario {

	private List<ExecutedTask> executedTasks;

	private ScoreConstraint globalScoreConstraint;

	public Scenario() {
		executedTasks = new ArrayList<>();
	}

	public int getLength() {
		return executedTasks.size();
	}

	public ScoreConstraint getGlobalScoreConstraint() {
		return this.globalScoreConstraint;
	}
	
	public void setExecutedTasks(List<ExecutedTask> pExecutedTasks) {
		this.executedTasks = pExecutedTasks;
	}
	
	public ExecutedTask getExecutedTask(int index) {
		return this.executedTasks.get(index);
	}
	
	public List<ExecutedTask> getExecutedTasks() {
		return this.executedTasks;
	}
}
