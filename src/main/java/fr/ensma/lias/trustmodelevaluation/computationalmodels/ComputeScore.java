package fr.ensma.lias.trustmodelevaluation.computationalmodels;

import fr.ensma.lias.trustmodelevaluation.engine.ReportEvaluation;

/**
 * @author Mickael BARON
 */
public interface ComputeScore {

	void computeScore(ReportEvaluation report);

	void computeScore(ReportEvaluation report, Parameters pParameters);
}
