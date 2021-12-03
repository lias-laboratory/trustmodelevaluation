package fr.ensma.lias.trustevaluation.computationalmodels;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;

import fr.ensma.lias.trustevaluation.engine.EvaluatedTask;
import fr.ensma.lias.trustevaluation.engine.ReportEvaluation;

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
