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

import java.util.List;

import fr.ensma.lias.trustmodelevaluation.model.Reason;
import fr.ensma.lias.trustmodelevaluation.model.TrustRequirementConstraint;

/**
 * @author Mickael BARON
 */
public class SimulatedTask {

	private List<Reason> reasons;

	private TrustRequirementConstraint constraint;

	private String name;

	private boolean isPositive = false;

	public SimulatedTask(String pName, List<Reason> pReasons, TrustRequirementConstraint pConstraint, boolean pIsPositive) {
		this.reasons= pReasons;
		this.constraint = pConstraint;
		this.name = pName;
		this.isPositive = pIsPositive;
	}

	public List<Reason> getReasons() {
		return this.reasons;
	}

	public TrustRequirementConstraint getConstraint() {
		return this.constraint;
	}

	public String getName() {
		return this.name;
	}

	public boolean isPositive() {
		return this.isPositive;
	}
	
	public String toString() {
		return this.name + " " + this.constraint;
	}
}
