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
