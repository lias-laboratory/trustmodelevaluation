/*********************************************************************************
 * This file is part of TME (Trust Model Evaluation) tool.
 * Copyright (C) 2022 LIAS/ISAE-ENSMA and O°Code
 * 
 * TMEDe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TME is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with TME.  If not, see <http://www.gnu.org/licenses/>.
 **********************************************************************************/
package fr.ensma.lias.trustmodelevaluation.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.ensma.lias.trustmodelevaluation.computationalmodels.ComputationalModel;
import fr.ensma.lias.trustmodelevaluation.computationalmodels.ComputeScore;
import fr.ensma.lias.trustmodelevaluation.model.IValueInt;
import fr.ensma.lias.trustmodelevaluation.model.TrustRequirementConstraint;

/**
 * @author Mickael BARON
 */
public class ComputationalModelRecommandationEngine {

	public ReportEvaluation eval(Scenario scenario, ComputationalModel computationalNetwork,
			ComputeScore computeScore) {
		ReportEvaluation report = new ReportEvaluation();
		report.setScoreElement(scenario.getScoreElement());
		report.setComputionalModelName(computationalNetwork.getComputationalModelName());
		
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
			TrustRequirementConstraint constraint = simulatedTask.getConstraint();

			if (constraint != null) {
				boolean eval = constraint.eval(new IValueInt() {
					@Override
					public int getValue() {
						return evaluatedTask.getComputedValue();
					}
				});
				evaluatedTask.setConstraintEvaluation(Optional.of(eval));
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
