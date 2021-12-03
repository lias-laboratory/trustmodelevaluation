package fr.ensma.lias.trustevaluation.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.ensma.lias.trustevaluation.computationalmodels.ComputationalModel;
import fr.ensma.lias.trustevaluation.computationalmodels.ComputeScore;
import fr.ensma.lias.trustevaluation.model.IValueInt;
import fr.ensma.lias.trustevaluation.model.TaskScoreConstraint;

/**
 * @author Mickael BARON
 */
public class ComputationalEvaluationEngine {

	public ReportEvaluation eval(Scenario scenario, ComputationalModel computationalNetwork, ComputeScore computeScore) {
		ReportEvaluation report = new ReportEvaluation();
		report.setScoreElement(scenario.getScoreElement());

		// First step: execute computational model.
		this.executeComputationalModel(scenario.getSimulatedTasks(), computationalNetwork, report);

		// Second step: eval constraint.
		this.evalConstraint(report);

		// Third step: compute distance.
		this.computeDistance(report);

		// Fourth step: compute final score.
		computeScore.computeScore(report);

		// Third step: create a report.
		return report;
	}

	/**
	 * Compute computational model values.
	 * 
	 * @param scenario
	 * @param computationalNetwork
	 * @param report
	 */
	protected void executeComputationalModel(List<SimulatedTask> simulatedTasks,
			ComputationalModel computationalNetwork, ReportEvaluation report) {
		List<EvaluatedTask> evaluatedTasks = new ArrayList<>();

		for (int i = 0; i < simulatedTasks.size(); i++) {
			EvaluatedTask evaluatedTask = new EvaluatedTask(simulatedTasks.get(i));
			evaluatedTask.setComputedValue(computationalNetwork.compute(simulatedTasks.subList(0, i + 1),
					report.getScoreElement().getMax(), report.getScoreElement().getMin()));

			evaluatedTasks.add(evaluatedTask);
		}

		report.setEvaluatedTasks(evaluatedTasks);
	}

	/**
	 * Evaluate score constraint expressions according to the values of a
	 * computational model.
	 * 
	 * @param pReport
	 */
	protected void evalConstraint(ReportEvaluation pReport) {
		// Evaluate expression.
		List<EvaluatedTask> evaluatedTasks = pReport.getEvaluatedTasks();
		for (EvaluatedTask evaluatedTask : evaluatedTasks) {
			SimulatedTask simulatedTask = evaluatedTask.getSimulatedTask();
			TaskScoreConstraint constraint = simulatedTask.getConstraint();

			if (constraint != null) {
				boolean eval = constraint.eval(new IValueInt() {
					@Override
					public int getValue() {
						return evaluatedTask.getComputedValue();
					}
				});
				evaluatedTask.setConstraintEvaluation(Optional.of(eval));

				// Compute distance.
				if (eval) {
					evaluatedTask.setDistance(
							Optional.of(Math.abs(simulatedTask.getConstraint().getConstraintValue().getValue()
									- evaluatedTask.getComputedValue())));
				}
			}
		}
	}

	/**
	 * Compute the distances between score constraint values and computational
	 * values.
	 * 
	 * @param pReport
	 */
	protected void computeDistance(ReportEvaluation pReport) {
		List<EvaluatedTask> evaluatedTasks = pReport.getEvaluatedTasks();
		for (EvaluatedTask evaluatedTask : evaluatedTasks) {
			if (evaluatedTask.getConstraintEvaluation().isPresent()) {
				if (evaluatedTask.getConstraintEvaluation().get()) {
					evaluatedTask.setDistance(Optional.of(
							Math.abs(evaluatedTask.getSimulatedTask().getConstraint().getConstraintValue().getValue()
									- evaluatedTask.getComputedValue())));
				}
			}
		}
	}
}
