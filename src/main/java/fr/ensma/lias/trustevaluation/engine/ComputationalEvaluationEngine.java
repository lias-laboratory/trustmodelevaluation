package fr.ensma.lias.trustevaluation.engine;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;

import fr.ensma.lias.trustevaluation.computationalmodels.ComputationalNetwork;
import fr.ensma.lias.trustevaluation.model.IValueInt;
import fr.ensma.lias.trustevaluation.model.TaskScoreConstraint;

/**
 * @author Mickael BARON
 */
public class ComputationalEvaluationEngine {

	public ReportEvaluation eval(Scenario scenario, ComputationalNetwork computationalNetwork) {
		ReportEvaluation report = new ReportEvaluation();
		report.setScoreElement(scenario.getScoreElement());

		// First step: execute computational model.
		this.executeComputationalModel(scenario.getSimulatedTasks(), computationalNetwork, report);

		// Second step: compute the distances between computational model values and
		// user requirements.
		this.evalConstraint(report);

		// Third step: compute distance.
		this.computeDistance(report);

		// Fourth step: compute final score.
		this.computeFinalScore(report);

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
			ComputationalNetwork computationalNetwork, ReportEvaluation report) {
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

	/**
	 * Compute final score (percentage).
	 * 
	 * @param pReport
	 */
	protected void computeFinalScore(ReportEvaluation pReport) {
		// Compute linear equation.
		int maxDistance = pReport.getScoreElement().getMax() - pReport.getScoreElement().getMin();

		List<Integer> scores = new ArrayList<>();
		for (EvaluatedTask current : pReport.getEvaluatedTasks()) {
			if (current.getConstraintEvaluation().isPresent()) {
				if (current.getConstraintEvaluation().get()) {
					scores.add(
							(int) (-((float) 100 / (float) maxDistance) * current.getDistance().get() + (float) 100));
				} else {
					scores.add(0);
				}
			}
		}

		IntSummaryStatistics summaryStatistics = scores.stream().mapToInt(Integer::intValue).summaryStatistics();
		pReport.setScore((int) summaryStatistics.getAverage());
	}
}
