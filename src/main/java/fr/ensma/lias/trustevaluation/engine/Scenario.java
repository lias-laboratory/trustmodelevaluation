package fr.ensma.lias.trustevaluation.engine;

import java.util.ArrayList;
import java.util.List;

import fr.ensma.lias.trustevaluation.model.ScoreElement;

/**
 * @author Mickael BARON
 */
public class Scenario {

	private List<SimulatedTask> simulatedTasks;

	private ScoreElement scoreElement;

	public Scenario() {
		simulatedTasks = new ArrayList<>();
	}

	public int getLength() {
		return simulatedTasks.size();
	}

	public void setSimulatedTasks(List<SimulatedTask> pSimulatedTasks) {
		this.simulatedTasks = pSimulatedTasks;
	}

	public SimulatedTask getSimulatedTask(int index) {
		return this.simulatedTasks.get(index);
	}

	public List<SimulatedTask> getSimulatedTasks() {
		return this.simulatedTasks;
	}

	public ScoreElement getScoreElement() {
		return scoreElement;
	}

	public void setScoreElement(ScoreElement pScoreElement) {
		this.scoreElement = pScoreElement;
	}
}
