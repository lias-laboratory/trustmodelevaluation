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
