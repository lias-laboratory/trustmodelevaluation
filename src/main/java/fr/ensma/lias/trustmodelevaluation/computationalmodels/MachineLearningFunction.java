package fr.ensma.lias.trustmodelevaluation.computationalmodels;

import java.util.List;

import fr.ensma.lias.trustmodelevaluation.engine.EvaluatedTask;
import fr.ensma.lias.trustmodelevaluation.engine.ReportEvaluation;
import fr.ensma.lias.trustmodelevaluation.exceptions.NotYetImplementedException;

public class MachineLearningFunction implements ComputeScore {

	public static final String SEUIL = "SEUIL";

	@Override
	public void computeScore(ReportEvaluation report) {
		throw new NotYetImplementedException();
	}

	@Override
	public void computeScore(ReportEvaluation report, Parameters pParameters) {
		if (!pParameters.isExistKey(SEUIL)) {
			throw new NotYetImplementedException();
		}
		int seuil = pParameters.getParameterValue(SEUIL);
		int true_positives = 0;
		int false_positives = 0;
		int false_negatives = 0;
		int true_negatives = 0;

		List<EvaluatedTask> evaluatedTasks = report.getEvaluatedTasks();
		for (EvaluatedTask evaluatedTask : evaluatedTasks) {
			if (evaluatedTask.getConstraintEvaluation().isPresent()) {
				if (evaluatedTask.getConstraintEvaluation().get()) {
					if ((Math.abs(
							evaluatedTask.getSimulatedTask().getConstraint().getConstraintValue().getValue()) >= seuil)
							&& evaluatedTask.getComputedValue() >= seuil) {
						true_positives++;
					}
					if ((Math.abs(
							evaluatedTask.getSimulatedTask().getConstraint().getConstraintValue().getValue()) >= seuil)
							&& evaluatedTask.getComputedValue() < seuil) {
						false_positives++;
					}
					if ((Math.abs(
							evaluatedTask.getSimulatedTask().getConstraint().getConstraintValue().getValue()) < seuil)
							&& evaluatedTask.getComputedValue() >= seuil) {
						false_negatives++;
					} else {
						true_negatives++;
					}
				}
			}
		}

		float accuracy = (true_positives + true_negatives)
				/ (true_positives + true_negatives + false_positives + false_negatives);
		float precision = true_positives / (true_positives + false_positives);
		float recall = true_positives / (true_positives + false_negatives);
		float F1_Score = 2 / ((1 / precision) + (1 / recall));

	}
}
