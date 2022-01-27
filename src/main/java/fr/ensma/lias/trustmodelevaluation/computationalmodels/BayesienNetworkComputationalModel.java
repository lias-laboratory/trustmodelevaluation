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
package fr.ensma.lias.trustmodelevaluation.computationalmodels;

import java.util.List;

import fr.ensma.lias.trustmodelevaluation.engine.SimulatedTask;

/**
 * @author Mickael BARON
 */
public class BayesienNetworkComputationalModel implements ComputationalModel {

	@Override
	public int compute(List<SimulatedTask> p, int max, int min) {
		long positive = p.stream().filter(sc -> sc.isPositive()).count();
		long negative = p.stream().filter(sc -> !sc.isPositive()).count();

		long num = positive - negative;
		long den = (positive + negative + 2);

		float value = (float) num / den;

		if (value >= 0) {
			return (int) (value * max);
		} else {
			return (int) (value * Math.abs(min));
		}
	}

	@Override
	public String getComputationalModelName() {
		return "BayesienNetworkComputationalModel";
	}
}