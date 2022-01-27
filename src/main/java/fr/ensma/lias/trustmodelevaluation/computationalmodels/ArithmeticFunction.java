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

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;

import fr.ensma.lias.trustmodelevaluation.engine.EvaluatedTask;
import fr.ensma.lias.trustmodelevaluation.engine.ReportEvaluation;

/**
 * @author Mickael BARON
 */
public class ArithmeticFunction implements ComputeScore {

	@Override
	public void computeScore(ReportEvaluation pReport) {
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
