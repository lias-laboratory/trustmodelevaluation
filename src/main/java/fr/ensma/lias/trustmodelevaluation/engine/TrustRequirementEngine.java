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

import fr.ensma.lias.trustmodelevaluation.exceptions.NotYetImplementedException;
import fr.ensma.lias.trustmodelevaluation.model.Decomposition;
import fr.ensma.lias.trustmodelevaluation.model.IterativeTrustRequirementConstraint;
import fr.ensma.lias.trustmodelevaluation.model.PercentageScoreValue;
import fr.ensma.lias.trustmodelevaluation.model.PositiveTask;
import fr.ensma.lias.trustmodelevaluation.model.Task;
import fr.ensma.lias.trustmodelevaluation.model.TrustRequirement;
import fr.ensma.lias.trustmodelevaluation.model.TrustRequirementConstraint;
import fr.ensma.lias.trustmodelevaluation.model.TrustRequirementValue;

/**
 * @author Mickael BARON
 */
public class TrustRequirementEngine {

	private Integer previousValue = null;

	public Scenario eval(TrustRequirement pRequirement) {
		List<SimulatedTask> eval = this.eval(pRequirement.getDescribedBy());
		Scenario currentScenario = new Scenario();
		currentScenario.setSimulatedTasks(eval);
		currentScenario.setScoreElement(pRequirement.getScoreElement());

		return currentScenario;
	}

	protected List<SimulatedTask> eval(Task task) {
		if (task.getDecomposition() == Decomposition.LEAF) {
			List<SimulatedTask> executedAllTasks = new ArrayList<>();

			for (int i = 0; i < task.getIteration(); i++) {
				if (task.getConstraint() instanceof IterativeTrustRequirementConstraint) {
					// Last element.
					if (i == task.getIteration() - 1) {
						executedAllTasks.add(build(task, true));
					} else {
						executedAllTasks.add(build(task, false));
					}
				} else {
					executedAllTasks.add(build(task, true));
				}
			}

			return executedAllTasks;
		} else if (task.getDecomposition() == Decomposition.SEQ) {
			List<SimulatedTask> executedAllTasks = new ArrayList<>();
			List<Task> subTasks = task.getSubTasks();

			for (int i = 0; i < task.getIteration(); i++) {
				for (Task current : subTasks) {
					List<SimulatedTask> executedTasks = eval(current);
					executedAllTasks.addAll(executedTasks);
				}
			}

			return executedAllTasks;
		} else {
			throw new NotYetImplementedException(task.getDecomposition().toString());
		}
	}

	protected SimulatedTask build(Task task, boolean withCause) {
		TrustRequirementConstraint constraint = null;

		if (task.getConstraint() != null) {
			if (withCause) {
				TrustRequirementValue constraintValue = task.getConstraint().getConstraintValue();
				if (constraintValue instanceof PercentageScoreValue) {
					this.previousValue = ((PercentageScoreValue) constraintValue).computeValue(this.previousValue);
				} else {
					this.previousValue = constraintValue.getValue();
				}

				constraint = (TrustRequirementConstraint) task.getConstraint().transform(previousValue);
			}
		}

		return new SimulatedTask(task.getName(), task.getReasons(), constraint, task instanceof PositiveTask);
	}
}
