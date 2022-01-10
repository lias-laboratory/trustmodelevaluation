package fr.ensma.lias.trustmodelevaluation.engine;

import java.util.Optional;

/**
 * @author Mickael BARON
 */
public class EvaluatedTask {

	private SimulatedTask simulatedTask;

	private int computedValue;

	private Optional<Boolean> constraintEvaluation = Optional.empty();

	private Optional<Integer> distance = Optional.empty();

	public EvaluatedTask(SimulatedTask pTask) {
		this.simulatedTask = pTask;
	}

	public int getComputedValue() {
		return computedValue;
	}

	public void setComputedValue(int computedValue) {
		this.computedValue = computedValue;
	}

	public SimulatedTask getSimulatedTask() {
		return simulatedTask;
	}

	public Optional<Boolean> getConstraintEvaluation() {
		return constraintEvaluation;
	}

	public void setConstraintEvaluation(Optional<Boolean> constraintEvaluation) {
		this.constraintEvaluation = constraintEvaluation;
	}

	public Optional<Integer> getDistance() {
		return distance;
	}

	public void setDistance(Optional<Integer> distance) {
		this.distance = distance;
	}
}
