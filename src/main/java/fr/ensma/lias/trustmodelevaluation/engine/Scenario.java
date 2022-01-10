package fr.ensma.lias.trustmodelevaluation.engine;

import java.util.ArrayList;
import java.util.List;

import fr.ensma.lias.trustmodelevaluation.model.TrustRequirementConstraintElement;

/**
 * @author Mickael BARON
 */
public class Scenario {

	private List<SimulatedTask> simulatedTasks;

	private TrustRequirementConstraintElement scoreElement;

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

	public TrustRequirementConstraintElement getScoreElement() {
		return scoreElement;
	}

	public void setScoreElement(TrustRequirementConstraintElement pScoreElement) {
		this.scoreElement = pScoreElement;
	}
}
